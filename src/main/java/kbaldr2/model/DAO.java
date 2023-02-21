package kbaldr2.model;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T extends DataCache> {
    
    protected static Connection connection;
    
    public DAO(Connection connection) {
        
        this.connection = connection;
    }
    
    public abstract ObservableList<T> getAll() throws SQLException;
    
    public abstract void create(T item);
    
    public abstract void update(T item);
    
    public abstract void delete(int id) throws SQLException;
    
}
