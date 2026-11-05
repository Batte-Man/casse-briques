package gui;

import metier.GameEngine;
import metier.GameState;
import model.Ball;
import model.GameConfig;
import model.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine engine;
    private GameController controller;
    private Thread loop;

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

    private void startLoop() {
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
        } catch (InterruptedException ignored) {
        }
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
