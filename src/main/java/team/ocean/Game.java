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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Game extends Application {
    private Stage mainStage;
    public static final int TILE_SIZE = 60;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    int rand;
    public Label randResult;
    public boolean player1Turn =false;
    public boolean player2Turn =false;
    Player player1 = new Player(TILE_SIZE, PlayerController.names.get(0));
    Player player2 = new Player( TILE_SIZE, PlayerController.names.get(1));
    public boolean gameStart = false;
    public Button gameButton;
    private Group tileGroup = new Group();

    //Methods
    private Parent createContent() throws URISyntaxException {
        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE*WIDTH+TILE_SIZE, TILE_SIZE*HEIGHT);
        root.getChildren().addAll(tileGroup);

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                Color color = ((i+j)%2==0 ? Color.LIGHTGREY : Color.DIMGRAY);

                Tile tile = new Tile(TILE_SIZE, TILE_SIZE, color);
                tile.setTranslateX(j*TILE_SIZE);
                tile.setTranslateY(i*TILE_SIZE);

                tileGroup.getChildren().add(tile);
                // Calculate the position number for each rectangle
                int positionNumber = i * WIDTH + j + 1;
                System.out.print(positionNumber+" ");
            }
            System.out.println();
        }

        Button button1 = new Button(PlayerController.names.get(0));
        button1.setTranslateX(0);
        button1.setTranslateY(TILE_SIZE*11+ (double) TILE_SIZE /2);
        button1.setOnAction(e-> {
                if(player1Turn){
                    if(player1.ifMissTurn()){
                        player1.setMissTurn(false);
                        showAlert("Alert", player1.getName()+" missed his chance", 3);
                    }
                    else{
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));
                        boolean ifWon = player1.updatePlayerPosition(rand);
                        if(ifWon){
                            showAlert("Congratulations!! ", player2.getName()+" won the game", 5);
                            mainStage.close();
                        }
                        System.out.println("Player1 x:" + player1.getxPos() + " y:" + player1.getyPos());
                        translatePlayer(player1.getxPos(), player1.getyPos(), player1.getObj());
                        for(Pair pair:Obstacle.pitPositions){
                            if(pair.getKey().equals(player1.getxPos()) && pair.getValue().equals(player1.getyPos())){
                                player1.setPosition(0,0);
                                translatePlayer(player1.getxPos(), player1.getyPos(), player1.getObj());
                            }
                        }
                        randResult.setText(String.valueOf(rand));
                    }
                    //test
                    player1Turn=false;
                    player2Turn=true;
                }
        });

        Button button2 = new Button(PlayerController.names.get(1));
        button2.setTranslateX(TILE_SIZE*8);
        button2.setTranslateY(TILE_SIZE*11+ (double) TILE_SIZE /2);
        button2.setOnAction(e->{
                if(player2Turn){
                    if(player2.ifMissTurn()){
                        player2.setMissTurn(false);
                        showAlert("Alert", player1.getName()+" missed his chance", 3);
                    }
                    else{
                        getDiceValue();
                        randResult.setText(String.valueOf(rand));
                        boolean ifWon = player2.updatePlayerPosition(rand);
                        if(ifWon){
                            showAlert("Congratulations!! ", player2.getName()+" won the game", 5);
                            mainStage.close();
                        }
                        translatePlayer(player2.getxPos(), player2.getyPos(), player2.getObj());
                        for(Pair pair:Obstacle.pitPositions){
                            if(pair.getKey().equals(player2.getxPos()) && pair.getValue().equals(player2.getyPos())){
                                player2.setPosition(0,0);
                                translatePlayer(player2.getxPos(), player2.getyPos(), player2.getObj());
                            }
                        }
                        randResult.setText(String.valueOf(rand));
                    }
                    //test
                    player2Turn = false;
                    player1Turn = true;
                }
        });

        gameButton = new Button("Start Game");
        gameButton.setTranslateX(TILE_SIZE*5);
        gameButton.setTranslateY(TILE_SIZE*11+(double) TILE_SIZE/2);
        gameButton.setOnAction(e->{
            if(!gameStart) {
                gameButton.setText("Game started");
                gameStart = true;
                player1Turn = true;
            }
        });

        randResult = new Label("0");
        randResult.setTranslateX(TILE_SIZE*4);
        randResult.setTranslateY(TILE_SIZE*11+(double)TILE_SIZE/2);

        ImageView fire1 = Obstacle.addFire( TILE_SIZE, 1, 1,1);
        ImageView fire2 = Obstacle.addFire( TILE_SIZE, 1, 8,9);
        ImageView spike1 = Obstacle.addSpike( TILE_SIZE, 1, 2,1);
        ImageView spike2 = Obstacle.addSpike( TILE_SIZE, 1, 3,1);
        ImageView teleport1 = Obstacle.addTeleport( TILE_SIZE, 1, 7,1);
        ImageView teleport2 = Obstacle.addTeleport( TILE_SIZE, 1, 6,5);
        ImageView pit1 = Obstacle.addPit( TILE_SIZE, 1, 6,1);
        ImageView pit2 = Obstacle.addPit( TILE_SIZE, 1, 6,9);

        tileGroup.getChildren().addAll(player1.getObj(), player2.getObj(), button1, button2, gameButton, randResult, fire1, fire2, spike1,spike2, teleport1, teleport2, pit1, pit2);

        return root;
    }

    private void getDiceValue(){
        rand = (int)(Math.random()*9+1);
    }
    private void translatePlayer(int x, int y, Circle b){
        TranslateTransition animate = new TranslateTransition(Duration.millis(100),b);
        animate.setToX(x*TILE_SIZE+(double)TILE_SIZE/2);
        animate.setToY(y*TILE_SIZE+(double)TILE_SIZE/2);
        animate.setAutoReverse(false);
        animate.play();
    }

    private void showAlert(String message, String alertMsg, int duration){
        Stage window = new Stage();
        window.setTitle(message);
        window.setMinWidth(250);
        Label label = new Label(alertMsg+"... Timeout in "+duration+" seconds");
        Button button = new Button("close");
        button.setOnAction(e-> window.close());
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
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        mainStage = stage;
        Scene scene = new Scene(createContent(), 800, 800);
        stage.setTitle("Ocean");
        stage.setScene(scene);
        stage.show();
    }
}