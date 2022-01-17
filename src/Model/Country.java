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
    private String name;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;

    /** constructs a Country object */
    public Country(int id, String name, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String updatedBy) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }

    /** @return country ID */
    public int getId() {
        return id;
    }

    /** @return country name */
    public String getName() {
        return name;
    }

    /** @return user who created country */
    public String getCreatedBy() {
        return createdBy;
    }

    /** @return time of the last update */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** @return user who updated last */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** @return list of all countries */
    public static ObservableList<Country> getCountries() {
        return countries;
    }

    /** @return country names as a string */
    @Override
    public String toString() {
        return this.name;
    }

    /** @return country name by ID
     * @param id */
    public static Country getCountryName(int id) {

        int i = 0;
        while (i < countries.size()) {
            if (id == countries.get(i).getId())
                return countries.get(i);
            i++;
        }

        return null;
    }

    /** @throws SQLException
     * @return list of countries from the database */
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
