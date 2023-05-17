package com.gradebook.gradebook.dao;

import com.gradebook.gradebook.models.Classes;
import com.gradebook.gradebook.models.Teachers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TeacherDao {
    void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    Optional<Teachers> authenticate(String username, String password) throws SQLException;

    List<Classes> getTeacherClasses(int id) throws SQLException;
}
