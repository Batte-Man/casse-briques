package model;

import gui.UIConfig;
import metier.Drawable;
import metier.GameEngine;

import java.awt.*;

public class MultiBallBrick extends Brick implements Drawable {
    public MultiBallBrick(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public Ball createExtraBall(Ball original) {
        Velocity v = original.getVelocity().invertY();
        return new Ball(
                original.getX(),
                original.getY(),
                original.getSize(),
                v
        );
    }

    @Override
    public void onCollide(GameEngine engine, Ball ball) {
        if (isDestroyed()) return;
        destroy();
        engine.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        if (isDestroyed()) return;
        Color old = g.getColor();
        g.setColor(UIConfig.ACCENT_COLOR_2);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
        g.setColor(old);
    }
}
