package kbaldr2.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kbaldr2.controller.*;

import java.io.IOException;

/**
 * A utility class responsible for creating and managing application scenes, including
 * managing the controllers for each scene.
 */
public class SceneManager {
    
    private static Stage loginStage;
    private static Stage dashboardStage;
    private static Stage customerStage;
    private static Stage appointmentStage;
    private static Stage reportStage;
    
    
    private SceneManager() {
    
    }
    
    /**
     * Opens a new login scene in a new window.
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void buildLoginScene() throws IOException {
        //builds login scene
        FXMLLoader loginLoader = new FXMLLoader(SceneManager.class.getResource("/kbaldr2/view/login.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginStage = new Stage();
        loginStage.setScene(new Scene(loginRoot));
        loginStage.setUserData(loginController);
        loginStage.setResizable(false);
        loginStage.show();
    }
    
    /**
     * Opens a new dashboard scene in a new window.
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void buildDashboardScene() throws IOException {
        //builds dashbord scene
        FXMLLoader dashboardLoader = new FXMLLoader(SceneManager.class.getResource("/kbaldr2/view/dashboard.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        DashboardController dashboardController = dashboardLoader.getController();
        dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(dashboardRoot));
        dashboardStage.setUserData(dashboardController);
        dashboardStage.setResizable(false);
        dashboardStage.show();
        
    }
    
    /**
     * Opens a new customer scene in a new window.
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void buildCustomerScene() throws IOException {
        //builds customer scene
        FXMLLoader customerLoader = new FXMLLoader(SceneManager.class.getResource("/kbaldr2/view/customer.fxml"));
        Parent customerRoot = customerLoader.load();
        CustomerController customerController = customerLoader.getController();
        customerStage = new Stage();
        customerStage.setScene(new Scene(customerRoot));
        customerStage.setUserData(customerController);
        customerStage.setResizable(false);
        customerStage.show();
        
    }
    
    /**
     * Opens a new appointment scene in a new window.
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void buildAppointmentScene() throws IOException {
        //builds appointment scene
        FXMLLoader appointmentLoader = new FXMLLoader(SceneManager.class.getResource("/kbaldr2/view/appointment.fxml"));
        Parent appointmentRoot = appointmentLoader.load();
        AppointmentController appointmentController = appointmentLoader.getController();
        appointmentStage = new Stage();
        appointmentStage.setScene(new Scene(appointmentRoot));
        appointmentStage.setUserData(appointmentController);
        appointmentStage.setResizable(false);
        appointmentStage.show();
    }
    
    /**
     * Opens a new report scene in a new window.
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void buildReportScene() throws IOException {
        //builds report scene
        FXMLLoader reportLoader = new FXMLLoader(SceneManager.class.getResource("/kbaldr2/view/report.fxml"));
        Parent reportRoot = reportLoader.load();
        ReportController reportController = reportLoader.getController();
        reportStage = new Stage();
        reportStage.setScene(new Scene(reportRoot));
        reportStage.setUserData(reportController);
        reportStage.setResizable(false);
        reportStage.show();
        
    }
    
    /**
     * Retrieves the stage object based on the stage name.
     *
     * @param theStageName the stage name to retrieve
     * @return the Stage object corresponding to the stage name
     */
    public static Stage getStage(String theStageName) {
        
        Stage theStage = null;
        switch (theStageName) {
            case "login":
                theStage = loginStage;
                break;
            case "dashboard":
                theStage = dashboardStage;
                break;
            case "customer":
                theStage = customerStage;
                break;
            case "appointment":
                theStage = appointmentStage;
                break;
            case "report":
                theStage = reportStage;
                break;
        }
        return theStage;
        
    }
    
    /**
     * Retrieves the LoginController from the login stage.
     *
     * @return the LoginController object
     */
    public static LoginController getLoginController() {
        
        LoginController loginController = (LoginController) loginStage.getUserData();
        
        return loginController;
    }
    
    /**
     * Retrieves the DashboardController from the dashboard stage.
     *
     * @return the DashboardController object
     */
    public static DashboardController getDashboardController() {
        
        DashboardController dashboardController = (DashboardController) dashboardStage.getUserData();
        
        return dashboardController;
    }
    
    /**
     * Retrieves the CustomerController from the customer stage.
     *
     * @return the CustomerController object
     */
    public static CustomerController getCustomerController() {
        
        CustomerController customerController = (CustomerController) customerStage.getUserData();
        
        return customerController;
    }
    
    /**
     * Retrieves the AppointmentController from the appointment stage.
     *
     * @return the AppointmentController object
     */
    public static AppointmentController getAppointmentController() {
        
        AppointmentController appointmentController = (AppointmentController) appointmentStage.getUserData();
        
        return appointmentController;
    }
    
    /**
     * Retrieves the ReportController from the report stage.
     *
     * @return the ReportController object
     */
    public static ReportController getReportController() {
        
        ReportController reportController = (ReportController) reportStage.getUserData();
        
        return reportController;
    }
    
}