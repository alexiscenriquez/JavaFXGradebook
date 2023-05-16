package com.gradebook.gradebook;

import com.gradebook.gradebook.DAO.TeacherDaoSql;
import com.gradebook.gradebook.models.Teachers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HelloController {
TeacherDaoSql teacher=new TeacherDaoSql();

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
                Scene scene = new Scene(fxmlLoader.load(), 320, 290);
                Stage stage = new Stage();
                stage.setTitle("Gradebook");
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();

            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}