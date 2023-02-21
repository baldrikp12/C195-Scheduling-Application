module kbaldr2.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    
    opens kbaldr2.controller to javafx.fxml;
    exports kbaldr2.controller;
    exports kbaldr2.main;
    opens kbaldr2.main to javafx.fxml;
    opens kbaldr2.model to javafx.base;
}