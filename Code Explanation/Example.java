//  ------- import the package -------
import java.sql.*;

public class Example {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/students_db";
        String user = "postgres";
        String password = "0000";

        String query = "select * from students";
        //  ------- register the driver -------
        // Class.forName("org.postgresql.Driver");
        //  ------- establish Connection  -------
        Connection conn = DriverManager.getConnection(url, user, password);
        //  ------- Create a Statement  -------
        Statement statement = conn.createStatement();
        //  ------- execute SQL queries  -------
        ResultSet result = statement.executeQuery(query);

        //  ------- Process Results  -------

        while(result.next()) {
            System.out.println(result.getString("name") + " is Studying " + result.getString(3));
        }
        
        //  ------- close resources  -------
        result.close();
        statement.close();
        conn.close();
    }
}
