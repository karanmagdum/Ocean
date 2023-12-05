package team.ocean;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class HighScore {

    public void writeScore(String name, int score){
        String filePath = "HighScore.csv";
        try (
                // Use FileWriter with append mode
                FileWriter fileWriter = new FileWriter(filePath, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter)
        ) {
            String scoreAsString = String.valueOf(score);
            csvWriter.writeNext(new String[]{name, scoreAsString});
            System.out.println("Data has been written to the highscore file successfully.");
            System.out.println("Player Name: " + name + " Score: " + score);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
