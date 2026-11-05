package gui;

import metier.Difficulty;
import metier.GameEngine;
import metier.IScoreService;
import metier.ServiceException;
import model.ScoreEntry;

import javax.swing.*;
import java.time.LocalDateTime;

public class GameController {
    private final ScreenManager screens;
    private final GameEngine engine;
    private final GamePanel panel;
    private final IScoreService scoreService;
    private GameOverScreen gameOverScreen;
    private Difficulty lastDifficulty;
    private VictoryScreen victoryScreen;

    public GameController(ScreenManager screens,
                          GameEngine engine,
                          GamePanel panel,
                          IScoreService scoreService) {
        this.screens = screens;
        this.engine = engine;
        this.panel = panel;
        this.scoreService = scoreService;
    }

    public void setGameOverScreen(GameOverScreen screen) {
        this.gameOverScreen = screen;
    }

    public void onStart(Difficulty diff) {
        this.lastDifficulty = diff;
        engine.initGame(diff);
        screens.show("game");
    }

    public void onPlay() {
        engine.play();
    }

    public void onPause() {
        engine.pauseGame();
        screens.show("pause");
    }

    public void onResume() {
        engine.resumeGame();
        screens.show("game");
    }

    public void onViewScores() {
        screens.show("scores");
    }

    public void onGameOver(int pts) {
        if (gameOverScreen != null) {
            gameOverScreen.setScore(pts);
            screens.show("gameover");
        }
    }

    public void onSaveScore(String name, int pts) {
        if (name == null || name.isBlank()) {
            JOptionPane.showMessageDialog(panel,
                    "Entrez un nom valide", "Sauvegarde", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            scoreService.saveScore(new ScoreEntry(name, pts, LocalDateTime.now()));
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(panel,
                    "Impossible d'enregistrer le score", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        screens.show("scores");
    }

    public void onWin(int finalPoints) {
        if (victoryScreen != null) {
            victoryScreen.setScore(finalPoints);
            screens.show("victory");
        }
    }

    public void onNextLevel() {
        engine.initGame(engine.getDifficulty());
        screens.show("game");
    }

    public void onRetry() {
        if (lastDifficulty != null) {
            engine.initGame(lastDifficulty);
            screens.show("game");
        } else {
            screens.show("menu");
        }
    }

    public void setVictoryScreen(VictoryScreen vs) {
        this.victoryScreen = vs;
    }

}