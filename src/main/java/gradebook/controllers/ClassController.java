package gradebook.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Classes;
import gradebook.util.StudentTableUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ClassController implements Initializable {
    @FXML
    public VBox vbox;
    public int classID;
    @FXML
    public Label label;
    @FXML
    public Label average;
    @FXML
    public Label median;
    @FXML
    public JFXButton addStudent;
    public JFXButton updateStudent;
    public JFXButton updatesubmit;
    public Label entergradelabel;
    public JFXTextField grade;
    public JFXButton deleteStudent;
    TableView<Map<String,Object>> studentsList;
    TeacherDaoSql teacherDao;
Alert alert=new Alert(Alert.AlertType.ERROR);
    private Integer studentid;

    public void initData(int classID, TeacherDaoSql teacher) {
        this.classID = classID;
        Classes classes = teacher.getClass(classID);
        label.setText(classes.getName() + " " + classes.getNum());
this.teacherDao=teacher;

            studentsList=new TableView<>(teacher.getClassStudents(classID));
            StudentTableUtil.addColumns(studentsList);
            studentsList.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
            vbox.getChildren().add(studentsList);
            average.setText(average.getText()+ " "+teacher.getClassAverage(classID).toString());
            median.setText(median.getText()+" " +teacher.getClassMedian(classID).toString());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudent.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addStudent-view.fxml"));
            try {
                Parent root = fxmlLoader.load();
                AddStudentController addStudentController= fxmlLoader.getController();
                addStudentController.initData(classID);
                Stage stage=(Stage) addStudent.getScene().getWindow();
                stage.setScene(new Scene(root,800, 400));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        updateStudent.setOnAction(actionEvent->{
     entergradelabel.setVisible(true);
     grade.setVisible(true);
     updatesubmit.setVisible(true);
        });

        updatesubmit.setOnAction(actionEvent->{
             studentid = (Integer) studentsList.getSelectionModel().getSelectedItem().get("id");
            double studentGrade=Double.valueOf(grade.getText());
            try {
                teacherDao.updateStudent(studentid,classID,studentGrade);
                ObservableList<Map<String, Object>> oList = teacherDao.getClassStudents(classID);
                studentsList.setItems(oList);
                entergradelabel.setVisible(false);
                grade.setVisible(false);
                updatesubmit.setVisible(false);
                average.setText("Average: "+teacherDao.getClassAverage(classID));
                median.setText("Median: "+teacherDao.getClassMedian(classID));
            } catch (SQLException e) {
              alert.setContentText("Could not update student");

            }
        });
        deleteStudent.setOnAction(ActionEvent->{
            if( studentsList.getSelectionModel().getSelectedItem()==null){
                alert.setContentText("Please select a student from the list");
            alert.show();
            }else{
            studentid= (Integer) studentsList.getSelectionModel().getSelectedItem().get("id");

            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "Are you sure you want to delete " +
                                      studentsList.getSelectionModel().getSelectedItem().get("fname")+ "?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            alert.setTitle("Confirm Delete");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                if (teacherDao.removeStudent(studentid, classID)) {
                    Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
                    a1.setContentText("Student Deleted");
                    studentsList.setItems(teacherDao.getClassStudents(classID));
                    average.setText("Average: "+teacherDao.getClassAverage(classID));
                    median.setText("Median: "+teacherDao.getClassMedian(classID));
                }
            }
            }
        });
    }
    public int getStudentID(){
        return 0;
    }
}
