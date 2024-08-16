package kduongmain.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Kelvin Duong
 */

public class Customer {
    private int id; // Auto-generated ID
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int divisionId;
    private int countryId;
    private String division;
    private String country;

    /** Customer class constructor.
     * @param id Object id.
     * @param name Object name.
     * @param address Object address.
     * @param postalCode Object postal code.
     * @param phoneNumber Object phone number.
     * @param createDate Object create date.
     * @param createdBy Object created by.
     * @param lastUpdated Object last updated.
     * @param lastUpdatedBy Object last updated by.
     * @param divisionId Object division id.*/
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /** Customer class overloaded constructor.
     * @param id Object id.
     * @param name Object name.
     * @param address Object address.
     * @param postalCode Object postal code.
     * @param phoneNumber Object phone number.
     * @param createDate Object create date.
     * @param createdBy Object created by.
     * @param lastUpdated Object last updated.
     * @param lastUpdatedBy Object last updated by.
     * @param divisionId Object division id.
     * @param countryId Object country id.
     * @param division Object division.
     * @param country Object country. */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionId, int countryId, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.division = division;
        this.country = country;
    }

    /** Getter for object id.
     * @return Object id. */
    public int getId() {
        return id;
    }

    /** Setter for object id.
     * @param id Object id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for object name.
     * @return Object name. */
    public String getName() {
        return name;
    }

    /** Setter for object name.
     * @param name Object name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for object address.
     * @return Object address. */
    public String getAddress() {
        return address;
    }

    /** Setter for object address.
     * @param address Object address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for object postal code.
     * @return Object postal code. */
    public String getPostalCode() {
        return postalCode;
    }

    /** Setter for object postal code.
     * @param postalCode Object postal code. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Getter for object phone number.
     * @return Object phone number. */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Setter for object phone number.
     * @param phoneNumber Object phone number */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Getter for object create date.
     * @return Object create date. */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** Setter for object create date.
     * @param createDate Object create date. */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** Getter for object created by.
     * @return Object created by. */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for object created by.
     * @param createdBy Object created by. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for object last updated.
     * @return Object last updated. */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /** Setter for object last updated.
     * @param lastUpdated Object last updated. */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /** Getter for object last updated by.
     * @return Object last updated by. */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for object last updated by.
     * @param lastUpdatedBy Object last updated by. */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Getter for object division id.
     * @return Object division id. */
    public int getDivisionId() {
        return divisionId;
    }

    /** Setter for object division id.
     * @param divisionId Object division id. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Getter for object country id.
     * @return Object country id. */
    public int getCountryId() {
        return countryId;
    }

    /** Setter for object country id.
     * @param countryId Object country id. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Getter for object division.
     * @return Object division. */
    public String getDivision() {
        return division;
    }

    /** Setter for object division.
     * @param division Object division. */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Getter for object country.
     * @return Object country. */
    public String getCountry() {
        return country;
    }

    /** Setter for object country.
     * @param country Object country. */
    public void setCountry(String country) {
        this.country = country;
    }
}
