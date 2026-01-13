package com.mastermind.util;

import com.mastermind.model.Game;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileSaver {
    private static final String SAVE_DIR = "parties";

    public static void saveGame(Game game) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = SAVE_DIR + "/partie_" + timestamp + ".json";
        new File(SAVE_DIR).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("{");
            writer.println("  \"date\": \"" + LocalDateTime.now() + "\",");
            writer.println("  \"pegs\": " + game.getPegs() + ",");
            writer.println("  \"colors\": " + game.getColors() + ",");
            writer.println("  \"max_attempts\": " + game.getMaxAttempts() + ",");
            writer.println("  \"won\": " + game.isWon() + ",");
            writer.println("  \"attempts_count\": " + game.getAttemptCount() + ",");
            writer.println("  \"secret\": " + game.getSecret() + ",");
            writer.println("  \"secret_colors\": \"" + game.getSecret().toColorString() + "\"");
            writer.println("}");
            System.out.println("Partie sauvegardée → " + filename);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }
}
