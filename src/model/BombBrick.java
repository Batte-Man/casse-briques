package model;

import gui.UIConfig;
import metier.GameEngine;
import metier.Updatable;

import java.awt.*;
import java.util.List;

public class BombBrick extends Brick {
    public BombBrick(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void onCollide(GameEngine engine, Ball ball) {
        if (isDestroyed()) return;
        destroy();
        engine.remove(this);

        List<Updatable> list = engine.getUpdatables();
        double hPad = GameConfig.BRICK_H_PADDING;
        double vPad = GameConfig.BRICK_V_PADDING;
        int cols = GameConfig.BRICK_COLUMNS;
        double totalHPad = (cols + 1) * hPad;
        double tileW = (GameConfig.WIDTH - totalHPad) / cols;
        double hSpacing = tileW + hPad;
        double tileH = GameConfig.BRICK_HEIGHT;
        double vSpacing = tileH + vPad;
        double epsilon = 0.01;  // tolérance adaptative

        for (Updatable u : list) {
            if (u instanceof Brick other && !other.isDestroyed()) {
                double dx = Math.abs(other.getX() - getX());
                double dy = Math.abs(other.getY() - getY());
                boolean adjacentH = Math.abs(dx - hSpacing) < epsilon && dy < epsilon;
                boolean adjacentV = Math.abs(dy - vSpacing) < epsilon && dx < epsilon;
                if (adjacentH || adjacentV) {
                    other.destroy();
                    engine.remove(other);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDestroyed()) return;
        Color old = g.getColor();
        g.setColor(UIConfig.DANGER_COLOR);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
        g.setColor(old);
    }
}
