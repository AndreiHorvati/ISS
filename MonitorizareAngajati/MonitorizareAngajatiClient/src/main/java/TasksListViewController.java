import controller.IController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Employer;
import model.Status;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class TasksListViewController {
    private IController controller;
    private Employer currentEmployer;
    private UpdateTaskViewController updateTaskViewController;

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
    @FXML
    private TableColumn<Task, String> tableColumnEmployee;
    @FXML
    private Button deleteTaskButton;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setCurrentEmployer(Employer employer) {
        this.currentEmployer = employer;
    }

    public void showUpdateTaskView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplicationClient.class.getResource("views/update-task-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();

        this.updateTaskViewController = loader.getController();
        this.updateTaskViewController.setController(this.controller);
        this.updateTaskViewController.setSelectedTask(this.tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void initModel() {
        Platform.runLater(() -> {
            try {
                model.clear();
                model.setAll((List<Task>)controller.getTasksOfAnEmployer(this.currentEmployer));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            this.initModel();

            try {
                tableColumnName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getName()));
                tableColumnDescription.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDescription()));
                tableColumnDeadline.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDeadline()));
                tableColumnStatus.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStatus()));
                tableColumnEmployee.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getEmployee().getUsername()));

                tableView.setItems(model);

                tableView.setRowFactory(tv -> new TableRow<>() {
                    @Override
                    protected void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null)
                            setStyle("");
                        else if (item.getStatus().equals(Status.UNFINISHED))
                            setStyle("-fx-background-color: #ff6666;");
                        else
                            setStyle("-fx-background-color: #00cc44;");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteTaskGUI() throws Exception {
        Task selectedTask = this.tableView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            this.controller.deleteTask(selectedTask);
        }
    }
}
