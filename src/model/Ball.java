package model;

import metier.Drawable;
import metier.Updatable;
import java.awt.*;

public class Ball implements Updatable, Drawable {
    private double x, y;
    private final int size;
    private Velocity velocity;

    public Ball(double x, double y, int size, Velocity velocity) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocity = velocity;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getSize() { return size; }
    public Velocity getVelocity() { return velocity; }
    public void setVelocity(Velocity v) { this.velocity = v; }

    @Override
    public void update(long dt) {
        x += velocity.dx() * dt;
        y += velocity.dy() * dt;
    }

    @Override
    public void draw(Graphics g) {
        Color old = g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval((int)x, (int)y, size, size);
        g.setColor(old);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }

}