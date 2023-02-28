package kbaldr2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kbaldr2.helper.Formatter;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDAO extends DAO<DataCache> {
    
    private static final String SELECT_ALL_STMT = "SELECT * FROM appointments";
    private static final String DELETE_RECORD_STMT = "DELETE FROM appointments WHERE Appointment_ID = ";
    private static final ObservableList<DataCache> appointmentData = FXCollections.observableArrayList();
    
    public AppointmentDAO(Connection connection) {
        
        super(connection);
    }
    
    /**
     * @return
     */
    @Override public ObservableList<DataCache> getAll() throws SQLException {
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(SELECT_ALL_STMT);
        int appointmentID;
        String title;
        String description;
        String location;
        String type;
        LocalDateTime startDateAndTime;
        LocalDateTime endDateAndTime;
        String createdBy;
        int customerID;
        int userID;
        int contactID;
        
        while (rs.next()) {
            
            appointmentID = rs.getInt("Appointment_ID");
            title = rs.getString("Title");
            description = rs.getString("Description");
            location = rs.getString("Location");
            type = rs.getString("Type");
            startDateAndTime = rs.getObject("Start", LocalDateTime.class);
            endDateAndTime = rs.getObject("End", LocalDateTime.class);
            createdBy = rs.getString("Created_By");
            customerID = rs.getInt("Customer_ID");
            userID = rs.getInt("User_ID");
            contactID = rs.getInt("Contact_ID");
            //converts times to system time for display purposes.
            appointmentData.add(new Appointment(appointmentID, title, description, location, type, Formatter.UTCtoLocal(startDateAndTime), Formatter.UTCtoLocal(endDateAndTime), createdBy, customerID, userID, contactID));
        }
        return appointmentData;
    }
    
    /**
     * @param item
     */
    @Override public void create(DataCache item) {
        
        Appointment appointment = (Appointment) item;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Customer_ID, User_ID, Contact_ID) " + "VALUES (?, ?, ?, ?, ?, ?, NOW(),?, NOW(), ?, ?, ?)")) {
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(appointment.getStartDateAndTime()));
            statement.setTimestamp(6, Timestamp.valueOf(appointment.getEndDateAndTime()));
            statement.setString(7, appointment.getCreatedBy());
            statement.setInt(8, appointment.getCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    
    /**
     * @param item
     */
    
/*    public void create(Appointment item) {
        
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Last_Update, Customer_ID, User_ID, Contact_ID) " + "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?, ?)")) {
            statement.setString(1, item.getTitle());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getLocation());
            statement.setString(4, item.getType());
            statement.setTimestamp(5, Timestamp.valueOf(item.getStartDateAndTimeFormatted()));
            statement.setTimestamp(6, Timestamp.valueOf(item.getEndDateAndTimeFormatted()));
            statement.setInt(7, item.getCustomerID());
            statement.setInt(8, item.getUserID());
            statement.setInt(9, item.getContactID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    
}
