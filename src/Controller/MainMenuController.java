package Controller;

import Database.DBConnection;
import Model.Appointment;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private static Customer modCust;
    private static int modCustInt;
    private static Appointment modAppt;
    private static int modApptInt;
    private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    public static Boolean runApptCheck = true;
    public static ZonedDateTime startView;
    public static ZonedDateTime endView;

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Customer, Integer> custIDCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> custAddressCol;

    @FXML
    private TableColumn<Customer, String> custZipCol;

    @FXML
    private TableColumn<Customer, String> custPhoneCol;

    @FXML
    private TableColumn<Customer, Integer> custCountryCol;

    @FXML
    private TableColumn<Customer, Integer> custDivCol;

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptDescCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, String> apptLocCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptContactCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustIDCol;

    @FXML
    private TableColumn<Appointment, Integer> apptUserIDCol;

    @FXML
    private RadioButton weekView;

    @FXML
    private RadioButton monthView;

    @FXML
    private RadioButton allView;

    @FXML
    private ToggleGroup ViewWeekMonth;

    @FXML
    private Label viewLabel;

    /** @return the index of the selected Customer */
    public static int getModCustInt() {
        return modCustInt;
    }

    /** @return the index of the selected Appointment */
    public static int getModApptInt() {
        return modApptInt;
    }

    /** Initializes the Main Menu */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (runApptCheck) {
            ObservableList<Appointment> allAppointments = Appointment.getAllAppointments();
            runApptCheck = false;
            int apptFound = 0;

            for (Appointment appt : allAppointments) {
                int minutesTil = (int) ChronoUnit.MINUTES.between(LocalDateTime.now(), appt.getStart());
                String apptID = String.valueOf(appt.getID());

                if (minutesTil > -1 && minutesTil < 16) {
                    apptFound++;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Upcoming");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment #" + apptID + " is happening soon at " + appt.getStart());

                    alert.showAndWait();
                }


            }

            if (apptFound == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Upcoming");
                alert.setHeaderText(null);
                alert.setContentText("No appointments within the next 15 minutes");
                alert.showAndWait();
            }
        }

        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("divID"));

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            custTable.setItems(Customer.getCustomers());
            apptTable.setItems(Appointment.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** goes to the Add Appointment page */
    @FXML
    void onAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();

    }

    /** goes to the Add Customer page */
    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();

    }

    /** goes to the Delete Appointment page */
    @FXML
    void onDeleteAppointment(ActionEvent event) throws SQLException {

        Appointment deleteAppt = apptTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + deleteAppt.getTitle() + " from the system?\n\nAppointment ID: " + deleteAppt.getID() + "\n\nType: " + deleteAppt.getType());
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Appointment.deleteAppointment(deleteAppt);
            Appointment.selectAppointments();
            apptTable.setItems(Appointment.getAllAppointments());

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Delete Successful");
            alert2.setHeaderText(null);
            alert2.setContentText(deleteAppt.getTitle() + " has been deleted from the system\n\nAppointment ID: " + deleteAppt.getID() + "\n\nType: " + deleteAppt.getType());

            alert2.showAndWait();
        }

    }

    /** deletes the selected Customer and any associated Appointments */
    @FXML
    void onDeleteCustomer(ActionEvent event) throws SQLException {

        Customer deleteCust = custTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete " + deleteCust.getName() + " from the system?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Customer.deleteCustomer(deleteCust);
            Customer.selectCustomers();
            custTable.setItems(Customer.getCustomers());
            Appointment.selectAppointments();
            apptTable.setItems(Appointment.getAllAppointments());

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Delete Successful");
            alert2.setHeaderText(null);
            alert2.setContentText(deleteCust.getName() + " and their appointments have been deleted from the system");

            alert2.showAndWait();
        }
    }

    /** goes to the Update Appointment page */
    @FXML
    void onUpdateAppointment(ActionEvent event) throws IOException {

        modAppt = apptTable.getSelectionModel().getSelectedItem();
        modApptInt = Appointment.getAllAppointments().indexOf(modAppt);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** goes to the Update Customer page */
    @FXML
    void onUpdateCustomer(ActionEvent event) throws SQLException, IOException {

        modCust = custTable.getSelectionModel().getSelectedItem();
        modCustInt = Customer.getCustomers().indexOf(modCust);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** sets the appointment table with either a weekly or monthly display */
    @FXML
    void weekMonthToggle(ActionEvent event) {

        if (ViewWeekMonth.getSelectedToggle().equals(allView)) {
            apptTable.setItems(Appointment.getAllAppointments());
            viewLabel.setText("All Appointments");
        }

        if (ViewWeekMonth.getSelectedToggle().equals(weekView)) {
//            startView = ZonedDateTime.now().withHour(0).withMinute(0);
//            endView = startView.plusWeeks(1).withHour(23).withMinute(59);
            ObservableList<Appointment> appts = Appointment.getAllAppointments();
            getApptThisWeek(appts);
            apptTable.setItems(filteredAppointments);
            viewLabel.setText("This Week");
        }

        if (ViewWeekMonth.getSelectedToggle().equals(monthView)) {
//            startView = ZonedDateTime.now().withHour(0).withMinute(0);
//            endView = startView.plusMonths(1).withHour(23).withMinute(59);
            ObservableList<Appointment> appts = Appointment.getAllAppointments();
            getApptThisMonth(appts);
            apptTable.setItems(filteredAppointments);
            viewLabel.setText("This Month");
        }

//        String start = startView.format(DateTimeFormatter.ISO_LOCAL_DATE);
//        String end = endView.format(DateTimeFormatter.ISO_LOCAL_DATE);
//        if (ViewWeekMonth.getSelectedToggle().equals(allView)) {
//            viewLabel.setText("All Appointments");
//        } else {
//            viewLabel.setText(start + " - " + end);
//        }
//
//        start = startView.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        end = endView.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ISO_LOCAL_DATE);
//
//        try {
//            Appointment.selectAppointments(start, end);
//        }
//        catch(SQLException e) {
//            System.out.println("SQL Error!!! " + e);
//        }
    }

    /** @throws IOException
     * goes to the Report screen */
    @FXML
    void onReport(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** closes the app and database connection */
    @FXML
    void onExit(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);
    }

    private static ObservableList<Appointment> getApptThisWeek(ObservableList<Appointment> apptList) {

        filteredAppointments.clear();

        WeekFields week = WeekFields.of(Locale.getDefault());
        for (Appointment appt : apptList) {
            int startWeek = appt.getStart().get(week.weekOfWeekBasedYear());
            int endWeek = appt.getEnd().get(week.weekOfWeekBasedYear());
            int currentWeek = LocalDate.now().get(week.weekOfWeekBasedYear());
            if (startWeek == currentWeek || currentWeek == endWeek) {
                filteredAppointments.add(appt);
            }
        }

        return filteredAppointments;
    }

    private static ObservableList<Appointment> getApptThisMonth(ObservableList<Appointment> apptList) {

        filteredAppointments.clear();

        for (Appointment appt : apptList) {
            int startMonth = appt.getStart().getMonthValue();
            int endMonth = appt.getEnd().getMonthValue();
            int currentMonth = LocalDate.now().getMonthValue();
            if (startMonth == currentMonth || currentMonth == endMonth) {
                filteredAppointments.add(appt);
            }
        }

        return filteredAppointments;

    }
}
