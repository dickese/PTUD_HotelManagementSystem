package vn.iuh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=hotel-management;encrypt=false;trustServerCertificate=true";
    private static final String user = "sa";
    private static final String pass = "Sapassword123";
    private static Connection connection;

    static {
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
            connection = DriverManager.getConnection(URL, user, pass);
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
