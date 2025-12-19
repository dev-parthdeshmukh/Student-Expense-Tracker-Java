import java.sql.*;
import java.util.Scanner;

public class ExpenseTracker {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Database.createTable();

        while (true) {
            System.out.println("\n--- Student Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total Expense");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> totalExpense();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void addExpense() {
        System.out.print("Category: ");
        sc.nextLine();
        String category = sc.nextLine();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();

        System.out.print("Date (DD-MM-YYYY): ");
        sc.nextLine();
        String date = sc.nextLine();

        String sql = "INSERT INTO expenses(category, amount, date) VALUES(?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, date);
            pstmt.executeUpdate();

            System.out.println("Expense added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding expense");
        }
    }

    static void viewExpenses() {
        String sql = "SELECT * FROM expenses";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | Category | Amount | Date");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("category") + " | " +
                        rs.getDouble("amount") + " | " +
                        rs.getString("date")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expenses");
        }
    }

    static void totalExpense() {
        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Total Expense: ₹" + rs.getDouble("total"));

        } catch (SQLException e) {
            System.out.println("Error calculating total");
        }
    }
}
