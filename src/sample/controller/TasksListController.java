package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import sample.database.DBHandler;
import sample.model.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

public class TasksListController {

    private ObservableList<Task> tasks;

    private ObservableList<Task> refreshedTasks;

    private DBHandler dbHandler;

    @FXML
    private JFXListView<Task> tasksListView;

    @FXML
    private JFXTextArea taskWindowTaskDescriptionTextArea;

    @FXML
    private JFXTextField taskWindowTaskNameTextField;

    @FXML
    private JFXButton taskWindowSaveTaskButton;

    @FXML
    private JFXButton refreshListButton;


    @FXML
    void initialize() throws SQLException {
        System.out.println("initialize called");
        refreshList();

        tasks = FXCollections.observableArrayList();

        dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.getTasksByUser(TaskController.userID);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskID(resultSet.getInt("taskID"));
            task.setTask(resultSet.getString("task"));
            task.setDate(resultSet.getTimestamp("date"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);

        }

        tasksListView.setItems(refreshedTasks);
        tasksListView.setCellFactory(CellController -> new CellController());
        refreshListButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        taskWindowSaveTaskButton.setOnAction(event -> {
            addNewTask();

        });
    }

    public void refreshList() throws SQLException {


        System.out.println("refreshList in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();


        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.getTasksByUser(TaskController.userID);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskID(resultSet.getInt("taskID"));
            task.setTask(resultSet.getString("task"));
            task.setDate(resultSet.getTimestamp("date"));
            task.setDescription(resultSet.getString("description"));

            refreshedTasks.addAll(task);

        }


        tasksListView.setItems(refreshedTasks);
        tasksListView.setCellFactory(CellController -> new CellController());

    }

    public void addNewTask() {

        if (!taskWindowTaskNameTextField.getText().equals("")) {
            Task myNewTask = new Task();

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());

            myNewTask.setUserID(TaskController.userID);
            myNewTask.setTask(taskWindowTaskNameTextField.getText().trim());
            myNewTask.setDescription(taskWindowTaskDescriptionTextArea.getText().trim());
            myNewTask.setDate(timestamp);

            dbHandler.createNewTask(myNewTask);

            taskWindowTaskNameTextField.setText("");
            taskWindowTaskDescriptionTextArea.setText("");

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }
}

