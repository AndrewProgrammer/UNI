package ai176.lukianchykov.Model;

import java.sql.*;
import java.util.ArrayList;

public class DBController {

  private static final String URL = "jdbc:mysql://127.0.0.1:3306/trellodb?serverTimezone=UTC";
  private static final String USER = "root";
  private static final String PASSWORD = "root";

  private static final String VALIDATE_USER = "SELECT * FROM users WHERE user_hash = ?";
  private static final String INSERT_USER = "INSERT INTO users VALUES (NULL, ?, ?, ?)";
  private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";

  private static final String VALIDATE_PROJECT = "SELECT * FROM projects WHERE project_title = ? AND user_ref = ?";
  private static final String PULL_PROJECTS = "SELECT * FROM projects WHERE user_ref = ?";
  private static final String INSERT_PROJECT = "INSERT INTO projects VALUES(NULL, ?, ?, ?, ?)";
  private static final String UPDATE_PROJECT = "UPDATE projects SET project_title=?, project_desc=?, project_due=? WHERE project_id=?";
  private static final String DELETE_PROJECT = "DELETE FROM projects WHERE project_id = ?";

  private static final String VALIDATE_TASK = "SELECT * FROM tasks WHERE task_title = ? AND project_ref = ?";
  private static final String PULL_TASKS = "SELECT * FROM tasks WHERE project_ref = ?";
  private static final String INSERT_TASK = "INSERT INTO tasks VALUES(NULL, ?, ?, ?, ?, ?, ?)";
  private static final String UPDATE_TASK = "UPDATE tasks SET task_title=?, task_desc=?, task_prior=?, task_due=? WHERE task_id=?";
  private static final String UPDATE_TASK_STATUS = "UPDATE tasks SET task_status=? WHERE task_id=?";
  private static final String DELETE_TASK = "DELETE FROM tasks WHERE task_id = ?";

  private Connection connection;
  private Driver driver;
  private ResultSet rSet;
  private PreparedStatement prep = null;

  //Connection related methods----------------------------------------------------------------------

  private void establishConnection() throws SQLException{

      driver = new com.mysql.cj.jdbc.Driver();

      DriverManager.registerDriver(driver);

      connection = DriverManager.getConnection(URL, USER, PASSWORD);
  }

  private void closeConnection() throws SQLException{

    if(!connection.isClosed()) {
      connection.close();
    }
  }

  //------------------------------------------------------------------------------------------------

  //User related methods----------------------------------------------------------------------------

  public void putUser(User user) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(VALIDATE_USER);
    prep.setInt(1,user.hashCode());

    rSet = prep.executeQuery();

