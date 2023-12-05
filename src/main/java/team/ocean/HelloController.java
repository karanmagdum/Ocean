package team.ocean;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller class for the Hello Application.
 * Handles the logic and behavior associated with the Hello Application's user interface.
 */
public class HelloController {

    /** The label displaying the welcome text. */
    @FXML
    private Label welcomeText;

    /** The progress bar indicating the loading progress. */
    @FXML
    private ProgressBar progressBar = new ProgressBar(0);

    /** The total progress steps. */
    private static final int TOTAL_PROGRESS = 100;

    /** The sleep time in milliseconds for each progress step. */
    private static final int SLEEP_TIME = 50; // Sleep time in milliseconds for each step

    /**
     * Handles the action when the "Hello" button is clicked.
     * Updates the welcome text, initiates progress bar animation, and opens the game.
     *
     * @throws IOException If there is an error opening the game.
     */
    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Starting the game !!");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(SLEEP_TIME), this::updateProgressBar)
        );
        timeline.setCycleCount(TOTAL_PROGRESS);
        timeline.play();

        new Thread(() -> {
            try {
                Thread.sleep(SLEEP_TIME * TOTAL_PROGRESS);
                Platform.runLater(this::openGame);
                Platform.runLater(() -> HelloApplication.getMainStage().close());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    /**
     * Opens the game by starting the PlayerController.
     */
    private void openGame() {
        try {
            PlayerController obj = new PlayerController();
            obj.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the progress bar by incrementing the progress value.
     * @param event The action event triggering the progress update.
     */
    private void updateProgressBar(ActionEvent event) {
        double currentProgress = progressBar.getProgress();
        progressBar.setProgress(currentProgress + (1.0 / TOTAL_PROGRESS));
    }
}
