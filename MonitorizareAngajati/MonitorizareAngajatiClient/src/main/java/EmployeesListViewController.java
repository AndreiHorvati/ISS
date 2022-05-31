import controller.IController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Employer;
import model.Status;

import java.io.IOException;
import java.util.List;

public class EmployeesListViewController {
    private AddEmployeeViewController addEmployeeViewController;
    private AddTaskViewController addTaskViewController;

    private IController controller;
    private Employer currentEmployer;
    ObservableList<Employee> model = FXCollections.observableArrayList();

    @FXML
    TableView<Employee> tableView;
    @FXML
    TableColumn<Employee, String> tableColumnFirstName;
    @FXML
    TableColumn<Employee, String> tableColumnLastName;
    @FXML
    TableColumn<Employee, Integer> tableColumnHour;

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

        this.addTaskViewController = loader.getController();
        this.addTaskViewController.setController(this.controller);
        this.addTaskViewController.setSelectedEmployee(this.tableView.getSelectionModel().getSelectedItem());
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

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                tableColumnFirstName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFirstName()));
                tableColumnLastName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getLastName()));
                tableColumnHour.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getHour()));

                tableView.setItems(model);

                tableView.setRowFactory(tv -> new TableRow<>() {
                    @Override
                    protected void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null) {
                            setStyle("");
                        }
                        else if (item.getHour() == -1) {
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

    @FXML
    public void initModel() {
        Platform.runLater(() -> {
            try {
                model.clear();
                model.setAll((List<Employee>)controller.getEmployeesOfAnEmployer(this.currentEmployer));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteEmployeeGUI() throws Exception {
        Employee selectedEmployee = this.tableView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            this.controller.deleteEmployee(selectedEmployee);
        }
    }
}
