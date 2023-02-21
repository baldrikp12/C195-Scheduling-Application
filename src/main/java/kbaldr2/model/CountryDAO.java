package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> countryData = FXCollections.observableArrayList();
    
    public CountryDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     * @throws SQLException
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select Country_ID, Country FROM countries");
        int id;
        String name;
        String email;
        
        while (rs.next()) {
            id = rs.getInt("Country_ID");
            name = rs.getString("Country");
            countryData.add(new Country(id, name));
        }
        
        return countryData;
    }
    
    /**
     * @param item
     */
    @Override public void create(DataCache item) {
    
    }
    
    /**
     * @param item
     */
    @Override public void update(DataCache item) {
    
    }
    
    /**
     * @param id
     * @throws SQLException
     */
    @Override public void delete(int id) throws SQLException {
    
    }
    
}
