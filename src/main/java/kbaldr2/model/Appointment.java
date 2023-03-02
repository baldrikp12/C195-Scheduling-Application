package kbaldr2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public int getId() {

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

    public void setUpdatedBy(String updatedBy) {

    }
}
