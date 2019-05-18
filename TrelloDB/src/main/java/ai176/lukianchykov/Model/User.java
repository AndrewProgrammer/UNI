package ai176.lukianchykov.Model;

import java.util.ArrayList;

public class User {

  private int userID;
  private String username;
  private String password;
  private ArrayList<Project> projects;

  public User() {  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ArrayList<Project> getProjects() {
    return projects;
  }

  public void setProjects(ArrayList<Project> projects) {
    this.projects = projects;
  }

  @Override
  public int hashCode() {

    String hashTemplate = username.concat(password);

    return hashTemplate.hashCode();
  }
}
