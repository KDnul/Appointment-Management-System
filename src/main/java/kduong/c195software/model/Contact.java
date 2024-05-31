package kduong.c195software.model;

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /** @return contactId. */
    public int getContactId() {
        return contactId;
    }

    /** @param contactId Sets Contact id. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** @return contactName. */
    public String getContactName() {
        return contactName;
    }

    /** @param contactName Sets Contact Name. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** @return contactEmail. */
    public String getContactEmail() {
        return contactEmail;
    }

    /** @param contactEmail Sets Contact Email. */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /** Contact Constructor
     @param contactId Customer Id
     @param contactName Customer Name
     @param contactEmail Customer Email. */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
}
