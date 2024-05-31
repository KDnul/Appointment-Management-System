package kduong.c195software.model;

import java.time.LocalDateTime;

public class FirstLevelDivision extends DataTracker{
    private int id;
    private String division;
    private int countryId;

    public FirstLevelDivision(int id, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    public void setId( int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDivision() {
        this.division = division;
    }
    public String getDivision() {
        return division;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

}
