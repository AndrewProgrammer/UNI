package ai176.lukianchykov.Controller;

import ai176.lukianchykov.Model.DBController;
import ai176.lukianchykov.Model.DBException;
import ai176.lukianchykov.Model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static ai176.lukianchykov.Main.model;


public class NewProjectController extends UIController{

  @FXML
  private Label errorMessage;

  @FXML
  private TextField titleField;

  @FXML
  private TextArea descField;

  @FXML
  private DatePicker dueField;

  @FXML
  private Button confirmButton;

  @FXML
  private Button discardButton;

  public void handleNewProjectConfirm(ActionEvent actionEvent) throws Exception{

    Project project = new Project();
    DBController dbaccess = new DBController();

    if(titleField.getText().isEmpty() || descField.getText().isEmpty() || dueField.getValue()==null){

      errorMessage.setText("Error: Fill in every field!");
    }
    else{

      project.setProjectTitle(titleField.getText());
      project.setProjectDesc(descField.getText());
      project.setUserReference(model.getUser().getUserID());
      project.setProjectDeadline(java.sql.Date.valueOf(dueField.getValue()));

      try{

        dbaccess.putProject(project);
        project.setProjectID(dbaccess.pullProject(project).getProjectID());

        if(model.getUser().getProjects() == null)
          model.getUser().setProjects(new ArrayList<>());

        model.getUser().getProjects().add(project);

        changeWindow(confirmButton,"/View/SavedProjectsWindow.fxml",800, 600);

      }catch(SQLException e){

        errorMessage.setText("Error: Database connection failed!");
        e.printStackTrace();
      }catch(DBException e){

        errorMessage.setText("Error: A project with such title is already assigned to this user!");
      }
    }
  }

  public void handleNewProjectDiscard(ActionEvent actionEvent) throws Exception{

    changeWindow(discardButton,"/View/SavedProjectsWindow.fxml",800, 600);
  }
}
