package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.database.DBHandler;
import sample.model.Task;

import java.io.IOException;
import java.util.Calendar;

public class CreateTaskController {

    private int userID;

    private DBHandler dbHandler;

    @FXML
    private JFXTextArea taskDescriptionTextArea;

    @FXML
    private JFXTextField taskNameTextField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private JFXButton showRecentTasksButton;

    @FXML
    private Label taskAddedInfoLabel;

    @FXML
    void initialize() {

        dbHandler = new DBHandler();
        Task task = new Task();

        saveTaskButton.setOnAction(event -> {

            //System.out.println("Create Task Controller Window , userId: " + getUserID());

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
            String taskName = taskNameTextField.getText().trim();
            String taskDescription = taskDescriptionTextArea.getText().trim();

            if (!taskName.equals("")){

                System.out.println("User Id: " + TaskController.userID);

                task.setUserID(TaskController.userID); //???? error ????? FINALLY AFTER 2 HOURS a?a??a
                task.setDate(timestamp);
                task.setTask(taskName);
                task.setDescription(taskDescription);

                dbHandler.createNewTask(task);

                taskNameTextField.setText("");
                taskDescriptionTextArea.setText("");

                taskAddedInfoLabel.setVisible(true);
                PauseTransition visiblePause = new PauseTransition(Duration.seconds(1));
                visiblePause.setOnFinished(event1 -> taskAddedInfoLabel.setVisible(false));
                visiblePause.play();



            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("You haven' typed anything");
                alert.setContentText("Please enter Task!");
                alert.showAndWait();

            }

        });

        showRecentTasksButton.setOnAction(event1 -> {
            // send users to tasks screen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/tasksListWindow.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

    public int getUserID() {
        System.out.println("from getUserID(): " + userID);
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
        System.out.println("From setUserId " + this.userID);
    }
}