import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainScene;

    private LoginViewController loginViewController;
    private MainViewController mainViewController;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void initLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationClient.class.getResource("views/login-view.fxml"));

        loginScene = new Scene(fxmlLoader.load(), 50, 250);
        loginViewController = fxmlLoader.getController();
    }

    public void initMainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationClient.class.getResource("views/employee-main-view.fxml"));

        mainScene = new Scene(fxmlLoader.load());
        mainViewController = fxmlLoader.getController();
    }

    public void changeToLoginScene() throws IOException {
        initLoginScene();

        this.primaryStage.setScene(loginScene);
        this.primaryStage.centerOnScreen();

        this.primaryStage.setWidth(400);
        this.primaryStage.setHeight(480);
        this.primaryStage.centerOnScreen();
    }

    public void changeToMainScene() {
        this.primaryStage.setScene(mainScene);
        this.primaryStage.setWidth(720);
        this.primaryStage.setHeight(470);
        this.primaryStage.setMaxWidth(720);
        this.primaryStage.setMaxHeight(470);
        this.primaryStage.centerOnScreen();
    }

    public LoginViewController getLoginViewController() {
        return this.loginViewController;
    }

    public MainViewController getMainViewController() {
        return mainViewController;
    }
}
