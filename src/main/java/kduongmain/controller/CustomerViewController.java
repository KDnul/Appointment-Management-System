package kduongmain.controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
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
import kduongmain.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {

    @FXML
    private Button customerAddBtn;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private Button customerBackBtn;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private Button customerDelBtn;

    @FXML
    private TableColumn<Customer, Integer> customerDivisionCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private Button customerModBtn;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableView<Customer> customerTableVIew;

    Stage stage;
    Parent scene;

    /** Action event for when the customer add button is clicked. Brings uer to the customer add form FXML. */
    @FXML
    void onCustomerAddBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AddCustomerForm.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /** Action to bring the user back to the main menu. */
    @FXML
    void onCustomerBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Action for when deleting a selected customer. First removes all associated appointments from the selected customer before deleting the customer. If no associated appointments are found
     * , then delete the customer by themselves. */
    @FXML
    void onCustomerDelBtnClicked(ActionEvent event) {
        try {
            int delCount = 0;
            ObservableList<Customer> custList = CustomerQuery.getAllCustomers();
            ObservableList<Appointment> appointList = AppointmentQuery.getAllAppointments();
            Customer c = customerTableVIew.getSelectionModel().getSelectedItem();
            //If no customer selected -> get an error
            if (c == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NoSelection");
                alert.setContentText("Please select item to continue.");
                alert.showAndWait();
            }
            int selectCust = customerTableVIew.getSelectionModel().getSelectedItem().getId();
            for (Appointment a : appointList) {
                int appointCustID = a.getCustomerId();
                if (appointCustID == selectCust) {
                    delCount++;
                }
            }
            //if selected customer has appointments, we remove all associated appointments first, and after the customer
            if (delCount > 0) {
                Alert assocAppoint = new Alert(Alert.AlertType.WARNING);
                assocAppoint.setTitle("Alert");
                assocAppoint.setContentText("Specific customer has " + delCount + " associated appointments." +
                        "Pressing OK will delete BOTH customer and associated appointments.");
                assocAppoint.getButtonTypes().clear();
                assocAppoint.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                assocAppoint.showAndWait();
                if (assocAppoint.getResult() == ButtonType.OK) {
                    for (Appointment a : appointList) {
                        if (a.getCustomerId() == selectCust) {
                            AppointmentQuery.delete(a.getId());
                        }
                    }
                    CustomerQuery.delete(customerTableVIew.getSelectionModel().getSelectedItem().getId());
                    custList = CustomerQuery.getAllCustomers();
                    customerTableVIew.setItems(custList);
                    customerTableVIew.refresh();
                } else if (assocAppoint.getResult() == ButtonType.CANCEL) {
                    assocAppoint.close();
                }
            }

            //if selected customer has no appointments, we remove the customer
            if (delCount == 0) {
                Alert confDelete = new Alert(Alert.AlertType.WARNING);
                confDelete.setTitle("Alert");
                confDelete.setContentText("Are you sure you want to remove customer?");
                confDelete.getButtonTypes().clear();
                confDelete.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                confDelete.showAndWait();
                if (confDelete.getResult() == ButtonType.OK) {
                    CustomerQuery.delete(customerTableVIew.getSelectionModel().getSelectedItem().getId());
                    custList = CustomerQuery.getAllCustomers();
                    customerTableVIew.setItems(custList);
                    customerTableVIew.refresh();
                } else if (confDelete.getResult() == ButtonType.CANCEL) {
                    confDelete.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Action for when the customer modification button is clicked. Brings user to modify selected customer. */
   @FXML
    void onCustomerModBtnClicked(ActionEvent event) {
        try {
            // Goes to modify customer fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/kduongmain/CustomerModify.fxml"));
            loader.load();

            CustomerModifyController CMController = loader.getController();
            if(customerTableVIew.getSelectionModel().getSelectedIndex() == -1) {
                throw new Exception("Error test message for modifying customer");
            }
            CMController.sendCustomer(customerTableVIew.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e){
            System.out.println("ERROR MODIFYING CUSTOMER: " + e.getMessage());
        }

    }

    /** Initial setup for when loading the Customer View FXML. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Customers View Table
        try {
            customerTableVIew.setItems(CustomerQuery.getAllCustomers());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
