package team.ocean;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
