package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends DAO<DataCache> {
    
    private static final ObservableList<DataCache> userData = FXCollections.observableArrayList();
    
    public UserDAO(Connection connection) {
        
        super(connection);
    }
    
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select User_ID, User_Name FROM users");
        int id;
        String name;
        
        while (rs.next()) {
            id = rs.getInt("User_ID");
            name = rs.getString("User_Name");
            userData.add(new User(id, name));
        }
        
        return userData;
    }
    
    @Override public void create(DataCache item) {
    
    }
    
    @Override public void update(DataCache item) {
    
    }
    
    @Override public void delete(int id) throws SQLException {
    
    }
    
}
