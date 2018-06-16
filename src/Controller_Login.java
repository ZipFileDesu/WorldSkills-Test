import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.ParseException;

public class Controller_Login {
    GetTime gt = new GetTime();

    @FXML
    private Label TimeLabel;

    @FXML
    public void initialize() throws ParseException {

    System.out.print(gt.GetTime());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

}
