package gradebook.controllers;

import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;

import gradebook.models.Classes;
import gradebook.models.Teachers;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HelloController {

    public TeacherDaoSql teacher=new TeacherDaoSql();
    @FXML
    public JFXListView<Classes> listview;
@FXML
TextField username;
@FXML
TextField password;

@FXML
Label loginValidation;
    @FXML
    protected void onLoginButtonClick() {
        try {
            teacher.setConnection();
            Optional<Teachers> found= teacher.authenticate(username.getText(), password.getText());
            if(found.isEmpty()){
            loginValidation.setText("Account not found");
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gradebook-view.fxml"));
                Parent root = fxmlLoader.load();

                GradebookController gradebookController= fxmlLoader.getController();
                gradebookController.initData(found.get());
                Stage home = (Stage) loginValidation.getScene().getWindow();
                home.setScene(new Scene(root, 800, 600));
                home.setTitle("Gradebook");

                home.setMaximized(true);
                home.show();



            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}