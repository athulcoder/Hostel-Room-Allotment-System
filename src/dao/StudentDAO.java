package dao;

import models.Student;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public boolean updateStudent(Student student){

        String sql = """
                UPDATE students SET
                age = ?,
                department = ?,
                academicYear = ?,
                contactNumber = ?,
                email = ?,
                guardianName = ?,
                guardianPhone = ?,
                preferredRoomType = ?,
                assignedRoom = ?,
                sleepType = ?
                WHERE studentId = ?;
                """;

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,student.getAge());
            stmt.setString(2, student.getDepartment());
            stmt.setString(3,student.getAcademicYear());
            stmt.setString(4,student.getContactNumber());
            stmt.setString(5,student.getEmail());
            stmt.setString(6,student.getGuardianName());
            stmt.setString(7,student.getGuardianPhone());
            stmt.setString(8,student.getPreferredRoomType());
            stmt.setString(9,student.getPreferredRoomType());
            stmt.setString(10,student.getSleepType());
            stmt.setString(11,student.getStudentId());

            int rowsAffected = stmt.executeUpdate();

            return  rowsAffected >0;
        }catch(SQLException e){
            System.out.println("Error DB : "+ e.getMessage());
            return false;
        }
    }



    // method to getStudents by room number

    public List<Student> getStudentsByRoom(String roomNumber){
        List<Student> students = new ArrayList<>();
        String sql = """
                SELECT * FROM students
                WHERE assignedRoom = ?
                """;
        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){


            stmt.setString(11,roomNumber);
            ResultSet res = stmt.executeQuery();
            while(res.next()){
             Student student = new Student(res.getString("studentId"),res.getString("name"),res.getString("gender"), res.getInt("age"), res.getString("department"), res.getString("academicYear"), res.getString("contactNumber"), res.getString("email"),res.getString("guardianName"), res.getString("guardianPhone"), res.getString("preferredRoomType"), res.getString("assignedRoom"),res.getString("sleepType"));
             students.add(student);
            }



        }catch(SQLException e){
            System.out.println("Error DB : "+ e.getMessage());


        }

        return students;
    }



}
