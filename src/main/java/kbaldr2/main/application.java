package kbaldr2.main;

import javafx.application.Application;
import javafx.stage.Stage;
import kbaldr2.helper.SceneManager;

import java.io.IOException;


public class application extends Application {
    
    
    public static void main(String[] args) {
        
        launch();
    }
    
    @Override public void start(Stage stage) throws IOException {
        
        SceneManager.buildLoginScene();
        
    }
    
}