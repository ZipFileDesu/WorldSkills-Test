
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
}
