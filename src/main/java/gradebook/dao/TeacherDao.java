package gradebook.dao;

import gradebook.models.Classes;
import gradebook.models.Teachers;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeacherDao {
    void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    Optional<Teachers> authenticate(String username, String password) throws SQLException;

    List<Classes> getTeacherClasses(int id) throws SQLException;
    ObservableList<Map<String,Object>> getClassStudents(int id) throws SQLException;

    Classes getClass(int id)throws SQLException;
}
