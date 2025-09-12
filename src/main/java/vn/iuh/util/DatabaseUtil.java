package vn.iuh.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String url;
    private static final String username;
    private static final String password;
    private static Connection connection;

    static {
        Dotenv dotenv = Dotenv.load();
        url = dotenv.get("DB_URL");
        username = dotenv.get("DB_USERNAME");
        password = dotenv.get("DB_PASSWORD");

        connection = createConnection();
    }

    //    Connection con = DataBaseUtils.getI.getCon
    public static Connection getConnect() {
        if (connection == null)
            connection = createConnection();


        return connection;
    }

    public static boolean closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.close();
                return true;
            } catch (SQLException e) {
                throw new SQLException("failed to close");
            }
        }
        return false;
    }

    private static Connection createConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLServerDriver không tìm thấy: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }

        return connection;
    }
}
