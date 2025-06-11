package gui;

import metier.Difficulty;
import metier.IScoreService;
import metier.ServiceException;
import metier.GameEngine;
import model.ScoreEntry;
import javax.swing.*;
import java.time.LocalDateTime;

public class GameController {
    private final ScreenManager screens;
    private final GameEngine engine;
    private final GamePanel panel;
    private final IScoreService scoreService;
    private Difficulty lastDifficulty;

    public GameController(ScreenManager screens,
                          GameEngine engine,
                          GamePanel panel,
                          IScoreService scoreService) {
        this.screens = screens;
        this.engine = engine;
        this.panel = panel;
        this.scoreService = scoreService;
    }

    public void onStart(Difficulty difficulty) {
        this.lastDifficulty = difficulty;
        engine.startGame(difficulty);
        screens.show("game");
    }

    public void onPause() {
        engine.pauseGame();
        screens.show("pause");
    }

    public void onResume() {
        engine.resumeGame();
        screens.show("game");
    }

    public void onRetry() {
        if (lastDifficulty != null) {
            engine.startGame(lastDifficulty);
            screens.show("game");
        } else {
            screens.show("menu");
        }
    }

    public void onGameOver(int finalPoints) {
        String name = JOptionPane.showInputDialog(
                panel,
                "Entrez votre nom pour enregistrer le score :",
                "Game Over",
                JOptionPane.PLAIN_MESSAGE
        );
        if (name != null && !name.isBlank()) {
            try {
                scoreService.saveScore(new ScoreEntry(name, finalPoints, LocalDateTime.now()));
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Impossible d'enregistrer le score",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        screens.show("scores");
    }


    public void onViewScores() {
        screens.show("scores");
    }
}