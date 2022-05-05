import controller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Employee;

public class LoginViewController {
    IController controller;
    SceneController sceneController;

    @FXML
    TextField usernameTextField;
    @FXML
    Button loginButton;

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void onLoginButtonClick() {
        try {
            String username = usernameTextField.getText();

            Employee employee = new Employee("", "", username, null);

            sceneController.initMainScene();
            MainViewController mainViewController = this.sceneController.getMainViewController();

            mainViewController.setController(controller);
            mainViewController.setSceneController(sceneController);

            controller.loginEmployee(employee, mainViewController);

            this.sceneController.changeToMainScene();

            Employee currentEmployee = controller.getEmployeeByUsername(username);

            mainViewController.setCurrentEmployee(currentEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Athletics Competition");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }

    }
}
