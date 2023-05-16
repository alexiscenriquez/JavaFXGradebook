package com.gradebook.gradebook.DAO;

import com.gradebook.gradebook.models.Teachers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface TeacherDao {
    void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    Optional<Teachers> authenticate(String username, String password) throws SQLException;
}
