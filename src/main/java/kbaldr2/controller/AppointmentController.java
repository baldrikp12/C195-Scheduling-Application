package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Formatter;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.Contact;
import kbaldr2.model.Customer;
import kbaldr2.model.DAO;
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
    private AnchorPane parentPane;
    @FXML
    private TextField appIDField;
    @FXML
    private MenuButton contactMenu;

    @FXML
    private Button addModifyButton;

    @FXML
    private Label currentUserLabel;

    @FXML
    private MenuButton customerMenu;

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
        currentUserLabel.setText(DAO.getUsername());
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
        if (LocalTime.now().isBefore(bod) || LocalTime.now().isAfter(eod)) {
            System.out.println("The selected date is NOT today.");
            ObservableList<String> items = FXCollections.observableArrayList();

            LocalTime start = Formatter.ESTtoLocal(LocalTime.of(8, 0));
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(Formatter.formatTime(time));
            }
            startTimeListView.setItems(items);
        } else {
            ObservableList<String> items = FXCollections.observableArrayList();
            LocalTime start = LocalTime.of(now().getHour(), 0);
            LocalTime end = LocalTime.of(localCloseHour, localCloseMinute);
            for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
                items.add(Formatter.formatTime(time));
            }
            startTimeListView.setItems(items);
        }

    }

    public void setScene(int i) {

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

        if (isFilledOut()) {
            String title = titleField.getText();
            String type = typeField.getTypeSelector();
            String location = locationField.getText();
            String description = descArea.getText();
            LocalDateTime startDateTime = getStartDateTime();
            LocalDateTime endDateTime = getEndDateTime();
            String customerName = customerMenu.getText();
            String contactName = contactMenu.getText();
            if (isAdding) {
                //TODO: User might be selected so created by will be different.
                String userName = currentUserLabel.getText();
                String createdBy = currentUserLabel.getText();
                //String assignUser = userMenu.getText();
                //initiate query to INSERT
            } else {
                //TODO: if modifying then created by stays the same and updatedby will be updated.
                String userName = currentUserLabel.getText();
                //initiate query to UPDATE
            }
        } else {
            //TODO add alert about missed areas
        }
    }

    private LocalDateTime getStartDateTime() {
        LocalDate selectedDate = datePicker.getValue();
        String startTime = startTimeListView.getSelectionModel().getSelectedItem();
        LocalTime start = LocalTime.parse(startTime);
        LocalDateTime localStart = LocalDateTime.of(selectedDate, start);
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
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                if (tf.getText().trim().isEmpty() && !tf.getId().equals("appIDField")) {
                    tf.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    tf.setStyle("");
                }
            } else if (node instanceof TextArea) {
                TextArea ta = (TextArea) node;
                if (ta.getText().trim().isEmpty()) {
                    ta.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    ta.setStyle("");
                }
            } else if (node instanceof ListView) {
                ListView lv = (ListView) node;
                if (lv.getItems().isEmpty()) {
                    lv.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    lv.setStyle("");
                }
            } else if (node instanceof MenuButton) {
                MenuButton mb = (MenuButton) node;
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
