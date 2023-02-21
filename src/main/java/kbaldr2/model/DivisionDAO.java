package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DivisionDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> divisionData = FXCollections.observableArrayList();
    
    public DivisionDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     * @throws SQLException
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select Division_ID, Division, Country_ID from first_level_divisions");
        int id;
        String name;
        int countryID;
        while (rs.next()) {
            id = rs.getInt("Division_ID");
            name = rs.getString("Division");
            countryID = rs.getInt("Country_ID");
            divisionData.add(new FirstLevelDivision(id, name, countryID));
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
