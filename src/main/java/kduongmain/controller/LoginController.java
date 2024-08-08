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
                currentUserName = loginUser;
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                loginErrorLbl.setText(rb.getString("Incorrect"));
            }
        } catch(Exception e) {
            System.out.println("ERROR AUTHENTICATING USER " +  e);
        }

//        // Calculate 15 minutes from the current time in UTC
//        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
//        ZonedDateTime fifteenMinutesLater = currentTime.plusMinutes(15);
//
//        // Convert ZonedDateTime to Instant
//        Instant currentTimeInstant = currentTime.toInstant();
//        Instant fifteenMinutesLaterInstant = fifteenMinutesLater.toInstant();
//
//        // Convert Instant to Timestamp
//        Timestamp currentTimeTimestamp = Timestamp.from(currentTimeInstant);
//        Timestamp fifteenMinutesLaterTimestamp = Timestamp.from(fifteenMinutesLaterInstant);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
//        String formattedCurrentTime = formatter.format(currentTimeTimestamp.toInstant());
//        String formattedFifteenMinutesLater = formatter.format(fifteenMinutesLaterTimestamp.toInstant());
//
//        System.out.println("Formatted Current Time (UTC): " + formattedCurrentTime);
//        System.out.println("Formatted Fifteen Minutes Later (UTC): " + formattedFifteenMinutesLater);
//
//        // Query to check if appointment is 15 minutes within a user's login
//        String sql = "SELECT * FROM Appointments WHERE User_ID = ? AND Start BETWEEN ? AND ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1,UserQuery.getUserIdByName(loginUser));
//        ps.setTimestamp(2, Timestamp.valueOf(formattedCurrentTime));
//        ps.setTimestamp(3, Timestamp.valueOf(formattedFifteenMinutesLater));
//
//        ResultSet rs = ps.executeQuery();
//
//        // Construct the SQL query string with parameter values replaced
//        String queryString = sql.replaceFirst("\\?", String.valueOf(UserQuery.getUserIdByName(loginUser)))
//                .replaceFirst("\\?", "'" + formattedCurrentTime + "'")
//                .replaceFirst("\\?", "'" + formattedFifteenMinutesLater + "'");
//
//        System.out.println("SQL Query: " + queryString);
//
//        if (rs.next()) {
//            // Get the appointment start time in UTC
//            Timestamp startTimestamp = rs.getTimestamp("Start");
//            LocalDateTime startDateTime = startTimestamp.toLocalDateTime();
//
//            // Convert the start time to ZonedDateTime in UTC
//            ZonedDateTime utcStartDateTime = startDateTime.atZone(ZoneOffset.UTC);
//
//            // Convert the UTC start time to the user's local time zone
//            ZoneId userZone = ZoneId.systemDefault();
//            ZonedDateTime userLocalStartDateTime = utcStartDateTime.withZoneSameInstant(userZone);
//
//            // Format the user's local start time
//            String appointmentDateTime = userLocalStartDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//
//            // Display the appointment details
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Upcoming Appointment");
//            alert.setHeaderText("Upcoming Appointment");
//            String appointmentId = rs.getString("Appointment_ID");
//            alert.setContentText("You have an upcoming appointment within 15 minutes.\nAppointment ID: " + appointmentId + "\nDate and Time: " + appointmentDateTime);
//            alert.showAndWait();
//        } else {
//            // If there are no appointments within 15 minutes, display custom message
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("No Upcoming Appointments");
//            alert.setHeaderText("No Upcoming Appointments");
//            alert.setContentText("You do not have any upcoming appointments within the next 15 minutes.");
//            alert.showAndWait();
//        }


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


    }

    public void loginCancelBtnClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default in English
        loginLanguageCB.setValue("English");
        loginLanguageCB.getItems().addAll("English","French");

        // Default in French
//        ResourceBundle rb = ResourceBundle.getBundle("Nat_fr", Locale.getDefault());
//        if(Locale.getDefault().getLanguage().equals("fr")) {
//
//            // Login page
//            loginLanguageCB.setValue("French");
//            loginUsernameLbl.setText(rb.getString("username"));
//            loginPasswordLbl.setText(rb.getString("password"));
//            loginLocationLbl.setText(rb.getString("location"));
//            loginLocationTxt.setText("France");
//            loginSubmitBtn.setText(rb.getString("login"));
//            loginCancelBtn.setText(rb.getString("cancel"));
//
//
//            System.out.println(rb.getString("username") + " " + rb.getString("password"));
//        }

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
