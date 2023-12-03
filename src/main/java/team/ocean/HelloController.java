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

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ProgressBar progressBar = new ProgressBar(0);
    private static final int TOTAL_PROGRESS = 100;
    private static final int SLEEP_TIME = 50; // Sleep time in milliseconds for each step

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

    private void openGame() {
        try {
            PlayerController obj = new PlayerController();
            obj.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateProgressBar(ActionEvent event) {
        double currentProgress = progressBar.getProgress();
        progressBar.setProgress(currentProgress + (1.0 / TOTAL_PROGRESS));
    }
}
