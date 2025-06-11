package gui;

import metier.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MoveAction extends AbstractAction {
    private final GameEngine engine;
    private final int direction;

    public MoveAction(GameEngine engine, int direction) {
        this.engine = engine;
        this.direction = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        engine.movePaddle(direction);
    }
}
