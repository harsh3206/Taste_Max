package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String url = "jdbc:mysql://localhost:3306/taste-max";
    private static final String dbuser = "root";
    private static final String dbpassword = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbuser, dbpassword);
    }
}
