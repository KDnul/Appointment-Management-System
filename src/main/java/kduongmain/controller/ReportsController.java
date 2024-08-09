package kduongmain.controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kduongmain.model.Appointment;
import kduongmain.model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TableColumn<Appointment, String> reportsScheduleDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> reportsApptMonthCol;

    @FXML
    private TableView<Appointment> reportsApptMonthTableView;

    @FXML
    private TableColumn<Appointment, Integer> reportsApptMonthTotalCol;

    @FXML
    private TableColumn<Appointment, Integer> reportsApptScheduleIdCol;

    @FXML
    private TableColumn<Appointment, String> reportsApptTypeCol;

    @FXML
    private TableView<Appointment> reportsApptTypeTableView;

    @FXML
    private TableColumn<Appointment, Integer> reportsApptTypeTotalCol;
    @FXML
    private TableView<Appointment> reportsScheduleContactTableView;

    @FXML
    private Button reportsBackBtn;

    @FXML
    private Tab reportsContactScheduleTab;

    @FXML
    private TableColumn<?, ?> reportsCountryCol;

    @FXML
    private TableView<?> reportsCountryCustomerTableView;

    @FXML
    private ComboBox<String> reportsContactCB;

    @FXML
    private TableColumn<?, ?> reportsCustomerTotalCol;

    @FXML
    private TableColumn<Appointment, Integer> reportsScheduleContactCol;

    @FXML
    private TableColumn<Appointment, Integer> reportsScheduleCustomerCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> reportsScheduleEndDateTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> reportsScheduleStartDateTimeCol;

    @FXML
    private TableColumn<Appointment, String > reportsScheduleTitleCol;

    @FXML
    private TableColumn<Appointment, String > reportsScheduleTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> reportsScheduleUserCol;

    @FXML
    private Tab reportsTotalApptTab;

    Parent scene;
    Stage stage;


    @FXML
    void onReportsBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void setContactCBPopulate(ActionEvent event) throws SQLException {
        String contactName = String.valueOf(reportsContactCB.getValue());
        int contactID = ContactQuery.returnContactId(contactName);
        if (AppointmentQuery.getContactAppointments(contactID).isEmpty()) {
            reportsScheduleContactTableView.setPlaceholder(new Label(contactName + " has no appointments."));
            reportsScheduleContactTableView.refresh();
            for (int i = 0; i < reportsScheduleContactTableView.getItems().size(); i++) {
                reportsScheduleContactTableView.getItems().clear();
                reportsScheduleContactTableView.setPlaceholder(new Label(contactName + " has no appointments."));
            }
        } else {
            reportsScheduleContactTableView.setItems(AppointmentQuery.getContactAppointments(contactID));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Appointment Totals Tab
        reportsApptTypeTableView.setItems(AppointmentQuery.getNumAppointmentsByType());
        reportsApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportsApptTypeTotalCol.setCellValueFactory(new PropertyValueFactory<>("typeTotal"));

        // Appointment Months tab
        reportsApptMonthTableView.setItems(AppointmentQuery.getAppointmentMonthlyTotal());
        reportsApptMonthCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportsApptMonthTotalCol.setCellValueFactory(new PropertyValueFactory<>("typeTotal"));

        // Contact Schedule tab
        try {
            contactPopulate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reportsApptScheduleIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        reportsScheduleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportsScheduleDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportsScheduleContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        reportsScheduleTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportsScheduleStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
        reportsScheduleEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
        reportsScheduleCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        reportsScheduleUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));



    }

    public void contactPopulate() throws SQLException {
        String query = "SELECT contact_name FROM contacts";
        List<String> contacts = new ArrayList<>();

        try (PreparedStatement statement = JDBC.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                contacts.add(resultSet.getString("Contact_Name"));
            }
        }

        reportsContactCB.getItems().addAll(contacts);
        // Preselect the first contact name if the list is not empty
        if (!contacts.isEmpty()) {
            reportsContactCB.setValue(contacts.get(0));
        }
        String selectedContact = reportsContactCB.getValue();
        if (selectedContact != null) {
            try {
                // Retrieve contact ID for the selected contact name
                int contactId = getContactId(selectedContact);

                // Fetch appointments associated with the selected contact ID
                ObservableList<Appointment> appointments = getAppointmentsForContact(contactId);

                // Display appointments in the table view
                reportsScheduleContactTableView.setItems(appointments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<Appointment> getAppointmentsForContact(int contactId) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT a.appointment_id, a.title, a.description, a.location, a.contact_id, a.type, a.start, a.end, a.customer_id, a.user_id, a.create_Date, a.created_By, a.last_update, a.last_updated_by, " +
                "c.customer_name AS customer_name, u.user_name AS user_name, con.contact_name AS contact_name " +
                "FROM appointments a " +
                "JOIN customers c ON a.customer_id = c.customer_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "JOIN contacts con ON a.contact_id = con.contact_id " +
                "WHERE con.contact_id = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, contactId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    String contact = rs.getString("contact_name");
                    String user = rs.getString("user_name");
                    String customer = rs.getString("customer_name");
                    Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactID, customer, user, contact);
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }
    private LocalDateTime parseToLocalDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    private int getContactId(String contactName) throws SQLException {
        String sql = "SELECT contact_id FROM contacts WHERE contact_name = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setString(1, contactName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("contact_id");
                }
            }
        }
        throw new SQLException("Contact ID not found for name: " + contactName);
    }

}
