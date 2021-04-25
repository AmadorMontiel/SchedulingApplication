package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that enables the connection to the Database
 *
 * Code in this class is taken from Malcolm Mabara's webinar @https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=37cfaa86-e261-4815-9eb8-ab490111ff48
 * and Mark Kinkead's webinar https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=1be32ba5-76c6-47f3-8816-accf0002109b
 *
 */
public class DBConnection {

    /**
     * JDBC URL information
     */
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ066E4";

    /**
     * Complete JDBC URL
     */
    private static final String dbURL = protocol + vendor + ipAddress;

    /**
     * Connection Interface Reference
     */
    private static Connection conn = null;
    /**
     * DB Username
     */
    private static final String username = "U066E4";
    /**
     * DB Password
     */
    private static final String password = "53688689796";

    /**
     * Starts the connection using the DBUrl, the username and the password.
     * Catches a SQLException and prints the stacktrace if triggered.
     */
    public static void startConnection() {
        try {
            conn = DriverManager.getConnection(dbURL,username,password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection so it is usable in the rest of the program.
     * @return the connection
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Closes the connection.
     * Catches an Exception but the exception is ignored.
     */
    public static void closeConnection()  {
        try {
            conn.close();
        }
        catch ( Exception ignore) {
        }
    }



}
