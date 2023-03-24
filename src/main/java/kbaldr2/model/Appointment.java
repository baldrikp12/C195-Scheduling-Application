package kbaldr2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment, extending the DataCache class.
 * Contains appointment-specific data such as title, description, location, type, start and end date times, customer, user, and contact.
 */
public class Appointment extends DataCache {
    
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private String createdBy;
    private String updatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String startDateAndTimeFormatted;
    
    /**
     * Constructs an Appointment object.
     *
     * @param appointmentID    The unique identifier of the appointment.
     * @param title            The title of the appointment.
     * @param description      The description of the appointment.
     * @param location         The location of the appointment.
     * @param type             The type of appointment.
     * @param startDateAndTime The start date and time of the appointment.
     * @param endDateAndTime   The end date and time of the appointment.
     * @param customerID       The unique identifier of the customer.
     * @param userID           The unique identifier of the user.
     * @param contactID        The unique identifier of the contact.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, int customerID, int userID, int contactID) {
        
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    /**
     * Constructs a default Appointment object.
     *
     */
    public Appointment() {
        
        this.appointmentID = 0000000;
        this.title = "";
        this.description = "";
        this.location = "";
        this.type = "";
        this.startDateAndTime = null;
        this.endDateAndTime = null;
        this.customerID = 0000000;
        this.userID = 0000000;
        this.contactID = 0000000;
    }
    
    @Override public int getId() {
        
        return appointmentID;
    }
    
    public void setAppointmentID(int appointmentID) {
        
        this.appointmentID = appointmentID;
    }
    
    public String getTitle() {
        
        return title;
    }
    
    public void setTitle(String title) {
        
        this.title = title;
    }
    
    public String getDescription() {
        
        return description;
    }
    
    public void setDescription(String description) {
        
        this.description = description;
    }
    
    public String getLocation() {
        
        return location;
    }
    
    public void setLocation(String location) {
        
        this.location = location;
    }
    
    public String getType() {
        
        return type;
    }
    
    public void setType(String type) {
        
        this.type = type;
    }
    
    public LocalDateTime getStartDateAndTime() {
        
        return startDateAndTime;
    }
    
    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        
        this.startDateAndTime = startDateAndTime;
    }
    
    public String getStartDateAndTimeFormatted() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return startDateAndTime.format(formatter);
    }
    
    public LocalDateTime getEndDateAndTime() {
        
        return endDateAndTime;
    }
    
    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        
        this.endDateAndTime = endDateAndTime;
    }
    
    public String getEndDateAndTimeFormatted() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return endDateAndTime.format(formatter);
    }
    
    public String getCreatedBy() {
        
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        
        this.createdBy = createdBy;
    }
    
    public int getCustomerID() {
        
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        
        this.customerID = customerID;
    }
    
    public int getUserID() {
        
        return userID;
    }
    
    public void setUserID(int userid) {
        
        this.userID = userid;
    }
    
    public int getContactID() {
        
        return contactID;
    }
    
    public void setContactID(int contactId) {
        
        this.contactID = contactId;
    }
    
    public String getUpdatedBy() {
        
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
    
    }
    
}
