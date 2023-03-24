package kbaldr2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import kbaldr2.model.DataCache;
import kbaldr2.model.ReportTypeMonth;
import kbaldr2.model.dao.AppointmentDAO;
import kbaldr2.model.dao.DAO;
import kbaldr2.model.dao.ReportDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    private static final ObservableList<ReportTypeMonth> reportTypeMonth = FXCollections.observableArrayList();
    @FXML
    private TextArea reportArea;
    /**
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.openConnection();
        ReportDAO dao = new ReportDAO(DBConnection.getConnection());

        try {
            reportTypeMonth.addAll(dao.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(ReportTypeMonth report : reportTypeMonth){
            reportArea.appendText(report.getType()+"     "+report.getMonth()+"     "+report.getQty()+"\n");
        }
    }
    
}
