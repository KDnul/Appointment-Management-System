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
     @param id Customer id
     @param name Customer Name
     @param address Customer Address
     @param postalCode Customer Postal Code
     @param phoneNumber Customer Phone Number
     @param createDate Object create date
     @param createdBy Object created by
     @param lastUpdate Object last update
     @param lastUpdatedBy Object last updated by
     @param divisionId  Customer Zone Id. */
    public Customer(int id, String name, String address, int postalCode, String phoneNumber,LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /** @return the id. */
    public int getId() {
        return id;
    }

    /** @param id Sets the id. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the name. */
    public String getName() {
        return name;
    }

    /** @param name Sets the name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the address. */
    public String getAddress() {
        return address;
    }

    /** @param address Sets the address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the postalCode. */
    public int getPostalCode() {
        return postalCode;
    }

    /** @param postalCode Sets the postal code. */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    /** @return the phoneNumber. */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber Sets the phone number. */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return the divisionId. */
    public int getDivisionId() {
        return divisionId;
    }

    /** @param divisionId Sets the division id. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
