package Database;

import Model.Country;
import Model.Customer;

import java.sql.*;

public class DBQuery {

    private static Statement statement;

    /** creates Statement objects */
    public static void setStatement(Connection conn) throws SQLException {

        statement = conn.createStatement();
    }

    /** @return statement object */
    public static Statement getStatement() {
        return statement;
    }

    public static void getCountries() throws SQLException {

        System.out.println("Country");
    }


}
