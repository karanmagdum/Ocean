package team.ocean;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;

public class Player {
    private int xPos;
    private int yPos;
    private int tileSize;
    private int minRow=1;
    private int minCol=0;
    private int maxRow=10;
    private int maxCol=10;

    private int score;

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    private boolean wonGame = false;
    public boolean ifWonGame() {
        return wonGame;
    }
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
        this.tileSize = tileSize;
        this.name = name;
        obj = new Circle((double) tileSize /2);
        setPosition(-1,0);
        obj.setId("player"+(++count));
        obj.setFill((count%2==0)?Color.BROWN:Color.LIGHTGREEN);
        score = 0;
    }

    public Circle getObj(){
        return  obj;
    }
    public void setPosition(int x, int y){
        this.xPos = x;
        this.yPos = y;
        obj.setTranslateX(x*tileSize+ (double) tileSize /2);
        obj.setTranslateY(y*tileSize+ (double) tileSize /2);
    }
    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public void updatePlayerPosition(int steps, int xRef, int yRef){
        int currentXPos = xPos, currentYPos = yPos;
        for(int i=0;i<steps;i++){
            if(minRow == maxRow && minCol == maxCol) {
                wonGame = true;
                return;
            }
            switch(dir) {
                case RIGHT:
                    currentXPos++;
                    break;
                case DOWN:
                    currentYPos++;
                    break;
                case LEFT:
                    currentXPos--;
                    break;
                case UP:
                    currentYPos--;
                    break;
            }
            for(Pair pair : Obstacle.firePositions){
                if(pair.getKey().equals(currentXPos) && pair.getValue().equals(currentYPos))
                    missTurn = true;
            }

            // Toggle direction
            if(dir == Direction.RIGHT && currentXPos == maxCol-1) {
                dir = Direction.DOWN;
                maxCol--;
            }
            else if(dir == Direction.DOWN && currentYPos == maxRow-1) {
                dir = Direction.LEFT;
                maxRow--;
            }
            else if(dir == Direction.LEFT && currentXPos == minCol) {
                dir = Direction.UP;
                minCol++;
            }
            else if(dir == Direction.UP && currentYPos == minRow) {
                dir = Direction.RIGHT;
                minRow++;
            }
        }

        for(Pair pair:Obstacle.pitPositions){
            if(pair.getKey().equals(currentXPos) && pair.getValue().equals(currentYPos)) {
                currentXPos = 0;
                currentYPos = 0;
                dir = Direction.RIGHT;
                minRow = 1;
                minCol = 0;
                maxRow = 10;
                maxCol = 10;
            }
        }

//        System.out.println(Obstacle.spikePositions);
//        System.out.println("x and y"+currentXPos+" "+currentYPos);
        for(Pair pair:Obstacle.spikePositions){
            if(pair.getKey().equals(currentXPos) && pair.getValue().equals(currentYPos)){
                Game.showAlert("Alert", getName() + " needs dice roll more than number of spikes", 3);
                return;
            }
        }

        Pair<Integer, Integer> teleport1 = Obstacle.teleportPositions.get(0);
        Pair<Integer, Integer> teleport2 = Obstacle.teleportPositions.get(1);
        System.out.println(Obstacle.teleportPositions);
        if(teleport1.getKey().equals(currentXPos) && teleport1.getValue().equals(currentYPos))
        {
            if(teleport2.getKey().equals(xRef) && teleport2.getValue().equals(yRef)){
                Game.showAlert("Alert", getName()+" cannot be teleported as another player exists", 3);
                return;
            }
            currentXPos = teleport2.getKey();
            currentYPos = teleport2.getValue();
            Game.showAlert("Alert", getName()+" is teleported", 2);
            dir = Direction.UP;
            maxCol--;
            maxRow--;
            minCol++;

        }
        else if(teleport2.getKey().equals(currentXPos) && teleport2.getValue().equals(currentYPos))
        {
            if(teleport1.getKey().equals(xRef) && teleport1.getValue().equals(yRef)){
                Game.showAlert("Alert", getName()+" cannot be teleported as another player exists", 3);
                return;
            }
            currentXPos = teleport1.getKey();
            currentYPos = teleport1.getValue();
            Game.showAlert("Alert", getName()+" is teleported", 2);
            dir = Direction.RIGHT;
            maxCol++;
            maxRow++;
            minCol--;
        }

        System.out.println("x and y"+currentXPos+" "+currentYPos);


        if(currentXPos == xRef && currentYPos== yRef){
            Game.showAlert("Alert", getName()+" cannot be moved to other player's position", 3);
            return;
        }
        xPos = currentXPos;
        yPos = currentYPos;
        translatePlayer(xPos, yPos);

    }
    public void translatePlayer(int x, int y){
        TranslateTransition animate = new TranslateTransition(Duration.millis(100),obj);
        animate.setToX(x*tileSize+(double)tileSize/2);
        animate.setToY(y*tileSize+(double)tileSize/2);
        animate.setAutoReverse(false);
        animate.play();
    }
}
