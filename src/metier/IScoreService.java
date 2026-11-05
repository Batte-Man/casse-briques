package metier;

import model.ScoreEntry;

import java.util.List;

public interface IScoreService {
    List<ScoreEntry> getAllScores() throws ServiceException;

    void saveScore(ScoreEntry entry) throws ServiceException;
}
