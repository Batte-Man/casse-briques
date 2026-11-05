package metier;

import model.*;

public class CollisionService implements ICollisionService {

    @Override
    public void handleWallCollision(GameEngine engine, Ball ball) {
        if (ball.getX() <= 0 || ball.getX() + ball.getSize() >= GameConfig.WIDTH) {
            Velocity v = ball.getVelocity().withDx(-ball.getVelocity().dx());
            ball.setVelocity(v);
        }
        if (ball.getY() <= 0) {
            Velocity v = ball.getVelocity().invertY();
            ball.setVelocity(v);
        }
    }

    @Override
    public void handlePaddleCollision(GameEngine engine, Ball ball, Paddle paddle) {
        if (!ball.getBounds().intersects(paddle.getBounds())) return;

        Velocity v = ball.getVelocity().invertY();

        double norm = computeNormalizedPosition(ball, paddle);
        double factor = GameConfig.getAngleAdjustFactor(engine.getDifficulty());
        v = v.withDx(v.dx() + norm * factor);
        double speed = GameConfig.getBallSpeed(engine.getDifficulty());
        v = v.normalize().scale(speed);

        double minAngle = Math.toRadians(20);
        double minDy = speed * Math.sin(minAngle);
        if (Math.abs(v.dy()) < minDy) {
            double dy = -minDy;
            double dx = Math.copySign(Math.sqrt(speed * speed - dy * dy), v.dx());
            v = new Velocity(dx, dy);
        }

        v = ball.getVelocity().invertY();
        speed = GameConfig.getBallSpeed(engine.getDifficulty());
        v = v.normalize().scale(speed);

        ball.setVelocity(v);
    }

    @Override
    public void handleBrickCollision(GameEngine engine, Brick brick, Ball ball) {
        if (!ball.getBounds().intersects(brick.getBounds())) return;

        brick.onCollide(engine, ball);

        Velocity v = ball.getVelocity().invertY();

        double speed = GameConfig.getBallSpeed(engine.getDifficulty());
        v = v.normalize().scale(speed);

        ball.setVelocity(v);
    }

    private double computeNormalizedPosition(Ball ball, Paddle paddle) {
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
        double ballCenter = ball.getX() + ball.getSize() / 2.0;
        return (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0);
    }
}
