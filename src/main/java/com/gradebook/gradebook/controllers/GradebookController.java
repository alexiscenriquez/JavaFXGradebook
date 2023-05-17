package com.gradebook.gradebook.controllers;

import com.gradebook.gradebook.dao.TeacherDao;
import com.gradebook.gradebook.dao.TeacherDaoSql;
import com.gradebook.gradebook.models.Classes;
import com.gradebook.gradebook.models.Teachers;
import com.gradebook.gradebook.util.ClassTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GradebookController implements Initializable {


    public VBox vbox;
    Teachers currentUser;

    public void setCurrentUser(Teachers currentUser) {
        this.currentUser = currentUser;
    }

    public void initData(Teachers teachers){
    setCurrentUser(teachers);
        TeacherDaoSql teacher=new TeacherDaoSql();
        try {
            teacher.setConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Classes> list = null;
        list = teacher.getTeacherClasses(currentUser.getId());
        ObservableList<Classes> oList= FXCollections.observableList(list);
        TableView<Classes> tableview=new TableView<>(ClassTableUtil.getClassList(oList));
        tableview.getColumns().addAll(ClassTableUtil.getIdColumn(),ClassTableUtil.getNameColumn(),ClassTableUtil.getNumColumn());
        vbox.getChildren().add(tableview);
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
