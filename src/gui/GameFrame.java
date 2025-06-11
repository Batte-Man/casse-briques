package gui;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(ScreenManager screens) {
        super("Casse Brique");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(screens.getContainer());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
