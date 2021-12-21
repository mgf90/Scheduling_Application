package Model;

import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {

    private int ID;
    private String name;
    private String password;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;

    public User(int ID, String name, String password, Timestamp createDate, String createdBy, Timestamp lastUpdate, String updatedBy) {
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

    public Timestamp getCreateDate() {
        return createDate;
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

    @Override
    public String toString() {
        return name;
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


