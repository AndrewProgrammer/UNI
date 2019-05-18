package ai176.lukianchykov.Controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DeleteTaskController {

  public void ConfirmDelete(ActionEvent actionEvent) throws Exception{

    //TODO

    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.hide();
  }

  public void CancelDelete(ActionEvent actionEvent) throws Exception{

    //TODO

    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.hide();
  }
}
