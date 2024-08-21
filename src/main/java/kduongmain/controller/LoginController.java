package kduongmain.controller;

import helper.AppointmentQuery;
import helper.UserQuery;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoginController implements Initializable {

    @FXML
    private Label loginErrorLbl;

    @FXML
    private Label loginLanguageLbl;

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

    // Grab current logged-in user
    private static String currentUserName;
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /** Action event for when the user clicks on the login button. Checks to see if the correct username and password is submitted. If username/password does not match
     * the login records, throws an error. If username/password is submitted successfully, checks if the current user has any appointments within 15 minutes of login time. If there is no
     * appointments within 15 minutes, display a custom message indicating there are no appointments within 15 minutes. If there is an appointment within 15 minutes, display a custom
     * message telling the user the date/time of the appointment that is happening within 15 minutes. Checks also the user's system language. If the language is set in English display
     * the login FXML in English, if the system language is French, display the login FXML in French. Initially also checks the user's system location to determine ZoneID. */
    @FXML
    void onLoginSubmitBtnClicked(ActionEvent event) {

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
                                appointment.getId() + " " + ResourceBundle.getBundle("Nat").getString("begins") + " " + appointment.getTimeStart().toLocalDate() + " " + appointment.getTimeStart().toLocalTime());
                        confirmRemoval.getButtonTypes().clear();
                        confirmRemoval.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                        confirmRemoval.showAndWait();
                        isWithin15Minutes = true;
                    }
                }
                // if there are no appointments within 15 minutes of a user login
                if (!isWithin15Minutes) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No " + rb.getString("Appointment") + " within 15 minutes");
                    alert.setContentText("No " + rb.getString("Appointment") + " within 15 minutes");
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

    /** Action event for when the cancel button is clicked. Exits the program entirely. */
    public void loginCancelBtnClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Method to record login attempts whether successful or unsuccessful. Records the date/time of the attempted login and puts it in a login_activity.txt.
     * @param loginSuccessful boolean for a successful login attempt.
     * @param username String value of current user. */
    private void loginAttempt(String username, boolean loginSuccessful) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String status = loginSuccessful ? "Successful" : "Failed";
        String record = String.format("Date/Time: %s | Username: %s | Status: %s%n", timestamp, username, status);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true))) {
            writer.write(record);
        } catch (IOException e) {
            System.out.println("ERROR LOGGING LOGIN ATTEMPT: " + e.getMessage());
        }
    }

    /** Initial setup for the login.FXML. Attempts to get user's current system language settings and translates the FXML to french if the language setting is set to French.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default in English
        loginLanguageLbl.setText("English");

        try{
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            System.out.println(rb.getString("hello"));
            loginUsernameLbl.setText(rb.getString("username"));
            System.out.println("LOCALE DEFAULT IS: " +Locale.getDefault());

            ZoneId zone = ZoneId.systemDefault();

            loginLocationTxt.setText(String.valueOf(zone));

            if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("FR") ) {
                loginLanguageLbl.setText("French");
                loginUsernameLbl.setText(rb.getString("username"));
                loginPasswordLbl.setText(rb.getString("password"));
                loginSubmitBtn.setText(rb.getString("login"));
                loginCancelBtn.setText(rb.getString("Exit"));
                loginLocationLbl.setText(rb.getString("location"));
            }

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
