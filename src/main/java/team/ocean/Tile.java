package team.ocean;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(int x, int y, Color color){
        setWidth(x);
        setHeight(y);
        setFill(color);
        setStroke(Color.LIGHTCYAN);
    }
}
