package kduong.c195software.model;

import java.util.Locale;

/**
 *
 * @author Kelvin Duong
 */

public class Customer {
    private int customerId; // Auto-generated ID
    private String customerName;
    private String customerAddress;
    private int customerPostalCode;
    private String customerPhoneNumber;
    private int customerDivisionId;

    /** Customer Constructor
     @param customerId Customer Id
     @param customerName Customer Name
     @param customerAddress Customer Address
     @param customerPostalCode Customer Postal Code
     @param customerPhoneNumber Customer Phone Number
     @param customerDivisionId  Customer Zone Id. */
    public Customer(int customerId, String customerName, String customerAddress, int customerPostalCode, String customerPhoneNumber, int customerDivisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerDivisionId = customerDivisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(int customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public int getCustomerDivisionId() {
        return customerDivisionId;
    }

    public void setCustomerDivisionId(int customerDivisionId) {
        this.customerDivisionId = customerDivisionId;
    }
}
