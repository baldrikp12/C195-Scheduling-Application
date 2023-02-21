package kbaldr2.model;

public class FirstLevelDivision extends DataCache {
    
    private int divisionID;
    private String divisionName;
    private int countryID;
    
    
    public FirstLevelDivision(int theDivisionID, String theDivisionName, int theCountryID) {
        
        this.divisionID = theDivisionID;
        this.divisionName = theDivisionName;
        this.countryID = theCountryID;
    }
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return divisionID;
    }
    
    public void setDivisionID(int divisionID) {
        
        this.divisionID = divisionID;
    }
    
    public String getDivisionName() {
        
        return divisionName;
    }
    
    public void setDivisionName(String divisionName) {
        
        this.divisionName = divisionName;
    }
    
    public int getCountryID() {
        
        return countryID;
    }
    
    public void setCountryID(int countryID) {
        
        this.countryID = countryID;
    }
    
}
