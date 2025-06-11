package model;

import metier.GameEngine;
import java.awt.*;

public class SimpleBrick extends Brick {
    public SimpleBrick(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void onCollide(GameEngine engine, Ball ball) {
        destroy();
    }

    @Override
    public void draw(Graphics g) {
        if (isDestroyed()) return;
        Color old = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect((int)x, (int)y, width, height);
        g.setColor(old);
    }

}
