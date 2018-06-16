/**
 *
 */

import javax.sql.ConnectionEvent;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB_Handler {

    public Connection getDBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mysql";
        Connection conn = null;
        conn = DriverManager.getConnection(url,"root", "");
        if (!conn.isClosed()) {
            System.out.print("Есть соединение!");
        }

            return conn;
    }
}
