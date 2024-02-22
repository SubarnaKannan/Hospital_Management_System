package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {

    static Connection connection;
    private static void createConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospitalmanagementsystem";
            String user = "root";
            String password = "123456789";
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(connection == null){
            createConnection();
        }
        return connection;
    }

}
