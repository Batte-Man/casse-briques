package data;

import model.ScoreEntry;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TextFileScoreRepository implements ScoreRepository {
    private final Path file;

    public TextFileScoreRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<ScoreEntry> findAll() throws DataAccessException {
        List<ScoreEntry> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                scores.add(new ScoreEntry(
                        parts[0],
                        Integer.parseInt(parts[1]),
                        LocalDateTime.parse(parts[2])
                ));
            }
        } catch (IOException | DateTimeParseException | NumberFormatException e) {
            throw new DataAccessException("Erreur lecture scores", e);
        }
        return scores;
    }


    @Override
    public void save(ScoreEntry entry) throws DataAccessException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file.toFile(), true))) {
            writer.write(
                    entry.player() + ";" +
                            entry.points() + ";" +
                            entry.timestamp()
            );
            writer.newLine();
        } catch (IOException e) {
            throw new DataAccessException("Erreur écriture score", e);
        }
    }


}