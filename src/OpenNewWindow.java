import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is a OpenNewWindow class, which open new window and set his title
 * @author ZipFileDesu
 */

public class OpenNewWindow {

    /**
     * This method get URL and name title and open new window
     * @param window URL of new window
     * @param title Title name of new window
     * @throws IOException If file is not found
     */
    public void OpenWindow(String window, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,790,590));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }
}
