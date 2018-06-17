
import com.sun.org.apache.regexp.internal.RE;

import javax.sql.ConnectionEvent;
import java.sql.*;

/**
 * Это класс DB_Handler, в котором мы выполняем выборку по таблицам БД и изменяем данные таблиц БД
 */
public class DB_Handler {

    /**
     * getDBConnection соединяется с базой данных
     * @return Возвращает объект Сonnection, который представляет из себя соединение с БД
     * @throws SQLException Если не удалось соединиться с БД
     */
    public Connection getDBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/worldskills_test";
        Connection conn = null;
        conn = DriverManager.getConnection(url,"root", "");
        if (!conn.isClosed())
            System.out.println("Есть соединение!");
        return conn;
    }

    /**
     * @param Email Почту используем для выборки пользователя в БД
     * @param password Пароль используем для выборки пользователя в БД
     * @return Получаем данные пользователя и заходим в систему
     * @throws SQLException Если не удалось соединиться с БД
     */
    public ResultSet LoginUser(String Email, String password) throws SQLException {
        ResultSet result = null;
        String statement = "SELECT * FROM USERS WHERE Email=? and Password=?";
        PreparedStatement prst = getDBConnection().prepareStatement(statement);
        prst.setString(1, Email);
        prst.setString(2, password);
        result = prst.executeQuery();
        return result;
    }

    /**
     * Сохраняем данные для автозаполнения при следующем запуске программы
     * @param id Id пользователя
     * @param Email Почта пользователя
     * @param password Пароль пользователя
     * @throws SQLException Если не удалось соединиться с БД
     */
    public void RememberMe(int id, String Email, String password) throws SQLException {
        String statement = "INSERT INTO remember_me VALUES (?,?,?)";
        PreparedStatement prst = getDBConnection().prepareStatement(statement);
        prst.setInt(1, id);
        prst.setString(2, Email);
        prst.setString(3, password);
        prst.executeUpdate();
    }

    /**
     * Возвращаем данные для автозаполнения и удаляем из таблицы данные для автозаполнения
     * @return Почту и пароль для автозаполнения
     * @throws SQLException Если не удалось соединиться с БД
     */
    public ResultSet AutoFilling() throws SQLException {
        ResultSet result = null;
        String statement_1 = "SELECT * FROM remember_me";
        String statement_2 = "DELETE FROM remember_me where id_user=?";
        PreparedStatement prst = getDBConnection().prepareStatement(statement_1);
        result = prst.executeQuery();
        if (result.isBeforeFirst()) {
            result.next();
            prst = getDBConnection().prepareStatement(statement_2);
            prst.setInt(1, result.getInt(1));
            prst.executeUpdate();
            result.previous();
        }
        return result;
    }

    public ResultSet GetPhoto(int id) throws SQLException {
        ResultSet result = null;
        String statement = "SELECT photo FROM user_photo WHERE id_user=?";
        PreparedStatement prst = getDBConnection().prepareStatement(statement);
        prst.setInt(1, id);
        result = prst.executeQuery();
        return result;
    }
}
