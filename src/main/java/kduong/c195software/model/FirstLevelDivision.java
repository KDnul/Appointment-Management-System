package kduong.c195software.model;

import java.time.LocalDateTime;

public class FirstLevelDivision extends DataTracker{
    private int id;
    private String name;
    private int countryId;

    /** First Level Division Constructor
     @param id Object id
     @param name Object name
     @param createDate Object create date
     @param createdBy Object created by
     @param lastUpdate Object last update
     @param lastUpdatedBy Object last updated by. */
    public FirstLevelDivision(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /** @param id Sets the id. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the id. */
    public int getId() {
        return id;
    }

    /** @param name Sets the name. */
    public void setDivision(String name) {
        this.name = name;
    }

    /** @return the name. */
    public String getDivision() {
        return name;
    }

    /** @param id Sets the id. */
    public void setCountryId(int id) {
        this.countryId = id;
    }

    /** @return the id. */
    public int getCountryId() {
        return id;
    }

}
