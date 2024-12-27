import java.sql.*;
import java.util.Scanner;

public class JdbcUpdate {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement statement = null;

        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";
        Scanner scan = new Scanner(System.in);
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            
            String updateSQL = "UPDATE students SET name = ? WHERE id = ?";
            
            statement = conn.prepareStatement(updateSQL);
    
            System.out.println("UPDATE");
            System.out.print("Enter Updated Name: ");
            String updated_name = scan.nextLine();
            System.out.print("Enter Student ID: ");
            int student_id = scan.nextInt();
            statement.setString(1, updated_name);
            statement.setInt(2, student_id);
            statement.executeUpdate();

            scan.close();
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
