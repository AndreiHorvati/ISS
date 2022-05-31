import controller.IController;
import controller.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    private Employee currentEmployee;
    private boolean startedWork = false;

    ObservableList<Task> model = FXCollections.observableArrayList();

    @FXML
    private TableView<Task> tableView;
    @FXML
    private TableColumn<Task, String> tableColumnName;
    @FXML
    private TableColumn<Task, String> tableColumnDescription;
    @FXML
    private TableColumn<Task, LocalDateTime> tableColumnDeadline;
    @FXML
    private TableColumn<Task, Status> tableColumnStatus;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setCurrentEmployee(Employee employee) {
        this.currentEmployee = employee;
    }

    /*
    public void loadEntryMenu() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/competition-entry-view.fxml"));
        CompetitionEntryViewController competitionEntryViewController;

        loadMainView(loader);

        competitionEntryViewController = loader.getController();
        competitionEntryViewController.setOnDatePickerChanged();
        competitionEntryViewController.setController(controller);
    }

    public void loadSearchMenu() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/search-view.fxml"));

        loadMainView(loader);

        this.searchViewController = loader.getController();
        this.searchViewController.setController(controller);
        this.searchViewController.setComboBoxItems();
    }

    public void onEntryButtonClicked() {
        loadEntryMenu();
    }

    public void onSearchButtonClicked() {
        loadSearchMenu();
    }
     */

    /*
    public void onCompetitionsButtonPressed() throws Exception {
        loadCompetitionsMenu();
    }
     */

    /*
    @Override
    public void officePersonLoggedIn(OfficePerson officePerson) {

    }

    @Override
    public void childEntered() throws Exception {
        if (this.competitionsViewController != null) {
            this.competitionsViewController.initModel();
        }

        if (this.searchViewController != null) {
            this.searchViewController.initModel();
        }
    }
    */

    @FXML
    public void initModel() {
        Platform.runLater(() -> {
            try {
                model.clear();
                model.setAll((List<Task>)controller.getTasksOfAnEmployee(this.currentEmployee));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            this.initModel();

            try {
                tableColumnName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getName()));
                tableColumnDescription.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDescription()));
                tableColumnDeadline.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDeadline()));
                tableColumnStatus.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStatus()));

                tableView.setItems(model);

                tableView.setRowFactory(tv -> new TableRow<>() {
                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setStyle("");
                        }
                        else if (item.getStatus().equals(Status.UNFINISHED)) {
                            setStyle("-fx-background-color: #ff6666;");
                        }
                        else {
                            setStyle("-fx-background-color: #00cc44;");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void employeeAdded() throws Exception {

    }

    @Override
    public void employeeDeleted() throws Exception {

    }

    @Override
    public void taskAdded() throws Exception {
         this.initModel();
    }

    @Override
    public void taskDeleted() throws Exception {
        this.initModel();
    }

    @Override
    public void taskUpdated() throws Exception {
        System.out.println("andrei");
        this.initModel();
    }

    @Override
    public void employeeUpdated() {

    }

    public void finishTask() throws Exception {
        if (startedWork) {
            Task selectedTask = this.tableView.getSelectionModel().getSelectedItem();

            selectedTask.setStatus(Status.FINISHED);
            try {
                this.controller.updateTask(selectedTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avertizare...");
            alert.setHeaderText("Avertizare");
            alert.setContentText("Nu puteti termina o sarcina daca nu ati inceput ziua de lucru!");
            alert.showAndWait();
        }
    }

    public void onLogoutButtonPressed() throws IOException {
        sceneController.changeToLoginScene();

        LoginViewController loginViewController = sceneController.getLoginViewController();

        loginViewController.setController(controller);
        loginViewController.setSceneController(sceneController);

        try {
            Employee e = this.currentEmployee;
            e.setHour(-1);

            controller.updateEmployee(e);
            controller.logoutEmployee(this.currentEmployee, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }
    }

    public void startWork() throws Exception {
        int hour = LocalDateTime.now().getHour();
        Employee employee = this.currentEmployee;
        employee.setHour(hour);

        System.out.println(employee.getId());
        System.out.println(employee.getHour());

        this.controller.updateEmployee(employee);

        this.startedWork = true;
    }
}
