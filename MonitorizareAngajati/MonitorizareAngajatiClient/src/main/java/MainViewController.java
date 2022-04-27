import controller.IController;
import controller.IObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import model.Employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, IObserver {
    private IController controller;
    SceneController sceneController;
    //CompetitionsViewController competitionsViewController;
    //SearchViewController searchViewController;

    Employer currentEmployer;

    @FXML
    GridPane gridPane;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    /*
    public void loadEntryMenu() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/competition-entry-view.fxml"));
        CompetitionEntryViewController competitionEntryViewController;

        loadMainView(loader);

        competitionEntryViewController = loader.getController();
        competitionEntryViewController.setOnDatePickerChanged();
        competitionEntryViewController.setController(controller);
    }

    public void loadSearchMenu() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/search-view.fxml"));

        loadMainView(loader);

        this.searchViewController = loader.getController();
        this.searchViewController.setController(controller);
        this.searchViewController.setComboBoxItems();
    }

    public void onEntryButtonClicked() {
        loadEntryMenu();
    }

    public void onSearchButtonClicked() {
        loadSearchMenu();
    }
     */

    public void onLogoutButtonPressed() throws IOException {
        /*
        sceneController.changeToLoginScene();

        LoginViewController loginViewController = sceneController.getLoginViewController();

        loginViewController.setController(controller);
        loginViewController.setSceneController(sceneController);

        try {
            controller.logout(currentOfficePerson, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }
        */
    }

    public void loadEmployerMainView() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/employer-main-view.fxml"));

        loadMainView(loader);

        //this.competitionsViewController = loader.getController();
        //this.competitionsViewController.setController(controller);
        //this.competitionsViewController.initModel();
    }

    /*
    public void onCompetitionsButtonPressed() throws Exception {
        loadCompetitionsMenu();
    }
     */

    /*
    @Override
    public void officePersonLoggedIn(OfficePerson officePerson) {

    }

    @Override
    public void childEntered() throws Exception {
        if (this.competitionsViewController != null) {
            this.competitionsViewController.initModel();
        }

        if (this.searchViewController != null) {
            this.searchViewController.initModel();
        }
    }
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCurrentEmployer(Employer employer) {
        this.currentEmployer = employer;
    }

    public void loadMainView(FXMLLoader loader) {
        try {
            gridPane.getChildren().clear();
            gridPane.add(loader.load(), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeesListView() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/employees-list-view.fxml"));
        loadMainView(loader);
    }

    public void loadTasksListView() {
        FXMLLoader loader = new FXMLLoader(MainApplicationClient.class.getResource("views/tasks-list-view.fxml"));
        loadMainView(loader);
    }
}
