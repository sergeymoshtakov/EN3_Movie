package ch.fhnw.oop2;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the movie application.
 * It extends the JavaFX Application class and launches the JavaFX application.
 */
public class Main extends Application {
    
    /**
     * The main method of the application.
     * It launches the JavaFX application by calling the launch() method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method of the JavaFX application.
     * It creates an instance of the ApplicationUI class, sets up the primary stage,
     * and displays the user interface.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an exception occurs during the application startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = new ApplicationUI();
        final Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Movie App");
        primaryStage.show();
    }
}
