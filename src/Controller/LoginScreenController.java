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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    String checkUser, checkPass;
    public static String correctUser;
    private static int correctID;
    private static LocalDateTime logInTime;
    private static ZonedDateTime loginUTC;
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

    /** @return the login time */
    public static LocalDateTime getLogInTime() {
        return logInTime;
    }

    /** exits the program */
    @FXML
    void onExit(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);
    }

    /** @throws IOException
     * Checks to see if the username and password are correct
     * If they are, it continues to the Main Menu
     * If not, it displays an error message */
    @FXML
    void onLogIn(ActionEvent event) throws IOException, SQLException {

        try {
            checkUser = usernameTxt.getText();
            checkPass = passwordTxt.getText();
            logInTime = LocalDateTime.now();
            loginUTC = logInTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

            if (checkUser != null && checkPass != null) {
                String sql = "Select * from users Where User_Name='" + checkUser + "' and Password='" + checkUser + "'";
                Statement stmt = DBConnection.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    this.correctUser = rs.getString("User_Name");
                    correctID = rs.getInt("User_ID");

                    ZonedDateTime startUTC = logInTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                    String start = logInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    LocalDateTime endView = logInTime.plusMinutes(15);
                    ZonedDateTime endUTC = endView.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                    String end = endView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

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

    /** initializes the Login screen */
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
