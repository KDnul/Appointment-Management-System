package kduongmain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomerViewController {

    @FXML
    private Button customerAddBtn;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private Button customerBackBtn;

    @FXML
    private TableColumn<?, ?> customerCountryCol;

    @FXML
    private Button customerDelBtn;

    @FXML
    private TableColumn<?, ?> customerDivisionCol;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private Button customerModBtn;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> customerPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> customerPostalCodeCol;

    @FXML
    private TableView<?> customerTableVIew;

    Stage stage;
    Parent scene;

    @FXML
    void onCustomerAddBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AddCustomerForm.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @FXML
    void onCustomerBackBtnClicked(ActionEvent event) {

    }

    @FXML
    void onCustomerDelBtnClicked(ActionEvent event) {

    }

    @FXML
    void onCustomerModBtnClicked(ActionEvent event) {

    }

}
