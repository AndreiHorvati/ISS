package com.example.monitorizareangajati;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeesListViewController {
    public void showAddTaskView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("views/add-task-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void showAddEmployeeView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("views/add-employee-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
