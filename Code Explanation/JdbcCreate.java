import java.sql.*;
import java.util.Scanner;

public class JdbcCreate {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement statement = null;

        Scanner scan = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";
        try {
            conn = DriverManager.getConnection(url, user, password);
            String insertSQL = "INSERT INTO students (name, speciality) VALUES (?, ?)";
            statement = conn.prepareStatement(insertSQL);

            System.out.print("Enter your Name: ");
            String name = scan.nextLine();
            System.out.print("Enter your Speciality: ");
            String speciality = scan.nextLine();
                
            statement.setString(1, name);
            statement.setString(2, speciality);
            int rowsaffected = statement.executeUpdate();
            System.out.println(rowsaffected);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    System.out.println("statement closed."); 
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed");
                }
                scan.close();
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
