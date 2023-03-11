package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocationDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> divisionData = FXCollections.observableArrayList();
    
    public LocationDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     * @throws SQLException
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select first_level_divisions.Division_ID, first_level_divisions.Division, Countries.Country_ID, Countries.Country from first_level_divisions inner join Countries on first_level_divisions.Country_ID = Countries.Country_ID; ");
        int divisionID;
        int countryID;
        String divisionName;
        String countryName;
        while (rs.next()) {
            divisionID = rs.getInt("first_level_divisions.Division_ID");
            divisionName = rs.getString("first_level_divisions.Division");
            countryID = rs.getInt("Countries.Country_ID");
            countryName = rs.getString("Countries.Country");
            divisionData.add(new Location(divisionID, divisionName, countryID, countryName));
        }
        return divisionData;
        
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
