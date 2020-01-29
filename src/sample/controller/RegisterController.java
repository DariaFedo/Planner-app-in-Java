package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.DBHandler;
import sample.model.User;

import java.io.IOException;

public class RegisterController {

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXTextField newUsernameTextField;

    @FXML
    private JFXCheckBox maleCheckBox;

    @FXML
    private JFXCheckBox femaleCheckBox;

    @FXML
    private JFXButton createAccountButton;

    @FXML
    private JFXPasswordField newPasswordTextField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    void initialize() {
        createAccountButton.setOnAction(event -> {
            registerUser();
            openLoginWindow(createAccountButton);

        });

        cancelButton.setOnAction(event -> {
            openLoginWindow(createAccountButton);

        });


    }

    private void registerUser() {
        DBHandler dbHandler = new DBHandler();

        String firstName = nameTextField.getText();
        String newUsername = newUsernameTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String gender = "";

        if (femaleCheckBox.isSelected()) {
            gender = "Female";
        } else if (maleCheckBox.isSelected())  // check if that is needed - unused checkbox
            gender = "Male";


        // PASSING CLASS OBJECT INSTEAD OF THE WHOLE METHOD
        User newUser = new User(firstName, newUsername, newPassword, gender);

        dbHandler.registerUser(newUser);

    }

    private void openLoginWindow(JFXButton button) {

        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/LoginWindow.fxml"));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();

    }
}
