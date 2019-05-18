package ai176.lukianchykov.Model;

import java.util.ArrayList;

public class Model {

  private User user;
  private Project project;
  private Task task;

  public User getUser() {return user;}

  public void setUser(User user) {this.user = user;}

  public Project getProject() {return project;}

  public void setProject(Project project) {this.project = project;}

  public Task getTask() {return task;}

  public void setTask(Task task) {this.task = task;}

  public void assignProjectsToUser(ArrayList<Project> projects){

    user.setProjects(projects);
  }

  public void assignTasksToProject(ArrayList<Task> tasks){

    project.setTasks(tasks);
  }
}
