package gradebook.dao;

import gradebook.models.Classes;
import gradebook.models.Student;
import gradebook.models.Teachers;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeacherDao {
    void setConnection() throws ClassNotFoundException, IOException, SQLException;
    Optional<Teachers> authenticate(String username, String password) ;
    boolean addTeacher(Teachers teacher) ;
    List<Classes> getTeacherClasses(int id) throws SQLException;
    ObservableList<Map<String,Object>> getClassStudents(int id) ;
    List<Student> getStudents(int id)throws SQLException;
    Classes getClass(int id);

    Double getClassAverage(int id) ;
    Double getClassMedian(int id) ;

    boolean addStudent(int studentId, int classId, double grade) throws SQLException;
    boolean updateStudent(int studentId, int classId,double grade) throws SQLException;
    boolean removeStudent(int studentId, int classId) ;

    boolean addClass(Classes classes);
}
