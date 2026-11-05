package gui;

import javax.swing.*;
import java.awt.*;

public class VictoryScreen extends AbstractScreen {
    private final GameController controller;
    private int score;
    private JLabel scoreLabel;

    public VictoryScreen(ScreenManager screens, GameController controller) {
        super(screens);
        this.controller = controller;
        initComponents();
    }

    public void setScore(int pts) {
        this.score = pts;
        scoreLabel.setText("Votre score : " + pts);
    }

    @Override
    public void onShow() {
        super.onShow();
        scoreLabel.setText("Votre score : " + score);
    }

    @Override
    protected void initComponents() {
        panel.removeAll();
        panel.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Victoire !");
        title.setFont(UIConfig.TITLE_FONT);
        title.setForeground(UIConfig.ACCENT_COLOR_1);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        scoreLabel = new JLabel("Votre score : 0");
        scoreLabel.setFont(UIConfig.BUTTON_FONT.deriveFont(24f));
        scoreLabel.setForeground(UIConfig.FG_COLOR_PRIMARY);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoreLabel);

        panel.add(Box.createVerticalStrut(30));

        JButton nextBtn = createStyledButton("Suivant");
        JButton retryBtn = createStyledButton("Retry");
        JButton menuBtn = createStyledButton("Menu");

        panel.add(nextBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(retryBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(menuBtn);

        nextBtn.addActionListener(e -> controller.onNextLevel());
        retryBtn.addActionListener(e -> controller.onRetry());
        menuBtn.addActionListener(e -> screens.show("menu"));
    }

    @Override
    protected void initListeners() {
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIConfig.BUTTON_FONT.deriveFont(20f));
        btn.setForeground(UIConfig.FG_COLOR_PRIMARY);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createLineBorder(UIConfig.FG_COLOR_PRIMARY, 3, true));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 50));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(UIConfig.BG_COLOR_PRIMARY);
                btn.setBackground(UIConfig.FG_COLOR_PRIMARY);
                btn.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(UIConfig.FG_COLOR_PRIMARY);
                btn.setContentAreaFilled(false);
            }
        });
        return btn;
    }
}
