package metier;

import data.DataAccessException;
import data.ScoreRepository;
import model.ScoreEntry;

import java.util.List;

public class ScoreService implements IScoreService {
    private final ScoreRepository repo;

    public ScoreService(ScoreRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ScoreEntry> getAllScores() throws ServiceException {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("Impossible de charger les scores", e);
        }
    }

    @Override
    public void saveScore(ScoreEntry entry) throws ServiceException {
        try {
            repo.save(entry);
        } catch (DataAccessException e) {
            throw new ServiceException("Impossible d'enregistrer le score", e);
        }
    }
}