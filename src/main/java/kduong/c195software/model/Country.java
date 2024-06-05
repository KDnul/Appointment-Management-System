package kduong.c195software.model;

public class Country {
    private int id;
    private String name;

    /** Country Constructor
     @param id Object id
     @param name Object name. */
    public Country(int id, String name) {
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
