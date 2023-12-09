package team.ocean;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class PlayerControllerTest {

    private PlayerController playerController;

    @Start
    void start(Stage stage) {
        playerController = new PlayerController();
        try {
            playerController.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getMainStage() {
        assertEquals(playerController.getMainStage(), playerController.getMainStage());
    }

    @Test
    void onHelloButtonClick(FxRobot robot) {
        // Arrange
        TextField playerOneTextField = robot.lookup("#playerOne").query();
        TextField playerTwoTextField = robot.lookup("#playerTwo").query();
        TextField dimension = robot.lookup("#dimension").query();
        Button beginButton = robot.lookup("#beginButton").query();

        // Act
        robot.interact(() -> {
            playerOneTextField.setText("Player One");
            playerTwoTextField.setText("Player Two");
            dimension.setText("10");
            beginButton.fire();
        });

        // Allow time for the background thread to finish
        CountDownLatch latch = new CountDownLatch(1);
        robot.interact(() -> {
            try {
                Thread.sleep(500); // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert
        assertEquals(2, PlayerController.names.size());
        assertEquals("Player One", PlayerController.names.get(0));
        assertEquals("Player Two", PlayerController.names.get(1));
        assertEquals(10, PlayerController.customGridLength);
    }
}
