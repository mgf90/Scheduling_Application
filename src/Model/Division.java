package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Division {

    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private int ID;
    private String division;
    private int countryID;

    public Division(int ID, String division, int countryID) {
        this.ID = ID;
        this.division = division;
        this.countryID = countryID;
    }

    public static ObservableList<Division> getAllDivisions() throws SQLException {

        String sql = "SELECT * FROM first_level_divisions;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            allDivisions.add(new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID")));
        }
        return allDivisions;
    }

    public int getID() {
        return ID;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }
}
