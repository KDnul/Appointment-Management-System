package kduongmain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;

public class AppointmentViewController {
    @FXML
    private ToggleGroup AppointmentTG;

    @FXML
    private Button appointmentAddBtn;

    @FXML
    private Button appointmentBackBtn;

    @FXML
    private TableColumn<?, ?> appointmentContactCol;

    @FXML
    private TableColumn<?, ?> appointmentCustomerIdCol;

    @FXML
    private TableColumn<?, ?> appointmentDescriptionCol;

    @FXML
    private TableColumn<?, ?> appointmentEndCol;

    @FXML
    private TableColumn<?, ?> appointmentIdCol;

    @FXML
    private TableColumn<?, ?> appointmentLocationCol;

    @FXML
    private Button appointmentModifyBtn;

    @FXML
    private TableColumn<?, ?> appointmentStartCol;

    @FXML
    private TableColumn<?, ?> appointmentTitleCol;

    @FXML
    private TableColumn<?, ?> appointmentTypeCol;

    @FXML
    private TableColumn<?, ?> appointmentUserIdCol;

    @FXML
    void onAppointmentAddBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAppointmentBackBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAppointmentModifyBtnClicked(ActionEvent event) {

    }
}
