package Model;

import Controller.LoginScreenController;
import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Appointment {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsSoon = FXCollections.observableArrayList();

    private int ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int custID;
    private int userID;
    private int contactID;

    /** constructs an Appointment object */
    public Appointment(int ID, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String updatedBy, int custID, int userID, int contactID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.custID = custID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /** @return a list of all appointments */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /** @return the appointment ID */
    public int getID() {
        return ID;
    }

    /** @return appointment title */
    public String getTitle() {
        return title;
    }

    /** @return appointment description */
    public String getDescription() {
        return description;
    }

    /** @return location */
    public String getLocation() {
        return location;
    }

    /** @return customer ID */
    public int getCustID() {
        return custID;
    }

    /** @return start time */
    public Timestamp getStart() {
        return start;
    }

    /** @return end time */
    public Timestamp getEnd() {
        return end;
    }

    /** @return type */
    public String getType() {
        return type;
    }

    /** @return the created date */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /** @return user that created the appointment */
    public String getCreatedBy() {
        return createdBy;
    }

    /** @return time of last update */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** @return user that last updated Appointment */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** @return user ID */
    public int getUserID() {
        return userID;
    }

    /** @return contact ID */
    public int getContactID() {
        return contactID;
    }

    /** @return list of appointments within 15 minutes */
    public static ObservableList<Appointment> getAppointmentsSoon() {
        return appointmentsSoon;
    }

    /** @throws SQLException
     * @param appt deletes appointment from database */
    public static void deleteAppointment(Appointment appt) throws SQLException {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appt.getID());
        ps.executeUpdate();

    }

    /** @throws SQLException
     * selects all appointments from the database */
    public static void selectAppointments() throws SQLException {

        allAppointments.clear();

        String sql = "SELECT * FROM appointments;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            allAppointments.add(new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start"), rs.getTimestamp("End"), null, null, null, null, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }
    }

    /** @throws SQLException
     * @param start selects appointments taking place between two times
     * @param end */
    public static void selectAppointments(String start, String end) throws SQLException {

        allAppointments.clear();

        String sql = "SELECT * FROM appointments WHERE (End BETWEEN ? AND ?);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, start);
        ps.setString(2, end);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allAppointments.add(new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start"), rs.getTimestamp("End"), null, null, null, null, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }
    }

    /** @param appt updates selected appointment */
    public static void updateAppointment(Appointment appt) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, appt.getStart());
        ps.setTimestamp(6, appt.getEnd());
        ps.setTimestamp(7, appt.getCreatedDate());
        ps.setString(8, appt.getCreatedBy());
        ps.setString(9, LoginScreenController.correctUser);
        ps.setInt(10, appt.getCustID());
        ps.setInt(11, appt.getUserID());
        ps.setInt(12, appt.getContactID());
        ps.setInt(13, appt.getID());

        ps.executeUpdate();
    }

    /** @param appt adds appointment to the database */
    public static void addAppointment(Appointment appt) throws SQLException {

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, appt.getStart());
        ps.setTimestamp(6, appt.getEnd());
        ps.setTimestamp(7, appt.getCreatedDate());
        ps.setString(8, appt.getCreatedBy());
        ps.setTimestamp(9, appt.getLastUpdate());
        ps.setString(10, appt.getUpdatedBy());
        ps.setInt(11, appt.getCustID());
        ps.setInt(12, appt.getUserID());
        ps.setInt(13, appt.getContactID());
        ps.executeUpdate();
    }

    /** @return whether the appointment is invalid based on overlapping times
     * @throws SQLException
     * @param appt */
    public static Boolean invalidAppointment(Appointment appt) throws SQLException {

        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND ((Start BETWEEN ? AND ?) OR (End BETWEEN ? AND ?)) AND Appointment_ID != ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appt.getCustID());
        ps.setTimestamp(2, appt.getStart());
        ps.setTimestamp(3, appt.getEnd());
        ps.setTimestamp(4, appt.getStart());
        ps.setTimestamp(5, appt.getEnd());
        ps.setInt(6, appt.getID());
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    /** @throws SQLException
     * @param id finds appointments between two times for a particular customer
     * @param start
     * @param end */
    public static void findAppointmentByStart(String start, String end, int id) throws SQLException {

        appointmentsSoon.clear();

        String sql = "SELECT * FROM appointments WHERE (Start BETWEEN ? AND ?) AND User_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, start);
        ps.setString(2, end);
        ps.setInt(3, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            appointmentsSoon.add(new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start"), rs.getTimestamp("End"), null, null, null, null, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }
    }
}
