package kduongmain.controller;

import helper.CustomerQuery;
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
import kduongmain.MainApplication;
import kduongmain.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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

    @FXML
    void onCustomerAddBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/AddCustomerForm.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @FXML
    void onCustomerBackBtnClicked(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/MainMenu.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onCustomerDelBtnClicked(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer?\nCustomer will be removed from the table.");
            if(customerTableVIew.getSelectionModel().getSelectedIndex() == -1) {
                throw new Exception("Please select a Customer to delete");
            }
            Customer customer = customerTableVIew.getSelectionModel().getSelectedItem();

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                CustomerQuery.delete(customer.getId());
                customerTableVIew.setItems(CustomerQuery.getAllCustomers());
                customerTableVIew.refresh();
                System.out.print("DELETING Customer: " + customer);
            }

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Deleting Part");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

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

//    private void populateCustomerTable() throws SQLException {
//        String sql = "SELECT c.customer_id, c.customer_name, c.phone, c.address, c.postal_code, d.division, co.country " +
//                "FROM customers c " +
//                "JOIN first_level_divisions d ON c.division_id = d.division_id " +
//                "JOIN countries co ON d.country_id = co.country_id";
//
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            Customer customer = new Customer(
//                    rs.getInt("Customer_ID"),
//                    rs.getString("Customer_Name"),
//                    rs.getString("Address"),
//                    rs.getString("Postal_Code"),
//                    rs.getString("Phone"),
//                    rs.getInt("division")
//                    );
//            customers.add(customer);
//        }
//        customerTableVIew.setItems(customers);
//
//    }
}
