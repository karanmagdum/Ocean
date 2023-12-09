package team.ocean;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Represents a player in the game.
 * Handles player movements, positions, and scoring.
 */
public class Player {

    /** Enumeration representing possible player movement directions. */
    public enum Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP
    }
    /** The x-coordinate position of the player. */
    private int xPos;
    /** The y-coordinate position of the player. */
    private int yPos;

    /** The size of the player's tile. */
    private int tileSize;

    /** The minimum row limit for the player's movement. */
    private int minRow=1;
    /** The minimum col limit for the player's movement. */
    private int minCol=0;
    /** The maximum row limit for the player's movement. */
    private int maxRow=10;
    /** The maximum column limit for the player's movement. */
    private int maxCol=10;
    /** The dimension of the game board */
    private int dimension = 10;
    /** The player's current score. */
    private int score;

    /**
     * Gets the player's score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Gets the player's score.
     *
     * @param score score to be added to player's current score
     */
    public void addScore(int score) {
        this.score += score;
    }

    /** The player's won status */
    private boolean wonGame = false;

    /**
     * Checks if the player has won the game.
     *
     * @return True if the player has won, false otherwise.
     */
    public boolean ifWonGame() {
        return wonGame;
    }

    /** The player's current direction, initially forward in right */
    private Direction dir = Direction.RIGHT;


    private static int count=0;

    /** The player's shape representation */
    private Circle obj;
    /**player missing the turn */
    private boolean missTurn = false;
    /**Player's name */
    private String name;

    /**
     * Checks if the player missed a turn.
     * @return True if the player missed a turn, false otherwise.
     */
    public boolean ifMissTurn() {
        return missTurn;
    }
    /**
     * Sets the flag indicating whether the player missed a turn.
     * @param missTurn True if the player missed a turn, false otherwise.
     */
    public void setMissTurn(boolean missTurn) {
        this.missTurn = missTurn;
    }

    /**
     * Gets the Player name
     * @return  Player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new player with the specified tile size and name.
     *
     * @param tileSize The size of the player's tile.
     * @param name     The name of the player.
     */
    public Player( int tileSize, int dimension,String  name){
        this.tileSize = tileSize;
        this.name = name;
        obj = new Circle((double) tileSize /2);
        setPosition(-1,0);
        obj.setId("player"+(++count));
        obj.setFill((count%2==0)?Color.BROWN:Color.LIGHTBLUE);
        score = 0;
        this.dimension = dimension;
        maxRow=dimension;
        maxCol=dimension;
    }

    /**
     * Gets the player's graphical representation.
     *
     * @return The player's graphical representation as a Circle.
     */
    public Circle getObj(){
        return  obj;
    }

    /**
     * Sets the player's position to the specified coordinates.
     *
     * @param x The x-coordinate position.
     * @param y The y-coordinate position.
     */
    public void setPosition(int x, int y){
        this.xPos = x;
        this.yPos = y;
        obj.setTranslateX(x*tileSize+ (double) tileSize /2);
        obj.setTranslateY(y*tileSize+ (double) tileSize /2);
    }

    /**
     * Gets the x-coordinate position of the player.
     *
     * @return The x-coordinate position.
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Gets the y-coordinate position of the player.
     *
     * @return The y-coordinate position.
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Updates the player's position based on the number of steps and reference coordinates.
     *
     * @param steps The number of steps to move.
     * @param xRef  The x-coordinate reference of other player.
     * @param yRef  The y-coordinate reference of other player.
     */
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
                maxRow = dimension;
                maxCol = dimension;
            }
        }

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

        if(currentXPos == xRef && currentYPos== yRef){
            Game.showAlert("Alert", getName()+" cannot be moved to other player's position", 3);
            return;
        }
        xPos = currentXPos;
        yPos = currentYPos;
        translatePlayer(xPos, yPos);

    }

    /**
     * Translates the player to the specified coordinates.
     *
     * @param x The x-coordinate position.
     * @param y The y-coordinate position.
     */
    public void translatePlayer(int x, int y){
        TranslateTransition animate = new TranslateTransition(Duration.millis(100),obj);
        animate.setToX(x*tileSize+(double)tileSize/2);
        animate.setToY(y*tileSize+(double)tileSize/2);
        animate.setAutoReverse(false);
        animate.play();
    }
}
