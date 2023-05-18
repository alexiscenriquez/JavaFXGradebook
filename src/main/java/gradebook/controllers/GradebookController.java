package gradebook.controllers;

import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Classes;
import gradebook.models.Teachers;
import gradebook.util.ClassTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class GradebookController implements Initializable {

    @FXML
    public VBox vbox;
    public Label welcome;
    Teachers currentUser;
    TableView<Classes> tableview;

    public void setCurrentUser(Teachers currentUser) {
        this.currentUser = currentUser;
    }

    public void initData(Teachers teachers) {
        setCurrentUser(teachers);
        TeacherDaoSql teacher = new TeacherDaoSql();
        try {
            teacher.setConnection();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        List<Classes> list = null;
        welcome.setText(welcome.getText() + " " + currentUser.getfName() + " " + currentUser.getlName());
        list = teacher.getTeacherClasses(currentUser.getId());
        ObservableList<Classes> oList = FXCollections.observableList(list);
        tableview = new TableView<>(ClassTableUtil.getClassList(oList));
        tableview.getColumns().addAll(ClassTableUtil.getIdColumn(), ClassTableUtil.getNameColumn(), ClassTableUtil.getNumColumn());
        tableview.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        vbox.getChildren().add(tableview);
        tableview.setRowFactory(tv -> {
            TableRow<Classes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {

                    Classes clickedRow = row.getItem();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("class-view.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        ClassController classController = fxmlLoader.getController();
                        classController.initData(clickedRow.getId(), teacher);
                        Stage students = new Stage();
                        students.setScene(new Scene(root, 800, 400));
                        students.setTitle("View Student");

                        students.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            });
            return row;
        });


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
