package gui;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends AbstractScreen {
    private final GameController controller;
    private JButton retryButton;
    private JButton menuButton;

    public GameOverScreen(ScreenManager screens, GameController controller) {
        super(screens);
        this.controller = controller;
        initComponents();
        initListeners();
    }

    @Override
    protected void initComponents() {
        panel.setBackground(UIConfig.BG_COLOR);
        panel.setLayout(new BorderLayout(0, 20));

        JLabel msg = new JLabel("Game Over", SwingConstants.CENTER);
        msg.setFont(UIConfig.TITLE_FONT);
        msg.setForeground(UIConfig.FG_COLOR);
        panel.add(msg, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        btnPanel.setOpaque(false);

        retryButton = new JButton("Retry");
        retryButton.setFont(UIConfig.BUTTON_FONT);
        btnPanel.add(retryButton);

        menuButton = new JButton("Menu");
        menuButton.setFont(UIConfig.BUTTON_FONT);
        btnPanel.add(menuButton);

        panel.add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void initListeners() {
        retryButton.addActionListener(e -> controller.onRetry());
        // Retour au menu principal
        menuButton.addActionListener(e -> screens.show("menu"));
    }
}