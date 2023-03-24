package kbaldr2.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.model.Appointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ReportDAO {
    
    protected static Connection connection;
    
    /**
     * Constructs a ReportDAO with the specified database connection.
     *
     * @param connection The database connection to use
     */
    public ReportDAO(Connection connection) {
        
        this.connection = connection;
    }
    
    /**
     * Retrieves all records of type T from the database.s
     *
     * @return An ObservableList containing all records of type T
     * @throws SQLException If there is an issue executing the query
     */
    public Map<Integer, Map<String, Integer>> getAppointmentsByTypeAndMonth() {
        
        String query = "SELECT type, EXTRACT(YEAR_MONTH FROM start) AS yearmonth, COUNT(*) as total FROM appointments GROUP BY yearmonth, type ORDER BY yearmonth, type;";
        
        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                int yearMonth = rs.getInt("yearmonth");
                String type = rs.getString("type");
                int count = rs.getInt("total");
                
                if (!result.containsKey(yearMonth)) {
                    result.put(yearMonth, new HashMap<>());
                }
                
                result.get(yearMonth).put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public ObservableList<Appointment> generateScheduleForContacts() {
        
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();
        
        // Query the appointments and contacts tables
        String query = "SELECT a.appointment_ID, a.title, a.type, a.description, a.start, a.end, a.customer_ID " + "FROM appointments a " + "JOIN contacts c ON a.contact_ID = c.contact_ID;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            // Iterate over the result set and add each appointment to the schedule
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt("appointment_ID"));
                appointment.setTitle(rs.getString("title"));
                appointment.setType(rs.getString("type"));
                appointment.setDescription(rs.getString("description"));
                appointment.setStartDateAndTime(rs.getTimestamp("start_datetime").toLocalDateTime());
                appointment.setEndDateAndTime(rs.getTimestamp("end_datetime").toLocalDateTime());
                appointment.setCustomerID(rs.getInt("customer_ID"));
                schedule.add(appointment);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return schedule;
    }
    
}
