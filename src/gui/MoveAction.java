package gui;

import metier.GameEngine;
import metier.GameState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction {
    private final GameEngine engine;
    private final int dir;

    public MoveAction(GameEngine engine, int direction) {
        this.engine = engine;
        this.dir = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (engine.getState() == GameState.RUNNING) {
            engine.movePaddle(dir);
        }
    }
}
