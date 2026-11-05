package model;

import metier.Drawable;
import metier.GameEngine;
import metier.Updatable;

import java.awt.*;

public abstract class Brick implements Updatable, Drawable {
    protected final double x, y;
    protected final int width, height;
    private boolean destroyed;

    public Brick(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    protected void destroy() {
        destroyed = true;
    }

    @Override
    public void update(long dt) {
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.fillRect((int) x, (int) y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void onCollide(GameEngine engine, Ball ball);
}
