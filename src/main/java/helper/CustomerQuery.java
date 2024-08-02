package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kduongmain.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerQuery {
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /** Method to put all customers to a list to retrieve for later use */
//    public static ObservableList<Customer> getAllCustomers() throws SQLException {
//        try {
//            String sql = "SELECT * FROM CUSTOMERS";
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                int customerId = rs.getInt("Customer_ID");
//                String customerName = rs.getString("Customer_Name");
//                String customerAddress = rs.getString("Address");
//                String customerPostalCode = rs.getString("Postal_Code");
//                String customerPhone = rs.getString("Phone");
//                int customerDivisionId = rs.getInt("Division_ID");
//                Customer customer = new Customer(customerId, customerName,customerAddress,customerPostalCode,customerPhone,customerDivisionId);
//                allCustomers.add(customer);
//            }
//        }catch (SQLException e) {
//            System.out.println("ERROR WITH CUSTOMER QUERY OBSERVABLE LIST: " + e.getMessage());
//        }
//        return allCustomers;
//    }

    /** Method to delete appointment based on customerId */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }



}
