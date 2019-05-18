package ai176.lukianchykov.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {

  public void changeWindow(Node eventSource, String fxmlFileName, int windowWidth, int windowHeight) throws IOException{

    Parent parent = FXMLLoader.load(getClass().getResource(fxmlFileName));
    Scene scene = new Scene(parent, windowWidth, windowHeight);
    scene.getStylesheets().add("style.css");
    Stage stage = (Stage) eventSource.getScene().getWindow();
    stage.hide();

    stage.setScene(scene);
    stage.show();
  }

  public void openNewWindow(String fxmlFileName, int windowWidth, int windowHeight, String title) throws IOException{

    Parent parent = FXMLLoader.load(getClass().getResource(fxmlFileName));
    Scene scene = new Scene(parent, windowWidth, windowHeight);
    scene.getStylesheets().add("style.css");
    Stage stage = new Stage();

    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }

  public void updateWindow(Node eventSource, String fxmlFileName, int windowWidth, int windowHeight) throws IOException{

    Parent parent = FXMLLoader.load(getClass().getResource(fxmlFileName));
    Scene scene = new Scene(parent, windowWidth, windowHeight);
    scene.getStylesheets().add("style.css");
    Stage stage = (Stage) eventSource.getScene().getWindow();

    stage.setScene(scene);
    stage.show();
  }

  public void closeWindow(Node eventSource) throws IOException {

    Stage stage = (Stage) eventSource.getScene().getWindow(); 
    stage.hide();
  }

}
