package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Appointment;
import kduongmain.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerQuery {

    /** Method to put all customers to a list to retrieve for later use */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, " +
                    "customers.Create_Date, customers.Last_Update, customers.Created_By, customers.Last_Updated_By, " +
                    "customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, " +
                    "first_level_divisions.Country_ID, countries.Country FROM customers " +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID " +
                    "ORDER BY customers.Customer_ID ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Customer customer = new Customer(id, name, address, postalCode, phoneNumber, createDate, createdBy, lastUpdated, lastUpdatedBy, divisionId, countryId, division, country);
                allCustomers.add(customer);

            }
        } catch (SQLException e) {
            System.out.println("ERROR WITH CUSTOMER QUERY: " +  e.getMessage());
        }
        return allCustomers;
    }

    public static void insert(String name, String address, String postalCode,
                              String phone, LocalDateTime createDate, String createdBy, Timestamp lastUpdated,
                              String lastUpdatedBy, int divisionId) throws SQLException {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4,phone);
            ps.setTimestamp(5, Timestamp.valueOf(createDate));
            ps.setString(6, createdBy);
            ps.setTimestamp(7, lastUpdated);
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionId);

            // Execute Query
            ps.executeUpdate();
    }

    /** Method to update customer based on customer Id */
    public static void update(int id, String name, String address, String postalCode,
                              String phone,Timestamp lastUpdated,
                              String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Updated_By = ?, Last_Update = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4,phone);
        ps.setString(5, lastUpdatedBy);
        ps.setTimestamp(6, lastUpdated);
        ps.setInt(7, divisionId);
        ps.setInt(8, id);

        // Execute query
        ps.executeUpdate();
    }

    /** Method to delete appointment based on customerId */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }
}
