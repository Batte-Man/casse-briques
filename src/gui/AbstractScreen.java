package gui;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractScreen implements Screen {
    protected final JPanel panel;
    protected final ScreenManager screens;

    protected AbstractScreen(ScreenManager screens) {
        this.screens = screens;
        this.panel = new JPanel(new BorderLayout());

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void onShow() {
    }

    protected abstract void initComponents();

    protected abstract void initListeners();
}
