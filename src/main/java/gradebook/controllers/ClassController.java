package gradebook.controllers;

import gradebook.dao.TeacherDaoSql;
import gradebook.models.Classes;
import gradebook.util.StudentTableUtil;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Map;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ClassController {
    public VBox vbox;
    public int classID;
    public Label label;
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
    }


}
