package team.ocean;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PlayerController {
    private static Stage mainStage2;
    public TextField playerOne;
    public TextField playerTwo;

    public static ArrayList<String> names = new ArrayList<>();

    @FXML
    private Button beginButton;

    public void start(Stage stage) throws IOException {
        mainStage2 = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Player Details");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static Stage getMainStage() {
        return mainStage2;
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        names.add(playerOne.getText());
        names.add(playerTwo.getText());
        System.out.println(names);
        new Thread(() -> {
            Platform.runLater(this::openGame);
        }).start();
        mainStage2.close();
    }


    private void openGame() {
        try {
            Game obj = new Game();
            obj.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
