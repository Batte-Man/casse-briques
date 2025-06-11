package model;

import java.time.LocalDateTime;

public record ScoreEntry(
        String player,
        int points,
        LocalDateTime timestamp
) {
}

