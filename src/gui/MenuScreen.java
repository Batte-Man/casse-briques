package gui;

import metier.Difficulty;
import javax.swing.*;
import java.awt.*;

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
        panel.setBackground(UIConfig.BG_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("Casse-Briques");
        title.setFont(UIConfig.TITLE_FONT.deriveFont(48f));
        title.setForeground(UIConfig.FG_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(40));

        combo = new JComboBox<>(Difficulty.values());
        combo.setFont(UIConfig.BUTTON_FONT);
        combo.setMaximumSize(new Dimension(200, 40));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(combo);

        panel.add(Box.createVerticalStrut(20));

        startButton = new JButton("Start");
        startButton.setFont(UIConfig.BUTTON_FONT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 50));
        panel.add(startButton);

        panel.add(Box.createVerticalStrut(10));

        exitButton = new JButton("Quitter");
        exitButton.setFont(UIConfig.BUTTON_FONT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 50));
        panel.add(exitButton);

        panel.add(Box.createVerticalStrut(10));

        scoresButton = new JButton("Scores");
        scoresButton.setFont(UIConfig.BUTTON_FONT);
        scoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresButton.setMaximumSize(new Dimension(200, 50));
        panel.add(scoresButton);

    }

    @Override
    protected void initListeners() {
        startButton.addActionListener(e -> controller.onStart((Difficulty) combo.getSelectedItem()));
        exitButton.addActionListener(e -> System.exit(0));
        scoresButton.addActionListener(e -> controller.onViewScores());

    }
}
