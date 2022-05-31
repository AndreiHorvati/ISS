import controller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Employee;
import model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UpdateTaskViewController {
    private IController controller;
    private Task selectedTask;

    @FXML
    TextField nameTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    DatePicker deadlineDatePicker;
    @FXML
    TextField hourTextField;
    @FXML
    Button updateTaskButton;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setSelectedTask(Task task) {
        this.selectedTask = task;
    }

    public void onUpdateTaskButtonPressed() throws Exception {
        String name = nameTextField.getText();
        String description = descriptionTextArea.getText();
        LocalDate deadlineDate = deadlineDatePicker.getValue();
        int hour = Integer.parseInt(hourTextField.getText());
        LocalDateTime deadline = deadlineDate.atTime(hour, 0);

        Task task = new Task(name, description, deadline, selectedTask.getEmployee());
        task.setId(selectedTask.getId());

        this.controller.updateTask(task);
    }
}
