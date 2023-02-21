package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> contactData = FXCollections.observableArrayList();
    
    public ContactDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     * @throws SQLException
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select Contact_ID, Contact_Name, Email FROM contacts");
        int id;
        String name;
        String email;
        
        while (rs.next()) {
            id = rs.getInt("Contact_ID");
            name = rs.getString("Contact_Name");
            email = rs.getString("Email");
            contactData.add(new Contact(id, name, email));
        }
        
        return contactData;
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
