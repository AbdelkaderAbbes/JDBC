import java.sql.*;
import java.util.Arrays;


public class AddBatch {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement statement = null;


        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";
        try {
            conn = DriverManager.getConnection(url, user, password);
            String insertSQL = "INSERT INTO students (name, speciality) VALUES (?, ?)";
            statement = conn.prepareStatement(insertSQL);

            conn.setAutoCommit(false);

            statement.setString(1, "student20");
            statement.setString(2, "ELN");
            statement.addBatch();
            
            statement.setString(1, "student21");
            statement.setString(2, "AUTO");
            statement.addBatch();
            
            statement.setString(1, "student22");
            statement.setString(2, "GP");
            statement.addBatch();
            
            int[] rowsAffected = statement.executeBatch();
            // Commit the transaction
            
            conn.commit();
            // conn.rollback();
            
            System.out.println("rows Affected " + Arrays.stream(rowsAffected).sum());

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
                
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
