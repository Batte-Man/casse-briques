package metier;

import model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final ICollisionService collisionService;
    private final IScoreService scoreService;
    private final List<Updatable> updatables = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final List<GameOverListener> gameOverListeners = new ArrayList<>();
    private final List<VictoryListener> victoryListeners = new ArrayList<>();

    private GameState state = GameState.READY;
    private Difficulty difficulty = Difficulty.MEDIUM;
    private Paddle paddle;
    private Ball ball;
    private int finalPoints;

    public GameEngine(ICollisionService colSvc, IScoreService scoreSvc) {
        this.collisionService = colSvc;
        this.scoreService = scoreSvc;
    }

    public void initGame(Difficulty diff) {
        this.difficulty = diff;
        initializeBoardFor(diff);
        state = GameState.READY;
    }

    public void play() {
        if (state == GameState.READY || state == GameState.PAUSED) {
            state = GameState.RUNNING;
        }
    }

    public void pauseGame() {
        if (state == GameState.RUNNING) state = GameState.PAUSED;
    }

    public void resumeGame() {
        if (state == GameState.PAUSED) state = GameState.RUNNING;
    }

    public GameState getState() {
        return state;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void movePaddle(int direction) {
        if (paddle == null) return;
        double dx = direction * GameConfig.getPaddleSpeed(difficulty);
        double newX = Math.max(0,
                Math.min(GameConfig.WIDTH - paddle.getWidth(), paddle.getX() + dx));
        paddle.setX(newX);
    }

    public void addGameOverListener(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    public void addVictoryListener(VictoryListener listener) {
        victoryListeners.add(listener);
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

    public void render(Graphics g) {
        for (Drawable d : drawables) d.draw(g);
    }

    private void endGame() {
        if (state == GameState.GAME_OVER) return;
        state = GameState.GAME_OVER;
        finalPoints = calculateScore();
        for (GameOverListener l : gameOverListeners) {
            l.onGameOver(finalPoints);
        }
    }

    public List<Updatable> getUpdatables() {
        return List.copyOf(updatables);
    }

    public void remove(Updatable u) {
        updatables.remove(u);
        if (u instanceof Drawable) drawables.remove(u);
    }

    private int calculateScore() {
        int total = GameConfig.BRICK_COLUMNS * GameConfig.getBrickRows(difficulty);
        int remaining = 0;
        for (Updatable u : updatables) {
            if (u instanceof Brick br && !br.isDestroyed()) remaining++;
        }
        return (total - remaining) * 10;
    }

    private void handleCollisions() {
        List<Updatable> snapshot = new ArrayList<>(updatables);
        List<Ball> newBalls = new ArrayList<>();

        for (Updatable u : snapshot) {
            if (u instanceof Ball b) {
                if (b.getY() + b.getSize() >= GameConfig.HEIGHT) {
                    // Balle perdue : on la retire. La partie ne se termine que
                    // lorsqu'il ne reste plus aucune balle (cas du multi-balles).
                    remove(b);
                    continue;
                }
                collisionService.handleWallCollision(this, b);
                collisionService.handlePaddleCollision(this, b, paddle);
                for (Updatable o : snapshot) {
                    if (o instanceof Brick brick && !brick.isDestroyed() &&
                            b.getBounds().intersects(brick.getBounds())) {
                        if (brick instanceof MultiBallBrick mb) newBalls.add(mb.createExtraBall(b));
                        collisionService.handleBrickCollision(this, brick, b);
                        break;
                    }
                }
            }
        }

        for (Updatable u : getUpdatables()) {
            if (u instanceof Brick br && br.isDestroyed()) {
                remove(br);
            }
        }

        boolean anyBall = !newBalls.isEmpty()
                || getUpdatables().stream().anyMatch(u -> u instanceof Ball);
        if (!anyBall) {
            endGame();
            return;
        }

        boolean anyBricks = getUpdatables().stream().anyMatch(u -> u instanceof Brick);
        if (!anyBricks) {
            state = GameState.GAME_OVER;
            finalPoints = calculateScore();
            for (VictoryListener v : victoryListeners) {
                v.onWin(finalPoints);
            }
            return;
        }

        for (Ball extra : newBalls) register(extra);
    }

    private void initializeBoardFor(Difficulty diff) {
        updatables.clear();
        drawables.clear();
        double px = (GameConfig.WIDTH - GameConfig.PADDLE_WIDTH) / 2.0;
        double py = GameConfig.HEIGHT - GameConfig.PADDLE_HEIGHT - 10;
        paddle = new Paddle(px, py, GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT);
        register(paddle);
        double speed = GameConfig.getBallSpeed(diff);
        double bx = paddle.getX() + (paddle.getWidth() - GameConfig.BALL_SIZE) / 2.0;
        double by = paddle.getY() - GameConfig.BALL_SIZE - 2;
        ball = new Ball(bx, by, GameConfig.BALL_SIZE, new Velocity(speed, -speed));
        register(ball);
        int cols = GameConfig.BRICK_COLUMNS;
        int rows = GameConfig.getBrickRows(diff);
        double hPad = GameConfig.BRICK_H_PADDING, vPad = GameConfig.BRICK_V_PADDING;
        double totalPad = (cols + 1) * hPad;
        double bw = (GameConfig.WIDTH - totalPad) / cols, bh = GameConfig.BRICK_HEIGHT;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = hPad + c * (bw + hPad);
                double y = GameConfig.BRICK_OFFSET_TOP + r * (bh + vPad);
                Brick brick = Math.random() < 0.025 ? new MultiBallBrick(x, y, (int) bw, (int) bh)
                        : Math.random() < 0.05 ? new BombBrick(x, y, (int) bw, (int) bh)
                        : new SimpleBrick(x, y, (int) bw, (int) bh);
                register(brick);
            }
        }
    }

}
