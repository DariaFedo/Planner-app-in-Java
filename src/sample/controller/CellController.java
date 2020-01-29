package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DBHandler;
import sample.model.Task;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class CellController extends JFXListCell<Task> {

    private FXMLLoader fxmlLoader;

    private DBHandler dbHandler;

    @FXML
    private AnchorPane cellAnchorPane;

    @FXML
    private ImageView taskIconImgView;

    @FXML
    private Label taskNameLabel;

    @FXML
    private Label taskDescriptionLabel;

    @FXML
    public ImageView listUpdateButton;

    @FXML
    private Label taskDateLabel;

    @FXML
    private ImageView deleteCellButton;

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Task someTask, boolean empty) {

        dbHandler = new DBHandler();

        super.updateItem(someTask, empty);
        //if nothing was sent
        if (empty || someTask == null){
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader  == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/Cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            taskNameLabel.setText(someTask.getTask());
            taskDateLabel.setText(someTask.getDate().toString());
            taskDescriptionLabel.setText(someTask.getDescription());

            int taskID = someTask.getTaskID();


            listUpdateButton.setOnMouseClicked(event -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTaskForm.fxml"));


                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(someTask.getTask());
                updateTaskController.setUpdateDescriptionField(someTask.getDescription());

                updateTaskController.updateTaskButton.setOnAction(event1 -> {

                    Calendar calendar = Calendar.getInstance();

                    java.sql.Timestamp timestamp =
                            new java.sql.Timestamp(calendar.getTimeInMillis());

                    try {

                        System.out.println("taskID " + someTask.getTaskID());

                        dbHandler.updateTask(timestamp, updateTaskController.getDescription(),
                                updateTaskController.getTask(), someTask.getTaskID());

                        //update our listController
                        //updateTaskController.refreshList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

                stage.show();

                updateTaskController.updateTaskButton.setOnMouseClicked(event1 -> {
                    stage.hide();
                });




            });


            deleteCellButton.setOnMouseClicked(event -> {

                try {

                    dbHandler.deleteTask(TaskController.userID,taskID);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());

            });

            setText(null);
            setGraphic(cellAnchorPane);

        }
    }
}
