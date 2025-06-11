package metier;

import model.ScoreEntry;
import metier.ServiceException;
import java.util.List;

public interface IScoreService {
    List<ScoreEntry> getAllScores() throws ServiceException;
    void saveScore(ScoreEntry entry) throws ServiceException;
}
