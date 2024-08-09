package kduongmain.controller;

import helper.AppointmentQuery;
import helper.JDBC;
import helper.UserQuery;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kduongmain.model.Appointment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
//    private static ObservableList<String> languageList = FXCollections.observableArrayList("English","French");

    @FXML
    private Label loginErrorLbl;

    @FXML
    private ComboBox<String> loginLanguageCB;

    @FXML
    private Label loginLocationLbl;

    @FXML
    private Label loginLocationTxt;

    @FXML
    private Label loginPasswordLbl;

    @FXML
    private PasswordField loginPasswordTxt;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private TextField loginUserTxt;

    @FXML
    private Label loginUsernameLbl;

    @FXML
    private Button loginCancelBtn;

    // FXML Event Handlers
    Stage stage;
    Parent scene;

    // Grab current logged in user
    private static String currentUserName;
    public static String getCurrentUserName() {
        return currentUserName;
    }


    @FXML
    void onLoginSubmitBtnClicked(ActionEvent event) throws IOException, SQLException {

        boolean loginSuccess = false; // Default value

        // Checks the system language of the user
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            try {
                if(loginUserTxt.getText().isEmpty() || loginPasswordTxt.getText().isEmpty()) {
                    loginErrorLbl.setText(rb.getString("Incorrect"));
                }
            }catch(Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        // Authenticates user login
        String loginUser = loginUserTxt.getText();
        String loginPass = loginPasswordTxt.getText();

        try {
            if (UserQuery.authenticateUser(loginUser, loginPass) > -1) {
                System.out.println("SUCCESS AUTHENTICATING USER");
                loginSuccess = true;
                currentUserName = loginUser;

                // Checks if there is an appointment within 15 minutes of a user's login
                boolean isWithin15Minutes = false;
                for (Appointment appointment : AppointmentQuery.getAllAppointments()) {
                    LocalDateTime startTime = appointment.getTimeStart();
                    if ((startTime.isAfter(LocalDateTime.now()) || startTime.isEqual(LocalDateTime.now().plusMinutes(15))) &&
                            (startTime.isBefore(LocalDateTime.now().plusMinutes(15)) || startTime.isEqual(LocalDateTime.now()))) {
                        Alert confirmRemoval = new Alert(Alert.AlertType.WARNING);
                        confirmRemoval.setTitle(ResourceBundle.getBundle("Nat").getString("Alert"));
                        confirmRemoval.setContentText(ResourceBundle.getBundle("Nat").getString("Appointment") + " " +
                                appointment.getId() + " " + ResourceBundle.getBundle("Nat").getString("beginsat") + " " + appointment.getTimeStart().toLocalTime());
                        confirmRemoval.getButtonTypes().clear();
                        confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                        confirmRemoval.showAndWait();
                        isWithin15Minutes = true;
                    }
                }
                // if there are no appointments within 15 minutes of a user login
                if (!isWithin15Minutes) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Appointments within 15 minutes");
                    alert.setContentText("No appointments within 15 minutes");
                    alert.showAndWait();
                }


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                loginErrorLbl.setText(rb.getString("Incorrect"));
            }
            //  Record login attempt to login_activity.txt
            loginAttempt(loginUser,loginSuccess);
        } catch(Exception e) {
            System.out.println("ERROR AUTHENTICATING USER " +  e);
        }


    }

    public void loginCancelBtnClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Method to record login attempt
    private void loginAttempt(String username, boolean loginSuccessful) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String status = loginSuccessful ? "Successful" : "Failed";
        String record = String.format("Date/Time: %s | Username: %s | Status: %s%n", timestamp, username, status);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))) {
            writer.write(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default in English
        loginLanguageCB.setValue("English");
        loginLanguageCB.getItems().addAll("English","French");

        try{
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

            ZoneId zone = ZoneId.systemDefault();

            //removed
            //loginScreenLocationField.setText(Locale.getDefault().getDisplayCountry());
            loginLocationTxt.setText(String.valueOf(zone));

            if(Locale.getDefault().getLanguage().equals("fr")) {
                loginLanguageCB.setValue("French");
                loginUsernameLbl.setText(rb.getString("username"));
                loginPasswordLbl.setText(rb.getString("password"));
                loginSubmitBtn.setText(rb.getString("Login"));
                loginCancelBtn.setText(rb.getString("Exit"));
                loginLocationLbl.setText(rb.getString("Location"));

            }

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e){
            System.out.println(e);
        }


    }
}
