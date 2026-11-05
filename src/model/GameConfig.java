package model;

import metier.Difficulty;

public final class GameConfig {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int PADDLE_WIDTH = 160;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BALL_SIZE = 15;
    public static final int BRICK_COLUMNS = 15;
    public static final int BRICK_HEIGHT = 20;
    public static final int BRICK_H_PADDING = 2;
    public static final int BRICK_V_PADDING = 2;
    public static final int BRICK_OFFSET_TOP = 10;
    private GameConfig() {
    }

    public static int getBrickRows(Difficulty level) {
        return switch (level) {
            case EASY -> 5;
            case MEDIUM -> 7;
            case HARD -> 10;
        };
    }

    public static double getBallSpeed(Difficulty level) {
        return switch (level) {
            case EASY -> 0.1;
            case MEDIUM -> 0.18;
            case HARD -> 0.24;
        };
    }

    public static double getPaddleSpeed(Difficulty level) {
        return switch (level) {
            case EASY -> 10;
            case MEDIUM -> 8.5;
            case HARD -> 7.5;
        };
    }

    public static double getBallSpeedIncrement(Difficulty level) {
        return switch (level) {
            case EASY -> 1.01;
            case MEDIUM -> 1.02;
            case HARD -> 1.05;
        };
    }

    public static double getAngleAdjustFactor(Difficulty level) {
        return switch (level) {
            case EASY -> 0.3;
            case MEDIUM -> 0.5;
            case HARD -> 0.8;
        };
    }
}
