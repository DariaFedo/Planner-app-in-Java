package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.database.DBHandler;
import sample.model.User;
import java.io.IOException;
import java.sql.ResultSet;

public class LoginWindowController {

    private DBHandler dbHandler;

    private int userID;

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXPasswordField passwordTextField;


    @FXML
    void initialize() {

        /* The trim() method in java checks this Unicode value before and after the string,
            if it exists then removes the spaces and returns the omitted string. */

        dbHandler = new DBHandler();

        loginButton.setOnAction(event -> {

            // VARIABLES username and password to check and login into APP
            String username = usernameTextField.getText().trim();
            String userPassword = passwordTextField.getText().trim();

            User loggingUser = new User();
            loggingUser.setUsername(username);
            loggingUser.setPassword(userPassword);

            ResultSet userRow = dbHandler.checkLogin(loggingUser);

            int i = 0;

            try {

                while (userRow.next()) {
                    i++;
                    // get userID from database just checking
                    int id = userID = userRow.getInt("userID");
                    System.out.println("user id: " + id);
                }
                if (i == 1)
                    showAddTaskWindow();
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Invalid username or password");
                    alert.setContentText("Please correct your credentials!");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Empty input");
                alert.setContentText("Please enter your credentials!");
                alert.showAndWait();
            }

        });

        //REGISTER EVENT -> close current window, open RegisterWindow
        registerButton.setOnAction(event -> {

            registerButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/RegisterWindow.fxml"));

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

    private void showAddTaskWindow() {
        //Take users to Add Task screen
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/sample/view/newTaskWindow.fxml"));

        try {
            loader.setRoot(loader.getRoot());  /// nie wiem czy to ma tu być
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        // 1. pass userID to controller to enable it to pass it forward to another controller
        // getting CreateTaskController to get into the setUserID method to give the userID
        //since while logging database can return userID :)

        //CreateTaskController CreateTaskController = loader.getController();
        //CreateTaskController.setUserID(userID); ??????????????????????

        TaskController taskController = loader.getController();
        taskController.setUserID(userID);

        stage.showAndWait();

    }
}
