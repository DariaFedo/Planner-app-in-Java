package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class TaskController {

    public static int userID;

    @FXML
    private AnchorPane tasksAnchorPane;

    @FXML
    private Label noTaskLabel;

    @FXML
    private JFXButton addNewTaskButton;

    @FXML
    void initialize() {

        addNewTaskButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            System.out.println("Add task button clicked"); // OKEJ
            System.out.println("TaskController Window user Id passed: " + getUserID()); // OKEJ

            try {
                AnchorPane taskPane =
                        FXMLLoader.load(getClass().getResource("/sample/view/createTaskForm.fxml"));

                TaskController.userID = getUserID();

                //CreateTaskController createTaskController = new CreateTaskController();
                //createTaskController.setUserID(getUserID());

                //to get UserID we need to know the location where to pass it next! We
                // We need that to make it POSSIBLE to add things and pass many other tthings!
                //CreateTaskController CreateTaskController = loader.getController();
               // CreateTaskController.setUserID(getUserID());

                tasksAnchorPane.getChildren().setAll(taskPane);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void setUserID (int userID){
        this.userID = userID;
        System.out.println("User Id still is: " + this.userID);
    }

    public int getUserID(){

        return this.userID;
    }

 }
