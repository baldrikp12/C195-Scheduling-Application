package kbaldr2.model.dao;

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
        
        try (Statement statement = connection.createStatement()) {
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
    
}
