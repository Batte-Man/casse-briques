package metier;

import model.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameEngine {
    private final ICollisionService collisionService;
    private final IScoreService scoreService;

    private final List<Updatable> updatables = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final List<GameOverListener> gameOverListeners = new ArrayList<>();

    private GameState state = GameState.READY;
    private Difficulty difficulty = Difficulty.MEDIUM;
    private Paddle paddle;
    private Ball ball;
    private int finalPoints;

    public GameEngine(ICollisionService colSvc, IScoreService scoreSvc) {
        this.collisionService = colSvc;
        this.scoreService = scoreSvc;
    }

    private void initializeBoardFor(Difficulty diff) {
        updatables.clear();
        drawables.clear();

        double paddleX = GameConfig.WIDTH / 2.0 - GameConfig.PADDLE_WIDTH / 2.0;
        double paddleY = GameConfig.HEIGHT - GameConfig.PADDLE_HEIGHT - 10;
        paddle = new Paddle(paddleX, paddleY,
                GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT);
        register(paddle);

        double speed = GameConfig.getBallSpeed(diff);
        double ballX  = paddle.getX() + (paddle.getWidth() - GameConfig.BALL_SIZE) / 2.0;
        double ballY  = paddle.getY() - GameConfig.BALL_SIZE - 5;
        ball = new Ball(ballX, ballY,
                GameConfig.BALL_SIZE,
                new Velocity(speed, -speed));
        register(ball);

        int cols   = GameConfig.BRICK_COLUMNS;
        int rows   = GameConfig.getBrickRows(diff);
        double hPad = GameConfig.BRICK_H_PADDING;
        double vPad = GameConfig.BRICK_V_PADDING;
        double totalHPad = (cols + 1) * hPad;
        double brickW = (GameConfig.WIDTH - totalHPad) / cols;
        double brickH = GameConfig.BRICK_HEIGHT;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = hPad + c * (brickW + hPad);
                double y = GameConfig.BRICK_OFFSET_TOP + r * (brickH + vPad);
                double rand = Math.random();
                Brick brick;
                if (rand < 0.025) {
                    brick = new BombBrick(x, y, (int) brickW, (int) brickH);
                } else if (rand < 0.05) {
                    brick = new MultiBallBrick(x, y, (int) brickW, (int) brickH);
                } else {
                    brick = new SimpleBrick(x, y, (int) brickW, (int) brickH);
                }
                register(brick);
            }
        }
    }

    public void startGame(Difficulty diff) {
        this.difficulty = diff;
        initializeBoardFor(diff);
        state = GameState.RUNNING;
    }

    public void pauseGame() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
        }
    }

    public void resumeGame() {
        if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
        }
    }

    public void endGame() {
        if (state == GameState.GAME_OVER) return;
        state = GameState.GAME_OVER;
        finalPoints = calculateScore();
        for (GameOverListener listener : gameOverListeners) {
            listener.onGameOver(finalPoints);
        }
    }

    public GameState getState() {
        return state;
    }

    public void addGameOverListener(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    public void register(Updatable u) {
        updatables.add(u);
        if (u instanceof Drawable d) drawables.add(d);
    }

    public void update(long dt) {
        if (state != GameState.RUNNING) return;
        for (Updatable u : updatables) u.update(dt);
        handleCollisions();
    }

    private void handleCollisions() {
        List<Updatable> snapshot = new ArrayList<>(updatables);
        List<Ball> newBalls = new ArrayList<>();

        for (Updatable u : snapshot) {
            if (u instanceof Ball b) {
                // balle tombée
                if (b.getY() + b.getSize() >= GameConfig.HEIGHT) {
                    endGame();
                    return;
                }
                collisionService.handleWallCollision(this, b);
                collisionService.handlePaddleCollision(this, b, paddle);
                for (Updatable o : snapshot) {
                    if (o instanceof Brick brick && !brick.isDestroyed()) {
                        if (b.getBounds().intersects(brick.getBounds())) {
                            if (brick instanceof MultiBallBrick mb) {
                                newBalls.add(mb.createExtraBall(b));
                            }
                            collisionService.handleBrickCollision(this, brick, b);
                            break;
                        }
                    }
                }
            }
        }

        Iterator<Updatable> it = updatables.iterator();
        while (it.hasNext()) {
            Updatable u = it.next();
            if (u instanceof Brick br && br.isDestroyed()) {
                it.remove(); drawables.remove(u);
            }
        }
        for (Ball extra : newBalls) register(extra);
    }

    private int calculateScore() {
        int totalBricks = GameConfig.getBrickRows(difficulty) * GameConfig.BRICK_COLUMNS;
        int remaining = 0;
        for (Updatable u : updatables) {
            if (u instanceof Brick) remaining++;
        }
        int destroyed = totalBricks - remaining;
        return destroyed * 10;
    }

    public void movePaddle(int direction) {
        if (paddle == null) return;
        double dx = direction * GameConfig.getPaddleSpeed(difficulty);
        double newX = Math.max(0,
                Math.min(GameConfig.WIDTH - paddle.getWidth(), paddle.getX() + dx)
        );
        paddle.setX(newX);
    }

    public void render(Graphics g) {
        for (Drawable d : drawables) d.draw(g);
    }

    public List<Updatable> getUpdatables() {
        return List.copyOf(updatables);
    }

    public void remove(Updatable u) {
        updatables.remove(u);
        drawables.remove(u);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
