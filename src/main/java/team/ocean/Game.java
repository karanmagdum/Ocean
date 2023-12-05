package team.ocean;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;

import static team.ocean.Player.Direction;

/**
 * The Game class represents the main application for the game.
 * It extends the JavaFX Application class and is responsible for
 * initializing and managing the graphical user interface and the game play.
 */
public class Game extends Application {

    /**
     * The main stage of the application.
     */
    public Stage mainStage;

    /**
     * The size of each game tile.
     */
    public static final int TILE_SIZE = 40;

    /**
     * The height and width of the game board.
     */
    private int dimension  = PlayerController.customGridLength;

    /**
     * The dice roll value from 1 to 9.
     */
    int rand;

    /**
     * The placeholder for showing the random dice roll value.
     */
    public Label randResult;

    /**
     * Flag indicating whether it is player 1's turn.
     */
    public boolean player1Turn = false;

    /**
     * Flag indicating whether it is player 2's turn.
     */
    public boolean player2Turn = false;

    /**
     * Player 1 instance.
     */
    Player player1 = new Player(TILE_SIZE, dimension, PlayerController.names.get(0));

    /**
     * Player 2 instance.
     */
    Player player2 = new Player(TILE_SIZE, dimension, PlayerController.names.get(1));

    /**
     * Flag indicating whether the game has started.
     */
    public boolean gameStart = false;

    /**
     * Button to start the game.
     */
    public Button gameButton;

    /**
     * Group to hold the game tiles.
     */
    private Group tileGroup = new Group();

    private int finalCoordinateX = 0;
    private int finalCoordinateY = 0;

    /**
     * Creates the content for the game scene.
     *
     * @return The root parent node for the game scene.
     * @throws URISyntaxException If there is an issue with resource URIs.
     */
    private Parent createContent() throws URISyntaxException {
        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE * dimension + TILE_SIZE, TILE_SIZE * dimension);
        root.getChildren().addAll(tileGroup);
        findEndCoordinates();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Color color = ((i + j) % 2 == 0 ? Color.LIGHTGREY : Color.DIMGRAY);
                if((i==0 && j==0) || (i==finalCoordinateY && j==finalCoordinateX))
                    color = Color.LIGHTGREEN;
                Tile tile = new Tile(TILE_SIZE, TILE_SIZE, color);
                tile.setTranslateX(j * TILE_SIZE);
                tile.setTranslateY(i * TILE_SIZE);

