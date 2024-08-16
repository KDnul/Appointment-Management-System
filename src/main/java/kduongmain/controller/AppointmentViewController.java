package kduongmain.controller;

import helper.AppointmentQuery;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
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

    /** Action event listener for when the "All" radio button is clicked. This sets the table to view all appointments in the database. */
    @FXML
    void appointmentAllRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getAllAppointments());
        appointmentTableView.refresh();
    }

    /** Action event listener for when the "Month" radio button is clicked. This sets the table to view all appointments that is occurring this month. */
    @FXML
    void appointmentMonthRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getMonthlyAppointments());
        appointmentTableView.refresh();
    }

    /** Action listener for when the "Week" radio button is clicked. This sets the table to view all appointments that is occurring on the current week. */
    @FXML
    void appointmentWeekRBClicked(ActionEvent event) {
        appointmentTableView.setItems(AppointmentQuery.getWeeklyAppointments());
        appointmentTableView.refresh();
    }

    /** Action event that sends the user to the Add Appointment FXML to add an appointment to the database. */
    @FXML
    void onAppointmentAddBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AddAppointmentForm.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Action event that sends the user back to the main menu FXML. */
    @FXML
    void onAppointmentBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Actione ven that sends the user to the Modify Appointment FXML to modify the selected appointment. */
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

    /** Action event to delete the currently selected appointment. */
    @FXML
    void onAppointmentDelBtn(ActionEvent event) {
        try {
            Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete appointment ID: " + appointment.getId() + " of type: " + appointment.getType() + "\nAppointment will be removed from the table.");
            if (appointmentTableView.getSelectionModel().getSelectedIndex() == -1) {
                throw new Exception("Please select an appointment to delete");
            }

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentQuery.delete(appointment.getId());
                if(appointmentAllRB.isSelected()) {
                    appointmentTableView.setItems(AppointmentQuery.getAllAppointments());
                    appointmentTableView.refresh();
                } else if (appointmentWeekRB.isSelected()) {
                    appointmentTableView.setItems(AppointmentQuery.getWeeklyAppointments());
                    appointmentTableView.refresh();
                } else if (appointmentMonthRB.isSelected()) {
                    appointmentTableView.setItems(AppointmentQuery.getMonthlyAppointments());
                    appointmentTableView.refresh();
                }
                System.out.print("DELETING APPOINTMENT: " + appointment);
            }
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Deleting Appointment");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /** Initial setup for the Appointment View FXML. Shows All appointments by default. */
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
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        } else if (appointmentWeekRB.isSelected()) {
            appointmentTableView.setItems(AppointmentQuery.getWeeklyAppointments());
        } else if (appointmentMonthRB.isSelected()) {
            appointmentTableView.setItems(AppointmentQuery.getMonthlyAppointments());
        }
    }
}
