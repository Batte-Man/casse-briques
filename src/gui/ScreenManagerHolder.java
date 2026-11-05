package gui;

public final class ScreenManagerHolder {
    private static ScreenManager instance;

    private ScreenManagerHolder() {
    }

    public static void init(ScreenManager screens) {
        instance = screens;
    }

    public static ScreenManager get() {
        return instance;
    }
}
