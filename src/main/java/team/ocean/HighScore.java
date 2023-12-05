package team.ocean;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles writing high scores to a CSV file.
 */
public class HighScore {

    /**
     * Writes a player's name and score to the highscore CSV file.
     *
     * @param name  The player's name.
     * @param score The player's score.
     */
    public void writeScore(String name, int score){
        String filePath = "HighScore.csv";
        try (
                FileWriter fileWriter = new FileWriter(filePath, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter)
        ) {
            String scoreAsString = String.valueOf(score);
            csvWriter.writeNext(new String[]{name, scoreAsString});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
