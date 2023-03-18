package kbaldr2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kbaldr2.helper.Alerts;
import kbaldr2.helper.SceneManager;
import kbaldr2.model.Customer;
import kbaldr2.model.DataCache;
import kbaldr2.model.Location;
import kbaldr2.model.dao.CustomerDAO;
import kbaldr2.model.dao.DAO;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    
    private Customer customerToMod;
    private boolean isAdding = true;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private Label addModLabel;
    @FXML
    private Label appIDField;
    @FXML
    private Button addModifyButton;
    
    @FXML
    private ComboBox<String> divisionCombo;
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField postalField;
    
    @FXML
    private ToggleGroup regionGroup;
    
    @FXML
    private RadioButton usRadio;
    @FXML
    private RadioButton ukRadio;
    @FXML
    private RadioButton caRadio;
    @FXML
    private Label userLabel;
    
    
    /**
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        setUSList();
        userLabel.setText(DAO.getUsername());
    }
    
    public void setCustomerToModify(DataCache theItem) {
        
        addModLabel.setText("Update Appointment");
        addModifyButton.setText("Update");
        customerToMod = (Customer) theItem;
        isAdding = false;
        populateForm();
    }
    
    private void populateForm() {
        
        appIDField.setText(Integer.toString(customerToMod.getId()));
        nameField.setText(customerToMod.getName());
        addressField.setText(customerToMod.getAddress());
        postalField.setText(customerToMod.getPostalCode());
        phoneField.setText(customerToMod.getPhone());
        switch (DataCache.getCountryIDFromDivisionID(customerToMod.getDivisionID())) {
            case 1:
                usRadio.setSelected(true);
                setUSList();
                break;
            case 2:
                ukRadio.setSelected(true);
                setUKList();
                break;
            case 3:
                caRadio.setSelected(true);
                setCAList();
                break;
        }
        divisionCombo.setValue(DataCache.getDivisionName(customerToMod.getDivisionID()));
        
    }
    
    @FXML private void addModifyCust(ActionEvent event) {
        
        if (isFilledOut()) {
            if (!containsDivision()) {
                String name = nameField.getText();
                String address = addressField.getText();
                String postal = postalField.getText();
                String phone = phoneField.getText();
                int firstDivision = DataCache.getFirstDivisionID(divisionCombo.getValue());
                
                DBConnection.openConnection();
                DAO<DataCache> dao = new CustomerDAO(DBConnection.getConnection());
                
                if (isAdding) {
                    Customer newCust = new Customer(0, name, address, postal, phone, firstDivision);
                    String createdBy = DAO.getUsername();
                    newCust.setCreatedBy(createdBy);
                    
                    dao.create(newCust);
                    DataCache.addCustomer(newCust);
                    
                } else {
                    String updatedBy = DAO.getUsername();
                    customerToMod.setName(name);
                    customerToMod.setAddress(address);
                    customerToMod.setPostalCode(postal);
                    customerToMod.setPhone(phone);
                    customerToMod.setDivisionID(firstDivision);
                    customerToMod.setUpdatedBy(updatedBy);
                    
                    dao.update(customerToMod);
                    
                }
                
                DBConnection.closeConnection();
                close();
            }
        } else {
            Alerts.showAlert("Please fill form out completely", "Empty Fields");
        }
    }
    
    private boolean containsDivision() {
        
        for (DataCache item : DataCache.getAllLocations()) {
            Location loc = (Location) item;
            if (addressField.getText().contains(loc.getDivisionName().toLowerCase())) {
                Alerts.showAlert("Address should only contain home and street number", "Invalid Address");
                return true;
            }
        }
        return false;
    }
    
    private boolean isFilledOut() {
        
        boolean isAllFilled = true;
        for (Node node : parentPane.getChildren()) {
            if (node instanceof TextField tf) {
                if (tf.getText().trim().isEmpty()) {
                    tf.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    tf.setStyle("");
                }
            } else if (node instanceof ComboBox cb) {
                if (cb.getSelectionModel().isEmpty()) {
                    cb.setStyle("-fx-border-color: RED;");
                    isAllFilled = false;
                } else {
                    System.out.println(cb.getValue());
                    cb.setStyle("");
                }
            }
        }
        return isAllFilled;
    }
    
    @FXML private void setUSList() {
        
        divisionCombo.setItems(Location.getUs());
        divisionCombo.setPromptText("States");
    }
    
    @FXML private void setUKList() {
        
        
        divisionCombo.setItems(Location.getUk());
        divisionCombo.setPromptText("Countries");
    }
    
    @FXML private void setCAList() {
        
        divisionCombo.setItems(Location.getCa());
        divisionCombo.setPromptText("Provinces");
        
    }
    
    
    @FXML private void close() {
        
        Stage custStage = SceneManager.getStage("customer");
        SceneManager.getStage("customer").fireEvent(new WindowEvent(custStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
