package gui;

import metier.IScoreService;
import metier.ServiceException;
import model.ScoreEntry;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class ScoresScreen extends AbstractScreen {
    private final IScoreService scoreService;
    private final GameController controller;

    public ScoresScreen(ScreenManager screens, IScoreService scoreService, GameController controller) {
        super(screens);
        this.scoreService = scoreService;
        this.controller = controller;
        initComponents();
        initListeners();
    }

    @Override
    protected void initComponents() {
        panel.setBackground(UIConfig.BG_COLOR_PRIMARY);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("scores", SwingConstants.CENTER);
        title.setFont(UIConfig.TITLE_FONT);
        title.setForeground(UIConfig.FG_COLOR_PRIMARY);
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
        list.setBackground(UIConfig.BG_COLOR_SECONDARY);
        list.setForeground(UIConfig.FG_COLOR_SECONDARY);
        JScrollPane scroll = new JScrollPane(list);
        panel.add(scroll, BorderLayout.CENTER);

        JButton back = createStyledButton("Retour");
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(back);
        panel.add(btnPanel, BorderLayout.SOUTH);

        back.addActionListener(e -> screens.show("menu"));


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
