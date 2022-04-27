import controller.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employer;

import java.io.IOException;

public class EmployeesListViewController {
    private AddEmployeeViewController addEmployeeViewController;

    private IController controller;
    private Employer currentEmployer;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setCurrentEmployer(Employer employer) {
        this.currentEmployer = employer;
    }

    public void showAddTaskView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplicationClient.class.getResource("views/add-task-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void showAddEmployeeView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplicationClient.class.getResource("views/add-employee-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();

        this.addEmployeeViewController = loader.getController();
        this.addEmployeeViewController.setController(this.controller);
        this.addEmployeeViewController.setCurrentEmployer(this.currentEmployer);
    }
}
