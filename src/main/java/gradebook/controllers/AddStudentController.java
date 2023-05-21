package gradebook.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    static TeacherDaoSql teacherDaoSql = new TeacherDaoSql();
    @FXML
    public JFXTextField grade;
    @FXML
    public JFXButton submit;
    @FXML
    public VBox vbox1;
    @FXML
    public JFXButton back;
    @FXML
    ListView<Student> listView;
    Alert a=new Alert(Alert.AlertType.NONE);
    int id;
    Double sGrade;
    int classId;
    public void initData(int classID) {
        this.classId=classID;
        try {
            teacherDaoSql.setConnection();
            ObservableList<Student> oList = FXCollections.observableArrayList(teacherDaoSql.getStudents(classID));
            listView.getItems().addAll(oList);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit.setOnAction(e -> {
             id = listView.getSelectionModel().selectedItemProperty().get().getId();
            System.out.println(id);
            sGrade=Double.valueOf(grade.getText());
            System.out.println(sGrade);

            try {
                if(teacherDaoSql.addStudent(id,classId,sGrade)){
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Student Added");

                    a.show();
                }
                grade.setText(" ");
                ObservableList<Student> oList = FXCollections.observableArrayList(teacherDaoSql.getStudents(classId));
                listView.setItems(oList);
            } catch (SQLException ex) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Could not add student");


            }
        });

        back.setOnAction(e->{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("class-view.fxml"));
            try {
                Parent root = fxmlLoader.load();
                ClassController classController = fxmlLoader.getController();
                classController.initData(classId, teacherDaoSql);
                Stage students = (Stage) back.getScene().getWindow();
                students.setScene(new Scene(root, 800, 400));
                students.setTitle("View Student");
                students.show();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

        });
    }

}
