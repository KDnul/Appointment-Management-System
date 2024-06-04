package kduong.c195software.model;

public class Contact {
    private int id;
    private String name;
    private String email;

    /** Contact Constructor
     @param id Object Id
     @param name Object Name
     @param email Object Email. */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return id. */
    public int getId() {
        return id;
    }

    /** @param id Sets the id. */
    public void setContactId(int id) {
        this.id = id;
    }

    /** @return the name. */
    public String getName() {
        return name;
    }

    /** @param name Sets the name. */
    public void setContactName(String name) {
        this.name = name;
    }

    /** @return the email. */
    public String getEmail() {
        return email;
    }

    /** @param email Sets Contact Email. */
    public void setEmail(String email) {
        this.email = email;
    }

}
