package ai176.lukianchykov.Controller;

import ai176.lukianchykov.Model.DBController;
import ai176.lukianchykov.Model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static ai176.lukianchykov.Main.*;

public class MainWindowController extends UIController {

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
  private Label projectNameLabel;

  @FXML
  private Label dueLabel;

  @FXML
  private Button deleteButton;

  @FXML
  private Button editButton;

  @FXML
  private Button deleteTaskButton;

  @FXML
  private Button editTaskButton;

  @FXML
  private Button newTaskButton;

  @FXML
  private ListView needToDoList;

  @FXML
  private ListView inProgressList;

  @FXML
  private ListView doneList;

  @FXML
  public void initialize() {

    usernameLabel.setText(model.getUser().getUsername());
    projectNameLabel.setText(model.getProject().getProjectTitle());
    dueLabel.setText("due: " + model.getProject().getProjectDeadline().toString());

    ArrayList<String> titlesNTD = new ArrayList<>();
    ArrayList<String> titlesIP = new ArrayList<>();
    ArrayList<String> titlesD = new ArrayList<>();
    ArrayList<Task> taskList = model.getProject().getTasks();

    editTaskButton.setDisable(true);
    deleteTaskButton.setDisable(true);

    if (taskList != null) {

      Collections.sort(taskList, new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2)
      {
          Long prior1;
          Long prior2;

          prior1 = task1.getTaskPriority()*task1.getTaskDeadline().getTime();
          prior2 = task2.getTaskPriority()*task2.getTaskDeadline().getTime();

          return  prior2.compareTo(prior1);
        }
      });

      for (int i = 0; i < taskList.size(); i++) {

        switch(taskList.get(i).getStatus()){

          case(needToDo):
            titlesNTD.add(taskList.get(i).toStringForList());
            break;
          case(inProgress):
            titlesIP.add(taskList.get(i).toStringForList());
            break;
          case(done):
            titlesD.add(taskList.get(i).toStringForList());
            break;
          default:
            break;
        }

      }

      ObservableList<String> itemsNTD = FXCollections.observableArrayList(titlesNTD);
      ObservableList<String> itemsIP = FXCollections.observableArrayList(titlesIP);
      ObservableList<String> itemsD = FXCollections.observableArrayList(titlesD);

      needToDoList.setItems(itemsNTD);
      inProgressList.setItems(itemsIP);
      doneList.setItems(itemsD);

      needToDoList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

          editTaskButton.setDisable(false);
          deleteTaskButton.setDisable(false);

