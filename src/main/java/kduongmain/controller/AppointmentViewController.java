package kduongmain.controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppointmentViewController implements Initializable {
    @FXML
    private ToggleGroup AppointmentTG;

    @FXML
    private Button appointmentAddBtn;

    @FXML
    private Button appointmentBackBtn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentContactCol;

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
    private RadioButton appointmentAllRB;

    @FXML
    private RadioButton appointmentMonthRB;

    @FXML
    private RadioButton appointmentWeekRB;
    Parent scene;
    Stage stage;

    @FXML
    void appointmentAllRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getAllAppointments());
        appointmentTableView.refresh();
    }

    @FXML
    void appointmentMonthRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getMonthlyAppointments());
        appointmentTableView.refresh();
    }

    @FXML
    void appointmentWeekRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getWeeklyAppointments());
        appointmentTableView.refresh();
    }

    @FXML
    void onAppointmentAddBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AddAppointmentForm.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onAppointmentBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onAppointmentModifyBtnClicked(ActionEvent event) throws Exception {
        try {
            // Goes to modify customer fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/kduongmain/AppointmentModify.fxml"));
            loader.load();

            AppointmentModifyController AMController = loader.getController();
            if (appointmentTableView.getSelectionModel().getSelectedIndex() == -1) {
                throw new Exception("Error test message for modifying customer");
            }
            AMController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(Exception e){
            System.out.println("ERROR MODIFYING CUSTOMER: " + e.getMessage());
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (appointmentAllRB.isSelected()) {
            appointmentTableView.setItems(AppointmentQuery.getAllAppointments());

            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        } else if (appointmentWeekRB.isSelected()) {
            appointmentTableView.setItems(AppointmentQuery.getWeeklyAppointments());
        } else if (appointmentMonthRB.isSelected()) {
            appointmentTableView.setItems(AppointmentQuery.getMonthlyAppointments());
        }
    }
}
