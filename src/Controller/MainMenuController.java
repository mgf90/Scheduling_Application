package Controller;

import Database.DBConnection;
import Database.DBQuery;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.Division;
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
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private static Customer modCust;
    private static int modCustInt;

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
    private TableColumn<Customer, Integer> custDivCol;

    @FXML
    private Button addCustBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private Button updateCustBtn;

    @FXML
    private Button deleteApptBtn;

    @FXML
    private Button updateApptBtn;

    @FXML
    private Button addApptBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptDescCol;

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

    public static Customer getModCust() {
        return modCust;
    }

    public static int getModCustInt() {
        return modCustInt;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("divID"));

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));

        try {
            custTable.setItems(Customer.getCustomers());
            apptTable.setItems(Appointment.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onAddAppointment(ActionEvent event) {

    }

    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void onDeleteAppointment(ActionEvent event) {

    }

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

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Delete Successful");
            alert2.setHeaderText(null);
            alert2.setContentText(deleteCust.getName() + " and their appointments have been deleted from the system");

            alert2.showAndWait();
        }
    }

    @FXML
    void onUpdateAppointment(ActionEvent event) {

    }

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

    @FXML
    void onExit(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);
    }
}
