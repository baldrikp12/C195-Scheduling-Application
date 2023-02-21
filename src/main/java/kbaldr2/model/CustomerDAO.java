package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO extends DAO<DataCache> {
    
    private static final String SELECT_ALL_STMT = "SELECT * FROM customers";
    private static final String DELETE_RECORD_STMT = "DELETE FROM customers WHERE Customer_ID = ";
    private static final ObservableList<DataCache> customerData = FXCollections.observableArrayList();
    
    public CustomerDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_ALL_STMT);
        int thecustomerID;
        String thename;
        String theaddress;
        String thepostalCode;
        String thephone;
        int thedivisionID;
        
        while (rs.next()) {
            
            thecustomerID = rs.getInt("Customer_ID");
            thename = rs.getString("Customer_Name");
            theaddress = rs.getString("Address");
            thepostalCode = rs.getString("Postal_Code");
            thephone = rs.getString("Phone");
            thedivisionID = rs.getInt("Division_ID");
            customerData.add(new Customer(thecustomerID, thename, theaddress, thepostalCode, thephone, thedivisionID));
            
        }
        return customerData;
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
     */
    @Override public void delete(int id) throws SQLException {
        
        Statement statement = connection.createStatement();
        String deleteRecord = DELETE_RECORD_STMT + id + ";";
        statement.executeUpdate(deleteRecord);
        
    }
    
}
