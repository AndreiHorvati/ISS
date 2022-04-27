import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TasksListViewController {
    public void showUpdateTaskView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplicationClient.class.getResource("views/update-task-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
