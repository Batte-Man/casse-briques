package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverScreen extends AbstractScreen {
    private final GameController controller;
    private int score;
    private JLabel scoreLabel;

    public GameOverScreen(ScreenManager screens, GameController controller) {
        super(screens);
        this.controller = controller;
        initComponents();
    }

    public void setScore(int pts) {
        this.score = pts;
        scoreLabel.setText("Votre score : " + pts);
    }

    @Override
    protected void initComponents() {
        panel.removeAll();
        panel.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Game Over!");
        title.setFont(UIConfig.TITLE_FONT);
        title.setForeground(UIConfig.FG_COLOR_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(50));

        scoreLabel = new JLabel("Votre score : 0");
        scoreLabel.setFont(UIConfig.BUTTON_FONT.deriveFont(20f));
        scoreLabel.setForeground(UIConfig.FG_COLOR_PRIMARY);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoreLabel);

        panel.add(Box.createVerticalStrut(20));

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(200, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setForeground(UIConfig.FG_COLOR_PRIMARY);
        nameField.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.add(nameField);

        panel.add(Box.createVerticalStrut(10));

        JButton saveBtn = createStyledButton("Save");
        panel.add(saveBtn);

        panel.add(Box.createVerticalStrut(10));

        JButton retryBtn = createStyledButton("Retry");
        panel.add(retryBtn);

        panel.add(Box.createVerticalStrut(10));

        JButton menuBtn = createStyledButton("Menu");
        panel.add(menuBtn);

        saveBtn.addActionListener(e -> controller.onSaveScore(nameField.getText(), score));
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
        btn.setBorder(new LineBorder(UIConfig.ACCENT_COLOR_1, 3, true));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 50));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(UIConfig.BG_COLOR_PRIMARY);
                btn.setBackground(UIConfig.FG_COLOR_PRIMARY);
                btn.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(UIConfig.FG_COLOR_PRIMARY);
                btn.setContentAreaFilled(false);
            }
        });

        return btn;
    }

}