                tileGroup.getChildren().add(tile);
                // Calculate the position number for each rectangle
                int positionNumber = i * dimension + j + 1;
            }
        }

        Button button1 = new Button(PlayerController.names.get(0));
        button1.setTranslateX(TILE_SIZE * 1);
        button1.setTranslateY(TILE_SIZE * dimension + (double) TILE_SIZE / 2);
        button1.setOnAction(e -> {
            if (player1Turn) {
                if (player1.ifMissTurn()) {
                    player1.setMissTurn(false);
                    showAlert("Alert", player1.getName() + " missed his/her chance", 3);
                } else {
                    getDiceValue();
                    randResult.setText("Rolled Dice : " + String.valueOf(rand));
                    player1.addScore(rand);
                    player1.updatePlayerPosition(rand, player2.getxPos(), player2.getyPos());
                    if (player1.ifWonGame()) {
                        HighScore obj = new HighScore();
                        obj.writeScore(player1.getName(), player1.getScore());
                        showAlert("Congratulations!! ", player1.getName() + " won the game, Well done!!", 5);
                        mainStage.close();
                    }
                }
                player1Turn = false;
                player2Turn = true;
            }
        });

        Button button2 = new Button(PlayerController.names.get(1));
        button2.setTranslateX(TILE_SIZE * 8);
        button2.setTranslateY(TILE_SIZE * dimension + (double) TILE_SIZE / 2);
        button2.setOnAction(e -> {
            if (player2Turn) {
                if (player2.ifMissTurn()) {
                    player2.setMissTurn(false);
                    showAlert("Alert", player2.getName() + " missed his/her chance", 3);
                } else {
                    getDiceValue();
                    player2.addScore(rand);
                    randResult.setText("Rolled Dice : " + String.valueOf(rand));
                    player2.updatePlayerPosition(rand, player1.getxPos(), player1.getyPos());
                    if (player2.ifWonGame()) {
                        HighScore obj = new HighScore();
                        obj.writeScore(player2.getName(), player2.getScore());
                        showAlert("Congratulations!! ", player2.getName() + " won the game, Well done!!", 5);
                        mainStage.close();
                    }
                }
                //test
                player2Turn = true;
                player1Turn = true;
            }
        });

        randResult = new Label("Rolled Dice : ");
        randResult.setTranslateX(TILE_SIZE * 4);
        randResult.setTranslateY(TILE_SIZE * dimension + (double) TILE_SIZE / 2);

        gameButton = new Button("Start Game");
        gameButton.setTranslateX(TILE_SIZE * 4);
        gameButton.setTranslateY(TILE_SIZE * dimension + (double) TILE_SIZE);
        gameButton.setOnAction(e -> {
            if (!gameStart) {
                gameButton.setText("Game started");
                gameStart = true;
                player1Turn = true;
            }
        });

        ImageView fire1 = Obstacle.addFire(TILE_SIZE,  1, 1);
        ImageView fire2 = Obstacle.addFire(TILE_SIZE, 8, 9);
        ImageView fire3 = Obstacle.addFire(TILE_SIZE, 8, 9);
        ImageView spike1 = Obstacle.addSpike(TILE_SIZE,  2, 2);
        ImageView spike2 = Obstacle.addSpike(TILE_SIZE,  3, 2);
        ImageView spike3 = Obstacle.addSpike(TILE_SIZE,  4, 2);
        ImageView spike4 = Obstacle.addSpike(TILE_SIZE,  5, 2);
        ImageView spike5 = Obstacle.addSpike(TILE_SIZE,  7, 8);
        ImageView spike6 = Obstacle.addSpike(TILE_SIZE,  8, 8);
        ImageView spike7 = Obstacle.addSpike(TILE_SIZE,  9, 8);
        ImageView teleport1 = Obstacle.addTeleport(TILE_SIZE, 7, 1);
        ImageView teleport2 = Obstacle.addTeleport(TILE_SIZE, 1, 5);
        ImageView pit1 = Obstacle.addPit(TILE_SIZE,  6, 1);
        ImageView pit2 = Obstacle.addPit(TILE_SIZE,  9, 3);
        ImageView pit3 = Obstacle.addPit(TILE_SIZE, 3, 5);
        ImageView pit4 = Obstacle.addPit(TILE_SIZE, 2, 8);


        tileGroup.getChildren().addAll(button1, button2, gameButton, randResult, fire1, fire2, fire3, spike1, spike2, spike3, spike4, spike5, spike6, spike7, teleport1, teleport2, pit1, pit2, pit3, pit4, player1.getObj(), player2.getObj());

        return root;
    }

    /**
     * Generates a random value for the dice roll.
     */
    public void getDiceValue() {
        rand = (int) (Math.random() * 9 + 1);
    }

    /**
     * Displays a timed alert message.
     *
     * @param message   The title of the alert.
     * @param alertMsg  The content of the alert message.
     * @param duration  The duration (in seconds) for the alert to be displayed.
     */
    public static void showAlert(String message, String alertMsg, int duration) {
        Stage window = new Stage();
        window.setTitle(message);
        window.setMinWidth(250);
        Label label = new Label(alertMsg + "... Timeout in " + duration + " seconds");
        Button button = new Button("close");
        button.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
        PauseTransition wait = new PauseTransition(Duration.seconds(duration));
        wait.setOnFinished((e) -> {
            window.close();
        });
        wait.play();
    }

    public void findEndCoordinates(){
        int minRow=1;
        int maxRow=dimension;
        int minCol=0;
        int maxCol=dimension;
        Player.Direction dir = Direction.RIGHT;
        for(int i=0;i<10000;i++){

            if(minRow == maxRow && minCol == maxCol) {
                break;

            }
            switch(dir) {
                case RIGHT:
                    finalCoordinateY++;
                    break;
                case DOWN:
                    finalCoordinateX++;
                    break;
                case LEFT:
                    finalCoordinateY--;
                    break;
                case UP:
                    finalCoordinateX--;
                    break;
            }

            // Toggle direction
            if(dir == Direction.RIGHT && finalCoordinateY == maxCol-1) {
                dir = Direction.DOWN;
                maxCol--;
            }
            else if(dir == Direction.DOWN && finalCoordinateX == maxRow-1) {
                dir = Direction.LEFT;
                maxRow--;
            }
            else if(dir == Direction.LEFT && finalCoordinateY == minCol) {
                dir = Direction.UP;
                minCol++;
            }
            else if(dir == Direction.UP && finalCoordinateX == minRow) {
                dir = Direction.RIGHT;
                minRow++;
            }

        }
    }

    /**
     * Starts the game application.
     *
     * @param stage The primary stage for this application.
     * @throws IOException        If there is an issue with loading resources.
     * @throws URISyntaxException If there is an issue with resource URIs.
     */
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {

        mainStage = stage;
        Scene scene = new Scene(createContent(), 1080, 1080);
        stage.setTitle("Ocean");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}