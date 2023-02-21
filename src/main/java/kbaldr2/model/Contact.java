package kbaldr2.model;

public class Contact extends DataCache {
    
    private int contactID;
    private String contactName;
    
    private String contactEmail;
    
    public Contact(int theId, String theName, String theEmail) {
        
        contactID = theId;
        contactName = theName;
        contactEmail = theEmail;
    }
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return contactID;
    }
    
    public String getContactName() {
        
        return contactName;
    }
    
    public void setContactName(String contactName) {
        
        this.contactName = contactName;
    }
    
    public String getContactEmail() {
        
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail) {
        
        this.contactEmail = contactEmail;
    }
    
}
