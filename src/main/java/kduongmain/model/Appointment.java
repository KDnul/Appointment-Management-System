package kduongmain.model;


import javafx.scene.control.Alert;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private int typeTotal;
    private LocalDateTime  timeStart;
    private LocalDateTime  timeEnd;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String userName;
    private String customerName;
    private String contactName;
    private  int numAppointments;

    /** Appointment Constructor
     @param id Object id
     @param title Object title
     @param description Object description
     @param location Object location
     @param type Object type
     @param timeStart Object time starts
     @param timeEnd Object time ends
     @param customerId Object customer id
     @param userId Object user id
     @param contactId Object contact id. */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime timeStart, LocalDateTime timeEnd,int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Overloaded Appointment Constructor.
     @param id Object id
     @param title Object title
     @param description Object description
     @param location Object location
     @param type Object type
     @param timeStart Object time starts
     @param timeEnd Object time ends
     @param customerId Object customer id
     @param userId Object user id
     @param contactId Object contact id
     @param contactName Object contact name. */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime timeStart, LocalDateTime timeEnd,int customerId, int userId, int contactId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** Overloaded Appointment Constructor.
     *  @param id Object id
     *  @param title Object title
     *  @param description Object description
     *  @param location Object location
     *  @param type Object type
     *  @param timeStart Object time starts
     *  @param timeEnd Object time ends
     *  @param customerId Object customer id
     *  @param userId Object user id
     *  @param contactId Object contact id
     *  @param contactName Object contact name
     *  @param userName Object user name
     *  @param customerName Object customer name. */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime timeStart, LocalDateTime timeEnd,int customerId, int userId, int contactId, String customerName, String userName, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
        this.userName = userName;
        this.customerName = customerName;
    }

    /** Overloaded Appointment constructor.
     * @param type Object type
     * @param typeTotal Object type total. */
    public Appointment(String type, int typeTotal) {
        this.type = type;
        this.typeTotal = typeTotal;
    }

    /** Getter for object Id.
     * @return Object Id. */
    public int getId() {
        return id;
    }

    /** Setter for object Id.
     * @param id Object Id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for object title.
     * @return Object title. */
    public String getTitle() {
        return title;
    }

    /** Setter for object title.
     * @param title Object title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for object description.
     * @return Object description. */
    public String getDescription() {
        return description;
    }

    /** Setter for object description.
     * @param description Object description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Getter for object location.
     * @return Object location. */
    public String getLocation() {
        return location;
    }

    /** Setter for object location.
     * @param location Object location. */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Getter for object type.
     * @return Object type. */
    public String getType() {
        return type;
    }

    /** Setter for object type.
     * @param type Object type. */
    public void setType(String type) {
        this.type = type;
    }

    /** Getter for object type total.
     * @return Object type total. */
    public int getTypeTotal() {
        return typeTotal;
    }

    /** Setter for object type total.
     * @param typeTotal Object type total. */
    public void setTypeTotal(int typeTotal) {
        this.typeTotal = typeTotal;
    }

    /** Getter for object time start.
     * @return Object time start. */
    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    /** Setter for object time start.
     * @param timeStart Object time start. */
    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    /** Getter for object time end.
     * @return Object time end. */
    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    /** Setter getter for object create date.
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

    /** Getter for object update date.
     * @return Object update date. */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /** Setter for object update date.
     * @param updateDate Object update date. */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /** Getter for object updated by.
     * @return Object update dby. */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /** Setter for object updated by.
     * @param updatedBy Object updated by. */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** Setter for object time end.
     * @param timeEnd Object time end. */
    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    /** Getter for object customer Id.
     * @return Object customer Id. */
    public int getCustomerId() {
        return customerId;
    }

    /** Setter for object customer Id.
     * @param customerId Object customer id. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Getter for object user Id.
     * @return Object user Id. */
    public int getUserId() {
        return userId;
    }

    /** Setter for object user Id.
     * @param userId Object user Id. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Getter for object contact id.
     * @return Object contact id. */
    public int getContactId() {
        return contactId;
    }

    /** Setter for object contact Id.
     * @param contactId Object contact id. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** Getter for object contact name.
     * @return Object contact name. */
    public String getContactName() {
        return contactName;
    }

    /** Setter for object contact name.
     * @param contactName Object contact name. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Getter for object number of appointments.
     * @return Object number of appointments. */
    public int getNumAppointments() {
        return numAppointments;
    }

    /** Setter for object number of appointments.
     * @param numAppointments Object number of appointments. */
    public void setNumAppointments(int numAppointments) {
        this.numAppointments = numAppointments;
    }

    /** Getter for object user name.
     * @return Object user name. */
    public String getUserName() {
        return userName;
    }

    /** Setter for object user name.
     * @param userName Object user name. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Getter for object customer name.
     * @return Object customer name. */
    public String getCustomerName() {
        return customerName;
    }

    /** Setter for object customer name.
     * @param customerName Object customer name. */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


}
