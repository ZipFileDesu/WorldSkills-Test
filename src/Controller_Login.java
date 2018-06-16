import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.ParseException;

public class Controller_Login {
    /**
     * This is gt class object, which I call for getting time for filling TimeLabel
     * @see GetTime
     */
    GetTime gt = new GetTime();

    /**
     * This is Open class object, which open new window
     * @see OpenNewWindow
     */
    OpenNewWindow Open = new OpenNewWindow();

    /**
     * This is label TimeLabel, which contains the remaining time
     */
    @FXML
    private Label TimeLabel;

    /**
     * This is button Back, which returns us on previous form
     */
    @FXML
    private Button Back;

    /**
     * This is a method initialize which start when form Login was openned. <br>
     * If we press any button, we show alert message. <br>
     * Also here we create new thread to show time in real time <br>
     * @throws ParseException If something goes wrong
     */
    @FXML
    public void initialize() throws ParseException {

        Back.setOnAction(event -> {
            Back.getScene().getWindow().hide();
            try {
                Open.OpenWindow("MainMenu.fxml", "WSR 2018");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //This is thread where I every 1 seconds set new text of labet TimeLabel
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    TimeLabel.setText(String.valueOf(gt.GetTime() / (24 * 60 * 60 * 1000)) +
                                            " дней " + String.valueOf(gt.GetTime() / (60 * 60 * 1000)) + " часов и " +
                                            String.valueOf(gt.GetTime() / (24 * 60 * 60 * 1000)) + " минут до начала чемпионата!");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
