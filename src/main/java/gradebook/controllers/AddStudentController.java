package gradebook.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.sql.SQLException;

public class AddStudentController {
    static TeacherDaoSql teacherDaoSql=new TeacherDaoSql();
    public JFXTextField grade;
    public JFXButton submit;
    @FXML
   ListView<Student> listView;

    public void initData(int classID){
        try {
            teacherDaoSql.setConnection();
           ObservableList<Student> oList= FXCollections.observableArrayList(teacherDaoSql.getStudents(classID));
          listView.getItems().addAll(oList);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