          for (int i = 0; i < taskList.size(); i++) {

            if (taskList.get(i).toStringForList().equals(newValue)) {
              model.setTask(taskList.get(i));

            }
          }
        }
      });

      inProgressList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

          editTaskButton.setDisable(false);
          deleteTaskButton.setDisable(false);

          for (int i = 0; i < taskList.size(); i++) {

            if (taskList.get(i).toStringForList().equals(newValue)) {
              model.setTask(taskList.get(i));


            }
          }
        }
      });

      doneList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

          editTaskButton.setDisable(false);
          deleteTaskButton.setDisable(false);

          for (int i = 0; i < taskList.size(); i++) {

            if (taskList.get(i).toStringForList().equals(newValue)) {
              model.setTask(taskList.get(i));

            }
          }
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

    changeWindow(deleteButton,"/View/SavedProjectsWindow.fxml",800,600);
  }

  public void handleProjectEdit(ActionEvent actionEvent) throws Exception{

    changeWindow(editButton, "/View/EditProjectWindow.fxml",800, 600);
  }

  public void handleTaskEdit(ActionEvent actionEvent) throws Exception{

    changeWindow(editButton, "/View/EditTaskWindow.fxml",800, 600);
  }

  public void handleTaskDelete(ActionEvent actionEvent) throws Exception{

    DBController dbaccess = new DBController();

    for (int i = 0; i < model.getProject().getTasks().size(); i++) {

      if(model.getProject().getTasks().get(i).getTaskID() == model.getTask().getTaskID()){
        model.getProject().getTasks().remove(i);

        dbaccess.deleteTask(model.getTask());
      }
    }

    updateWindow(deleteButton,"/View/MainWindow.fxml",800,600);
  }

  public void handleNewTask(ActionEvent actionEvent) throws Exception{

    changeWindow(editButton, "/View/NewTaskWindow.fxml",800, 600);
  }

  public void handleDragDetectionNTD(MouseEvent mouseEvent) throws Exception{

    Dragboard board = needToDoList.startDragAndDrop(TransferMode.ANY);

    ClipboardContent cbContent = new ClipboardContent();
    cbContent.putString(needToDoList.getSelectionModel().getSelectedItem().toString());

    board.setContent(cbContent);

    mouseEvent.consume();
  }

  public void handleDragDropNTD(DragEvent dragEvent) throws Exception{

    DBController dbaccess = new DBController();
    String temp = dragEvent.getDragboard().getString();

    for (int i = 0; i < model.getProject().getTasks().size(); i++) {

      if(temp.equals(model.getProject().getTasks().get(i).toStringForList())){

        model.getProject().getTasks().get(i).setStatus(needToDo);
        dbaccess.updateTaskStatus(needToDo, model.getProject().getTasks().get(i));
      }
    }

    updateWindow(needToDoList,"/View/MainWindow.fxml",800,600);
  }

  public void handleDragOverNTD(DragEvent dragEvent) throws Exception{

    if(dragEvent.getDragboard().hasString()){
      dragEvent.acceptTransferModes(TransferMode.ANY);
    }
  }

  public void handleDragDetectionIP(MouseEvent mouseEvent) throws Exception{

    Dragboard board = inProgressList.startDragAndDrop(TransferMode.ANY);

    ClipboardContent cbContent = new ClipboardContent();
    cbContent.putString(inProgressList.getSelectionModel().getSelectedItem().toString());

    board.setContent(cbContent);

    mouseEvent.consume();
  }

  public void handleDragDropIP(DragEvent dragEvent) throws Exception{

    DBController dbaccess = new DBController();
    String temp = dragEvent.getDragboard().getString();

    for (int i = 0; i < model.getProject().getTasks().size(); i++) {

      if(temp.equals(model.getProject().getTasks().get(i).toStringForList())){

        model.getProject().getTasks().get(i).setStatus(inProgress);
        dbaccess.updateTaskStatus(inProgress, model.getProject().getTasks().get(i));
      }
    }

    updateWindow(needToDoList,"/View/MainWindow.fxml",800,600);
  }

  public void handleDragOverIP(DragEvent dragEvent) throws Exception{

    if(dragEvent.getDragboard().hasString()){
      dragEvent.acceptTransferModes(TransferMode.ANY);
    }
  }

  public void handleDragDetectionD(MouseEvent mouseEvent) throws Exception{

    Dragboard board = doneList.startDragAndDrop(TransferMode.ANY);

    ClipboardContent cbContent = new ClipboardContent();
    cbContent.putString(doneList.getSelectionModel().getSelectedItem().toString());

    board.setContent(cbContent);

    mouseEvent.consume();
  }

  public void handleDragDropD(DragEvent dragEvent) throws Exception{

    DBController dbaccess = new DBController();
    String temp = dragEvent.getDragboard().getString();

    for (int i = 0; i < model.getProject().getTasks().size(); i++) {

      if(temp.equals(model.getProject().getTasks().get(i).toStringForList())){

        model.getProject().getTasks().get(i).setStatus(done);
        dbaccess.updateTaskStatus(done, model.getProject().getTasks().get(i));
      }
    }

    updateWindow(needToDoList,"/View/MainWindow.fxml",800,600);
  }

  public void handleDragOverD(DragEvent dragEvent) throws Exception{

    if(dragEvent.getDragboard().hasString()){
      dragEvent.acceptTransferModes(TransferMode.ANY);
    }
  }
}
