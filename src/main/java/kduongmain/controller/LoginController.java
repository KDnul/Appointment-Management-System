package kduongmain.controller;

import helper.UserQuery;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
//    private static ObservableList<String> languageList = FXCollections.observableArrayList("English","French");

    @FXML
    private Label loginErrorLbl;

    @FXML
    private ComboBox<String> loginLanguageCB;

    @FXML
    private Label loginLocationLbl;

    @FXML
    private Label loginLocationTxt;

    @FXML
    private Label loginPasswordLbl;

    @FXML
    private PasswordField loginPasswordTxt;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private TextField loginUserTxt;

    @FXML
    private Label loginUsernameLbl;

    @FXML
    private Button loginCancelBtn;

    // FXML Event Handlers
    Stage stage;
    Parent scene;


    @FXML
    void onLoginSubmitBtnClicked(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            try {
                if(loginUserTxt.getText().isEmpty() || loginPasswordTxt.getText().isEmpty()) {
                    loginErrorLbl.setText(rb.getString("Incorrect"));
                }
            }catch(Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        String loginUser = loginUserTxt.getText();
        String loginPass = loginPasswordTxt.getText();

        try {
            if (UserQuery.authenticateUser(loginUser, loginPass) > -1) {
                System.out.println("SUCCESS AUTHENTICATING USER");
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/test.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                loginErrorLbl.setText(rb.getString("Incorrect"));
            }
        } catch(Exception e) {
            System.out.println("ERROR AUTHENTICATING USER " +  e);
        }


    }

    public void loginCancelBtnClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default in English
        loginLanguageCB.setValue("English");
        loginLanguageCB.getItems().addAll("English","French");

        // Default in French
//        ResourceBundle rb = ResourceBundle.getBundle("Nat_fr", Locale.getDefault());
//        if(Locale.getDefault().getLanguage().equals("fr")) {
//
//            // Login page
//            loginLanguageCB.setValue("French");
//            loginUsernameLbl.setText(rb.getString("username"));
//            loginPasswordLbl.setText(rb.getString("password"));
//            loginLocationLbl.setText(rb.getString("location"));
//            loginLocationTxt.setText("France");
//            loginSubmitBtn.setText(rb.getString("login"));
//            loginCancelBtn.setText(rb.getString("cancel"));
//
//
//            System.out.println(rb.getString("username") + " " + rb.getString("password"));
//        }

        try{
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

            ZoneId zone = ZoneId.systemDefault();

            //removed
            //loginScreenLocationField.setText(Locale.getDefault().getDisplayCountry());
            loginLocationTxt.setText(String.valueOf(zone));

            if(Locale.getDefault().getLanguage().equals("fr")) {
                loginLanguageCB.setValue("French");
                loginUsernameLbl.setText(rb.getString("username"));
                loginPasswordLbl.setText(rb.getString("password"));
                loginSubmitBtn.setText(rb.getString("Login"));
                loginCancelBtn.setText(rb.getString("Exit"));
                loginLocationLbl.setText(rb.getString("Location"));

            }

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e){
            System.out.println(e);
        }


    }
}
