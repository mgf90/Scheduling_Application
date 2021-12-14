package Controller;

import Model.Country;
import Model.Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddCustomerController {

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
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    void onSave(ActionEvent event) {

    }

}
