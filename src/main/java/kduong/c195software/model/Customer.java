package kduong.c195software.model;

import java.time.LocalDateTime;

/**
 *
 * @author Kelvin Duong
 */

public class Customer extends DataTracker {
    private int id; // Auto-generated ID
    private String name;
    private String address;
    private int postalCode;
    private String phoneNumber;
    private int divisionId;

    /** Customer Constructor
     @param id Customer Id
     @param name Customer Name
     @param address Customer Address
     @param postalCode Customer Postal Code
     @param phoneNumber Customer Phone Number
     @param divisionId  Customer Zone Id. */
    public Customer(int id, String name, String address, int postalCode, String phoneNumber,LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
