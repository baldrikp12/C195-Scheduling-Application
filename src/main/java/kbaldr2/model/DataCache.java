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

    public abstract int getId();

    public static ObservableList<ObservableList<DataCache>> getAllObjectList() {
        return allObjectList;
    }

    public static void addToAllObjectList(ObservableList<DataCache> objectList) {
        DataCache.allObjectList.add(objectList);
    }

}
