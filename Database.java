import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:expense.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            return null;
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "category TEXT," +
                     "amount REAL," +
                     "date TEXT)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table creation failed");
        }
    }
}
