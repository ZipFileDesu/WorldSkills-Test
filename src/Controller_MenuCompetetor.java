import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

public class Controller_MenuCompetetor {
    /**
     * Создаём новый объект класса db, который работает с базой данных
     * @see DB_Handler
     */
    DB_Handler db = new DB_Handler();

    /**
     * Создаём новый объект класса TodayTime, который будет показывать текущее время
     */
    Calendar TodayTime = Calendar.getInstance();

    /**
     * Создаём объект класса gt, который будет возвращать текущую дату и разницу дат
     * @see GetTime
     */
    GetTime gt = new GetTime();

    /**
     * Создаём объект класса user, которых хранит в себе данные авторизированного пользователя
     */
    UserData user = new UserData();

    /**
     * Создаём объеласса open, который отвечает за открытие других форма
     * @see OpenNewWindow
     */
    OpenNewWindow open = new OpenNewWindow();

    /**
     *
     */
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * Это строка с текстом LabelTime_2, которая приветствует участника в зависимости от текущего времени суток
     */
    @FXML
    private Label LabelTime_2;

    /**
     * Это строка с текстом GNLabel, в которой отображется имя пользователя и mrs/mr в зависимости от пола участника
     */
    @FXML
    private Label GNLabel;

    /**
     * Это фотография, в которой отображается фотография пользователя
     */
    @FXML
    private javafx.scene.image.ImageView Photo;

    /**
     * Это кнопка, при нажатии которой открывается
     */
    @FXML
    private Button MyProfile;

    /**
     *
     */
    @FXML
    private Button Back;

    /**
     * Для начала мы задаём текст приветсвтия участника, который зависит от текущего времени суток. <br>
     * Потом, в зависимости от пола участника, пишем mr или mrs и пишем его имя. <br>
     * Потом достаём из БД фотографию данного участника и вставляем её в Photo <br>
     *
     */
    @FXML
    public void initialize() throws SQLException {
        ButtonType yes = new ButtonType("Да");
        ButtonType no = new ButtonType("Нет");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);
        alert.setTitle("Выход из системы");
        alert.setHeaderText(null);
        alert.setContentText("Вы точно хотите выйти из системы?");

        if(TodayTime.get(Calendar.HOUR_OF_DAY) >= 6 || (TodayTime.get(Calendar.HOUR_OF_DAY) <= 11 && TodayTime.get(Calendar.MINUTE) <= 59))
            LabelTime_2.setText("Доброе утро!");

        if(TodayTime.get(Calendar.HOUR_OF_DAY) >= 12 || (TodayTime.get(Calendar.HOUR_OF_DAY) <= 17 && TodayTime.get(Calendar.MINUTE) <= 59))
            LabelTime_2.setText("Добрый день!");

        if(TodayTime.get(Calendar.HOUR_OF_DAY) >= 18 || (TodayTime.get(Calendar.HOUR_OF_DAY) <= 5 && TodayTime.get(Calendar.MINUTE) <= 59))
            LabelTime_2.setText("Добрый вечер!");

        if(Objects.equals(user.Gender, "Male"))
            GNLabel.setText("Ms " + user.getFirstName());
        else
            GNLabel.setText("Mrs " + user.getFirstName());

        ResultSet result = db.GetPhoto(user.getId());
        if (result.isBeforeFirst()) {
            result.next();
            Blob img = result.getBlob(1);
            Photo.setImage(new Image(img.getBinaryStream()));
        }

        Back.setOnAction(event -> {
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == yes) {
                Back.getScene().getWindow().hide();
                user.ClearUserData();
                try {
                    open.OpenWindow("MainMenu.fxml","WSR 2018");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        MyProfile.setOnAction(event -> {

        });

    }
}
