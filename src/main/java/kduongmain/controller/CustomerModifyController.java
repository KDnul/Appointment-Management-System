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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kduongmain.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerModifyController implements Initializable {

    @FXML
    private Label modCustomerErrorLbl;

    @FXML
    private TextField modCustomerIdTxt;

    @FXML
    private TextField modCustomerAddressTxt;

    @FXML
    private Button modCustomerCancelBtn;

    @FXML
    private ComboBox<String> modCustomerCountryCB;

    @FXML
    private ComboBox<String> modCustomerDivisionCB;

    @FXML
    private TextField modCustomerNameTxt;

    @FXML
    private TextField modCustomerPhoneNumberTxt;

    @FXML
    private TextField modCustomerPostalTxt;

    @FXML
    private Button modCustomerSaveBtn;

    Parent scene;
    Stage stage;

    @FXML
    void onModCustomerCancelBtnClicked(ActionEvent event) {

    }

    @FXML
    void onModCustomerSaveBtnClicked(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(modCustomerIdTxt.getText());
        String name = modCustomerNameTxt.getText();
        String address = modCustomerAddressTxt.getText();
        String postal = modCustomerPostalTxt.getText();
        String phone = modCustomerPhoneNumberTxt.getText();
        String country = (String) modCustomerCountryCB.getValue();
        String division = (String) modCustomerDivisionCB.getValue();

        if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty() || country == null || division == null) {
            modCustomerErrorLbl.setText("Please fill out all required fields.");
        } else {
            // Generate Timestamp
            LocalDateTime currentTime = LocalDateTime.now();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcTime = ZonedDateTime.of(currentTime, utcZone);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = Timestamp.valueOf(utcTime.format(formatter));

            // Get the logged-in username
            String createdBy = LoginController.getCurrentUserName();

            // Get division and country id by name
            int divisionId = getDivisionId(division);

            // Insert customer into database
            CustomerQuery.update(id,name, address, postal, phone, timestamp, createdBy, divisionId);

            // Return to Customer View Page
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/CustomerView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    @FXML
    void onModCountryCBFilter(ActionEvent event) {
        String selectedDivision = modCustomerDivisionCB.getValue();


        System.out.println("ATTEMPTING TO FILTER COUNTRY.");

        if(selectedDivision != null) {
            try {
                String associatedCountry = associatedCountry(selectedDivision);
                if(associatedCountry != null) {
                    modCustomerCountryCB.setValue(associatedCountry);
                }
            } catch (SQLException e) {
                System.out.println("ERROR FILTERING COUNTRY: " + e.getMessage());
            }
        }

    }

    @FXML
    void onModDivisionCBFilter(ActionEvent event) {
        String selectedCountry = modCustomerCountryCB.getValue();
        System.out.println("DIVISION CB FILTER ACTIVATED");

        if(selectedCountry != null) {
            try {
                List<String> divisions = associatedDivision(selectedCountry);

                modCustomerDivisionCB.getItems().clear();
                modCustomerDivisionCB.getItems().addAll(divisions);

                System.out.println("Divisions for country " + selectedCountry + ": " + divisions);
            }catch (SQLException e) {
                System.out.println("ERROR FILTERING DIVISIONS BY COUNTRY: " + e.getMessage());
            }
        } else {
            System.out.println("Country not found by division.");
        }

    }

    public void sendCustomer(Customer customer) {
        modCustomerIdTxt.setText(String.valueOf(customer.getId()));
        modCustomerNameTxt.setText(customer.getName());
        modCustomerAddressTxt.setText(customer.getAddress());
        modCustomerPostalTxt.setText(customer.getPostalCode());
        modCustomerPhoneNumberTxt.setText(customer.getPhoneNumber());
        modCustomerCountryCB.setValue(customer.getCountry());
        modCustomerDivisionCB.setValue(customer.getDivision());

    }

    /** Gets associated country by division name */
    private String associatedCountry(String division) throws SQLException {
        String associatedCountry = null;

        String sql = "SELECT c.Country FROM countries c " +
                "JOIN first_level_divisions f on c.Country_ID = f.Country_ID " +
                "WHERE f.Division = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);

            try {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    associatedCountry = rs.getString("Country");
                }
            }catch (SQLException e) {
                System.out.println("ERROR GETTING ASSOCIATED COUNTRY: " + e.getMessage());
            }

        }catch (SQLException e) {
            System.out.println("ERROR GETTING ASSOCIATED COUNTRY: " + e.getMessage());
        }

        return associatedCountry;

    }
    /** Gets associated division by country */
    private ObservableList<String> associatedDivision(String country) throws SQLException {
        ObservableList<String> divisions = FXCollections.observableArrayList();
//        List<String> divisions = new ArrayList<>();

        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = " +
                "(SELECT Country_ID FROM countries WHERE Country = ?)";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, country);

            // Execute query
            try {
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    divisions.add(rs.getString("Division"));
                }
            }catch (SQLException e) {
                System.out.println("ERROR GETTING ASSOCIATED DIVISION BY COUNTRY: " + e.getMessage());
            }
        }catch (SQLException e) {
            System.out.println("ERROR GETTING ASSOCIATED DIVISION BY COUNTRY: " + e.getMessage());
        }

        return divisions;
    }

    private void populateCountryCB() {
        String sql = "SELECT Country FROM countries";
        ObservableList<String> countries = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countries.add(rs.getString(("Country")));
            }

            modCustomerCountryCB.getItems().addAll(countries);

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

            modCustomerDivisionCB.getItems().addAll(divisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Gets Division id based on name */
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountryCB();
        populateDivisionCB();

    }
}
