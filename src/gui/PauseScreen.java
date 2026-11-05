package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        panel.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Pause", SwingConstants.CENTER);
        label.setFont(UIConfig.TITLE_FONT.deriveFont(48f));
        label.setForeground(UIConfig.FG_COLOR_PRIMARY);
        panel.add(label, BorderLayout.CENTER);

        resumeButton = createStyledButton("Reprendre");
        panel.add(resumeButton, BorderLayout.SOUTH);
    }

    @Override
    protected void initListeners() {
        resumeButton.addActionListener(e -> controller.onResume());
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
