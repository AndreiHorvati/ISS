import controller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Employee;
import model.Employer;
import model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddTaskViewController {
    private IController controller;
    private Employee selectedEmployee;

    @FXML
    TextField nameTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    DatePicker deadlineDatePicker;
    @FXML
    TextField hourTextField;
    @FXML
    Button addTaskButton;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setSelectedEmployee(Employee employee) {
        this.selectedEmployee = employee;
    }

    public void onAddTaskButtonPressed() throws Exception {
        String name = nameTextField.getText();
        String description = descriptionTextArea.getText();
        LocalDate deadlineDate = deadlineDatePicker.getValue();
        int hour = Integer.parseInt(hourTextField.getText());
        LocalDateTime deadline = deadlineDate.atTime(hour, 0);

        Task task = new Task(name, description, deadline, selectedEmployee);

        if (selectedEmployee.getHour() != -1) {
            this.controller.addTask(task);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avertizare...");
            alert.setHeaderText("Avertizare");
            alert.setContentText("Nu puteti da o sarcina unui angajat care nu a venit inca la munca!");
            alert.showAndWait();
        }
    }
}
