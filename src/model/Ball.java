package model;

import gui.UIConfig;
import metier.Drawable;
import metier.Updatable;

import java.awt.*;

public class Ball implements Updatable, Drawable {
    private final int size;
    private double x, y;
    private Velocity velocity;

    public Ball(double x, double y, int size, Velocity velocity) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocity = velocity;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    @Override
    public void update(long dt) {
        x += velocity.dx() * dt;
        y += velocity.dy() * dt;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(UIConfig.DANGER_COLOR);
        int sx = (int) x, sy = (int) y, s = size;
        g2.fillOval(sx, sy, s, s);

        g2.setColor(UIConfig.ACCENT_COLOR_1.darker());
        g2.setStroke(new BasicStroke(2f));  // épaisseur : 2px
        g2.drawOval(sx, sy, s, s);

        g2.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

}