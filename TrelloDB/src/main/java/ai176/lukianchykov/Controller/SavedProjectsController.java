package ai176.lukianchykov.Controller;

import ai176.lukianchykov.Model.DBController;
import ai176.lukianchykov.Model.Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

import static ai176.lukianchykov.Main.model;

public class SavedProjectsController extends UIController{

  @FXML
  private MenuBar menuBar;

  @FXML
  private Label usernameLabel;

  @FXML
  private Button logoutButton;

  @FXML
  private MenuItem newProjectButton;

  @FXML
  private MenuItem savedProjectsButton;

  @FXML
  private ListView savedProjectsList;

  @FXML
  private TextFlow projectDesc;

  @FXML
  private Button openProjectButton;

  @FXML
  private Button deleteProjectButton;

  @FXML
  public void initialize(){

    usernameLabel.setText(model.getUser().getUsername());

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Project> projectList = model.getUser().getProjects();

    openProjectButton.setDisable(true);
    deleteProjectButton.setDisable(true);

    if(projectList != null) {

      for (int i = 0; i < projectList.size(); i++) {

        titles.add(projectList.get(i).getProjectTitle());
      }

      ObservableList<String> items = FXCollections.observableArrayList(titles);
      savedProjectsList.setItems(items);

      savedProjectsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

              openProjectButton.setDisable(false);
              deleteProjectButton.setDisable(false);

              projectDesc.getChildren().clear();

              Text text = new Text();

              for (int i = 0; i < projectList.size(); i++) {

                if (projectList.get(i).getProjectTitle().equals(newValue)) {
                  text.setText(projectList.get(i).toString());
                  model.setProject(projectList.get(i));
                }
              }

              projectDesc.getChildren().add(text);
            }
          });
    }
  }

  public void handleNewProjectCreate(ActionEvent actionEvent) throws Exception{

    changeWindow(menuBar,"/View/NewProjectWindow.fxml",800, 600);
  }

  public void handleSavedProjectsOpen(ActionEvent actionEvent) throws Exception{

    updateWindow(menuBar,"/View/SavedProjectsWindow.fxml",800,600);
  }

  public void handleLogOut(ActionEvent actionEvent) throws Exception{

    changeWindow(logoutButton,"/View/StartWindow.fxml",400,80);
  }

  public void handleProjectDelete(ActionEvent actionEvent) throws Exception{

    //openNewWindow("/View/DeleteProjectWindow.fxml",400,100,"Delete Project");

    DBController dbaccess = new DBController();

    for (int i = 0; i < model.getUser().getProjects().size(); i++) {

      if(model.getUser().getProjects().get(i).getProjectID() == model.getProject().getProjectID()){
        model.getUser().getProjects().remove(i);

        dbaccess.deleteProject(model.getProject());
      }
    }

    updateWindow(deleteProjectButton,"/View/SavedProjectsWindow.fxml",800,600);
  }

  public void handleProjectOpen(ActionEvent actionEvent) throws Exception{

    DBController dbaccess = new DBController();

    if(dbaccess.pullTasks(model.getProject().getProjectID()) != null) {

      model.assignTasksToProject(dbaccess.pullTasks(model.getProject().getProjectID()));
    }

    changeWindow(openProjectButton,"/View/MainWindow.fxml",800,600);
  }
}
