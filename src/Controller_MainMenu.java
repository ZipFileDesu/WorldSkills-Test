import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

/**
 * This is a controller main menu class, where I initialize methods of window "main menu"
 * @author ZipFileDesu
 */

public class Controller_MainMenu {

    /**
     * This is Open class object, which open new window
     * @see OpenNewWindow
     */
    OpenNewWindow Open = new OpenNewWindow();

    /**
     * This is alert window, which I will call when I press button
     */
    Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * This is gt class object, which I call for getting time for filling TimeLabel
     * @see GetTime
     */
    GetTime gt = new GetTime();

    /**
     * This is label TimeLabel, which contains the remaining time
     */
    @FXML
    private Label TimeLabel;

    /**
     * This is button AboutWS which shows alert message because his for is not ready
     */
    @FXML
    private Button AboutWS;

    /**
     * This is button AboutWSRussia which shows alert message because his for is not ready
     */
    @FXML
    private Button AboutWSRussia;

    /**
     * This is button AboutKR which shows alert message because his for is not ready
     */
    @FXML
    private Button AboutKR;

    /**
     * This is button Login which open new windows with login form
     */
    @FXML
    private Button Login;

    /**
     * This is a method initialize which start when form MainMenu was openned. <br>
     * If we press any button, we show alert message. <br>
     * Also here we create new thread to show time in real time <br>
     * @throws ParseException If something goes wrong
     */
    @FXML
    public void initialize() throws ParseException {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(gt.GetNewDate());
        System.out.println(rightNow.get(Calendar.HOUR));
        alert.setTitle("Warning!");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Эта форма находится в стадии разработки!");

        AboutWS.setOnAction(event -> {
            alert.show();
        });

        AboutWSRussia.setOnAction(event -> {
            alert.show();
        });

        AboutKR.setOnAction(event -> {
            alert.show();
        });

        Login.setOnAction(event -> {
            Login.getScene().getWindow().hide();
            try {
                Open.OpenWindow("Login.fxml","WSR 2018 - Авторизация");
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
                                            String.valueOf(gt.GetTime() / (60 * 1000)) + " минут до начала чемпионата!");
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
