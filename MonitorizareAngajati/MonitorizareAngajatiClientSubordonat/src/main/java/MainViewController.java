import controller.IController;
import controller.IObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import model.Employee;
import model.Employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, IObserver {
    private IController controller;
    private SceneController sceneController;

    private Employee currentEmployee;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public void setCurrentEmployee(Employee employee) {
        this.currentEmployee = employee;
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

    @Override
    public void employeeAdded() throws Exception {

    }

    @Override
    public void employeeDeleted() throws Exception {

    }
}
