import controller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Employer;

public class LoginViewController {
    IController controller;
    SceneController sceneController;

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
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
            String password = passwordTextField.getText();

            Employer employer = new Employer("", "", username, password);

            sceneController.initMainScene();
            MainViewController mainViewController = this.sceneController.getMainViewController();

            mainViewController.setController(controller);
            mainViewController.setSceneController(sceneController);

            controller.loginEmployer(employer, mainViewController);

            this.sceneController.changeToMainScene();
            mainViewController.setCurrentEmployer(controller.getEmployerByUsername(username));
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
