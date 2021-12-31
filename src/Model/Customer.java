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

    /** constructs Customer object */
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

    /** @throws SQLException
     * @return list of all customers */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        return allCustomers;
    }

    /** @return customer ID */
    public int getID() {
        return ID;
    }

    /** @return customer name */
    public String getName() {
        return name;
    }

    /** @return address */
    public String getAddress() {
        return address;
    }

    /** @return zipcode */
    public String getZipCode() {
        return zipCode;
    }

    /** @return phone number */
    public String getPhoneNum() {
        return phoneNum;
    }

    /** @return user who created customer */
    public String getCreatedBy() {
        return createdBy;
    }

    /** @return time of last update */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** @return user who last updated */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** @return division ID */
    public int getDivID() {
        return divID;
    }

    @Override
    public String toString() {
        return name;
    }

    /** @return customer by id
     * @param id */
    public static Customer getCustomer(int id) {

        int i = 0;
        while (i < allCustomers.size()) {
            if (id == allCustomers.get(i).getID())
                return allCustomers.get(i);
            i++;
        }

        return null;
    }

    /** @return country by associated customer
     * @param cust */
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

    /** @throws SQLException
     * @param cust deletes particular customer */
    public static void deleteCustomer(Customer cust) throws SQLException {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, cust.getID());
        ps.executeUpdate();

        String sql2 = "DELETE FROM appointments WHERE Customer_ID = ?;";
        PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(sql2);
        ps2.setInt(1, cust.getID());
        ps2.executeUpdate();

    }

    /** @throws SQLException
     * selects all customers from the database */
    public static void selectCustomers() throws SQLException {

        allCustomers.clear();

        String sql = "SELECT * FROM customers;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            Customer.allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), null, null, null, null, rs.getInt("Division_ID")));
        }
    }

    /** @throws SQLException
     * @param cust updates customer in database */
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

    /** @throws SQLException
     * @param cust adds particular customer to database */
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
