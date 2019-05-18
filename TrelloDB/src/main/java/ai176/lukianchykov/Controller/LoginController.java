package ai176.lukianchykov.Controller;

import ai176.lukianchykov.Model.DBController;
import ai176.lukianchykov.Model.DBException;
import ai176.lukianchykov.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static ai176.lukianchykov.Main.model;

public class LoginController extends UIController {

  @FXML
  private Label errorMessage;

  @FXML
  private TextField usernameField;

  @FXML
  private TextField passwordField;

  @FXML
  private Button confirmButton;

  @FXML
  private Button cancelButton;

  public void handleLogInConfirm(ActionEvent actionEvent) throws Exception {

    User user = new User();
    DBController dbaccess = new DBController();

    if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){

      errorMessage.setText("Error: Fill in every field!");
    }
    else{

      user.setUsername(usernameField.getText());
      user.setPassword(passwordField.getText());

      try{

        model.setUser(dbaccess.pullUser(user.hashCode()));

        if(dbaccess.pullProjects(model.getUser().getUserID()) != null) {

          model.assignProjectsToUser(dbaccess.pullProjects(model.getUser().getUserID()));
        }

        changeWindow(confirmButton,"/View/SavedProjectsWindow.fxml",800,600);
      }catch (SQLException e){

        e.printStackTrace();
        errorMessage.setText("Error: Database connection failed!");
      }catch (DBException e){

        errorMessage.setText("Error: No such user exists!");
      }
    }
  }

  public void handleLogInCancel(ActionEvent actionEvent) throws Exception {

    changeWindow(cancelButton,"/View/StartWindow.fxml",400,80);
  }
}
