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
import kduongmain.model.Appointment;

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

public class AppointmentModifyController implements Initializable {

    @FXML
    private Label addAppointmentErrorLbl;

    @FXML
    private Button modAppointmentCancelBtn;

    @FXML
    private ComboBox<String> modAppointmentContactCB;

    @FXML
    private ComboBox<String> modAppointmentCustomerIdCB;

    @FXML
    private TextField modAppointmentDescriptionTxt;

    @FXML
    private DatePicker modAppointmentEndDateDP;

    @FXML
    private ComboBox<LocalTime> modAppointmentEndTimeCB;

    @FXML
    private TextField modAppointmentId;

    @FXML
    private TextField modAppointmentLocationTxt;

    @FXML
    private Button modAppointmentSaveBtn;

    @FXML
    private DatePicker modAppointmentStartDateDP;

    @FXML
    private ComboBox<LocalTime> modAppointmentStartTimeCB;

    @FXML
    private TextField modAppointmentTitleTxt;

    @FXML
    private TextField modAppointmentTypeTxt;

    @FXML
    private ComboBox<String> modAppointmentUserIdCB;

    Parent scene;
    Stage stage;

    /** Action even for when the cancel button is clicked. Discards all changes being made and sends the user back to the Appointment View FXML.*/
    @FXML
    void onModAppointmentCancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?\nAll values will be discarded.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Return to Customer View Page
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AppointmentView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Action event for when the user clicks the save button in the Appointment Modify Form FMXL. Grabs all current fields and updates the currently selected
     * appointment using the Appointment Update method. If there is an error in the form, throws an error to the user to fill out the forms correctly.*/
    @FXML
    void onModAppointmentSaveBtnClicked(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(modAppointmentId.getText());
        String title = modAppointmentTitleTxt.getText();
        String description = modAppointmentDescriptionTxt.getText();
        String location = modAppointmentLocationTxt.getText();
        String type = modAppointmentTypeTxt.getText();
        String contact = modAppointmentContactCB.getValue();
        LocalDate startDate = modAppointmentStartDateDP.getValue();
        LocalTime startTime = modAppointmentStartTimeCB.getValue();
        LocalDate endDate = modAppointmentEndDateDP.getValue();
        LocalTime endTime = modAppointmentEndTimeCB.getValue();
        String user = modAppointmentUserIdCB.getValue();
        String customer = modAppointmentCustomerIdCB.getValue();

        int userId = getUserIdByName(user);
        int customerId = getCustomerIdByName(customer);
        int contactId = getContactIdByName(contact);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        try {
            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startDate == null || endDate == null || user.isEmpty() || customer.isEmpty() || contact == null) {
                addAppointmentErrorLbl.setText("Please fill out all required fields.");
            } else if (!isInBusinessHours(startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Your appointment must be within business hours from 8:00 a.m to 10:00 p.m ET, including weekends.");
                alert.showAndWait();

            }else {
                // Generate Timestamp
                LocalDateTime currentTime = LocalDateTime.now();
                ZoneId utcZone = ZoneId.of("UTC");
                ZonedDateTime utcTime = ZonedDateTime.of(currentTime, utcZone);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = Timestamp.valueOf(utcTime.format(formatter));

                // Get the logged-in username
                String createdBy = LoginController.getCurrentUserName();

                AppointmentQuery.update(title, description, location, type, startDateTime, endDateTime, timestamp, createdBy, customerId, userId, contactId, id);

                // Return to Customer View Page
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AppointmentView.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }catch(Exception e){
            System.out.println("ERROR MODIFYING APPOINTMENT: " + e.getMessage());
        }
    }

    /** Grabs data form the Appointment view FXML of the current selected customer and populates the Appointment modify FXML with the appropriate values.
     * @param appointment Appointment class of selected Appointment in the Appointment view FXML.*/
    public void sendAppointment(Appointment appointment) {
        modAppointmentId.setText(String.valueOf(appointment.getId()));
        modAppointmentTitleTxt.setText(appointment.getTitle());
        modAppointmentDescriptionTxt.setText(appointment.getDescription());
        modAppointmentLocationTxt.setText(appointment.getLocation());
        modAppointmentContactCB.setValue(appointment.getContactName());
        modAppointmentTypeTxt.setText(appointment.getType());
        modAppointmentStartDateDP.setValue(appointment.getTimeStart().toLocalDate());
        modAppointmentStartTimeCB.setValue(appointment.getTimeStart().toLocalTime());
        modAppointmentEndDateDP.setValue(appointment.getTimeEnd().toLocalDate());
        modAppointmentEndTimeCB.setValue(appointment.getTimeEnd().toLocalTime());
        modAppointmentCustomerIdCB.setValue(appointment.getCustomerName());
        modAppointmentUserIdCB.setValue(appointment.getUserName());
    }

    /** Initial setup for Appointment Modify FXML. Pre-populates the combo boxes. The LAMBDA EXPRESSIONS also helps populates the  time combo box to increment every 15 minutes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            modAppointmentStartTimeCB.setItems(getTime());
            modAppointmentEndTimeCB.setItems(getTime());
            populateUserCB();
            populateCustomerCB();
            populateContactCB();

            // Lambda Expressions
            modAppointmentStartDateDP.valueProperty().addListener((ov, oldValueDate, newValueDate) -> modAppointmentEndDateDP.setValue(newValueDate.plusDays(0)));
            modAppointmentStartTimeCB.valueProperty().addListener((ov1, oldValueTime, newValueTime) -> modAppointmentEndTimeCB.setValue(newValueTime.plusMinutes(15)));

            modAppointmentUserIdCB.setValue(LoginController.getCurrentUserName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Populates the Start/End time Combo Box.*/
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

    /** Populates the contact combo box.*/
    private void populateContactCB() throws SQLException {
        String sql = "SELECT contact_name FROM contacts";
        ObservableList<String> contacts = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contacts.add(rs.getString("Contact_Name"));
            }
            modAppointmentContactCB.getItems().addAll(contacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the user combo box.*/
    private void populateUserCB() throws SQLException {
        String sql = "SELECT User_Name FROM users";
        ObservableList<String> users = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("User_Name"));
            }
            modAppointmentUserIdCB.getItems().addAll(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Populates the customer combo box.*/
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

        modAppointmentCustomerIdCB.getItems().addAll(customers);
    }

    /** Gets user id by username.
     * @param userName String value of current username.
     * @return Returns Integer for userId.*/
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
     * @param contactName String value of current contact.
     * @return contactId Returns Integer for contact Id.*/
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
     * @param customerName String value of current customer.
     * @return Returns Integer for customer Id.*/
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

    /** Method to check if start and end times are within the business hours of the business.
     * @param startDateTime Local Date Time of the start hours of the business.
     * @param endDateTime Loca Date Time of the end hours of the business.*/
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
