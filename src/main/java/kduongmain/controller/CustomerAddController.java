package kduongmain.controller;

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
import javafx.stage.Stage;
import kduongmain.MainApplication;
import kduongmain.model.FirstLevelDivision;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerAddController implements Initializable {

    @FXML
    private TextField addCustomerAddressTxt;

    @FXML
    private Button addCustomerCancelBtn;

    @FXML
    private ComboBox<String> addCustomerCountryCB;

    @FXML
    private ComboBox<String> addCustomerDivisionCB;

    @FXML
    private TextField addCustomerIdTxt;

    @FXML
    private TextField addCustomerNameTxt;

    @FXML
    private TextField addCustomerPhoneNumberTxt;

    @FXML
    private TextField addCustomerPostalTxt;

    @FXML
    private Button addCustomerSaveBtn;

    @FXML
    private Label addCustomerErrorLbl;

    Stage stage;
    Parent scene;

    @FXML
    void onAddCustomerCancelBtnClicked(ActionEvent event) {

    }

    @FXML
    void onAddCustomerSaveBtnClicked(ActionEvent event) {
        String name = addCustomerNameTxt.getText();
        String address = addCustomerAddressTxt.getText();
        String postal = addCustomerPostalTxt.getText();
        String phone = addCustomerPhoneNumberTxt.getText();
        String country = (String) addCustomerCountryCB.getValue();
        String division = (String) addCustomerDivisionCB.getValue();

        if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty() || country == null || division == null) {
            addCustomerErrorLbl.setText("Please fill out all required fields.");
        } else {
            try {
                // Insert new customer into database
                String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Generate Timestamp
                LocalDateTime currentTime = LocalDateTime.now();
                ZoneId utcZone = ZoneId.of("UTC");
                ZonedDateTime utcTime = ZonedDateTime.of(currentTime, utcZone);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = utcTime.format(formatter);

                // Get the logged-in username
                String createdBy = LoginController.getCurrentUserName();

                // Get division and country id by name
                int divisionId = getDivisionId(division);

                // Prepared statement into the database
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, postal);
                ps.setString(4, phone);
                ps.setString(5, timestamp);
                ps.setString(6, createdBy);
                ps.setString(7, timestamp);
                ps.setString(8, createdBy);
                ps.setInt(9, divisionId);

                // Execute query
                ps.executeUpdate();
                System.out.println("SUCCESSFULLY ADDED CUSTOMER TO DATABASE");



                // Return to Customer View Page
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/CustomerView.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ADDING Customer");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }


    }
    /** Populates the division combo box. */
    private void populateDivisionCB() {
        String sql = "SELECT Division FROM first_level_divisions";
        ObservableList<String> divisions = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisions.add(rs.getString("Division"));
            }

            addCustomerDivisionCB.getItems().addAll(divisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Populates the country combo box */
    private void populateCountryCB() {
        String sql = "SELECT Country FROM countries";
        ObservableList<String> countries = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countries.add(rs.getString(("Country")));
            }

            addCustomerCountryCB.getItems().addAll(countries);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getDivisionId(String division) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);

        ResultSet rs = ps.executeQuery();

        int divisionId = -1;

        if (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }

        return divisionId;
    }

    private int getCountryId (String country) throws SQLException {
        String sql = "SELECT Country_ID FROM countries WHERE Country = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, country);

        ResultSet rs = ps.executeQuery();

        int countryId = -1;

        if (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }

        return countryId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateDivisionCB();
        populateCountryCB();
    }
}
