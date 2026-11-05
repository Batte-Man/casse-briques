package data;

import model.ScoreEntry;

import java.util.List;

public interface ScoreRepository {

    List<ScoreEntry> findAll() throws DataAccessException;

    void save(ScoreEntry entry) throws DataAccessException;

}