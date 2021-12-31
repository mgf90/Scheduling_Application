package Controller;

import Model.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private RadioButton report1Radio;

    @FXML
    private ToggleGroup ReportToggle;

    @FXML
    private RadioButton report2Radio;

    @FXML
    private RadioButton report3Radio;

    @FXML
    private TextArea reportTxt;

    @FXML
    private TextField infoTxt;

    @FXML
    private Pane clearInfo1;

    @FXML
    private Pane clearInfo2;

    @FXML
    private Pane clearInfo3;

    @FXML
    private Pane clearInfo4;

    /** displays the current report type selected
     * @throws SQLException*/
    @FXML
    void runReport(ActionEvent event) throws SQLException {

        if (ReportToggle.getSelectedToggle().equals(report1Radio)) {
            reportTxt.clear();
            reportTxt.setText(Report.Report1());
        } else if (ReportToggle.getSelectedToggle().equals(report2Radio)) {
            reportTxt.clear();
            reportTxt.setText(Report.Report2());
        } else if (ReportToggle.getSelectedToggle().equals(report3Radio)) {
            reportTxt.clear();
            reportTxt.setText(Report.Report3());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a report to run");

            alert.showAndWait();
        }
    }

    /** exits to the Main Menu */
    @FXML
    void onExit(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** initializes the Reports screen
     * uses two different lambda expression to display and erase report info on mouse hover */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        report1Radio.hoverProperty().addListener(l -> {
            infoTxt.setText("Counts and groups together appointments by type and month");
        });
        report2Radio.hoverProperty().addListener(l -> {
            infoTxt.setText("Orders each contact's appointment by date");
        });
        report3Radio.hoverProperty().addListener(l -> {
            infoTxt.setText("Counts and groups together appointments by day of the week");
        });

        clearInfo1.setOnMouseMoved(m -> {
            infoTxt.clear();
        });
        clearInfo2.setOnMouseMoved(m -> {
            infoTxt.clear();
        });
        clearInfo3.setOnMouseMoved(m -> {
            infoTxt.clear();
        });
        clearInfo4.setOnMouseMoved(m -> {
            infoTxt.clear();
        });
    }
}