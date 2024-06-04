package kduong.c195software.model;

import java.time.LocalDateTime;

public class User extends DataTracker {
    private int id;
    private String name;

    /** User constructor
     @param id Object id
     @param name Object name
     @param createDate Object create date
     @param createdBy Object created by
     @param lastUpdate Object last update
     @param lastUpdatedBy Object last updated by. */
    public User(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
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
}
