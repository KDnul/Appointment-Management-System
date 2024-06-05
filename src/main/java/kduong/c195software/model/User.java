package kduong.c195software.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String password;

    /** User constructor
     @param id Object id
     @param name Object name
     @param password Object password. */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    /** @return the password */
    public String getPassword() {return password;}

    /** @param password Sets the password */
    public void setPassword(String password) {this.password = password;}
}
