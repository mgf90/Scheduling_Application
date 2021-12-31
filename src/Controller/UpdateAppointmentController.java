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

public class UpdateAppointmentController implements Initializable {

    private int modApptIndex = MainMenuController.getModApptInt();
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
    private DatePicker apptEndDate;

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

    @FXML
    private TextField apptIDTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int i = 0;
        while (i < 24) {
            hours.add(i);
            i++;
        }

        try {
            Appointment appt = Appointment.getAllAppointments().get(modApptIndex);
            apptTitleTxt.setText(appt.getTitle());
            apptStartDate.setValue(appt.getStart().toLocalDateTime().toLocalDate());
            apptStartMinCombo.setItems(minutes);
            apptStartHourCombo.setItems(hours);
            apptStartMinCombo.setValue(appt.getStart().toLocalDateTime().getMinute());
            apptStartHourCombo.setValue(appt.getStart().toLocalDateTime().getHour());
            apptDescTxt.setText(appt.getDescription());
            apptLocTxt.setText(appt.getLocation());
            apptTypeTxt.setText(appt.getType());
            apptIDTxt.setText(String.valueOf(appt.getID()));
            apptEndDate.setValue(appt.getEnd().toLocalDateTime().toLocalDate());
            apptEndMinCombo.setItems(minutes);
            apptEndHourCombo.setItems(hours);
            apptEndMinCombo.setValue(appt.getEnd().toLocalDateTime().getMinute());
            apptEndHourCombo.setValue(appt.getEnd().toLocalDateTime().getHour());

            apptContCombo.setItems(Contact.getAllContacts());
            apptContCombo.setValue(Contact.getContact(appt.getContactID()));
            apptCustCombo.setItems(Customer.getCustomers());
            apptCustCombo.setValue(Customer.getCustomer(appt.getCustID()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void onSave(ActionEvent event) throws ParseException, SQLException, IOException {

        if (apptStartHourCombo.getValue() < 8 || apptStartHourCombo.getValue() >= 22 || apptEndHourCombo.getValue() >= 22) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please set your appointment between 8am and 10pm");

            alert.showAndWait();

        } else {
            int id = Integer.parseInt(apptIDTxt.getText());
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();

            String startString = apptStartDate.getValue() + " " + apptStartHourCombo.getValue() + ":" + apptStartMinCombo.getValue() + ":00";
            String endString = apptEndDate.getValue() + " " + apptEndHourCombo.getValue() + ":" + apptEndMinCombo.getValue() + ":00";

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
                Appointment.updateAppointment(appt);
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
