package Model;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class User {

    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    private int ID;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String updatedBy;

    public User(int ID, String name, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public String toString() {
        return name;
    }

    /** @return User by id
     * @param id the user id  */
    public static User getUserName(int id) {

        int i = 0;
        while (i < allUsers.size()) {
            if (id == allUsers.get(i).getID())
                return allUsers.get(i);
            i++;
        }

        return null;
    }

    /** @return all Users from the database
     * @throws SQLException displays error with database */
    public static ObservableList<User> getAllUsers() throws SQLException {

        allUsers.clear();

        String sql = "SELECT * FROM users;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
            allUsers.add(new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"), rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(localZone).toLocalDateTime(), rs.getString("Created_By"), rs.getTimestamp("Last_Update").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(localZone).toLocalDateTime(), rs.getString("Last_Updated_By")));
        }
        return allUsers;
    }

    /** prepared statement keeps saying incorrect pw even when putting in the correct info */
//    public static Boolean verifyUser(String checkUser, String checkPass) throws SQLException {
//
//        String sql = "Select * from users Where User_Name = ? AND Password = ?;";
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//        ps.setString(1, checkUser);
//        ps.setString(2, checkPass);
//        ResultSet rs = ps.executeQuery(sql);
//
//        if (rs.next()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}


