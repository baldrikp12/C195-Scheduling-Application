package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Alerts;
import kbaldr2.helper.SceneManager;
import kbaldr2.helper.TableFormatter;
import kbaldr2.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    String[] customerColumns = {"Name", "Address", "Phone"};

    String[] appointmentColumns = {"AppointmentID", "Title", "Description", "Location", "Contact", "Type", "Start Date and Time", "End Date and Time", "CustomerID", "UserID"};
    private String user = "";
    private boolean viewingAppointments = true;
    @FXML
    private Label appAlertLabel;
    @FXML
    private Label appAlertInfoLabel;
    @FXML
    private TableView<DataCache> dataCacheTable;
    @FXML
    private Button closeAppAlertButton;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableFormatter.setTableView(dataCacheTable);
        TableFormatter.buildAppTables();

        try {
            getAllCustomers();
            getAllAppointments();
            getAllContacts();
            getAllFirstDivisions();
            getAllCountries();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dataCacheTable.setItems(DataCache.getAllAppointments());
        fifteenMinuteCheck();
    }

    private void getAllCustomers() throws SQLException {

        DBConnection.openConnection();
        DAO<DataCache> dao = new CustomerDAO(DBConnection.getConnection());
        DataCache.setAllCustomers(dao.getAll());
        DataCache.addToAllObjectList(DataCache.getAllCustomers());

        DBConnection.closeConnection();
    }

    private void getAllAppointments() throws SQLException {

        DBConnection.openConnection();
        DAO<DataCache> dao = new AppointmentDAO(DBConnection.getConnection());
        DataCache.setAllAppointments(dao.getAll());
        DataCache.addToAllObjectList(DataCache.getAllAppointments());

        DBConnection.closeConnection();
    }

    private void getAllContacts() throws SQLException {

        DBConnection.openConnection();
        DAO<DataCache> dao = new ContactDAO(DBConnection.getConnection());
        DataCache.setAllContacts(dao.getAll());
        DBConnection.closeConnection();
    }

    private void getAllFirstDivisions() throws SQLException {

        DBConnection.openConnection();
        DAO<DataCache> dao = new DivisionDAO(DBConnection.getConnection());
        DataCache.setAllFirstLevelDivision(dao.getAll());
        DataCache.addToAllObjectList(DataCache.getAllFirstLevelDivision());

        DBConnection.closeConnection();
    }

    private void getAllCountries() throws SQLException {

        DBConnection.openConnection();
        DAO<DataCache> dao = new CountryDAO(DBConnection.getConnection());
        DataCache.setAllCountries(dao.getAll());
        DataCache.addToAllObjectList(DataCache.getAllCountries());

        DBConnection.closeConnection();
    }

    private void fifteenMinuteCheck() {

        LocalDateTime now = LocalDateTime.now();
        boolean appointmentInFifteen = false;
        Appointment nearestAppointment = null;
        long nearestDifference = Long.MAX_VALUE;

        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment appointment = (Appointment) item;
            LocalDateTime appointmentDateTime = appointment.getStartDateAndTime();

            // Calculate the difference between the appointment time and the current time in minutes
            long difference = ChronoUnit.MINUTES.between(now, appointmentDateTime);

            // Check if the difference is less than or equal to 15 and is positive
            if (difference <= 15 && difference >= 0) {
                // Check if this appointment is the nearest one to the current time
                if (difference < nearestDifference) {
                    nearestDifference = difference;
                    nearestAppointment = appointment;
                }
                appointmentInFifteen = true;
            }
        }

        if (appointmentInFifteen) {
            // Display the nearest appointment that is within 15 minutes
            appAlertLabel.setText("There is an appointment within 15 minutes.");
            appAlertLabel.setStyle("-fx-text-fill: RED; -fx-background-color: CCCCCC");
            appAlertInfoLabel.setText("[  Appointment " + nearestAppointment.getId() + " | " + nearestAppointment.getStartDateAndTime().toLocalDate() + " | " + nearestAppointment.getStartDateAndTime().toLocalTime() + "  ]");
            appAlertInfoLabel.setStyle("-fx-text-fill: RED; -fx-background-color: CCCCCC");
            appAlertLabel.setVisible(true);
            appAlertInfoLabel.setVisible(true);
        } else {
            // Display that there are no appointments within 15 minutes
            appAlertLabel.setText("There are no appointments within 15 minutes.");
            appAlertLabel.setStyle("-fx-text-fill: BLUE; -fx-background-color: CCCCCC");
            appAlertInfoLabel.setVisible(false);
        }

        closeAppAlertButton.setVisible(true);
    }


    @FXML
    private void viewAppsThisMonth() {

        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }
        ObservableList<DataCache> filteredData = FXCollections.observableArrayList();

        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();

        for (DataCache item : DataCache.getAllAppointments()) {

            Appointment appointment = (Appointment) item;
            if (appointment.getStartDateAndTime().getMonthValue() == currentMonth) {
                filteredData.add(appointment);
            }

        }
        dataCacheTable.setItems(filteredData);
    }

    @FXML
    private void viewAppsThisWeek() {

        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.truncatedTo(ChronoUnit.DAYS).with(DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6);
        ObservableList<DataCache> currentWeekAppointments = FXCollections.observableArrayList();

        for (DataCache item : DataCache.getAllAppointments()) {
            Appointment appointment = (Appointment) item;
            LocalDateTime startDateTime = ((Appointment) item).getStartDateAndTime();
            if (startDateTime.isBefore(startOfWeek) || startDateTime.isAfter(endOfWeek)) {

            } else {
                currentWeekAppointments.add(appointment);
            }
        }
        dataCacheTable.setItems(currentWeekAppointments);
    }

    @FXML
    private void viewAllApps() {

        if (!viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildAppTables();
            viewingAppointments = true;
        }
        dataCacheTable.setItems(DataCache.getAllAppointments());
        dataCacheTable.refresh();
    }

    @FXML
    private void viewAllCusts() {

        if (viewingAppointments) {
            dataCacheTable.getColumns().clear();
            TableFormatter.buildCustTables();
            viewingAppointments = false;
        }
        dataCacheTable.setItems(DataCache.getAllCustomers());
        dataCacheTable.refresh();
    }

    @FXML
    void closeAppAlert(ActionEvent event) throws IOException {

        appAlertLabel.setVisible(false);
        appAlertInfoLabel.setVisible(false);
        closeAppAlertButton.setVisible(false);

    }

    @FXML
    private void addRecord() throws IOException {

        disableDashboard(true);
        SceneManager.buildAppointmentScene();
        SceneManager.getStage("appointment").setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                disableDashboard(false);
            }
        });
        SceneManager.getAppointmentController().setScene(1);
    }

    public void disableDashboard(boolean theOption) {

        SceneManager.getStage("dashboard").getScene().getRoot().setDisable(theOption);
    }

    @FXML
    private void modifyRecord() throws IOException {

        SceneManager.buildAppointmentScene();
        SceneManager.getAppointmentController().setScene(2);
    }

    @FXML
    private void deleteRecord() throws SQLException {

        if (dataCacheTable.getSelectionModel().isEmpty()) {
            Alerts.showWarning("You have not selected anything to delete.", "Nothing Selected");

        } else {
            boolean result = Alerts.showConfirmationDialog("Are you sure you want to delete the selected record?", "Delete?");

            if (result) {
                DataCache recordToDelete = dataCacheTable.getSelectionModel().getSelectedItem();
                DBConnection.openConnection();
                DAO<DataCache> dao;
                if (dataCacheTable.getSelectionModel().getSelectedItem() instanceof Appointment) {
                    dao = new AppointmentDAO(DBConnection.getConnection());
                    dao.delete(recordToDelete.getId());
                    DataCache.getAllAppointments().remove(dataCacheTable.getSelectionModel().getSelectedItem());
                    DataCache tempList = dataCacheTable.getSelectionModel().getSelectedItem();
                    dataCacheTable.getItems().remove(tempList);
                } else if (hasAppointments()) {
                    Alerts.showWarning("Customer still has appointments", "Open Appointments");
                } else {
                    dao = new CustomerDAO(DBConnection.getConnection());
                    dao.delete(recordToDelete.getId());
                    DataCache.getAllCustomers().remove(dataCacheTable.getSelectionModel().getSelectedItem());
                    DataCache tempList = dataCacheTable.getSelectionModel().getSelectedItem();
                    dataCacheTable.getItems().remove(tempList);
                }

            }
        }
    }

    private boolean hasAppointments() {

        boolean hasAppointments = false;
        List<Appointment> listOfAppointments = new ArrayList<>();
        Customer cust = (Customer) dataCacheTable.getSelectionModel().getSelectedItem();
        Appointment app;
        for (DataCache data : DataCache.getAllAppointments()) {
            app = (Appointment) data;
            if (cust.getId() == app.getCustomerID()) {
                listOfAppointments.add(app);
                hasAppointments = true;
            }
        }
        return hasAppointments;
    }

    @FXML
    private void logout() throws IOException {
        //TODO: clear all the objects and lists in case we log back in...

        /**DataCache.getAllAppointments().clear();
        DataCache.getAllAppointments().clear();
        DataCache.getAllContacts().clear();
        DataCache.getAllCountries().clear();
        DataCache.getAllFirstLevelDivision().clear();*/
        DataCache.clearObjects();
        SceneManager.buildLoginScene();
        SceneManager.getStage("dashboard").close();
    }
}