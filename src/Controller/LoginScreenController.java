package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Database.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    String checkUser, checkPass;
    public static String correctUser;
    public static int correctID;
    Locale currentLocale = Locale.getDefault();
    ResourceBundle lang = ResourceBundle.getBundle("Language.Nat", currentLocale);

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label locationLbl;

    @FXML
    private Button logBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Label currLocTxt;

    @FXML
    private Label titleLbl;

    @FXML
    void onExit(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);
    }

    @FXML
    void onLogIn(ActionEvent event) throws IOException, SQLException {

        checkUser = usernameTxt.getText();
        checkPass = passwordTxt.getText();

        try {
            if (checkUser != null && checkPass != null) {
                String sql = "Select * from users Where User_Name='" + checkUser + "' and Password='" + checkUser + "'";
                Statement stmt = DBConnection.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                /** prepared statement keeps saying incorrect pw even when putting in the correct info */
//                String sql = "Select * from users Where User_Name = '?' AND Password = '?';";
//                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//                ps.setString(1, checkUser);
//                ps.setString(2, checkPass);
//                ResultSet rs = ps.executeQuery(sql);

                if (rs.next()) {
                    this.correctUser = rs.getString("User_Name");
                    this.correctID = rs.getInt("User_ID");

                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
                    stage.setScene(new Scene(scene));
                    stage.show();
                    stage.centerOnScreen();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(lang.getString("Login"));
                    alert.setHeaderText(null);
                    alert.setContentText(lang.getString("LogInFail"));

                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        locationLbl.setText(ZoneId.systemDefault().toString());
        usernameTxt.setPromptText(lang.getString("Username"));
        passwordTxt.setPromptText(lang.getString("Password"));
        logBtn.setText(lang.getString("Login"));
        exitBtn.setText(lang.getString("Exit"));
        currLocTxt.setText(lang.getString("Location"));
        titleLbl.setText(lang.getString("Title"));
    }
}
