package kbaldr2.model;

/**
 * Represents a Customer, extending the DataCache class.
 * Contains customer-specific data such as name, address, postal code, phone number, and division ID.
 */
public class Customer extends DataCache {
    
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String createdBy;
    private String updatedBy;
    
    /**
     * Constructs a new Customer object with the specified parameters.
     *
     * @param customerID the unique identifier for the customer
     * @param name       the name of the customer
     * @param address    the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone      the phone number of the customer
     * @param divisionID the division ID associated with the customer
     */
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
        
        this.customerID = customerID;
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
    
    public String getCreatedBy() {
        
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        
        this.createdBy = createdBy;
    }
    
    public String getUpdatedBy() {
        
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        
        this.updatedBy = updatedBy;
    }
    
}
