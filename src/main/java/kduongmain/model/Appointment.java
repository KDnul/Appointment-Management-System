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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

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

    public Appointment(String type, int numAppointments) {
        this.type = type;
        this.numAppointments = numAppointments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getNumAppointments() {
        return numAppointments;
    }

    public void setNumAppointments(int numAppointments) {
        this.numAppointments = numAppointments;
    }


}
