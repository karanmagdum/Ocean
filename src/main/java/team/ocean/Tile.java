package team.ocean;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents different tile of the game board.
 * Provides methods to create custom tiles.
 */
public class Tile extends Rectangle {

    /**
     * Constructs a new Tile with the specified coordinates and color.
     *
     * @param x     The x-coordinate of the tile.
     * @param y     The y-coordinate of the tile.
     * @param color The color of the tile.
     */
    public Tile(int x, int y, Color color){
        setWidth(x);
        setHeight(y);
        setFill(color);
        setStroke(Color.LIGHTCYAN);
    }
}
