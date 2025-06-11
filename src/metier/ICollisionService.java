package metier;

import model.Ball;
import model.Brick;
import model.Paddle;

public interface ICollisionService {
    void handleWallCollision(GameEngine engine, Ball ball);
    void handlePaddleCollision(GameEngine engine, Ball ball, Paddle paddle);
    void handleBrickCollision(GameEngine engine, Brick brick, Ball ball);
}
