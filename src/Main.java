import data.ScoreRepository;
import data.TextFileScoreRepository;
import gui.*;
import metier.*;
import javax.swing.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScoreRepository repo   = new TextFileScoreRepository(Paths.get("scores.txt"));
            IScoreService    scoreService = new ScoreService(repo);
            ICollisionService colSvc   = new CollisionService();
            GameEngine       engine   = new GameEngine(colSvc, scoreService);

            ScreenManager screens  = new ScreenManager();
            ScreenManagerHolder.init(screens);
            GamePanel panel    = new GamePanel(engine);
            GameController controller = new GameController(screens, engine, panel, scoreService);
            engine.addGameOverListener(controller::onGameOver);

            screens.register("menu", new MenuScreen(screens, controller));
            screens.register("game", new GameScreen(screens, panel));
            screens.register("pause", new PauseScreen(screens, controller));
            screens.register("gameover", new GameOverScreen(screens, controller));
            screens.register("scores",   new ScoresScreen(screens, scoreService, controller));

            GameFrame frame = new GameFrame(screens);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            screens.show("menu");
        });
    }
}
