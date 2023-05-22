package gradebook.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gradebook.HelloApplication;
import gradebook.dao.TeacherDaoSql;
import gradebook.models.Classes;
import gradebook.models.Teachers;
import gradebook.util.ClassTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    @FXML
    public Label welcome;
    @FXML
    public Label nameLabel;
    @FXML
    public JFXTextField cNameText;
    @FXML
    public Label numLabel;
    @FXML
    public JFXTextField numText;
    @FXML
    public JFXButton submit;
    Teachers currentUser;
    @FXML
    TableView<Classes> tableview;
    TeacherDaoSql teacher = new TeacherDaoSql();
    private List<Classes> list;

    public void setCurrentUser(Teachers currentUser) {
        this.currentUser = currentUser;
    }

    public void initData(Teachers teachers) {
        setCurrentUser(teachers);

        try {
            teacher.setConnection();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

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

    public void showForm(ActionEvent actionEvent) {

        cNameText.setVisible(true);
        nameLabel.setVisible(true);
        numLabel.setVisible(true);
        numText.setVisible(true);
        submit.setVisible(true);

    }


    public void addClass(ActionEvent actionEvent) {
        Classes classes=new Classes(0, cNameText.getText(), Integer.valueOf(numText.getText()), currentUser.getId());
if(teacher.addClass(classes)){
    cNameText.setVisible(false);
    nameLabel.setVisible(false);
    numLabel.setVisible(false);
    numText.setVisible(false);
    submit.setVisible(false);
list = teacher.getTeacherClasses(currentUser.getId());
    ObservableList<Classes> oList = FXCollections.observableList(list);
    tableview.setItems(oList);
}

    }
}
