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
import javafx.stage.Stage;
import kduongmain.MainApplication;
import kduongmain.model.FirstLevelDivision;

import javax.xml.transform.Result;
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
import java.util.*;

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

    /** Action event for when the cancel button is clicked. Sends user back to the Customer View FXML. */
    @FXML
    void onAddCustomerCancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?\nAll values will be discarded.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // Return to Customer View Page
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/CustomerView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /** Action even for when the save button is clicked. Grabs values from the text fields and makes a new customer class based off them. If there are missing values, throw an error. If there
     * are no errors, insert the added customer into the database using the Customer Query insert method. */
    @FXML
    void onAddCustomerSaveBtnClicked(ActionEvent event) {
        String name = addCustomerNameTxt.getText();
        String address = addCustomerAddressTxt.getText();
        String postal = addCustomerPostalTxt.getText();
        String phone = addCustomerPhoneNumberTxt.getText();
        String country = addCustomerCountryCB.getValue();
        String division = addCustomerDivisionCB.getValue();

        if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty() || country == null || division == null) {
            addCustomerErrorLbl.setText("Please fill out all required fields.");
        } else {
            try {
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
                CustomerQuery.insert(name, address, postal, phone, LocalDateTime.now(), createdBy,timestamp, createdBy, divisionId);

                // Return to Customer View Page
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/kduongmain/CustomerView.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Action event for when a division is selected, automatically filters the country CB value to the appropriate country for that division. */
    @FXML
    void onCountryCBFilter(ActionEvent event) {
        String selectedDivision = addCustomerDivisionCB.getValue();


        System.out.println("ATTEMPTING TO FILTER COUNTRY.");

        if(selectedDivision != null) {
            try {
                String associatedCountry = associatedCountry(selectedDivision);
                    if(associatedCountry != null) {
                        addCustomerCountryCB.setValue(associatedCountry);
                    }
            } catch (SQLException e) {
                System.out.println("ERROR FILTERING COUNTRY: " + e.getMessage());
            }
        }
    }

    /** Action event for when a country is selected, automatically filters the country CB value to the appropriate divisions for that country. */
    @FXML
    void onDivisionCBFilter(ActionEvent event) throws SQLException {
        String selectedCountry = addCustomerCountryCB.getValue();
        System.out.println("DIVISION CB FILTER ACTIVATED");

        if(selectedCountry != null) {
            try {
                List<String> divisions = associatedDivision(selectedCountry);

                addCustomerDivisionCB.getItems().clear();
                addCustomerDivisionCB.getItems().addAll(divisions);

                System.out.println("Divisions for country " + selectedCountry + ": " + divisions);
            }catch (SQLException e) {
                System.out.println("ERROR FILTERING DIVISIONS BY COUNTRY: " + e.getMessage());
            }
        } else {
            System.out.println("Country not found by division.");
        }
    }


    /** Gets associated country by division name.
     * @param division String value of selected division. */
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

    /** Gets associated division by country.
     * @param country String value of selected country.
     * @return ObservableList<String> divisions. return a list of divisions for that country.  */
    private ObservableList<String> associatedDivision(String country) throws SQLException {
        ObservableList<String> divisions = FXCollections.observableArrayList();

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

    /** Gets Division id based on name.
     * @param division String value of selected division. */
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

    /** Initial setup for when customer add FXML is loaded. Automatically populates the division and country combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateDivisionCB();
        populateCountryCB();
    }
}
