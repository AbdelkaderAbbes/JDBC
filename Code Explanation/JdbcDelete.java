import java.sql.*;
import java.util.Scanner;

public class JdbcDelete {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement preparedstatement = null;

        Scanner scan = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";

        try {
            conn = DriverManager.getConnection(url, user, password);

            System.out.println("DELETE");
            String deleteSQL = "DELETE FROM students WHERE id = ?";
            preparedstatement = conn.prepareStatement(deleteSQL);
            System.out.print("Enter Student ID you want to delete: ");
            int student_id = scan.nextInt();
            preparedstatement.setInt(1, student_id);
            preparedstatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (preparedstatement != null) {
                    preparedstatement.close();
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
