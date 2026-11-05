package gui;

import metier.GameEngine;
import metier.GameState;
import model.Ball;
import model.GameConfig;
import model.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private static final int FRAME_MS = 16; // ~60 images/s
    private final GameEngine engine;
    private GameController controller;
    private Timer loop;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        setBackground(UIConfig.BG_COLOR_PRIMARY);
        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
        bindKeys();
        startLoop();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void bindKeys() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        am.put("moveLeft", new MoveAction(engine, -1));
        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        am.put("moveRight", new MoveAction(engine, 1));
        im.put(KeyStroke.getKeyStroke("ENTER"), "play");
        am.put("play", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine.getState() == GameState.READY && controller != null) {
                    controller.onPlay();
                    requestFocusInWindow();
                }
            }
        });
        im.put(KeyStroke.getKeyStroke('p'), "togglePause");
        am.put("togglePause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine.getState() == GameState.RUNNING) {
                    controller.onPause();
                } else if (engine.getState() == GameState.PAUSED) {
                    controller.onResume();
                }
            }
        });
    }

    /**
     * Boucle de jeu basée sur javax.swing.Timer : chaque tick s'exécute sur l'EDT,
     * le même thread que le rendu et le clavier. Avec un Thread séparé, le moteur était
     * modifié pendant que Swing le dessinait, et les changements d'écran (game over,
     * victoire) se faisaient hors de l'EDT, ce que Swing n'autorise pas.
     */
    private void startLoop() {
        loop = new Timer(FRAME_MS, e -> {
            if (engine.getState() == GameState.RUNNING) {
                engine.update(FRAME_MS);
                repaint();
            }
        });
        loop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (engine.getState() == GameState.READY) {
            Paddle p = engine.getPaddle();
            Ball b = engine.getBall();
            b.setX(p.getX() + (p.getWidth() - b.getSize()) / 2.0);
            b.setY(p.getY() - b.getSize() - 2);
        }
        engine.render(g);
    }
}
