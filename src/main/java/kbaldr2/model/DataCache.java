package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class DataCache {

    private static ObservableList<ObservableList<DataCache>> allObjectList = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allUsers = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allContacts = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allFirstLevelDivision = FXCollections.observableArrayList();
    private static ObservableList<DataCache> allCountries = FXCollections.observableArrayList();
    private static int userId;

    public static ObservableList<DataCache> getAllAppointments() {

        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<DataCache> allAppointments) {

        DataCache.allAppointments = allAppointments;
    }

    public static void addAppointment(Appointment app) {

        allAppointments.add(app);
    }

    public static void addCustomer(Customer app) {

        allCustomers.add(app);
    }

    public static ObservableList<DataCache> getAllCustomers() {

        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<DataCache> allCustomers) {

        DataCache.allCustomers = allCustomers;
    }

    public static ObservableList<DataCache> getAllContacts() {

        return allContacts;
    }

    public static void setAllContacts(ObservableList<DataCache> allContacts) {

        DataCache.allContacts = allContacts;
    }

    public static ObservableList<DataCache> getAllFirstLevelDivision() {

        return allFirstLevelDivision;
    }

    public static void setAllFirstLevelDivision(ObservableList<DataCache> allFirstLevelDivision) {

        DataCache.allFirstLevelDivision = allFirstLevelDivision;
    }

    public static ObservableList<DataCache> getAllCountries() {

        return allCountries;
    }

    public static void setAllCountries(ObservableList<DataCache> allCountries) {

        DataCache.allCountries = allCountries;
    }

    public static void setUserId(int userId) {

        DataCache.userId = userId;
    }

    public static void clearObjects() {

        for (ObservableList<DataCache> objectList : getAllObjectList()) {
            objectList.clear();
        }
    }

    public static ObservableList<DataCache> getAllUsers() {

        return allUsers;
    }

    public static void setAllUsers(ObservableList<DataCache> allUsers) {

        DataCache.allUsers = allUsers;
    }

    public static int getUserID(String userName) {
        int id = -1;
        for (DataCache item : getAllUsers()) {
            User theUser = (User) item;
            if (theUser.getUserName().equals(userName)) {
                return theUser.getId();
            }
        }
        return id;
    }

    public static int getCustomerID(String customerName) {
        int id = -1;
        for (DataCache item : getAllCustomers()) {
            Customer theCustomer = (Customer) item;
            if (theCustomer.getName().equals(customerName)) {
                return theCustomer.getId();
            }
        }
        return id;
    }

    public static int getContactID(String contactName) {
        int id = -1;
        for (DataCache item : getAllContacts()) {
            Contact theContact = (Contact) item;
            if (theContact.getContactName().equals(contactName)) {
                return theContact.getId();
            }
        }
        return id;
    }

    public static String getContactName(int contactID) {
        String name = "";
        for (DataCache item : getAllContacts()) {
            Contact theContact = (Contact) item;
            if (theContact.getId() == contactID) {
                return theContact.getContactName();
            }
        }
        return name;
    }

    public static String getCustomerName(int customerID) {
        String name = "";
        for (DataCache item : getAllCustomers()) {
            Customer theCustomer = (Customer) item;
            if (theCustomer.getId() == customerID) {
                return theCustomer.getName();
            }
        }
        return name;
    }

    public abstract int getId();

    public static ObservableList<ObservableList<DataCache>> getAllObjectList() {

        return allObjectList;
    }

    public static void addToAllObjectList(ObservableList<DataCache> objectList) {

        DataCache.allObjectList.add(objectList);
    }

}
