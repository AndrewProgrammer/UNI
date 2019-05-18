package ai176.lukianchykov;

import ai176.lukianchykov.Model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static final int needToDo = 0;
  public static final int inProgress = 1;
  public static final int done = 2;

  public static Model model;

  public static void main(String[] args) throws Exception {

    model = new Model();
    launch(args);
  } 

  @Override
  public void start(Stage stage) throws Exception {
    String fxmlFile = "/View/StartWindow.fxml";
    FXMLLoader loader = new FXMLLoader();
    Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
    stage.setTitle("Prioritizer");
    Scene scene = new Scene(root, 400, 80);
    scene.getStylesheets().add("style.css");
    stage.setScene(scene);      
    stage.show();
  }
}