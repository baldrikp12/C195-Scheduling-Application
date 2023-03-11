package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kbaldr2.model.Customer;
import kbaldr2.model.DAO;
import kbaldr2.model.DataCache;
import kbaldr2.model.Location;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    
    private Customer customerToMod;
    private boolean isAdding = true;
    @FXML
    private Button addModifyButton;
    
    @FXML
    private Label addModLabel;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private RadioButton caRadio;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Label custIDField;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField postalField;
    
    @FXML
    private RadioButton ukRadio;
    
    @FXML
    private RadioButton usRadio;
    
    @FXML
    private ComboBox<String> divisionCombo;
    
    @FXML
    private Label userLabel;
    
    /**
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        divisionCombo.setItems(Location.getUs());
        userLabel.setText(DAO.getUsername());
        //TODO 1: populate form when modifying
        //TODO 2: set up radio button actions
        //TODO 3: set up addmodifybutton method
        //TODO 4: need to check if string contains any string from divisioncombo.
        //TODO 5: verify no empty fields
    }
    
    public void setCustomerToModify(DataCache theItem) {
        
        addModLabel.setText("Modify Appointment");
        addModifyButton.setText("Update");
        customerToMod = (Customer) theItem;
        isAdding = false;
        populateForm();
    }
    
    private void populateForm() {
    
    }
    
}
