package team.ocean;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class Player {
    private int xPos;
    private int yPos;
    private int tileSize;
    private int minRow=1;
    private int minCol=0;
    private int maxRow=10;
    private int maxCol=10;

    enum Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP
    }

    private Direction dir = Direction.RIGHT;
    private static int count=0;
    private Circle obj;
    private boolean missTurn = false;
    private String name;

    public boolean ifMissTurn() {
        return missTurn;
    }

    public void setMissTurn(boolean missTurn) {
        this.missTurn = missTurn;
    }

    public String getName() {
        return name;
    }

    public Player( int tileSize, String name){
        this.xPos = -1;
        this.yPos = 0;
        this.tileSize = tileSize;
        this.name = name;
        obj = new Circle((double) tileSize /2);
        setPosition(this.xPos,this.yPos);
        obj.setId("player"+(++count));
        obj.setFill((count%2==0)?Color.BROWN:Color.LIGHTGREEN);
    }

    public Circle getObj(){
        return  obj;
    }
    public void setPosition(int x, int y){
        obj.setTranslateX(x*tileSize+ (double) tileSize /2);
        obj.setTranslateY(y*tileSize+ (double) tileSize /2);
    }
    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public boolean updatePlayerPosition(int steps){
        for(int i=0;i<steps;i++){
            if(minRow == maxRow && minCol == maxCol)
                return true;
            switch(dir) {
                case RIGHT:
                    xPos++;
                    break;
                case DOWN:
                    yPos++;
                    break;
                case LEFT:
                    xPos--;
                    break;
                case UP:
                    yPos--;
                    break;
            }
            for(Pair pair : Obstacle.firePositions){
                if(pair.getKey().equals(xPos) && pair.getValue().equals(yPos))
                    missTurn = true;
            }
            // Toggle direction
            if(dir == Direction.RIGHT && xPos == maxCol-1) {
                dir = Direction.DOWN;
                maxCol--;
            }
            else if(dir == Direction.DOWN && yPos == maxRow-1) {
                dir = Direction.LEFT;
                maxRow--;
            }
            else if(dir == Direction.LEFT && xPos == minCol) {
                dir = Direction.UP;
                minCol++;
            }
            else if(dir == Direction.UP && yPos == minRow) {
                dir = Direction.RIGHT;
                minRow++;
            }
        }
        return false;
    }
}
