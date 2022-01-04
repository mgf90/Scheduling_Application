package Model;

import Database.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {

    /** @return report #1
     * @throws SQLException
     * report counts and groups together appointments by Type and Month */
    public static String Report1() throws SQLException {

        String sql = "SELECT Type , MONTH(Start) AS Month, COUNT(Appointment_ID) AS Count FROM appointments GROUP BY Month, Type;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String report = "";

        while (rs.next()) {
            report += "Type: " + rs.getString(1) + " || Month: " + rs.getString(2) + " || Count: " + rs.getString(3) + "\n\n";
        }

        return report;
    }

    /** @return report #2
     * @throws SQLException
     * report orders each Contact's Appointment by date and displays various info */
    public static String Report2() throws SQLException {

        String sql = "SELECT Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments INNER JOIN contacts USING (Contact_ID) ORDER BY Contact_Name, Start;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String report = "";

        while (rs.next()) {
            report += rs.getString(1) + " || Appt ID: " + rs.getString(2) + " || " + rs.getString(3) + " || " + rs.getString(4) + " || " + rs.getString(5) + " || " + rs.getString(6) + " - " + rs.getString(7) + " || Customer ID: " + rs.getString(8) + "\n\n";
        }

        return report;
    }

    /** @return report #3
     * @throws SQLException
     * report counts and groups together appointments by day of the week */
    public static String Report3() throws SQLException {

        String sql = "SELECT DAYNAME(Start) AS Day, COUNT(Appointment_ID) AS Count FROM appointments GROUP BY Day;\n";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String report = "";

        while (rs.next()) {
            report += rs.getString(1) + " - " + rs.getString(2) + "\n\n";
        }

        return report;
    }
}
