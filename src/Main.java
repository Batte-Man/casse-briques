import data.ScoreRepository;
import data.TextFileScoreRepository;
import gui.*;
import metier.*;
import model.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScoreRepository repo = new TextFileScoreRepository(Paths.get("scores.txt"));
            IScoreService scoreSvc = new ScoreService(repo);
            ICollisionService colSvc = new CollisionService();
            GameEngine engine = new GameEngine(colSvc, scoreSvc);

            ScreenManager screens = new ScreenManager();
            GamePanel panel = new GamePanel(engine);
            GameController controller = new GameController(screens, engine, panel, scoreSvc);
            panel.setController(controller);

            engine.addGameOverListener(controller::onGameOver);
            engine.addVictoryListener(controller::onWin);

            GameOverScreen overScreen = new GameOverScreen(screens, controller);
            controller.setGameOverScreen(overScreen);

            VictoryScreen win = new VictoryScreen(screens, controller);
            controller.setVictoryScreen(win);

            screens.register("victory", win);
            screens.register("menu", new MenuScreen(screens, controller));
            screens.register("game", new GameScreen(screens, panel));
            screens.register("pause", new PauseScreen(screens, controller));
            screens.register("gameover", overScreen);
            screens.register("scores", new ScoresScreen(screens, scoreSvc, controller));

            JFrame frame = new GameFrame(screens);

            JPanel container = screens.getContainer();
            container.setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            screens.show("menu");
        });
    }
}
