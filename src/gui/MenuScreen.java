package gui;

import metier.Difficulty;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuScreen extends AbstractScreen {
    private final GameController controller;
    private JComboBox<Difficulty> combo;
    private JButton startButton;
    private JButton exitButton;
    private JButton scoresButton;


    public MenuScreen(ScreenManager screens, GameController controller) {
        super(screens);
        this.controller = controller;
        initComponents();
        initListeners();
    }

    @Override
    protected void initComponents() {
        panel.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("Casse-Briques");
        title.setFont(UIConfig.TITLE_FONT.deriveFont(34f));
        title.setForeground(UIConfig.FG_COLOR_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(40));

        startButton = createStyledButton("Démarrer");
        panel.add(startButton);

        panel.add(Box.createVerticalStrut(10));

        scoresButton = createStyledButton("Scores");
        panel.add(scoresButton);

        panel.add(Box.createVerticalStrut(10));

        exitButton = createStyledButton("Quitter");
        panel.add(exitButton);

        panel.add(Box.createVerticalStrut(20));

        combo = createStyledComboBox(Difficulty.values());
        panel.add(combo);


    }

    @Override
    protected void initListeners() {
        startButton.addActionListener(e -> controller.onStart((Difficulty) combo.getSelectedItem()));
        exitButton.addActionListener(e -> System.exit(0));
        scoresButton.addActionListener(e -> controller.onViewScores());

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

    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(UIConfig.BUTTON_FONT.deriveFont(20f));
        combo.setForeground(UIConfig.FG_COLOR_PRIMARY);
        combo.setBackground(UIConfig.BG_COLOR_PRIMARY);
        combo.setMaximumSize(new Dimension(220, 40));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);

        combo.setBorder(new LineBorder(UIConfig.FG_COLOR_SECONDARY, 3, true));

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setFont(UIConfig.BUTTON_FONT.deriveFont(20f));
                lbl.setForeground(isSelected ? UIConfig.BG_COLOR_SECONDARY : UIConfig.FG_COLOR_SECONDARY);
                lbl.setBackground(isSelected ? UIConfig.FG_COLOR_SECONDARY : UIConfig.BG_COLOR_SECONDARY);
                lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return lbl;
            }
        });

        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrow = super.createArrowButton();
                arrow.setBorder(null);
                arrow.setContentAreaFilled(false);
                return arrow;
            }
        });

        return combo;
    }

}
