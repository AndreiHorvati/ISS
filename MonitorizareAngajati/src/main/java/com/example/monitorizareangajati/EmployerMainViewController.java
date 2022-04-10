package com.example.monitorizareangajati;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EmployerMainViewController {
    @FXML
    GridPane gridPane;

    public void loadMainView(FXMLLoader loader) {
        try {
            gridPane.getChildren().clear();
            gridPane.add(loader.load(), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeesListView() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/employees-list-view.fxml"));
        loadMainView(loader);
    }

    public void loadTasksListView() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/tasks-list-view.fxml"));
        loadMainView(loader);
    }
}
