package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Division {

    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private int ID;
    private String division;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int countryID;

    public Division(int ID, String division, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String updatedBy, int countryID) {
        this.ID = ID;
        this.division = division;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.countryID = countryID;
    }

    public static ObservableList<Division> getAllDivisions() throws SQLException {

        allDivisions.clear();

        String sql = "SELECT * FROM first_level_divisions;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            allDivisions.add(new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getTimestamp("Create_Date"), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"), rs.getInt("Country_ID")));
        }
        return allDivisions;
    }

    public int getID() {
        return ID;
    }

    public String getDivision() {
        return division;
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

    public int getCountryID() {
        return countryID;
    }

    public static Division getDivisionName(int id) {

        int i = 0;
        while (i < allDivisions.size()) {
            if (id == allDivisions.get(i).getID())
                return allDivisions.get(i);
            i++;
        }

        return null;
    }

    @Override
    public String toString() {
        return division;
    }

    public static ObservableList<Division> getCountryDivisions(Country c) {
        ObservableList<Division> div = FXCollections.observableArrayList();

        int i = 0;
        while (i < allDivisions.size()) {
            if (allDivisions.get(i).getCountryID() == c.getId())
                div.add(allDivisions.get(i));
            i++;
        }

        return div;
    }


}
