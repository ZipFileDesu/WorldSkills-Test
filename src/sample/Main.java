package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author ZipFileDesu
 * This is main class, where I initialize my programm and open window "main menu"
 */
public class Main extends Application {

    /**
     * Here I open window "main menu" and set resolution 1024x768 and set window not resizable
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
        primaryStage.setResizable(false);
        DB_Handler db = new DB_Handler();
        db.getDBConnection();
    }

    public static void main(String[] args){
            launch(args);
    }
}
