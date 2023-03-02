package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Formatter;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.*;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;

public class AppointmentController implements Initializable {

    Appointment appointToMod;
    private int localOpenHour;
    private int localOpenMinute;
    private int localCloseHour;
    private int localCloseMinute;
    private boolean isAdding = true;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private ComboBox<String> customerCombo;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private ComboBox<String> userCombo;
    @FXML
    private Label addModLabel;
    @FXML
    private Button addModifyButton;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descArea;

    @FXML
    private ListView<String> endTimeListView;

    @FXML
    private TextField locationField;

    @FXML
    private ListView<String> startTimeListView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //====================== Sets calendar's date
        LocalTime eod = Formatter.ESTtoLocal(LocalTime.of(22, 0));
        if (LocalTime.now().isAfter(eod)) {
            datePicker.setValue(LocalDate.now().plusDays(1));
        } else {
            datePicker.setValue(LocalDate.now());
            datePicker.setDayCellFactory(datePicker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {

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
        localOpenHour = localOpenTime.getHour(); // Open HOUR in local time
        localOpenMinute = localOpenTime.getMinute(); // Open MINUTES in local time

        //Closing time
        ZonedDateTime easternDateTimeC = ZonedDateTime.of(localDateTime.toLocalDate(), easternTimeClose, easternZoneId);
        ZonedDateTime localCloseTime = easternDateTimeC.withZoneSameInstant(localDateTime.getZone());
        localCloseHour = localCloseTime.getHour();// Closing HOUR in local time
        localCloseMinute = localCloseTime.getMinute();// Closing MINUTES in local time

        updateStartTime();

        //=====populate customers
        for (DataCache item : DataCache.getAllCustomers()) {
            Customer customer = (Customer) item;
            String customerOption = customer.getName();
            customerCombo.getItems().add(customerOption);

        }
        //=====populate contacts
        for (DataCache item : DataCache.getAllContacts()) {
            Contact contact = (Contact) item;
            String contactOption = contact.getContactName();
            contactCombo.getItems().add(contactOption);
        }

        //=====populate users
        for (DataCache item : DataCache.getAllUsers()) {
            User user = (User) item;
            String userOption = user.getUserName();
            userCombo.getItems().add(userOption);
        }
    }

    @FXML
    private void updateStartTime() {

        LocalTime bod = Formatter.ESTtoLocal(LocalTime.of(8, 0));
        LocalTime eod = Formatter.ESTtoLocal(LocalTime.of(22, 0));
        startTimeListView.getSelectionModel().clearSelection();
        endTimeListView.getItems().clear();
        // Get the selected date from the DatePicker
        LocalDate selectedDate = datePicker.getValue();
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Check if the selected date is not today
        if (LocalTime.now().isBefore(bod) || LocalTime.now().isAfter(eod) || LocalDate.now().isBefore(selectedDate)) {
            System.out.println("The selected date is NOT today.");
            ObservableList<String> items = FXCollections.observableArrayList();

            LocalTime start = Formatter.ESTtoLocal(LocalTime.of(8, 0));
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(Formatter.formatTime(time));//TODO Decide if I want to have unformatted or formatted and update the time in populate method.
            }
            startTimeListView.setItems(items);
        } else {
            ObservableList<String> items = FXCollections.observableArrayList();
            LocalTime start = LocalTime.of(now().getHour(), 0);
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(time.toString());
            }
            startTimeListView.setItems(items);
        }

    }

    public void setAppointmentToModify(DataCache theItem) {

        addModLabel.setText("Modify Appointment");
        addModifyButton.setText("Update");
        appointToMod = (Appointment) theItem;
        isAdding = false;
        populateForm();

    }


    private void populateForm() {
        customerCombo.setValue(DataCache.getCustomerName(appointToMod.getCustomerID()));
        contactCombo.setValue(DataCache.getContactName(appointToMod.getContactID()));
        titleField.setText(appointToMod.getTitle());
        typeField.setText(appointToMod.getType());
        locationField.setText(appointToMod.getLocation());
        descArea.setText(appointToMod.getDescription());
        datePicker.setValue(appointToMod.getStartDateAndTime().toLocalDate());
        startTimeListView.getSelectionModel().select(appointToMod.getStartDateAndTime().toLocalTime().toString());
        System.out.println(appointToMod.getStartDateAndTime().toLocalTime().toString());
        //TODO populate rest of forms and dates and times
    }

