package ai176.lukianchykov.Controller;

import ai176.lukianchykov.Model.DBController;
import ai176.lukianchykov.Model.DBException;
import ai176.lukianchykov.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

import static ai176.lukianchykov.Main.model;
import static java.lang.Integer.parseInt;

public class EditTaskController extends UIController {

  @FXML
  private TextField titleField;

  @FXML
  private TextArea descField;

  @FXML
  private DatePicker dueField;

  @FXML
  private TextField priorField;

  @FXML
  private Button confirmButton;

  @FXML
  private Button discardButton;

  @FXML
  private Label errorMessage;

  @FXML
  public void initialize(){

    titleField.setText(model.getTask().getTaskTitle());
    descField.setText(model.getTask().getTaskDesc());
    dueField.setValue(model.getTask().getTaskDeadline().toLocalDate());
    priorField.setText(String.valueOf(model.getTask().getTaskPriority()));
  }


  public void handleTaskEditConfirm(ActionEvent actionEvent) throws Exception{

    Task task = new Task();
    DBController dbaccess = new DBController();

    if(titleField.getText().isEmpty() || descField.getText().isEmpty() || dueField.getValue()==null){

      errorMessage.setText("Error: Fill in every field!");
    }
    else{

      task.setTaskID(model.getTask().getTaskID());
      task.setTaskTitle(titleField.getText());
      task.setTaskDesc(descField.getText());
      task.setTaskPriority(parseInt(priorField.getText()));
      task.setProjectReference(model.getProject().getProjectID());
      task.setTaskDeadline(java.sql.Date.valueOf(dueField.getValue()));



      try{

        dbaccess.updateTask(task);

        model.setTask(dbaccess.pullTask(task));

        for (int i = 0; i < model.getProject().getTasks().size(); i++) {

          if(model.getProject().getTasks().get(i).getTaskID() == model.getTask().getTaskID()){

            model.getProject().getTasks().set(i, model.getTask());
          }

        }

        changeWindow(confirmButton,"/View/MainWindow.fxml",800, 600);

      }catch(SQLException e) {
        e.printStackTrace();
        errorMessage.setText("Error: Database connection failed!");
      }catch (DBException e){
        errorMessage.setText("DBException");
      }
    }
  }

  public void handleTaskEditDiscard(ActionEvent actionEvent) throws Exception{

    changeWindow(discardButton,"/View/MainWindow.fxml",800, 600);
  }
}
