package Controller;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    private int modCustIndex = MainMenuController.getModCustInt();

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhoneTxt;

    @FXML
    private TextField custAddressTxt;

    @FXML
    private TextField custZipTxt;

    @FXML
    private ComboBox<Country> custCountryCombo;

    @FXML
    private ComboBox<Division> custDivCombo;

    @FXML
    private TextField custIDTxt;

    /** initializes the Update Customer screen */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Customer cust = Customer.getCustomers().get(modCustIndex);
            custNameTxt.setText(cust.getName());
            custPhoneTxt.setText(cust.getPhoneNum());
            custAddressTxt.setText(cust.getAddress());
            custZipTxt.setText(cust.getZipCode());
            custIDTxt.setText(String.valueOf(cust.getID()));

            custCountryCombo.setItems(Country.getCountries());
            custCountryCombo.setValue(Customer.getCountry(cust));
            custDivCombo.setItems(Division.getCountryDivisions(Customer.getCountry(cust)));
            custDivCombo.setValue(Division.getDivisionName(cust.getDivID()));


//            custCountryCombo.setItems(Country.getCountries());
//            custCountryCombo.setValue(Country.getCountryName(cust.getID()));
//            custDivCombo.setItems(Division.getCountryDivisions(custCountryCombo.getValue()));
//            custDivCombo.setValue(Division.getDivisionName(cust.getDivID()));

            System.out.println("Selected country = " + custCountryCombo.getValue());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** displays the divisions */
    @FXML
    void divisionList(ActionEvent event) {

        Country c = custCountryCombo.getValue();
        custDivCombo.setItems(Division.getCountryDivisions(c));
    }

    /** @throws IOException
     * returns to Main Menu without saving */
    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** @throws SQLException
     * @throws IOException
     * saves the customer and returns to the Main Menu */
    @FXML
    void onSave(ActionEvent event) throws SQLException, IOException {

        int id = Integer.parseInt(custIDTxt.getText());
        String name = custNameTxt.getText();
        String address = custAddressTxt.getText();
        String zip = custZipTxt.getText();
        String phone = custPhoneTxt.getText();
        Timestamp created = new Timestamp(System.currentTimeMillis());
        String createdBy = LoginScreenController.correctUser;
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String updatedBy = LoginScreenController.correctUser;
        int div = custDivCombo.getSelectionModel().getSelectedItem().getID();

        Customer cust = new Customer(id, name, address, zip, phone, created, createdBy, lastUpdate, updatedBy, div);
        Customer.updateCustomer(cust);
        Customer.selectCustomers();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }
}

