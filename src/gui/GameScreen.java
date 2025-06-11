package gui;

import model.GameConfig;
import java.awt.*;

public class GameScreen extends AbstractScreen {
    private final GamePanel gamePanel;

    public GameScreen(ScreenManager screens, GamePanel panel) {
        super(screens);
        this.gamePanel = panel;

        this.panel.setLayout(new BorderLayout());
        this.panel.add(gamePanel, BorderLayout.CENTER);
        this.panel.setBackground(UIConfig.BG_COLOR);
        this.panel.setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
    }

    @Override
    protected void initComponents() {}

    @Override
    protected void initListeners() {}

    @Override
    public void onShow() {
        gamePanel.bindKeys();
        gamePanel.requestFocusInWindow();
        gamePanel.startLoop();
    }
}
