package kduongmain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button mainAppointmentsBtn;

    @FXML
    private Button mainCustomersBtn;

    @FXML
    private Button mainExitBtn;

    @FXML
    private Button mainRecordsBtn;

    @FXML
    private Label welcomeText;

    // FXML Event Handlers
    Stage stage;
    Parent scene;

    /** Action even for when the appointment button is clicked. Sends the user to the Appointment View FXML. */
    @FXML
    void onAppointmentsBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AppointmentView.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Action event for when the customer button is clicked. Sends the user to the Customer View FXML. */
    @FXML
    void onCustomersBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/CustomerView.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Action event for when the exit button is clicked. Exits the whole application */
    @FXML
    void onExitBtnClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    /** Action event for when the records button is clicked. Sends user to the Reports FXML. */
    @FXML
    void onRecordsBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/Reports.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Initial setup for when the main menu FXML is loaded. Gets default language as English, if the user system language
     * is French, translate the text to the French language. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")) {
                mainCustomersBtn.setText(rb.getString("Customers"));
                mainAppointmentsBtn.setText(rb.getString("Appointment"));
                mainRecordsBtn.setText(rb.getString("Records"));
                mainExitBtn.setText(rb.getString("Exit"));
            }
        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        }
    }


}