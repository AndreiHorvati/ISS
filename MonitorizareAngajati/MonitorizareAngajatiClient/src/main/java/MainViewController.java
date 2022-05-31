import controller.IController;
import controller.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import model.Employee;
import model.Employer;
import model.Status;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, IObserver {
    private IController controller;
    private SceneController sceneController;
    private EmployeesListViewController employeesListViewController;
    private TasksListViewController tasksListViewController;

    private Employer currentEmployer;

    @FXML
    private GridPane gridPane;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setCurrentEmployer(Employer employer) {
        this.currentEmployer = employer;
    }

    public void loadMainView(FXMLLoader loader) {
        try {
            gridPane.getChildren().clear();
            gridPane.add(loader.load(), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeesListView() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/employees-list-view.fxml"));
        loadMainView(loader);

        this.employeesListViewController = loader.getController();
        this.employeesListViewController.setController(this.controller);
        this.employeesListViewController.setCurrentEmployer(this.currentEmployer);
        this.employeesListViewController.initModel();
    }

    public void loadTasksListView() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/tasks-list-view.fxml"));
        loadMainView(loader);

        this.tasksListViewController = loader.getController();
        this.tasksListViewController.setController(this.controller);
        this.tasksListViewController.setCurrentEmployer(this.currentEmployer);
        this.tasksListViewController.initModel();
    }

    @Override
    public void employeeAdded() {
        if (this.employeesListViewController != null) {
            this.employeesListViewController.initModel();
        }
    }

    @Override
    public void employeeDeleted() throws Exception {
        if (this.employeesListViewController != null) {
            this.employeesListViewController.initModel();
        }
    }

    @Override
    public void taskAdded() throws Exception {

    }

    @Override
    public void taskDeleted() throws Exception {
        if (this.tasksListViewController != null) {
            this.tasksListViewController.initModel();
        }
    }

    @Override
    public void taskUpdated() throws Exception {
        if (this.tasksListViewController != null) {
            this.tasksListViewController.initModel();
        }
    }

    @Override
    public void employeeUpdated() {
        if (this.employeesListViewController != null) {
            this.employeesListViewController.initModel();
        }
    }

    public void onLogoutButtonPressed() throws IOException {
        sceneController.changeToLoginScene();

        LoginViewController loginViewController = sceneController.getLoginViewController();

        loginViewController.setController(controller);
        loginViewController.setSceneController(sceneController);

        try {
            controller.logoutEmployer(this.currentEmployer, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }
    }
}
