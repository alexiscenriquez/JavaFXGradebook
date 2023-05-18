package gradebook.dao;

import gradebook.connection.ConnectionManager;
import gradebook.models.Classes;
import gradebook.models.Teachers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TeacherDaoSql implements TeacherDao{
    Connection conn;
    @Override
    public void setConnection() throws SQLException, IOException, ClassNotFoundException {
        conn= ConnectionManager.getConnection();
    }

    @Override
    public Optional<Teachers> authenticate(String username, String password) throws SQLException {
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

            //			System.out.println("Welcome "+ uName +"!" +"\n\n");
            return Optional.of(teacher);

        }
        catch (Exception e){
            System.out.println("User not found");
        }
        return Optional.empty();
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
    public ObservableList<Map<String, Object>> getClassStudents(int id)throws SQLException {
        final String idColumnKey="id";
        final String fnameColumnKey="fname";
        final String lnameColumnKey="lname";
        final String gradeColumnKey="grade";
        PreparedStatement pstmt= conn.prepareStatement("SELECT student.id, student.first_name as 'fName',student.last_name as 'lName',grade from student_class join student on student_id=student.id where class_id=?");
        pstmt.setInt(1,id);
        ResultSet rs= pstmt.executeQuery();
        ArrayList<Map<String,Object>> arrList=new ArrayList<>();
        while(rs.next()){
            Map<String,Object>map=new HashMap<>();
            map.put(idColumnKey,rs.getInt("student.id"));
            map.put(fnameColumnKey,rs.getString("fName"));
            map.put(lnameColumnKey,rs.getString("lName"));
            map.put(gradeColumnKey,rs.getDouble("grade"));
            arrList.add(map);
        }


        return FXCollections.observableList(arrList);
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
          System.out.println(e.getMessage());
      }
        return null;
    }
}
