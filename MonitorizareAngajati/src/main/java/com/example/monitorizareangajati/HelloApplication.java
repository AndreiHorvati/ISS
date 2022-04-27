package com.example.monitorizareangajati;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/employer-main-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/employee-main-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 720, 445);
        //stage.setScene(scene);
        //stage.show();

        //EmployeeORMRepository employeeORMRepository
    }

    public static void main(String[] args) {
        launch();
    }
}