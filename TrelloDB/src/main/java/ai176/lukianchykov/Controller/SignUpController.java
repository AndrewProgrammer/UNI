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


public class SignUpController extends UIController{

  @FXML
  private Label errorMessage;

  @FXML
  private TextField usernameField;

  @FXML
  private TextField passwordField;

  @FXML
  private TextField passwordRepeatField;

  @FXML
  private Button confirmButton;

  @FXML
  private Button cancelButton;

  public void handleSignUpConfirm(ActionEvent actionEvent) throws Exception {

    User user = new User();
    DBController dbaccess = new DBController();

    if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordRepeatField.getText().isEmpty()){

      errorMessage.setText("Error: Fill in every field!");
    }
    else if (passwordField.getText().equals(passwordRepeatField.getText())) {

      user.setUsername(usernameField.getText());
      user.setPassword(passwordField.getText());

      try{

        dbaccess.putUser(user);

        changeWindow(confirmButton,"/View/LoginWindow.fxml",600,235);
      }catch (SQLException e){

        errorMessage.setText("Error: Database connection failed!");
        e.printStackTrace();
      }catch (DBException e){

        errorMessage.setText("Error: Such a user already exists!");
      }
    }
    else{

      errorMessage.setText("Error: Passwords need to match!");
    }
  }

  public void handleSignUpCancel(ActionEvent actionEvent) throws Exception {

    changeWindow(cancelButton,"/View/StartWindow.fxml",400,80);
  }
}
