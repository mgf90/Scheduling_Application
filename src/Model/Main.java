package Model;

/** This application manages and customers and their appointments.
 * JavaDoc is found in JavaDoc folder*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Database.DBConnection;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/LoginScreen.fxml")));
        primaryStage.setTitle(null);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    /** This is the main method. This is the first method to be called when program is run.
     * @param args */

    public static void main(String[] args) throws SQLException {

        DBConnection.openConnection();
        Customer.selectCustomers();
        Country.updateCountries();
        Division.getAllDivisions();
        Appointment.selectAppointments();
        Contact.getAllContacts();

        launch(args);
    }
}
