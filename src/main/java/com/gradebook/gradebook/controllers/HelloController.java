package com.gradebook.gradebook.controllers;

import com.gradebook.gradebook.HelloApplication;
import com.gradebook.gradebook.dao.TeacherDaoSql;

import com.gradebook.gradebook.models.Classes;
import com.gradebook.gradebook.models.Teachers;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
//                Stage stage = (Stage) password.getScene().getWindow();
//                stage.close();
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