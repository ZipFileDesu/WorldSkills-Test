import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is main class, where I initialize my programm and open window "main menu"
 * @author ZipFileDesu
 */
public class Main extends Application {

    /**
     * Here I open window "MainMenu.fxml" and set resolution 800x600 and set window is not resizable
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("WSR 2018");
        primaryStage.setScene(new Scene(root, 790, 590));
        primaryStage.show();
        primaryStage.setResizable(false);
        DB_Handler db = new DB_Handler();
        db.getDBConnection();
    }

    public static void main(String[] args){
            launch(args);
    }
}
