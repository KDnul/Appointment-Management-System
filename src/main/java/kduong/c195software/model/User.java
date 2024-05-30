package kduong.c195software.model;

public class User {
    private int userId;
    private String userName;
    private int userZoneId;

    /** User Constructor
     @param userId User id
     @param userName User Name
     @param userZoneId User ZoneId. */
    public User(int userId, String userName, int userZoneId) {
        this.userId = userId;
        this.userName = userName;
        this.userZoneId = userZoneId;
    }

    /** @return the userId. */
    public int getUserId() {
        return userId;
    }

    /** @param userId sets the userId. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return the userName. */
    public String getUserName() {
        return userName;
    }

    /** @param userName sets the userName. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @return the userZoneId. */
    public int getUserZoneId() {
        return userZoneId;
    }

    /** @param userZoneId setts the userZoneId. */
    public void setUserZoneId(int userZoneId) {
        this.userZoneId = userZoneId;
    }
}
