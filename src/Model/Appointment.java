package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Appointment {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private int ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int custID;
    private int userID;
    private int contactID;

    public Appointment(int ID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String updatedBy, int custID, int userID, int contactID) {
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

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getContact() {
        return contactID;
    }

    public int getCustID() {
        return custID;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public static void selectAppointments() throws SQLException {

        allAppointments.clear();

        String sql = "SELECT * FROM appointments;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            allAppointments.add(new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), null, null, null, null, null, null, rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }
    }
}
