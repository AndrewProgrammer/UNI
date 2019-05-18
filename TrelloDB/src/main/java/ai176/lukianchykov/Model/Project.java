package ai176.lukianchykov.Model;

import java.sql.Date;
import java.util.ArrayList;

public class Project {

  private int projectID;
  private String projectTitle;
  private String projectDesc;
  private Date projectDeadline;
  private ArrayList<Task> tasks;
  private int userReference;

  public Project() {  }

  public Project(String projectTitle, String projectDesc, Date projectDeadline, int userReference) {
    this.projectID = projectID;
    this.projectTitle = projectTitle;
    this.projectDesc = projectDesc;
    this.projectDeadline = projectDeadline;
    this.userReference = userReference;
  }

  public int getProjectID() {
    return projectID;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public String getProjectTitle() {
    return projectTitle;
  }

  public void setProjectTitle(String projectTitle) {
    this.projectTitle = projectTitle;
  }

  public String getProjectDesc() {
    return projectDesc;
  }

  public void setProjectDesc(String projectDesc) {
    this.projectDesc = projectDesc;
  }

  public Date getProjectDeadline() {
    return projectDeadline;
  }

  public void setProjectDeadline(Date projectDeadline) {
    this.projectDeadline = projectDeadline;
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public void setTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  public int getUserReference() {return userReference;}

  public void setUserReference(int userReference) {this.userReference = userReference;}

  @Override
  public String toString() {
    return projectTitle + "\n\nDue: " + projectDeadline + "\n\n" + projectDesc + "\n";
  }
}