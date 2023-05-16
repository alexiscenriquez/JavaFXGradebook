package com.gradebook.gradebook.DAO;

import com.gradebook.gradebook.connection.ConnectionManager;
import com.gradebook.gradebook.models.Teachers;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeacherDaoSql implements TeacherDao{
    Connection conn;
    @Override
    public void setConnection() throws SQLException, IOException, ClassNotFoundException {
        conn=ConnectionManager.getConnection();
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
}
