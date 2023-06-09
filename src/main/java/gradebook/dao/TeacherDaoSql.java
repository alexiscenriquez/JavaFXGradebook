package gradebook.dao;

import gradebook.connection.ConnectionManager;
import gradebook.models.Classes;
import gradebook.models.Student;
import gradebook.models.Teachers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.control.Alert;
import org.decimal4j.util.DoubleRounder;

public class TeacherDaoSql implements TeacherDao{
    Connection conn;
    @Override
    public void setConnection() throws SQLException, IOException, ClassNotFoundException {
        conn= ConnectionManager.getConnection();
    }

    @Override
    public Optional<Teachers> authenticate(String username, String password){
        Teachers teacher = null;
        try (PreparedStatement pstmt = conn.prepareStatement("select * from teacher where username=? and password=?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                teacher = new Teachers(id, fName, lName);
            }
            return Optional.of(teacher);

        }
        catch (Exception e){
            System.out.println("User not found");
        }
        return Optional.empty();
    }

    @Override
    public boolean addTeacher(Teachers teacher) {
       try(PreparedStatement pstmt = conn.prepareStatement("insert into Teacher values(null,?,?,?,?)")) {
           pstmt.setString(1, teacher.getfName());
           pstmt.setString(2, teacher.getlName());
           pstmt.setString(3, teacher.getUsername());
           pstmt.setString(4, teacher.getPassword());
           int count = pstmt.executeUpdate();
           return count > 0;
       }
       catch (SQLException e){
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText("User name has already been taken");
           alert.show();
       }
        return false;
    }

    @Override
    public List<Classes> getTeacherClasses(int id) {
        List<Classes> classList;
        try (PreparedStatement pstmt = conn.prepareStatement("select * from class where teacher_id=?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Classes classes;
            classList = new ArrayList<>();
            while (rs.next()) {
                int cid = rs.getInt("id");
                String name = rs.getString("name");
                int tid = rs.getInt("teacher_id");
                int num = rs.getInt("num");
                classes = new Classes(cid, name, num, tid);
                classList.add(classes);

            }
            return classList;
        } catch (SQLException e) {
            System.out.println("Could not get classes");
        }
        return null;
    }

    @Override
    public ObservableList<Map<String, Object>> getClassStudents(int id) {
        final String idColumnKey="id";
        final String fnameColumnKey="fname";
        final String lnameColumnKey="lname";
        final String gradeColumnKey="grade";
    try (   PreparedStatement pstmt= conn.prepareStatement("SELECT student.id, student.first_name as 'fName',student.last_name as 'lName',grade from student_class join student on student_id=student.id where class_id=?")) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Map<String, Object>> arrList = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            map.put(idColumnKey, rs.getInt("student.id"));
            map.put(fnameColumnKey, rs.getString("fName"));
            map.put(lnameColumnKey, rs.getString("lName"));
            map.put(gradeColumnKey, rs.getDouble("grade"));
            arrList.add(map);
        }
        return FXCollections.observableList(arrList);

    }
    catch (SQLException e){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Could not load students");
    }
        return null;
    }

    @Override
    public List<Student> getStudents(int classID) throws SQLException {
        PreparedStatement pstmt=conn.prepareStatement("select * from student where id not in(select student_id from student_class where class_id=?);");
        pstmt.setInt(1,classID);
        ResultSet rs=pstmt.executeQuery();
        List<Student> studentList=new ArrayList<>();

        while(rs.next()){
            int id=rs.getInt("id");
            String fName=rs.getString("first_name");
            String lName=rs.getString("last_name");
            Student student=new Student(id,fName,lName);
            studentList.add(student);
        }
        return studentList;
    }

    @Override
    public Classes getClass(int id) {
        try(PreparedStatement pstmt=conn.prepareStatement("Select * from class where id=?")){
            pstmt.setInt(1,id);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()) {
                int cid = rs.getInt("id");
                String name = rs.getString("name");
                int num = rs.getInt("num");
                int tid = rs.getInt("teacher_id");
                return new Classes(cid, name, num, tid);
            }
        }
      catch (SQLException e){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Could not get class");
      }
        return null;
    }

    @Override
    public Double getClassAverage(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement("Select avg(grade) as avg from student_class where class_id=?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            return DoubleRounder.round(rs.getDouble("avg"), 2);
        }catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not get average");
        }
        return null;
    }

    @Override
    public Double getClassMedian(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT AVG(grade) as median_val\n" +
                "FROM (\n" +
                "SELECT sc.grade, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum\n" +
                "  FROM student_class sc, (SELECT @rownum:=0) r\n" +
                "  WHERE sc.class_id=?\n" +
                "  ORDER BY sc.grade\n" +
                ") as med\n" +
                "WHERE med.row_number IN ( FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2) );")) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return DoubleRounder.round(rs.getDouble("median_val"), 2);
        }catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not get median");
        }
        return null;
    }
    @Override
    public boolean addStudent(int studentId, int classId, double grade) throws SQLException {
        PreparedStatement pstmt= conn.prepareStatement("insert into student_class values(?,?,?)");
        pstmt.setInt(1,classId);
        pstmt.setInt(2,studentId);
        pstmt.setDouble(3,grade);
        int count= pstmt.executeUpdate();
        return count>0;
    }

    @Override
    public boolean updateStudent(int studentId, int classId, double grade) throws SQLException {
        PreparedStatement pstmt= conn.prepareStatement("Update Student_Class set grade=? where student_id=? and class_id=?");

        pstmt.setDouble(1,grade);
        pstmt.setInt(2,studentId);
        pstmt.setInt(3,classId);
        int count= pstmt.executeUpdate();

        return count>0;
    }

    @Override
    public boolean removeStudent(int studentId, int classId) {
      try(  PreparedStatement pstmt=conn.prepareStatement("delete from student_class where student_id=? and class_id=?")) {
          pstmt.setInt(1, studentId);
          pstmt.setInt(2, classId);
          int count = pstmt.executeUpdate();

          return count > 0;
      }catch (SQLException e){
          Alert alert=new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Could not Delete Student");
      }

        return false;
    }

    @Override
    public boolean addClass(Classes classes) {
        try(PreparedStatement pstmt= conn.prepareStatement("Insert into Class values(null,? ,?,?)")){
            pstmt.setInt(1,classes.getTeacherId());
            pstmt.setString(2, classes.getName());
            pstmt.setInt(3,classes.getNum());
            int count = pstmt.executeUpdate();
            return count>0;
        } catch (SQLException e) {
          Alert alert=new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Could not Add Class");
        }
        return false;
    }
}
