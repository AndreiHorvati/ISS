import controller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Employee;
import model.Employer;

public class AddEmployeeViewController {
    private IController controller;
    private Employer currentEmployer;

    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField usernameTextField;
    @FXML
    Button addEmployeeButton;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setCurrentEmployer(Employer employer) {
        this.currentEmployer = employer;
    }

    public void onAddEmployeeButtonPressed() throws Exception {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();

        Employee employee = new Employee(firstName, lastName, username, currentEmployer);

        System.out.println(currentEmployer.getId());

        this.controller.addEmployee(employee);
    }
}
