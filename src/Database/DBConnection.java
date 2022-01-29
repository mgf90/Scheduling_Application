package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//us-cdbr-east-05.cleardb.net:3306/";
    private static final String databaseName = "heroku_21e576f18960900";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "bab109a4ae9767"; // Username
    private static String password = "bd3831ef"; // Password
    public static Connection connection;  // Connection Interface

    /** opens up a connection to the MySQL database */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** closes connection to the MySQL database */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** @return connection */
    public static Connection getConnection() {
        return connection;
    }
}
