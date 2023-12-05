package team.ocean;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TileTest {

    @Test
    void testTileInitialization() {
        int width = 50;
        int height = 30;
        Color color = Color.BLUE;

        Tile tile = new Tile(width, height, color);

        assertNotNull(tile);
        assertEquals(width, tile.getWidth());
        assertEquals(height, tile.getHeight());
        assertEquals(color, tile.getFill());
        assertEquals(Color.LIGHTCYAN, tile.getStroke());
    }
}
