package kduongmain.model;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime timeStart;
    private ZonedDateTime timeEnd;
    private int customerId;
    private int userId;
    private int contactId;

    /** Appointment Constructor
     @param id Object id
     @param title Object title
     @param description Object description
     @param location Object location
     @param type Object type
     @param timeStart Object time starts
     @param timeEnd Object time ends
     @param createDate Object create date
     @param createdBy Object created by
     @param lastUpdate Object last update
     @param lastUpdatedBy Object last updated by
     @param customerId Object customer id
     @param userId Object user id
     @param contactId Object contact id. */
    public Appointment(int id, String title, String description, String location, String type, ZonedDateTime timeStart, ZonedDateTime timeEnd,LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
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

    /** @return the id. */
    public int getId() {
        return id;
    }

    /** @param id Sets the id. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the title. */
    public String getTitle() {
        return title;
    }

    /** @param title Sets the title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the description */
    public String getDescription() {
        return description;
    }

    /** @param description Sets the description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the location. */
    public String getLocation() {
        return location;
    }

    /** @param location sets the location. */
    public void setLocation(String location) {
        this.location = location;
    }

    /** @return the type. */
    public String getType() {
        return type;
    }

    /** @param type Sets the type. */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the timeStart. */
    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    /** @param timeStart Sets the time start. */
    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    /** @return the timeEnd. */
    public ZonedDateTime getTimeEnd() {
        return timeEnd;
    }

    /** @param timeEnd Sets the time end. */
    public void setTimeEnd(ZonedDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    /** @return the customerId. */
    public int getCustomerId() {
        return customerId;
    }

    /** @param customerId Sets the customer id. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** @return the userId. */
    public int getUserId() {
        return userId;
    }

    /** @param userId Sets the user id. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return contactId. */
    public int getContactId() {
        return contactId;
    }

    /** @param contactId Sets the contact id. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
