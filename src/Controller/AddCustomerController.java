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

public class AddCustomerController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        custCountryCombo.setItems(Country.getCountries());
    }

    /** @throws IOException
     * exits the screen without saving the customer */
    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** @throws IOException
     * @throws SQLException
     * saves the customer data and returns to the Main Menu */
    @FXML
    void onSave(ActionEvent event) throws IOException, SQLException {

        int id = 0;
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
        Customer.addCustomers(cust);

        Customer.selectCustomers();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** gets a list of divisions */
    @FXML
    private void divisionList(ActionEvent event) {
        Country c = custCountryCombo.getValue();
        custDivCombo.setItems(Division.getCountryDivisions(c));
    }

}
