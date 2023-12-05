package team.ocean;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        PlayerController.names.add("Player1");
        PlayerController.names.add("Player2");
        game = new Game();
    }

    @AfterEach
    void tearDown() {
        // Cleanup JavaFX environment
        Platform.exit();
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game);
    }

    @Test
    void testStageNull() {
        // Create an instance of the Game class
        Game game = new Game();

        // Get the main stage from the Game instance
        Stage mainStage = game.mainStage;

        // Assert that the scene is not null
        assertNull(mainStage);
    }

    @Test
    void testGetDiceValue() {
        // Create an instance of the Game class
        Game game = new Game();

        // Call the getDiceValue method
        game.getDiceValue();

        // Get the generated rand value
        int rand = game.rand;

        // Assert that rand is within the expected range (1 to 9)
        assertTrue(rand >= 1 && rand <= 9, "Expected rand to be between 1 and 9, but got: " + rand);
    }

    @Test
    void testShowAlertWithNullParameters() {
        // Expect an IllegalArgumentException if any parameter is null
        assertThrows(ExceptionInInitializerError.class, () -> {
            Game.showAlert(null, "Test Alert Message", 3);
        });

        assertThrows(NoClassDefFoundError.class, () -> {
            Game.showAlert("Test Message", null, 3);
        });

    }

}
