package kduong.c195software.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private Label loginErrorLbl;

    @FXML
    private Label loginLanguageLbl;

    @FXML
    private Label loginLocationLbl;

    @FXML
    private PasswordField loginPasswordTxt;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private TextField loginUserTxt;


    @FXML
    void onLoginSubmitBtnClicked(ActionEvent event) {
        // Validate if username is empty
        if(loginUserTxt.getText().isEmpty()) {
           loginErrorLbl.setText("Please provide a username/password");
        }
        // Validate if password is empty
        if(loginPasswordTxt.getText().isEmpty()) {
            loginErrorLbl.setText("Please provide a username/password");
        }
    }

}
