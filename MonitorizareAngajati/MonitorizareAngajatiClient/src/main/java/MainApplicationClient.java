import controller.IController;
import javafx.application.Application;
import javafx.stage.Stage;
import network.objectprotocol.ObjectProxy;

import java.io.IOException;
import java.util.Properties;

public class MainApplicationClient extends Application {
    Properties properties;
    IController server;

    private static int defaultChatPort = 55556;
    private static String defaultServer = "localhost";

    public static void main(String[] args) {
        launch(args);
    }

    private void initializeController() {
        properties = new Properties();

        try {
            properties.load(MainApplicationClient.class.getResourceAsStream("/client.properties"));
        } catch (IOException exception) {
            System.out.println("Cannot find bd.config " + exception);
        }

        String serverIP = properties.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        this.server = new ObjectProxy(serverIP, serverPort);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initView(primaryStage);

        primaryStage.setWidth(260);
        primaryStage.setHeight(500);

        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private void initView(Stage primaryStage) throws IOException {
        SceneController sceneController = new SceneController(primaryStage);

        primaryStage.setTitle("Employees Monitoring");
        sceneController.changeToLoginScene();
        primaryStage.show();

        initializeController();

        LoginViewController loginViewController = sceneController.getLoginViewController();

        loginViewController.setController(server);
        loginViewController.setSceneController(sceneController);
    }
}
