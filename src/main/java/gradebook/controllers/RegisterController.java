package gradebook.controllers;

import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Teachers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {
    public TextField fname;
    public TextField lname;
    public TextField username;
    public PasswordField password;

    public void onRegisterButtonClick(ActionEvent actionEvent) {

        TeacherDaoSql teacherDaoSql=new TeacherDaoSql();
        try {
            teacherDaoSql.setConnection();
            Teachers teacher=new Teachers(0,fname.getText(), lname.getText(),username.getText(),password.getText());
            if(teacherDaoSql.addTeacher(teacher)){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) fname.getScene().getWindow();
                stage.setTitle("Gradebook");
                stage.setScene(scene);

                stage.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
