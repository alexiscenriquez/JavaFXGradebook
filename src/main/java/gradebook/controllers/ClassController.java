package gradebook.controllers;

import com.jfoenix.controls.JFXButton;
import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Classes;
import gradebook.util.StudentTableUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
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
    TableView<Map<String,Object>> studentsList;
    public void initData(int classID, TeacherDaoSql teacher) {
        this.classID = classID;
        Classes classes = teacher.getClass(classID);
        label.setText(classes.getName() + " " + classes.getNum());

        try {
            studentsList=new TableView<>(teacher.getClassStudents(classID));
            StudentTableUtil.addColumns(studentsList);
            studentsList.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
            vbox.getChildren().add(studentsList);

        } catch (SQLException e) {
          label.setText("Error loading page");
        }
        try {
            average.setText(average.getText()+ " "+teacher.getClassAverage(classID).toString());

        } catch (SQLException e) {
            average.setText("Could not get average");
        }
        try {
            median.setText(median.getText()+" " +teacher.getClassMedian(classID).toString());

        } catch (SQLException e) {
            median.setText("Could not get median");
        }
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
    }
}
