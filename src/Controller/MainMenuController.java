package Controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        try {
            Country.getCountries();
            Division.getAllDivisions();
            Customer.getCustomers();
        } catch (SQLException e) {
            System.out.println("SQL error" + e);
        }

        custIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("divID"));
        try {
            custTable.setItems(Customer.getCustomers());
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
    void onDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onUpdateAppointment(ActionEvent event) {

    }

    @FXML
    void onUpdateCustomer(ActionEvent event) {

    }

    @FXML
    void onExit(ActionEvent event) {

    }
}
