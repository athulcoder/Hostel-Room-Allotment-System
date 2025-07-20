package dao;

import models.Student;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {


    //method to save a new student to db
    public void saveStudent(Student student) {

        String sql = "INSERT INTO students (studentId, name, gender, age,department,academicYear, contactNumber, email, guardianName, guardianPhone, preferredRoomType, assignedRoom, sleepType, dateOfAdmission) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getGender());
            stmt.setInt(4, student.getAge());
            stmt.setString(5, student.getDepartment());
            stmt.setString(6, student.getAcademicYear());
            stmt.setString(7, student.getContactNumber());
            stmt.setString(8, student.getEmail());
            stmt.setString(9, student.getGuardianName());
            stmt.setString(10, student.getGuardianPhone());
            stmt.setString(11, student.getPreferredRoomType());
            stmt.setString(12, student.getAssignedRoom());
            stmt.setString(13, student.getSleepType());
            stmt.setString(14, student.getDateOfAdmission());
            stmt.executeUpdate();
            System.out.println("Student saved to database.");

        } catch (SQLException e) {
            System.err.println(" DB Error: " + e.getMessage());
        }
    }

    //method to update student data
    public void updateStudent(Student student){
        
    }
}
