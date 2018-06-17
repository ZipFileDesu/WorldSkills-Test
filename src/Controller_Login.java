import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

public class Controller_Login {
    /**
     * This is gt class object, which I call for getting time for filling TimeLabel
     * @see GetTime
     */
    GetTime gt = new GetTime();

    /**
     * This is db class object, which I call for work with data in data base
     * @see DB_Handler
     */
    DB_Handler db = new DB_Handler();
    ResultSet result = null;

    /**
     * This is alert class object, which I call when I input wrong email or password
     */
    Alert alert = new Alert(Alert.AlertType.ERROR);

    /**
     * This is attempts variable. If this variable = 0, then we could not log in the system
     */
    static int attempts = 3;

    /**
     * This is user class object, which contains data about autorizated user
     */
    UserData user = new UserData();

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
     * This is button Login, which, if pressed, login in the system or show error message
     */
    @FXML
    private Button Login;

    /**
     * This is button cancel, which, if pressed, set text in text fields is empty
     */
    @FXML
    private Button Cancel;

    /**
     * This is text field Email, where we input Email
     */
    @FXML
    private TextField Email;

    /**
     * This is text field Password, where we input password
     */
    @FXML
    private PasswordField Password;

    /**
     * This is checkbox RememberMe. If this checkbox is selected, then we write information to DB for
     * autofilling email field and password field
     */
    @FXML
    private CheckBox RememberMe;

    /**
     * This is a method initialize which start when form Login was openned. <br>
     * If we press login button, we take text from texts fields and try find this data in DB. <br>
     * If user is exist, we login in the system. If not, we got the error message and we lost 1 try.
     * If we put wrong password or email 3 times, system will locked button login
     * If we press cancel button, then text fields set to empty
     * If we press back button, then we return to previous window
     * Also here we create new thread to show time in real time <br>
     * @throws ParseException If something goes wrong
     */
    @FXML
    public void initialize() throws ParseException, SQLException {

    result = db.AutoFilling();
    if (result.isBeforeFirst()){
        result.next();
        Email.setText(result.getString(2));
        Password.setText(result.getString(3));
    }

        Back.setOnAction(event -> {
            Back.getScene().getWindow().hide();
            try {
                Open.OpenWindow("MainMenu.fxml", "WSR 2018");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Login.setOnAction(event -> {
            try {
                if (attempts > 0)
                    result = db.LoginUser(Email.getText(),Password.getText());
                if(result == null || !result.next() || attempts <= 0){
                    --attempts;
                    if (attempts > 0) {
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Ошибка!");
                        alert.setContentText("Ошибка! Неправильный логин или пароль! Осталось " + attempts +
                        " попытки для входа в систему");
                        alert.show();
                    }
                    else {
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Ошибка!");
                        alert.setContentText("Попытки израсходованы. В доступе отказано.");
                        alert.show();
                        Login.setDisable(true);
                    }
                }
                 else {
                    System.out.println("Вы авторизовались в системе!");
                    user.setId(result.getInt(1));
                    user.setEmail(result.getString(2));
                    user.setPassword(result.getString(3));
                    user.setFirstName(result.getString(4));
                    user.setLastName(result.getString(5));
                    user.setRole(result.getString(6));
                    user.setGender(result.getString(7));
                    user.setDateOfBirth(result.getString(8));
                    user.setRegionCode(result.getInt(9));

                    if (RememberMe.isSelected()){
                        db.RememberMe(user.getId(),user.getEmail(),user.getPassword());
                    }

                    if (Objects.equals(user.getRole(), "C")){
                        Login.getScene().getWindow().hide();
                        Open.OpenWindow("MenuCompetetor_C.fxml","WSR 2017 - Меню участника");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Cancel.setOnAction(event -> {
            Email.setText("");
            Password.setText("");
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
