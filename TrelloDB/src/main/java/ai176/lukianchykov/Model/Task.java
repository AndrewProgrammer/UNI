package ai176.lukianchykov.Model;

import java.sql.Date;

public class Task {

  private int taskID;
  private String taskTitle;
  private String taskDesc;
  private int taskPriority;
  private Date taskDeadline;
  private int status;
  private int projectReference;

  public Task() {  }

  public Task(String taskTitle, String taskDesc, int taskPriority, Date taskDeadline, int projectReference) {
    this.taskTitle = taskTitle;
    this.taskDesc = taskDesc;
    this.taskPriority = taskPriority;
    this.taskDeadline = taskDeadline;
    this.status = 0;
    this.projectReference = projectReference;

  }

  public int getTaskID() {
    return taskID;
  }

  public void setTaskID(int taskID) {
    this.taskID = taskID;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }

  public String getTaskDesc() {
    return taskDesc;
  }

  public void setTaskDesc(String taskDesc) {
    this.taskDesc = taskDesc;
  }

  public int getTaskPriority() {
    return taskPriority;
  }

  public void setTaskPriority(int taskPriority) {
    this.taskPriority = taskPriority;
  }

  public Date getTaskDeadline() {
    return taskDeadline;
  }

  public void setTaskDeadline(Date taskDeadline) {
    this.taskDeadline = taskDeadline;
  }

  public int getStatus() {return status;}

  public void setStatus(int status) {
    this.status = status;
  }

  public int getProjectReference() {return projectReference;}

  public void setProjectReference(int projectReference) {this.projectReference = projectReference;}

  @Override
  public String toString() {
    return taskTitle + "\nPriority: " + taskPriority + "\nDue: " + taskDeadline + "\n\n" + taskDesc + "\n";
  }

  public String toStringForList() {
    return taskTitle + "\nPriority: " + taskPriority + "\nDue: " + taskDeadline;
  }


}