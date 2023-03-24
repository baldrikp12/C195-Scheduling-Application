package kbaldr2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import kbaldr2.model.dao.ReportDAO;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AppointmentReportController implements Initializable {
    
    @FXML
    private TextArea typeMonthReport;
    
    /**
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        
        DBConnection.openConnection();
        ReportDAO dao = new ReportDAO(DBConnection.getConnection());
        
        String report = generateReport(dao.getAppointmentsByTypeAndMonth());
        typeMonthReport.setText(report);
    }
    
    private String generateReport(Map<Integer, Map<String, Integer>> appointmentsByTypeAndMonth) {
        
        StringBuilder reportBuilder = new StringBuilder();
        String currentYear = "";
        
        reportBuilder.append("Appointments Report\n");
        reportBuilder.append("===================\n\n");
        
        for (Map.Entry<Integer, Map<String, Integer>> yearMonthEntry : appointmentsByTypeAndMonth.entrySet()) {
            Integer yearMonth = yearMonthEntry.getKey();
            String year = Integer.toString(yearMonth).substring(0, 4);
            String monthNumber = Integer.toString(yearMonth).substring(4);
            Month month = Month.of(Integer.parseInt(monthNumber));
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
            Map<String, Integer> types = yearMonthEntry.getValue();
            
            if (!year.equals(currentYear)) {
                currentYear = year;
                reportBuilder.append(String.format("Year: %s\n", year));
            }
            
            reportBuilder.append(String.format("   Month: %s\n", monthName));
            
            for (Map.Entry<String, Integer> typeEntry : types.entrySet()) {
                String type = typeEntry.getKey();
                int count = typeEntry.getValue();
                reportBuilder.append(String.format("      - %s: %d\n", type, count));
            }
        }
        return reportBuilder.toString();
    }
    
}
