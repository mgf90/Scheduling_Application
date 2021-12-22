package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private int ID;
    private String name;
    private String email;

    public Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Contact getContact(int id) throws SQLException {

        int i = 0;
        while (i < allContacts.size()) {
            if (id == allContacts.get(i).getID()) {
                return allContacts.get(i);
            }
            i++;
        }

        return null;
//        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?;";
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//        ps.setInt(1, conID);
//        ResultSet rs = ps.executeQuery(sql);
//        Contact c = rs.getObject(1);
//        return name;
    }

    public static ObservableList<Contact> getAllContacts() throws SQLException {

        allContacts.clear();

        String sql = "SELECT * FROM contacts;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            allContacts.add(new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email")));
        }

        return allContacts;
    }
}
