package kbaldr2.model;

public class User extends DataCache {
    
    private final int userID;
    private String userName;
    
    
    public User(int theId, String theName) {
        
        this.userID = theId;
        this.userName = theName;
        
    }
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return userID;
    }
    
    public String getUserName() {
        
        return userName;
    }
    
    public void setUserName(String userName) {
        
        this.userName = userName;
    }
    
}
