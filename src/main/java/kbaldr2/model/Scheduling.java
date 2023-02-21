package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * The Scheduling class is responsible for managing the appointments and customers in the application. It uses
 *
 * @author Kenneth Baldridge
 */
public class Scheduling {
    
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    
    /**
     * This method adds a new Customer object to the allCustomers list.
     *
     * @param customer The Customer object to be added to the scheduling.
     */
    public static void addCustomer(Customer customer) {
        
        allCustomers.add(customer);
    }
    
    /**
     * This method removes a specified Customer object from the allCustomers list.
     *
     * @param selectedCustomer The Customer object to be removed from the scheduling.
     * @return A boolean value indicating whether the deletion was successful.
     */
    public static boolean deleteCustomer(Customer selectedCustomer) {
        
        return true;
    }
    
    /**
     * This method removes a specified Appointment object from the allAppointments list.
     *
     * @param selectedAppointment The Appointment object to be removed from the scheduling.
     * @return A boolean value indicating whether the deletion was successful.
     */
    public static boolean deleteAppointment(Appointment selectedAppointment) {
        
        return true;
    }
    
    /**
     * This method searches the allCustomers list for a Customer object with a specified name.
     *
     * @param customerName The name of the Customer to be searched for.
     * @return The Customer object with the specified name, or null if no such object
     * is found.
     */
    public static Customer lookupCustomer(String customerName) {
        
        return null;
    }
    
    /**
     * This method returns the allCustomers list.
     *
     * @return The allCustomers list.
     */
    public static ObservableList<Customer> getAllCustomers() {
        
        return allCustomers;
    }
    
    
    /**
     * This method adds a new Appointment object to the allAppointments list.
     *
     * @param appointment The Appointment object to be added to the scheduling.
     */
    public static void addAppointment(Appointment appointment) {
    
    
    }
    
    /**
     * This method returns the allAppointments list.
     *
     * @return The allAppointments list.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        
        return allAppointments;
    }
    
    /**
     * This method searches the allAppointments list for Appointment objects with specified
     * dates.
     *
     * @param startDate The start date of the Appointments to be searched for.
     * @param endDate   The end date of the Appointments to be searched for.
     * @return An ObservableList of all Appointment objects with dates between the specified
     * start and end dates.
     */
    public static ObservableList<Appointment> lookupAppointment(LocalDate startDate, LocalDate endDate) {
        
        return null;
    }
    
    /**
     * This method updates a Customer object in the allCustomers list with a specified
     * index.
     *
     * @param index The index of the Customer object to be updated.
     * @param part  The Customer Part object.
     */
    public static void updateCustomer(int index, Customer part) {
        
        allCustomers.set(index, part);
    }
    
    /**
     * This method updates a Appointment object in the allAppointments list with a
     * specified index.
     *
     * @param index   The index of the Appointment object to be updated.
     * @param product The updated Appointment object.
     */
    public static void updateAppointment(int index, Appointment product) {
        
        allAppointments.set(index, product);
    }
    
}