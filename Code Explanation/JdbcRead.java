import java.sql.*;

public class JdbcRead {
    public static void main(String[] args) {
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;


        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";

        try {
            conn = DriverManager.getConnection(url, user, password);
            String selectSQL = "SELECT * FROM students ORDER BY id";
            statement = conn.createStatement();
            res = statement.executeQuery(selectSQL);
            while (res.next()) {
                int id = res.getInt(1);
                String name = res.getString(2);
                String speciality = res.getString(3);
                System.out.println(id + ": " + name + " is studying " + speciality);
            }                    
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            
            System.out.println("--------------------"); 
            try {
                if (res != null) {
                    res.close();
                    System.out.println("ResultSet closed."); 
                }
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
