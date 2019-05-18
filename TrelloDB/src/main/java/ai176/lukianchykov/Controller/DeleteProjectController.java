package ai176.lukianchykov.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static ai176.lukianchykov.Main.model;

public class DeleteProjectController extends UIController {

  @FXML
  private Button confirmButton;

  @FXML
  private Button cancelButton;

  @FXML
  private Label warningMessage;

  @FXML
  public void initialize(){

    warningMessage.setText("Delete " + model.getProject().getProjectTitle() + "?");
  }

  public void handleDeleteConfirm(ActionEvent actionEvent) throws Exception{

    closeWindow(confirmButton);
  }

  public void handleDeleteCancel(ActionEvent actionEvent) throws Exception{

    closeWindow(cancelButton);
  }
}
