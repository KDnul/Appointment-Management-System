package kduongmain.controller;

import helper.AppointmentQuery;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {

    @FXML
    private Button addAppointmentCancelBtn;

    @FXML
    private ComboBox<String> addAppointmentContactCB;

    @FXML
    private ComboBox<String> addAppointmentCustomerIdCB;

    @FXML
    private TextField addAppointmentDescriptionTxt;

    @FXML
    private DatePicker addAppointmentEndDateDP;

    @FXML
    private ComboBox<LocalTime> addAppointmentEndTimeCB;

    @FXML
    private TextField addAppointmentId;

    @FXML
    private TextField addAppointmentLocationTxt;

    @FXML
    private Button addAppointmentSaveBtn;

    @FXML
    private DatePicker addAppointmentStartDateDP;

    @FXML
    private ComboBox<LocalTime> addAppointmentStartTimeCB;

    @FXML
    private TextField addAppointmentTitleTxt;

    @FXML
    private TextField addAppointmentTypeTxt;

    @FXML
    private ComboBox<String> addAppointmentUserIdCB;
    @FXML
    private Label addAppointmentErrorLbl;

    Parent scene;
    Stage stage;

    /** Action event for when the user clicks the cancel button. Sends the user back to the Appointment View FXML. */
    @FXML
    void onAddAppointmentCancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?\nAll values will be discarded.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Return to Appointment View Page
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AppointmentView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Action event for when the user clicks the save button. Grabs all the fields in the form and inserts it into an appointment class
     * .If a field is missing, throws an error to the user to correctly fill out all fields. If all fields are filled, use the Appointment Query Insert method to
     * add the appointment to the database. */
    @FXML
    void onAddAppointmentSaveBtnClicked(ActionEvent event) throws SQLException {
        String title = addAppointmentTitleTxt.getText();
        String description = addAppointmentDescriptionTxt.getText();
        String location = addAppointmentLocationTxt.getText();
        String type = addAppointmentTypeTxt.getText();
        String contact = addAppointmentContactCB.getValue();
        LocalDate startDate = addAppointmentStartDateDP.getValue();
        LocalTime startTime = addAppointmentStartTimeCB.getValue();
        LocalDate endDate = addAppointmentEndDateDP.getValue();
        LocalTime endTime = addAppointmentEndTimeCB.getValue();
        String user = addAppointmentUserIdCB.getValue();
        String customer = addAppointmentCustomerIdCB.getValue();

        int userId = getUserIdByName(user);
        int customerId = getCustomerIdByName(customer);
        int contactId = getContactIdByName(contact);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        System.out.println("TIMESTAMP OF START DATE TIME IN PST: " + Timestamp.valueOf(startDateTime));

        try {
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startDate == null || endDate == null || user.isEmpty() || customer.isEmpty() || contact == null) {
                addAppointmentErrorLbl.setText("Please fill out all required fields.");
            }else if (!isInBusinessHours(startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Your appointment must be within business hours from 8:00 a.m to 10:00 p.m ET, including weekends.");
                alert.showAndWait();

            }else if (isOverlapping(customerId, Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Appointment overlaps with existing appointments for this customer.");
                alert.showAndWait();
            } else {
                // Generate Timestamp
                LocalDateTime currentTime = LocalDateTime.now();
                ZoneId utcZone = ZoneId.of("UTC");
                ZonedDateTime utcTime = ZonedDateTime.of(currentTime, utcZone);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = Timestamp.valueOf(utcTime.format(formatter));

                // Get the logged-in username
                String createdBy = LoginController.getCurrentUserName();

                AppointmentQuery.add(title, description, location, type, startDateTime, endDateTime,LocalDateTime.now(), createdBy, timestamp, createdBy, customerId,userId,contactId);

                // Return to Customer View Page
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AppointmentView.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (Exception e) {
            System.out.println("ERROR ADDING APPOINTMENT: " + e.getMessage());
        }
    }

    /** Initial setup for Appointment Add FXML. Pre-populates the combo boxes. The LAMBDA EXPRESSIONS also helps populates the  time combo box to increment every 15 minutes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addAppointmentEndTimeCB.setItems(getTime());
            addAppointmentStartTimeCB.setItems(getTime());
            populateUserCB();
            populateCustomerCB();
            populateContactCB();

            // Lambda Expressions
            addAppointmentStartDateDP.valueProperty().addListener((ov, oldValueDate, newValueDate) -> addAppointmentEndDateDP.setValue(newValueDate.plusDays(0)));
            addAppointmentStartTimeCB.valueProperty().addListener((ov1, oldValueTime, newValueTime) -> addAppointmentEndTimeCB.setValue(newValueTime.plusMinutes(15)));

            addAppointmentUserIdCB.setValue(LoginController.getCurrentUserName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Populates the time Combo Box. */
    public static ObservableList<LocalTime> getTime() {
        ObservableList<LocalTime> appointmentTimeList = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(1, 00);
        LocalTime end = LocalTime.MIDNIGHT.minusHours(1);

        while (start.isBefore(end.plusSeconds(2))) {
            appointmentTimeList.add(start);
            start = start.plusMinutes(15);
        }
        return appointmentTimeList;
    }

    /** Populates the contact combo box. */
    private void populateContactCB() throws SQLException {
        String sql = "SELECT contact_name FROM contacts";
        ObservableList<String> contacts = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contacts.add(rs.getString("Contact_Name"));
            }
            addAppointmentContactCB.getItems().addAll(contacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the user combo box.
     */
    private void populateUserCB() throws SQLException {
        String sql = "SELECT User_Name FROM users";
        ObservableList<String> users = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("User_Name"));
            }
            addAppointmentUserIdCB.getItems().addAll(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Populates the customer combo box. */
    private void populateCustomerCB() throws SQLException {
        String sql = "SELECT Customer_Name FROM customers";
        ObservableList<String> customers = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                customers.add(rs.getString("Customer_Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addAppointmentCustomerIdCB.getItems().addAll(customers);
    }

    /** Gets user id by username.
     * @param userName String value of username. */
    private int getUserIdByName(String userName) throws SQLException {
        String sql = "SELECT User_ID FROM users WHERE user_name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();
        int userId = -1; //Default value

        if(rs.next()) {
            userId = rs.getInt("User_ID");
        }
        return userId;
    }

    /** Gets contact id by contact name.
     * @param contactName String value of contact name. */
    private int getContactIdByName(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);

        ResultSet rs = ps.executeQuery();
        int contactId = -1; //Default value

        if(rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /** Gets customer id by customer name.
     * @param customerName String value of customer name. */
    private int getCustomerIdByName(String customerName) throws SQLException {
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);

        ResultSet rs = ps.executeQuery();
        int customerId = -1; //Default value

        if(rs.next()) {
            customerId = rs.getInt("Customer_ID");
        }
        return customerId;
    }

    /** Method to check it appointments overlaps with existing appointments for customer. If there is an overlapping appointment, throws an error to the user that
     * they cannot make an appointment that is over an existing one. If there are no errors, continue with the adding appointment process.
     * @param customerId Integer value of selected customer ID.
     * @param startTime Timestamp value of the current start time of the appointment being made.
     * @param endTime Timestamp value of the current end time of the appointment being made.*/
    private boolean isOverlapping(int customerId, Timestamp startTime, Timestamp endTime) {
        try {
            System.out.println("Checking for overlapping appointments");

            System.out.println("isAppointmentOverLapping - customerId: " + customerId);
            System.out.println("isAppointmentOverLapping - start: " + startTime);
            System.out.println("isAppointmentOverLapping - end: " + endTime);

            // Query to retrieve existing appointments for the customer that overlap with the new appointment
            String sql = "SELECT COUNT(*) FROM Appointments WHERE Customer_ID = ? " +
                    "AND ((Start <= ? AND End >= ?) OR (Start <= ? AND End >= ?) OR (Start < ? AND End > ?))";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setTimestamp(2, Timestamp.valueOf(startTime.toLocalDateTime()));
            ps.setTimestamp(3, Timestamp.valueOf(startTime.toLocalDateTime()));
            ps.setTimestamp(4, Timestamp.valueOf(endTime.toLocalDateTime()));
            ps.setTimestamp(5, Timestamp.valueOf(endTime.toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(startTime.toLocalDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(endTime.toLocalDateTime()));

            ResultSet rs = ps.executeQuery();

            // Check if there are overlapping appointments
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Overlapping appointments found.");
                return true;
            }

            // No overlapping appointments found
            System.out.println("No overlapping appointments found.");
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Method to check if start and end times are within the business hours of the business.
     * @param startDateTime Local Date Time value of the start hours of the business.
     * @param endDateTime Local Date Time value of the end hours of the business.*/
    private boolean isInBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        // Define business hours in ET
        ZoneId businessZone = ZoneId.of("America/New_York");
        // Business start and end time in ET
        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        System.out.println("BUSINESS START TIME: " + businessStart);
        System.out.println("BUSINESS END TIME: " + businessEnd);

        // Get user's local time zone
        ZoneId userTimeZone = ZoneId.systemDefault();

        // Convert business hours to user's local time zone
        // Create ZonedDateTime for business hours in ET
        ZonedDateTime businessStartTimeET = ZonedDateTime.of(LocalDate.now(), businessStart, businessZone);
        ZonedDateTime businessEndTimeET = ZonedDateTime.of(LocalDate.now(), businessEnd, businessZone);

        // Convert to user's local time zone
        LocalTime businessStartTimeLocal = businessStartTimeET.withZoneSameInstant(userTimeZone).toLocalTime();
        LocalTime businessEndTimeLocal = businessEndTimeET.withZoneSameInstant(userTimeZone).toLocalTime();

        // Extract the LocalTime components from the appointment times
        LocalTime apptStartTime = startDateTime.toLocalTime();
        LocalTime apptEndTime = endDateTime.toLocalTime();

        // Check if appointment times are within business hours
        boolean startsWithinBusinessHours = !apptStartTime.isBefore(businessStartTimeLocal) && !apptStartTime.isAfter(businessEndTimeLocal);
        boolean endsWithinBusinessHours = !apptEndTime.isBefore(businessStartTimeLocal) && !apptEndTime.isAfter(businessEndTimeLocal);

        return startsWithinBusinessHours && endsWithinBusinessHours;
    }

}