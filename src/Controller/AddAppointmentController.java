package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    private ObservableList<Integer> hours = FXCollections.observableArrayList();
    private ObservableList<Integer> minutes = FXCollections.observableArrayList(0, 15, 30, 45);

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private TextField apptDescTxt;

    @FXML
    private TextField apptLocTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ComboBox<Integer> apptStartHourCombo;

    @FXML
    private ComboBox<Integer> apptEndHourCombo;

    @FXML
    private ComboBox<Contact> apptContCombo;

    @FXML
    private ComboBox<Customer> apptCustCombo;

    @FXML
    private ComboBox<Integer> apptStartMinCombo;

    @FXML
    private ComboBox<Integer> apptEndMinCombo;

    /** initializes the Add Appointment screen */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int i = 0;
        while (i < 24) {
            hours.add(i);
            i++;
        }

        try {
            apptCustCombo.setItems(Customer.getCustomers());
            apptContCombo.setItems(Contact.getAllContacts());
            apptStartMinCombo.setItems(minutes);
            apptEndMinCombo.setItems(minutes);
            apptStartHourCombo.setItems(hours);
            apptEndHourCombo.setItems(hours);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** @throws IOException
     * exits the screen without saving the appointment */
    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();

    }

    /** @throws SQLException
     * @throws ParseException
     * @throws IOException
     * Saves the appointment and returns to the Main Menu */
    @FXML
    void onSave(ActionEvent event) throws SQLException, ParseException, IOException {

        if (apptStartHourCombo.getValue() < 8 || apptStartHourCombo.getValue() >= 22 || apptEndHourCombo.getValue() >= 22 && apptEndMinCombo.getValue() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please set your appointment between 8am and 10pm");

            alert.showAndWait();

        } else {

            int id = 0;
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();

            String startString = apptStartDate.getValue() + " " + apptStartHourCombo.getValue() + ":" + apptStartMinCombo.getValue() + ":00";
            String endString = apptStartDate.getValue() + " " + apptEndHourCombo.getValue() + ":" + apptEndMinCombo.getValue() + ":00";

            var date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startString);
            var date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endString);
            Timestamp start = new Timestamp(date1.getTime());
            Timestamp end = new Timestamp(date2.getTime());

            Timestamp createdDate = new Timestamp(System.currentTimeMillis());
            String createdBy = LoginScreenController.correctUser;
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
            String updatedBy = LoginScreenController.correctUser;
            int custID = apptCustCombo.getSelectionModel().getSelectedItem().getID();
            int userID = LoginScreenController.getCorrectID();
            int contactID = apptContCombo.getSelectionModel().getSelectedItem().getID();

            Appointment appt = new Appointment(id, title, description, location, type, start, end, createdDate, createdBy, lastUpdate, updatedBy, custID, userID, contactID);

            if (Appointment.invalidAppointment(appt)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Customer already has an appointment at this time");

                alert.showAndWait();

            } else {
                Appointment.addAppointment(appt);
                Appointment.selectAppointments();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
                stage.centerOnScreen();
            }
        }
    }
}


