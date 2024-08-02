package kduongmain.controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import kduongmain.model.Appointment;
import kduongmain.model.Contact;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentViewController implements Initializable {
    @FXML
    private ToggleGroup AppointmentTG;

    @FXML
    private Button appointmentAddBtn;

    @FXML
    private Button appointmentBackBtn;

    @FXML
    private TableColumn<Contact, Integer> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    @FXML
    private TableColumn<AppointmentQuery, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private Button appointmentModifyBtn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    @FXML
    private TableColumn<AppointmentQuery, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    void onAppointmentAddBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAppointmentBackBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAppointmentModifyBtnClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTableView.setItems(AppointmentQuery.getAllAppointments());

            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));


        } catch (SQLException e) {
            System.out.println("ERROR GETTING ALL APPOINTMENTS: " + e.getMessage());
        }

    }
}
