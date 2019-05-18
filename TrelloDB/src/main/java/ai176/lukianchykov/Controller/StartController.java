package ai176.lukianchykov.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController extends UIController{

  @FXML
  private Button signUpButton;

  @FXML
  private Button logInButton;

  public void handleSignUp(ActionEvent actionEvent) throws Exception{

    changeWindow(signUpButton,"/View/SignUpWindow.fxml",600,270);
  }

  public void handleLogIn(ActionEvent actionEvent) throws Exception{

    changeWindow(logInButton,"/View/LoginWindow.fxml",600,235);
  }
}