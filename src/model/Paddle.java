package model;

import metier.Drawable;
import metier.Updatable;
import java.awt.*;

public class Paddle implements Updatable, Drawable {
    private double x, y;
    private final int width, height;

    public Paddle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public void update(long dt) {}

    @Override
    public void draw(Graphics g) {
        Color old = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, width, height);
        g.setColor(old);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}