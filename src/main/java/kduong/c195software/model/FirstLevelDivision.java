package kduong.c195software.model;

import java.time.LocalDateTime;

public class FirstLevelDivision {
    private int id;
    private String name;
    private int countryId;

    /** First Level Division Constructor
     @param id Object id
     @param name Object name. */
    public FirstLevelDivision(int id, String name,int countryId) {
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
