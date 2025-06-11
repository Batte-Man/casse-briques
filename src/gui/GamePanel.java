package gui;

import metier.GameEngine;
import metier.GameState;
import metier.Updatable;
import model.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements Runnable, Updatable {
    private final GameEngine engine;
    private Thread loop;
    private GameController controller;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        setBackground(UIConfig.BG_COLOR);
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        engine.register(this);
    }

    public void bindKeys() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new MoveAction(engine, -1));

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new MoveAction(engine, 1));

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "startGame");
        actionMap.put("startGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine.getState() == GameState.READY) {
                    controller.onStart(engine.getDifficulty());
                    startLoop();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke('p'), "togglePause");
        actionMap.put("togglePause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine.getState() == GameState.RUNNING) {
                    engine.pauseGame();
                    ScreenManagerHolder.get().show("pause");
                } else if (engine.getState() == GameState.PAUSED) {
                    engine.resumeGame();
                    ScreenManagerHolder.get().show("game");
                }
            }
        });
    }

    public void startLoop() {
        if (loop != null && loop.isAlive()) {
            loop.interrupt();
        }
        loop = new Thread(this, "GameLoop");
        loop.start();
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (engine.getState() == GameState.RUNNING) {
                    engine.update(16);
                    repaint();
                }
                Thread.sleep(16);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        engine.render(g);
    }

    @Override
    public void update(long dt) {}
}
