package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Country {

    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    final private int id;
    final private String name;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;

    public Country(int id, String name, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String updatedBy) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public static ObservableList<Country> getCountries() {
        return countries;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Country getCountryName(int id) {

        int i = 0;
        while (i < countries.size()) {
            if (id == countries.get(i).getId())
                return countries.get(i);
            i++;
        }

        return null;
    }

    public static ObservableList<Country> updateCountries() throws SQLException {

        countries.clear();

        String sql = "SELECT * FROM countries;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while(rs.next()) {
            countries.add(new Country(rs.getInt("Country_ID"), rs.getString("Country"), rs.getTimestamp("Create_Date"), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By")));
        }

        System.out.println("country");

        return countries;
    }
}