    if(rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      prep = connection.prepareStatement(INSERT_USER);
      prep.setInt(1,user.hashCode());
      prep.setString(2,user.getUsername());
      prep.setString(3,user.getPassword());

      prep.execute();

      prep.close();

      closeConnection();
    }
  }

  public User pullUser(int userHash) throws SQLException, DBException{

    establishConnection();

    User user = new User();

    prep = connection.prepareStatement(VALIDATE_USER);
    prep.setInt(1,userHash);

    rSet = prep.executeQuery();

    if(!rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      user.setUserID(rSet.getInt("user_id"));
      user.setUsername(rSet.getString("username"));
      user.setPassword(rSet.getString("password"));

      prep.close();

      closeConnection();
    }

    return user;
  }

  public void deleteUser(User user) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(DELETE_USER);
    prep.setInt(1,user.getUserID());

    prep.execute();

    prep.close();

    closeConnection();
  }

  //------------------------------------------------------------------------------------------------

  //Project related methods-------------------------------------------------------------------------

  public void putProject(Project project) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(VALIDATE_PROJECT);
    prep.setString(1,project.getProjectTitle());
    prep.setInt(2,project.getUserReference());

    rSet = prep.executeQuery();

    if(rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      prep = connection.prepareStatement(INSERT_PROJECT);
      prep.setString(1,project.getProjectTitle());
      prep.setString(2,project.getProjectDesc());
      prep.setDate(3,project.getProjectDeadline());
      prep.setInt(4,project.getUserReference());

      prep.execute();

      prep.close();

      closeConnection();
    }
  }

  public Project pullProject(Project project) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(VALIDATE_PROJECT);
    prep.setString(1,project.getProjectTitle());
    prep.setInt(2,project.getUserReference());

    rSet = prep.executeQuery();

    if(!rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      project.setProjectID(rSet.getInt("project_id"));
      project.setProjectTitle(rSet.getString("project_title"));
      project.setProjectDesc(rSet.getString("project_desc"));
      project.setProjectDeadline(rSet.getDate("project_due"));
      project.setUserReference(rSet.getInt("user_ref"));

      prep.close();

      closeConnection();
    }

    return project;
  }

  public ArrayList<Project> pullProjects(int userReference) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(PULL_PROJECTS);
    prep.setInt(1,userReference);

    ArrayList<Project> list = new ArrayList<>();

    rSet = prep.executeQuery();
    ResultSet rSetCheck = rSet;

    if(!rSetCheck.next()) {
      prep.close();
      closeConnection();
      return null;
    }
    else {

      do {

        Project project = new Project();

        project.setProjectID(rSet.getInt("project_id"));
        project.setProjectTitle(rSet.getString("project_title"));
        project.setProjectDesc(rSet.getString("project_desc"));
        project.setProjectDeadline(rSet.getDate("project_due"));
        project.setUserReference(rSet.getInt("user_ref"));

        list.add(project);

      }while(rSet.next());

      prep.close();

      closeConnection();
    }

    return list;
  }

  public void updateProject(Project project) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(UPDATE_PROJECT);
    prep.setString(1,project.getProjectTitle());
    prep.setString(2,project.getProjectDesc());
    prep.setDate(3,project.getProjectDeadline());
    prep.setInt(4,project.getProjectID());

    prep.execute();

    prep.close();
    
    closeConnection();
  }

  public void deleteProject(Project project) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(DELETE_PROJECT);
    prep.setInt(1,project.getProjectID());

    prep.execute();

    prep.close();

    closeConnection();
  }

  //------------------------------------------------------------------------------------------------

  //Task related methods----------------------------------------------------------------------------

  public void putTask(Task task) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(VALIDATE_TASK);
    prep.setString(1,task.getTaskTitle());
    prep.setInt(2,task.getProjectReference());

    rSet = prep.executeQuery();

    if(rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      prep = connection.prepareStatement(INSERT_TASK);
      prep.setString(1,task.getTaskTitle());
      prep.setString(2,task.getTaskDesc());
      prep.setInt(3,task.getTaskPriority());
      prep.setInt(4,task.getStatus());
      prep.setDate(5,task.getTaskDeadline());
      prep.setInt(6,task.getProjectReference());

      prep.execute();

      prep.close();

      closeConnection();
    }
  }

  public Task pullTask(Task task) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(VALIDATE_TASK);
    prep.setString(1,task.getTaskTitle());
    prep.setInt(2,task.getProjectReference());

    rSet = prep.executeQuery();

    if(!rSet.next()) {
      prep.close();
      closeConnection();
      throw new DBException();
    }
    else {

      task.setTaskID(rSet.getInt("task_id"));
      task.setTaskTitle(rSet.getString("task_title"));
      task.setTaskDesc(rSet.getString("task_desc"));
      task.setTaskPriority(rSet.getInt("task_prior"));
      task.setStatus(rSet.getInt("task_status"));
      task.setTaskDeadline(rSet.getDate("task_due"));
      task.setProjectReference(rSet.getInt("project_ref"));

      prep.close();

      closeConnection();
    }

    return task;
  }

  public ArrayList<Task> pullTasks(int projectReference) throws SQLException, DBException {

    establishConnection();

    prep = connection.prepareStatement(PULL_TASKS);
    prep.setInt(1,projectReference);

    ArrayList<Task> list = new ArrayList<>();

    rSet = prep.executeQuery();
    ResultSet rSetCheck = rSet;

    if(!rSetCheck.next()) {
      prep.close();
      closeConnection();
      return null;
    }
    else {

      do {

        Task task = new Task();

        task.setTaskID(rSet.getInt("task_id"));
        task.setTaskTitle(rSet.getString("task_title"));
        task.setTaskDesc(rSet.getString("task_desc"));
        task.setTaskDeadline(rSet.getDate("task_due"));
        task.setTaskPriority(rSet.getInt("task_prior"));
        task.setStatus(rSet.getInt("task_status"));
        task.setProjectReference(rSet.getInt("project_ref"));

        list.add(task);

      }while(rSet.next());

      prep.close();

      closeConnection();
    }

    return list;
  }

  public void updateTask(Task task) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(UPDATE_TASK);
    prep.setString(1,task.getTaskTitle());
    prep.setString(2,task.getTaskDesc());
    prep.setInt(3,task.getTaskPriority());
    prep.setDate(4,task.getTaskDeadline());
    prep.setInt(5,task.getTaskID());

    prep.execute();

    prep.close();

    closeConnection();
  }

  public void updateTaskStatus(int newStatus, Task task) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(UPDATE_TASK_STATUS);
    prep.setInt(1,newStatus);
    prep.setInt(2,task.getTaskID());

    prep.execute();

    prep.close();

    closeConnection();
  }

  public void deleteTask(Task task) throws SQLException{

    establishConnection();

    prep = connection.prepareStatement(DELETE_TASK);
    prep.setInt(1,task.getTaskID());

    prep.execute();

    prep.close();

    closeConnection();
  }

  //------------------------------------------------------------------------------------------------
}