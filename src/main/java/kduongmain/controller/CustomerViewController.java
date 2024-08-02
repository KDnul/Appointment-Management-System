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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kduongmain.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Customers View Table
        try {
            populateCustomerTable();

            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void populateCustomerTable() throws SQLException {
        String sql = "SELECT c.customer_id, c.customer_name, c.phone, c.address, c.postal_code, d.division, co.country " +
                "FROM customers c " +
                "JOIN first_level_divisions d ON c.division_id = d.division_id " +
                "JOIN countries co ON d.country_id = co.country_id";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("division")
                    );
            customers.add(customer);
        }
        customerTableVIew.setItems(customers);

    }
}
