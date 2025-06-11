package gui;

import metier.IScoreService;
import metier.ServiceException;
import model.ScoreEntry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ScoresScreen extends AbstractScreen {
    private final IScoreService scoreService;
    private final GameController controller;

    public ScoresScreen(ScreenManager screens, IScoreService scoreService, GameController controller) {
        super(screens);
        this.scoreService = scoreService;
        this.controller   = controller;
        initComponents();
        initListeners();
    }

    @Override
    protected void initComponents() {
        panel.setBackground(UIConfig.BG_COLOR);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Classement des scores", SwingConstants.CENTER);
        title.setFont(UIConfig.TITLE_FONT);
        title.setForeground(UIConfig.FG_COLOR);
        panel.add(title, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            List<ScoreEntry> scores = new ArrayList<>(scoreService.getAllScores());
            scores.sort((a, b) -> Integer.compare(b.points(), a.points()));
            for (ScoreEntry entry : scores) {
                model.addElement(entry.player() + " : " + entry.points());
            }
        } catch (ServiceException e) {
            model.addElement("Erreur chargement des scores");
        }

        JList<String> list = new JList<>(model);
        list.setFont(UIConfig.BUTTON_FONT);
        list.setBackground(UIConfig.BG_COLOR);
        list.setForeground(UIConfig.FG_COLOR);
        JScrollPane scroll = new JScrollPane(list);
        panel.add(scroll, BorderLayout.CENTER);

        JButton back = new JButton("Retour");
        back.setFont(UIConfig.BUTTON_FONT);
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(back);
        panel.add(btnPanel, BorderLayout.SOUTH);

        back.addActionListener(e -> screens.show("menu"));
    }

    @Override
    protected void initListeners() {}
}
