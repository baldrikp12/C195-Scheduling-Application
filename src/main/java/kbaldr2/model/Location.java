package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Location extends DataCache {
    
    private static final ObservableList<String> us = FXCollections.observableArrayList();
    private static final ObservableList<String> uk = FXCollections.observableArrayList();
    private static final ObservableList<String> ca = FXCollections.observableArrayList();
    private final int divisionID;
    private final String divisionName;
    private final int countryID;
    private final String countryName;
    
    public Location(int theDivisionID, String theDivisionName, int theCountryID, String theCountryName) {
        
        this.divisionID = theDivisionID;
        this.divisionName = theDivisionName;
        this.countryID = theCountryID;
        this.countryName = theCountryName;
        addToList();
    }
    
    private void addToList() {
        
        switch (countryID) {
            case 1:
                us.add(divisionName);
                break;
            case 2:
                uk.add(divisionName);
                break;
            case 3:
                ca.add(divisionName);
                break;
        }
        
    }
    
    public static ObservableList<String> getCa() {
        
        return ca;
    }
    
    public static ObservableList<String> getUs() {
        
        return us;
    }
    
    public static ObservableList<String> getUk() {
        
        return uk;
    }
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return divisionID;
    }
    
    public String getDivisionName() {
        
        return divisionName;
    }
    
    public int getCountryID() {
        
        return countryID;
    }
    
    public String getCountryName() {
        
        return countryName;
    }
    
}
