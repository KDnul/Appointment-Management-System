package kduongmain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class AppointmentAddController {
    @FXML
    private Button addAppointmentCancelBtn;

    @FXML
    private ComboBox<String> addAppointmentContactCB;

    @FXML
    private TextField addAppointmentCustomerIdTxt;

    @FXML
    private TextField addAppointmentDescriptionTxt;

    @FXML
    private DatePicker addAppointmentEndDateDP;

    @FXML
    private ComboBox<LocalDateTime> addAppointmentEndTimeCB;

    @FXML
    private TextField addAppointmentId;

    @FXML
    private TextField addAppointmentLocationTxt;

    @FXML
    private Button addAppointmentSaveBtn;

    @FXML
    private DatePicker addAppointmentStartDateDP;

    @FXML
    private ComboBox<LocalDateTime> addAppointmentStartTimeCB;

    @FXML
    private TextField addAppointmentTitleTxt;

    @FXML
    private TextField addAppointmentTypeTxt;

    @FXML
    private TextField addAppointmentUserIdTxt;

    @FXML
    void onAddAppointmentCancelBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAddAppointmentSaveBtnClicked(ActionEvent event) {

    }
}
