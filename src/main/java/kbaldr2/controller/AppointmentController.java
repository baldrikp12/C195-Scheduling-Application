package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Formatter;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.Contact;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;

public class AppointmentController implements Initializable {
    
    private int localOpenHour;
    private int localOpenMinute;
    private int localCloseHour;
    private int localCloseMinute;
    private boolean isAdding = true;
    @FXML
    private MenuButton contactMenu;
    
    @FXML
    private MenuButton customerMenu;
    @FXML
    private MenuButton userMenu;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ListView<String> endTimeListView;
    
    @FXML
    private ListView<String> timeListView;
    @FXML
    private Button addModifyButton;
    
    /**
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        //====================== Sets calendar's date
        LocalTime eod = Formatter.ESTtoLocal(LocalTime.of(22, 0));
        if (LocalTime.now().isAfter(eod)) {
            datePicker.setValue(LocalDate.now().plusDays(1));
        } else {
            datePicker.setValue(LocalDate.now());
            datePicker.setDayCellFactory(datePicker -> new DateCell() {
                @Override public void updateItem(LocalDate date, boolean empty) {
                    
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            });
        }
        //======================= find Business EOD Hour and Minute
        LocalTime easternTimeOpen = LocalTime.of(8, 00);
        LocalTime easternTimeClose = LocalTime.of(22, 00);
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        
        ZonedDateTime localDateTime = ZonedDateTime.now();
        
        ZonedDateTime easternDateTimeO = ZonedDateTime.of(localDateTime.toLocalDate(), easternTimeOpen, easternZoneId);
        ZonedDateTime localOpenTime = easternDateTimeO.withZoneSameInstant(localDateTime.getZone());
        localOpenHour = localOpenTime.getHour(); //Open HOUR in local time
        localOpenMinute = localOpenTime.getMinute(); //Open MINUTES in local time
        
        //Closing time
        ZonedDateTime easternDateTimeC = ZonedDateTime.of(localDateTime.toLocalDate(), easternTimeClose, easternZoneId);
        ZonedDateTime localCloseTime = easternDateTimeC.withZoneSameInstant(localDateTime.getZone());
        
        localCloseHour = localCloseTime.getHour(); //Close HOUR in local time
        localCloseMinute = localCloseTime.getMinute(); //Close MINUTES in local time
        
        updateStartTime();
        
        //=====populate customers
        for (DataCache item : DataCache.getAllCustomers()) {
            Customer customer = (Customer) item;
            MenuItem menuItem = new MenuItem((((Customer) customer).getName()));
            menuItem.setOnAction(event -> customerMenu.setText(menuItem.getText()));
            customerMenu.getItems().add(menuItem);
        }
        
        
        //=====populate contacts
        for (DataCache item : DataCache.getAllContacts()) {
            Contact contact = (Contact) item;
            MenuItem menuItem = new MenuItem((((Contact) contact).getContactName()));
            menuItem.setOnAction(event -> contactMenu.setText(menuItem.getText()));
            contactMenu.getItems().add(menuItem);
        }
        
        //=====populate users
        for (DataCache item : DataCache.getAllContacts()) {
            Contact contact = (Contact) item;
            MenuItem menuItem = new MenuItem((((Contact) contact).getContactName()));
            menuItem.setOnAction(event -> userMenu.setText(menuItem.getText()));
            userMenu.getItems().add(menuItem);
        }
        
    }
    
    @FXML private void updateStartTime() {
        
        LocalTime bod = Formatter.ESTtoLocal(LocalTime.of(8, 0));
        LocalTime eod = Formatter.ESTtoLocal(LocalTime.of(22, 0));
        timeListView.getSelectionModel().clearSelection();
        endTimeListView.getItems().clear();
        // Get the selected date from the DatePicker
        LocalDate selectedDate = datePicker.getValue();
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Check if the selected date is not today
        if (LocalTime.now().isBefore(bod) || LocalTime.now().isAfter(eod)) {
            System.out.println("The selected date is NOT today.");
            ObservableList<String> items = FXCollections.observableArrayList();
            
            LocalTime start = Formatter.ESTtoLocal(LocalTime.of(8, 0));
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(Formatter.formatTime(time));
            }
            timeListView.setItems(items);
        } else {
            ObservableList<String> items = FXCollections.observableArrayList();
            LocalTime start = LocalTime.of(now().getHour(), 0);
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(Formatter.formatTime(time));
            }
            timeListView.setItems(items);
        }
        
    }
    
    public void setScene(int i) {
    
    }
    
    @FXML private void updateEndTime() {
        
        endTimeListView.getSelectionModel().clearSelection();
        //gets starting time from starting time list
        String theTime = timeListView.getSelectionModel().getSelectedItem();
        
        //converts string to localtime object
        LocalTime theStartingTime = Formatter.parseTime(theTime);
        
        //gets the hour and minute from localtime object
        int theHour = theStartingTime.getHour();
        int theMinute = theStartingTime.getMinute();
        
        
        ObservableList<String> items = FXCollections.observableArrayList();
        
        LocalTime endTime = LocalTime.of(theHour, theMinute);
        
        while (endTime.isBefore(LocalTime.of(localCloseHour, localCloseMinute))) {
            endTime = endTime.plusMinutes(15);
            //items.add(endTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
            items.add(Formatter.formatTime(endTime));
            
        }
        endTimeListView.setItems(items);
    }
    
    @FXML private void addModifyApp(ActionEvent event) {
        
        if (isAdding) {
            // Get the selected start and end times from the list views
            LocalDate selectedDate = datePicker.getValue();
            String startTime = timeListView.getSelectionModel().getSelectedItem();
            String endTime = endTimeListView.getSelectionModel().getSelectedItem();
            
            // Concatenate the selected date with the selected start and end times
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            LocalDateTime localStart = LocalDateTime.of(selectedDate, start);
            LocalDateTime localEnd = LocalDateTime.of(selectedDate, end);
            // Get the selected customer, contact, and user
            String customerName = customerMenu.getText();
            String contactName = contactMenu.getText();
            String userName = userMenu.getText();
            
        }
    }
    
    @FXML private void close() {
        
        Stage appStage = SceneManager.getStage("appointment");
        SceneManager.getStage("appointment").fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