    @FXML
    private void updateEndTime() {

        endTimeListView.getSelectionModel().clearSelection();
        //gets starting time from starting time list
        String theTime = startTimeListView.getSelectionModel().getSelectedItem();

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

    @FXML
    private void addModifyApp(ActionEvent event) {


        if (isFilledOut() && !hasAppOverlap()) {
            String title = titleField.getText();
            String description = descArea.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            LocalDateTime startDateTime = getStartDateTime();
            LocalDateTime endDateTime = getEndDateTime();
            int customerID = DataCache.getCustomerID(customerCombo.getValue());
            int contactID = DataCache.getContactID(contactCombo.getValue());
            int userID = DataCache.getUserID(userCombo.getValue());
            System.out.println(customerCombo.getValue() + "   " + customerID + "\n" + contactCombo.getValue() + "    " + contactID + "\n" + userCombo.getValue() + "    " + userID + "\n");

            if (isAdding) {
                String createdBy = DAO.getUsername();
                Appointment newApp = new Appointment(0, title, description, location, type, Formatter.localToUTC(startDateTime), Formatter.localToUTC(endDateTime), createdBy, customerID, userID, contactID);
                //Appointment newApp = new Appointment(0, "testy", "testy", "testy", "testy", Formatter.localToUTC(startDateTime), Formatter.localToUTC(endDateTime), "testy", 1, 2, 1);

                DBConnection.openConnection();
                DAO<DataCache> dao = new AppointmentDAO(DBConnection.getConnection());
                dao.create(newApp);
                DataCache.addAppointment(newApp);
                DBConnection.closeConnection();

            } else {
                String updatedBy = DAO.getUsername();
                //TODO initiate query to UPDATE
            }
        } else {
            //TODO add alert about missed areas
        }
    }

    private boolean hasAppOverlap() {
        boolean hasOverlap = false;
        //TODO figure out how to find appointment overlap
        return hasOverlap;
    }

    private LocalDateTime getStartDateTime() {

        LocalDate selectedDate = datePicker.getValue();
        String startTime = startTimeListView.getSelectionModel().getSelectedItem();
        LocalTime start = LocalTime.parse(startTime);
        LocalDateTime localStart = LocalDateTime.of(selectedDate, start);
        System.out.println("localStarttime   " + localStart);
        return localStart;
    }

    private LocalDateTime getEndDateTime() {

        LocalDate selectedDate = datePicker.getValue();
        String endTime = endTimeListView.getSelectionModel().getSelectedItem();
        LocalTime end = LocalTime.parse(endTime);
        LocalDateTime localEnd = LocalDateTime.of(selectedDate, end);
        return localEnd;
    }

    private boolean isFilledOut() {

        boolean isAllFilled = true;
        for (Node node : parentPane.getChildren()) {
            if (node instanceof TextField tf) {
                if (tf.getText().trim().isEmpty() && !tf.getId().equals("appIDField")) {
                    tf.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    tf.setStyle("");
                }
            } else if (node instanceof TextArea ta) {
                if (ta.getText().trim().isEmpty()) {
                    ta.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    ta.setStyle("");
                }
            } else if (node instanceof ListView lv) {
                if (lv.getItems().isEmpty()) {
                    lv.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    lv.setStyle("");
                }
            } else if (node instanceof MenuButton mb) {
                if (mb.getText().trim().isEmpty()) {
                    mb.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    System.out.println(mb.getText());
                    mb.setStyle("");
                }
            }
        }

        return isAllFilled;
    }

    @FXML
    private void close() {

        Stage appStage = SceneManager.getStage("appointment");
        SceneManager.getStage("appointment").fireEvent(new WindowEvent(appStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
