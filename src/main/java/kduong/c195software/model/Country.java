package kduong.c195software.model;

import java.time.LocalDateTime;

public class Country extends DataTracker {
    private int id;
    private String name;

    public Country(int id, String name,LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
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
}
