package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.DataCache;
import kbaldr2.model.ReportTypeMonth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO extends DAO<DataCache> {
    
    private static final ObservableList<ReportTypeMonth> reportData = FXCollections.observableArrayList();
    
    /**
     * Constructs a ReportDAO with the specified database connection.
     *
     * @param connection The database connection to use
     */
    public ReportDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * Retrieves all records of type T from the database.s
     *
     * @return An ObservableList containing all records of type T
     * @throws SQLException If there is an issue executing the query
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        String sql = "SELECT MONTH(a.start) AS month, a.appointment_type, COUNT(*) AS total_appointments FROM appointment a INNER JOIN customer c ON a.customer_id = c.customer_id " + "GROUP BY MONTH(a.appointment_date), a.appointment_type " + "ORDER BY MONTH(a.appointment_date), a.appointment_type";
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        
        while (rs.next()) {
            int month = rs.getInt("month");
            String type = rs.getString("appointment_type");
            int total = rs.getInt("total_appointments");
            ReportTypeMonth report = new ReportTypeMonth(type, month, total);
            reportData.add(report);
        }
        
        return null;
    }
    
    /**
     * Creates a new record of type T in the database.
     *
     * @param item The record of type T to create
     */
    @Override public void create(DataCache item) {
    
    }
    
    /**
     * Updates an existing record of type T in the database.
     *
     * @param item The record of type T with updated data
     */
    @Override public void update(DataCache item) {
    
    }
    
    /**
     * Deletes a record of type T from the database using its ID.
     *
     * @param id The ID of the record to delete
     * @throws SQLException If there is an issue executing the deletion
     */
    @Override public void delete(int id) throws SQLException {
    
    }
    
}
