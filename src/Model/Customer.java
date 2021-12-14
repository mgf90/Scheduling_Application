package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private SimpleIntegerProperty ID;
    private String name;
    private String address;
    private String zipCode;
    private String phoneNum;
    private int divID;

    public Customer(int id, String name, String address, String zipCode, String phoneNum, int divID) {
        this.ID = new SimpleIntegerProperty(id);
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.divID = divID;
    }

    public static ObservableList<Customer> getCustomers() throws SQLException {

        String sql = "SELECT * FROM customers;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            Customer.allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID")));
        }

        return allCustomers;
    }

    public int getID() {
        return this.ID.get();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getDivID() {
        return divID;
    }

    public void selectAllCustomers() throws SQLException {

        String sql = "SELECT * FROM client_schedule.customers";
        Statement stmt = DBConnection.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID")));
        }

    }
}
