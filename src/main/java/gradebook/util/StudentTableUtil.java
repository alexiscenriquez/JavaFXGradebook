package gradebook.util;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class StudentTableUtil {
    @SuppressWarnings("unchecked")
    public static void addColumns(TableView table) {
        final String idColumnKey = "id";
        final String fnameColumnKey = "fname";
        final String lnameColumnKey = "lname";
        final String gradeColumnKey = "grade";
        TableColumn<Map, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new MapValueFactory<>(idColumnKey));
        TableColumn<Map, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new MapValueFactory<>(fnameColumnKey));

        TableColumn<Map, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new MapValueFactory<>(lnameColumnKey));
        TableColumn<Map, Double> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new MapValueFactory<>(gradeColumnKey));
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, gradeCol);

    }
}
