package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
    private ComboBox<User> apptUserCombo;

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
            apptUserCombo.setItems(User.getAllUsers());
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

        int id = 0;
        String title = apptTitleTxt.getText();
        String description = apptDescTxt.getText();
        String location = apptLocTxt.getText();
        String type = apptTypeTxt.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDate startDate = apptStartDate.getValue();

        String startHour = String.valueOf(apptStartHourCombo.getValue());
        if (apptStartHourCombo.getValue() < 10) {
            startHour = "0" + startHour;
        }
        String startMinute = String.valueOf(apptStartMinCombo.getValue());
        String startString = startHour + ":" + startMinute + ":00";


        if (apptStartMinCombo.getValue() == 0) {
            startString = startHour + ":" + startMinute + "0:00";
        }

        LocalTime startTime = LocalTime.parse(startString, formatter);
//            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
//            ZonedDateTime startLocal = start.atZone(localZone);
//            ZonedDateTime startUTC = startLocal.withZoneSameInstant(ZoneId.of("UTC"));

        String endHour = String.valueOf(apptEndHourCombo.getValue());
        if (apptEndHourCombo.getValue() < 10) {
            endHour = "0" + endHour;
        }
        String endMinute = String.valueOf(apptEndMinCombo.getValue());
        String endString = endHour + ":" + endMinute + ":00";


        if (apptEndMinCombo.getValue() == 0) {
            endString = endHour + ":" + endMinute + "0:00";
        }

        LocalTime endTime = LocalTime.parse(endString, formatter);
        LocalDateTime end = LocalDateTime.of(startDate, endTime);
//            ZonedDateTime endLocal = end.atZone(localZone);
//            ZonedDateTime endUTC = endLocal.withZoneSameInstant(ZoneId.of("UTC"));

//            var date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startString);
//            var date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endString);
//            LocalTime localStart = LocalTime.parse((CharSequence) date1, DateTimeFormatter.ISO_TIME);
//            ZonedDateTime startUTC = ZonedDateTime.of(LocalDateTime.from(localStart), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
//            LocalTime localEnd = LocalTime.parse((CharSequence) date2, DateTimeFormatter.ISO_TIME);
//            ZonedDateTime endUTC = ZonedDateTime.of(LocalDateTime.from(localEnd), ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));

        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("UTC"));
        String createdBy = LoginScreenController.correctUser;
        LocalDateTime lastUpdate = LocalDateTime.now(ZoneId.of("UTC"));
        String updatedBy = LoginScreenController.correctUser;
        int custID = apptCustCombo.getSelectionModel().getSelectedItem().getID();
        int userID = apptUserCombo.getSelectionModel().getSelectedItem().getID();
        int contactID = apptContCombo.getSelectionModel().getSelectedItem().getID();

        Appointment appt = new Appointment(id, title, description, location, type, start, end, createdDate, createdBy, lastUpdate, updatedBy, custID, userID, contactID);

        if (Appointment.invalidAppointment(appt)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Customer already has an appointment at this time");

            alert.showAndWait();

        } else if (!Appointment.withinBusinessHours(appt)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please set your appointment between 8am and 10pm Eastern Time");

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


