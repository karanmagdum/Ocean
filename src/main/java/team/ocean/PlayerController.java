package team.ocean;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controller class for the player details view.
 * Handles player input, initiates the game, and displays high scores.
 */
public class PlayerController {

    /** The main stage for the player details view. */
    private static Stage mainStage2;

    /** TextField for entering the first player's name. */
    public TextField playerOne;

    /** TextField for entering the second player's name. */
    public TextField playerTwo;

    /** Text field for entering the custom dimension */
    @FXML
    public TextField dimension;

    /** Label for displaying high scores. */
    @FXML
    public Label HighScore;

    /** List to store player names. */
    public static ArrayList<String> names = new ArrayList<>();

    /** Button to begin the game. */
    public static int customGridLength = 10;

    /** Button to begin the game. */
    @FXML
    private Button beginButton;

    /**
     * Initializes the player details view.
     * This method is called by JavaFX after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // This method is called by JavaFX after the FXML file has been loaded
        loadAndDisplayHighScores();
    }

    /**
     * Starts the player details view.
     *
     * @param stage The main stage for the player details view.
     * @throws IOException If there is an error loading the FXML file.
     */
    public void start(Stage stage) throws IOException {
        mainStage2 = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Player Details");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Gets the main stage for the player details view.
     *
     * @return The main stage.
     */
    public static Stage getMainStage() {
        return mainStage2;
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        names.add(playerOne.getText());
        names.add(playerTwo.getText());
        int givenDimension = Integer.parseInt(dimension.getText());
        if(givenDimension>10 && givenDimension<=20)
            customGridLength = givenDimension;

        new Thread(() -> {
            Platform.runLater(this::openGame);
        }).start();
        mainStage2.close();
    }

    /**
     * Opens the game view.
     */
    private void openGame() {
        try {
            Game obj = new Game();
            obj.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads and displays high scores from the CSV file.
     */
    private void loadAndDisplayHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("HighScore.csv"))) {
            List<HighScoreEntry> highScores = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new HighScoreEntry(parts[0].replace("\"", ""), Integer.parseInt(parts[1].replace("\"", "")));
                    })
                    .sorted(Comparator.comparingInt(HighScoreEntry::getScore).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            StringBuilder highScoresText = new StringBuilder("High Scores\n");
            for (int i = 0; i < highScores.size(); i++) {
                highScoresText.append(i + 1).append(". ").append(highScores.get(i).toString()).append("\n");
            }

            Platform.runLater(() -> HighScore.setText(highScoresText.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Represents a high score entry with a name and score.
     */
    private static class HighScoreEntry {
        private final String name;
        private final int score;

        public HighScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return name + ": " + score;
        }
    }
}
