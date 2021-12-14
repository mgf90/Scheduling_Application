package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Country {

    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    final private int id;
    final private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ObservableList<Country> getCountries() throws SQLException {
        String sql = "SELECT * FROM countries;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while(rs.next()) {
            countries.add(new Country(rs.getInt("Country_ID"), rs.getString("Country")));
        }

        System.out.println("country");

        return countries;
    }
}
