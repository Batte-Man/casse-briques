package model;

public record Velocity(double dx, double dy) {
    public Velocity invertY() {
        return new Velocity(dx, -dy);
    }

    public Velocity scale(double factor) {
        return new Velocity(dx * factor, dy * factor);
    }

    public Velocity withDx(double newDx) {
        return new Velocity(newDx, dy);
    }

    public double length() {
        return Math.hypot(dx, dy);
    }

    public Velocity normalize() {
        double len = length();
        return (len == 0) ? this : new Velocity(dx / len, dy / len);
    }
}