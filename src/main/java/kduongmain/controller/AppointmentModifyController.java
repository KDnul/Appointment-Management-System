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
    private ComboBox<String> modAppointmentEndTimeCB;

    @FXML
    private TextField modAppointmentId;

    @FXML
    private TextField modAppointmentLocationTxt;

    @FXML
    private Button modAppointmentSaveBtn;

    @FXML
    private DatePicker modAppointmentStartDateDP;

    @FXML
    private ComboBox<String> modAppointmentStartTimeCB;

    @FXML
    private TextField modAppointmentTitleTxt;

    @FXML
    private TextField modAppointmentTypeTxt;

    @FXML
    private ComboBox<String> modAppointmentUserIdCB;

    Parent scene;
    Stage stage;

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

    @FXML
    void onModAppointmentSaveBtnClicked(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(modAppointmentId.getText());
        String title = modAppointmentTitleTxt.getText();
        String description = modAppointmentDescriptionTxt.getText();
        String location = modAppointmentLocationTxt.getText();
        String type = modAppointmentTypeTxt.getText();
        String contact = modAppointmentContactCB.getValue();
        LocalDate startDate = modAppointmentStartDateDP.getValue();
        LocalTime startTime = LocalTime.parse(modAppointmentStartTimeCB.getValue());
        LocalDate endDate = modAppointmentEndDateDP.getValue();
        LocalTime endTime = LocalTime.parse(modAppointmentEndTimeCB.getValue());
        String user = modAppointmentUserIdCB.getValue();
        String customer = modAppointmentCustomerIdCB.getValue();

        LocalDateTime appointmentStart = LocalDateTime.of(modAppointmentStartDateDP.getValue(), LocalTime.parse(modAppointmentStartTimeCB.getValue()));
        LocalDateTime appointmentEnd = LocalDateTime.of(modAppointmentEndDateDP.getValue(), LocalTime.parse(modAppointmentEndTimeCB.getValue()));

        int userId = getUserIdByName(user);
        int customerId = getCustomerIdByName(customer);
        int contactId = getContactIdByName(contact);


        // Convert local times to UTC Instant objects
        Instant startDateTimeInstant = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault()).toInstant();
        Instant endDateTimeInstant = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault()).toInstant();

        // Convert Instant objects to strings without "T" and "Z"
        String startDateTimeUTCString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startDateTimeInstant.atZone(ZoneOffset.UTC));
        String endDateTimeUTCString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(endDateTimeInstant.atZone(ZoneOffset.UTC));

        try {
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startDate == null || endDate == null || user.isEmpty() || customer.isEmpty() || contact == null) {
                addAppointmentErrorLbl.setText("Please fill out all required fields.");
            }else if (!isInBusinessHours(startDate, startTime, endDate, endTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Your appointment must be within business hours from 8:00 a.m to 10:00 p.m ET, including weekends.");
                alert.showAndWait();

            }else if (isOverlapping(customerId, startDateTimeUTCString, endDateTimeUTCString)) {
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

                AppointmentQuery.update(title, description, location, type, appointmentStart, appointmentEnd,timestamp, createdBy, customerId,userId,contactId, id);

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

    public void sendAppointment(Appointment appointment) {
        modAppointmentId.setText(String.valueOf(appointment.getId()));
        modAppointmentTitleTxt.setText(appointment.getTitle());
        modAppointmentDescriptionTxt.setText(appointment.getDescription());
        modAppointmentLocationTxt.setText(appointment.getLocation());
        modAppointmentContactCB.setValue(appointment.getContactName());
        modAppointmentTypeTxt.setText(appointment.getType());
        modAppointmentStartDateDP.setValue(appointment.getTimeStart().toLocalDate());
        modAppointmentStartTimeCB.setValue(String.valueOf(appointment.getTimeStart().toLocalTime()));
        modAppointmentEndDateDP.setValue(appointment.getTimeEnd().toLocalDate());
        modAppointmentEndTimeCB.setValue(String.valueOf(appointment.getTimeEnd().toLocalTime()));
        modAppointmentCustomerIdCB.setValue(appointment.getCustomerName());
        modAppointmentUserIdCB.setValue(appointment.getUserName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateEndTimeCB();
            populateStartTimeCB();
            populateUserCB();
            populateCustomerCB();
            populateContactCB();

            modAppointmentUserIdCB.setValue(LoginController.getCurrentUserName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populate options for start times in the combo box.
     */
    private void populateStartTimeCB() {
        for (int hour = 0; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String hourStr = String.format("%02d", hour);
                String minuteStr = String.format("%02d", minute);
                modAppointmentStartTimeCB.getItems().add(hourStr + ":" + minuteStr);
            }
        }
        for (int hour = 0; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String hourStr = String.format("%02d", hour + 12); // Convert to PM
                String minuteStr = String.format("%02d", minute);
                modAppointmentStartTimeCB.getItems().add(hourStr + ":" + minuteStr);
            }
        }
    }

    /**
     * Populate options for end times in the combo box.
     */
    private void populateEndTimeCB() {
        for (int hour = 0; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String hourStr = String.format("%02d", hour);
                String minuteStr = String.format("%02d", minute);
                modAppointmentEndTimeCB.getItems().add(hourStr + ":" + minuteStr);
            }
        }
        for (int hour = 0; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String hourStr = String.format("%02d", hour + 12); // Convert to PM
                String minuteStr = String.format("%02d", minute);
                modAppointmentEndTimeCB.getItems().add(hourStr + ":" + minuteStr);
            }
        }

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
            modAppointmentContactCB.getItems().addAll(contacts);
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
            modAppointmentUserIdCB.getItems().addAll(users);
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

        modAppointmentCustomerIdCB.getItems().addAll(customers);
    }

    /** Gets user id by username. */
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

    /** Gets contact id by contact name. */
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

    /** Gets customer id by customer name. */
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

    /** Method to check it appointments overlaps with existing appointments for customer */
    private boolean isOverlapping(int customerId, String startString, String endString) {
        try {
            System.out.println("Checking for overlapping appointments");

            System.out.println("isAppointmentOverLapping - customerId: " + customerId);
            System.out.println("isAppointmentOverLapping - start: " + startString);
            System.out.println("isAppointmentOverLapping - end: " + endString);

            // Query to retrieve existing appointments for the customer that overlap with the new appointment
            String sql = "SELECT COUNT(*) FROM Appointments WHERE Customer_ID = ? " +
                    "AND ((Start <= ? AND End >= ?) OR (Start <= ? AND End >= ?) OR (Start < ? AND End > ?))";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, startString);
            ps.setString(3, startString);
            ps.setString(4, endString);
            ps.setString(5, endString);
            ps.setString(6, startString);
            ps.setString(7, endString);

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

    /** Method to check if start and end times are within the business hours of the business. */
    private boolean isInBusinessHours(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        ZonedDateTime startDateTime = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());
        ZonedDateTime endDateTime = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());

        // Check if day falls within Monday to Friday
        if (startDateTime.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
                startDateTime.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()) {
            // Check if appointment start and end time are within business hours (8:00 a.m. to 10:00 p.m. ET)
            if (startDateTime.getHour() >= 8 && startDateTime.getHour() < 22 &&
                    endDateTime.getHour() >= 8 && endDateTime.getHour() < 22) {
                return true;
            }
        } else {
            // Check if appointment start and end time are within business hours (8:00 a.m. to 10:00 p.m. ET) on weekends
            if ((startDateTime.getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue() ||
                    startDateTime.getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue()) &&
                    startDateTime.getHour() >= 8 && startDateTime.getHour() < 22 &&
                    endDateTime.getHour() >= 8 && endDateTime.getHour() < 22) {
                return true;
            }
        }
        return false;
    }
}
