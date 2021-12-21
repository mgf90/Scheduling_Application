package Model;

import Controller.LoginScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Database.DBConnection;

import java.sql.*;

public class Customer {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private int ID;
    private String name;
    private String address;
    private String zipCode;
    private String phoneNum;
    private Timestamp createdTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int divID;

    public Customer(int ID, String name, String address, String zipCode, String phoneNum, Timestamp createdTime, String createdBy, Timestamp lastUpdate, String updatedBy, int divID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.createdTime = createdTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.divID = divID;
    }


    public static ObservableList<Customer> getCustomers() throws SQLException {
        return allCustomers;
    }

    public int getID() {
        return ID;
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

    public Timestamp getCreatedTime() {
        return createdTime;
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

    public int getDivID() {
        return divID;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Country getCountry(Customer cust) {

        Division d;
        Country c;

        if (cust.getDivID() < 55) {
            d = Division.getDivisionName(54);
            c = Country.getCountryName(d.getCountryID());
        } else if (cust.getDivID() < 73) {
            d = Division.getDivisionName(72);
            c = Country.getCountryName(d.getCountryID());
        } else {
            d = Division.getDivisionName(101);
            c = Country.getCountryName(d.getCountryID());
        }

        return c;
    }

    public static void deleteCustomer(Customer cust) throws SQLException {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, cust.getID());
        ps.executeUpdate();

    }

    public static void selectCustomers() throws SQLException {

        allCustomers.clear();

        String sql = "SELECT * FROM customers;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            Customer.allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), null, null, null, null, rs.getInt("Division_ID")));
        }
    }

    public static void updateCustomer(Customer cust) throws SQLException {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString   (1, cust.getName());
        ps.setString(2, cust.getAddress());
        ps.setString(3, cust.getZipCode());
        ps.setString(4, cust.getPhoneNum());
        ps.setString(5, LoginScreenController.correctUser);
        ps.setInt(6, cust.getDivID());
        ps.setInt(7, cust.getID());
        ps.executeUpdate();
    }

    public static void addCustomers(Customer cust) throws SQLException {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, cust.getName());
        ps.setString(2, cust.getAddress());
        ps.setString(3, cust.getZipCode());
        ps.setString(4, cust.getPhoneNum());
        ps.setTimestamp(5, cust.createdTime);
        ps.setString(6, cust.getCreatedBy());
        ps.setTimestamp(7, cust.lastUpdate);
        ps.setString(8, cust.updatedBy);
        ps.setInt(9, cust.getDivID());
        ps.executeUpdate();
    }
}
