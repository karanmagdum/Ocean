package team.ocean;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main application class for Team Ocean's application.
 * Extends JavaFX's Application class and serves as the entry point for the application.
 */
public class HelloApplication extends Application {

    /** The main stage of the application. */
    private static Stage mainStage;

    /**
     * Start method called when the application is launched.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Team Ocean");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Gets the main stage of the application.
     *
     * @return The main stage.
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * The entry point of the application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        /*launch();*/
        Application.launch();
    }
}