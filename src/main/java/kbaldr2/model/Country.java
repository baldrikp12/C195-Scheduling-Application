package kbaldr2.model;

public class Country extends DataCache {
    
    private int countryID;
    private String countryName;
    
    
    public Country(int theCountryID, String theCountryName) {
        
        this.countryID = theCountryID;
        this.countryName = theCountryName;
    }
    
    
    /**
     * @return
     */
    @Override public int getId() {
        
        return countryID;
    }
    
    public String getCountryName() {
        
        return countryName;
    }

    
}
