package team.ocean;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import static team.ocean.Player.count;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(Game.TILE_SIZE, 10, "TestPlayer");
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals("TestPlayer", player.getName());
        assertFalse(player.ifMissTurn());
        assertNotNull(player.getObj());
    }
}
