package gui;

import javax.swing.*;
import java.awt.*;

public class PauseScreen extends AbstractScreen {
    private final GameController controller;
    private JButton resumeButton;

    public PauseScreen(ScreenManager screens, GameController controller) {
        super(screens);
        this.controller = controller;
        initComponents();
        initListeners();
    }

    @Override
    protected void initComponents() {
        panel.setBackground(UIConfig.BG_COLOR);
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Pause", SwingConstants.CENTER);
        label.setFont(UIConfig.TITLE_FONT.deriveFont(48f));
        label.setForeground(UIConfig.FG_COLOR);
        panel.add(label, BorderLayout.CENTER);

        resumeButton = new JButton("Reprendre");
        resumeButton.setFont(UIConfig.BUTTON_FONT);
        panel.add(resumeButton, BorderLayout.SOUTH);
    }

    @Override
    protected void initListeners() {
        resumeButton.addActionListener(e -> controller.onResume());
    }
}
