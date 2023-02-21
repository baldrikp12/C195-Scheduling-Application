package kbaldr2.model;

public class Customer extends DataCache {
    
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    
    
    public Customer(int customerID, String name, String address, String postalCode, String phone, int divisionID) {
        
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        
    }
    
    @Override public int getId() {
        
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        
        this.customerID = this.customerID;
    }
    
    public String getName() {
        
        return name;
    }
    
    public void setName(String name) {
        
        this.name = name;
    }
    
    public String getAddress() {
        
        return address;
    }
    
    public void setAddress(String address) {
        
        this.address = address;
    }
    
    public String getPostalCode() {
        
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        
        this.postalCode = postalCode;
    }
    
    public String getPhone() {
        
        return phone;
    }
    
    public void setPhone(String phone) {
        
        this.phone = phone;
    }
    
    public int getDivisionID() {
        
        return divisionID;
    }
    
    public void setDivisionID(int divisionID) {
        
        this.divisionID = divisionID;
    }
    
}
