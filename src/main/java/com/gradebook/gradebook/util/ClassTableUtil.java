package com.gradebook.gradebook.util;

import com.gradebook.gradebook.dao.TeacherDaoSql;
import com.gradebook.gradebook.models.Classes;
import com.gradebook.gradebook.models.Teachers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;
import java.util.List;

public class ClassTableUtil {
    public static ObservableList<Classes> getClassList(ObservableList<Classes> olist){

        return olist;
    }

    public static TableColumn<Classes,Integer> getIdColumn(){
        TableColumn<Classes,Integer> idColumn=new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        return idColumn;
    }
    public static TableColumn<Classes,String> getNameColumn(){
        TableColumn<Classes,String> nameColumn=new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        return nameColumn;
    }

    public static TableColumn<Classes,Integer> getNumColumn(){
        TableColumn<Classes,Integer> numColumn=new TableColumn<>("Num");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        return numColumn;
    }
}
