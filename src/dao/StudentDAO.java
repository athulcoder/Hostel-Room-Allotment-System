package dao;

import models.Student;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This class contains the methods that are need to be performed by the Student
public class StudentDAO {


    //addStudent
    public boolean addStudent(Student student){

        //Sql strings to check whether student already exists
        String checkSql = "SELECT COUNT(*) FROM students WHERE studentId =?";
        String insertSql = "INSERT INTO students (studentId, name, gender, age, department, academicYear, contactNumber, email, guardianName, guardianPhone, preferredRoomType, assignedRoom, sleepType, dateOfAdmission, hostelId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn= DatabaseInitializer.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql);PreparedStatement insertStmt = conn.prepareStatement(insertSql)){
            checkStmt.setString(1,student.getStudentId());
            ResultSet checkRes = checkStmt.executeQuery();
            checkRes.next();
            int count = checkRes.getInt(1);

            //if the student Already Exists then return false
            if(count>0){
                return false;
            }

            //create the statement since student is not available with the given data
            insertStmt.setString(1,student.getStudentId());
            insertStmt.setString(2,student.getName());
            insertStmt.setString(3,student.getGender());
            insertStmt.setInt(4,student.getAge());
            insertStmt.setString(5,student.getDepartment());
            insertStmt.setString(6,student.getAcademicYear());
            insertStmt.setString(7,student.getContactNumber());
            insertStmt.setString(8,student.getEmail());
            insertStmt.setString(9,student.getGuardianName());
            insertStmt.setString(10,student.getGuardianPhone());
            insertStmt.setString(11, student.getPreferredRoomType());

            insertStmt.setString(12,student.getAssignedRoom());
            insertStmt.setString(13,student.getSleepType());
            insertStmt.setString(14,student.getDateOfAdmission());

            insertStmt.setString(15, student.getHostelId());

            int rowsInserted = insertStmt.executeUpdate();

            if(rowsInserted>0)
                    return true;
        }catch (SQLException e){
            System.out.println("ERROR WHILE ADDING STUDENT : "+e);
        }



    return false;
    }

    //update student logic (takes the
    public boolean updateStudent(Student student) {
        //check student already exists or not

        String updateSql = """
                UPDATE students
                SET name = ?,
                    age = ?,
                    department = ?,
                    academicYear =?,
                    contactNumber =?,
                    email =?,
                    guardianName =?,
                    guardianPhone =?,
                    preferredRoomType =?,
                    assignedRoom = ?,
                    sleepType = ?
                WHERE studentId = ?;
                """;

        try (Connection conn = DatabaseInitializer.getConnection();  PreparedStatement updateStmt = conn.prepareStatement(updateSql);) {
//
            updateStmt.setString(1, student.getName());
            updateStmt.setInt(2, student.getAge());
            updateStmt.setString(3, student.getDepartment());
            updateStmt.setString(4, student.getAcademicYear());
            updateStmt.setString(5, student.getContactNumber());
            updateStmt.setString(6, student.getEmail());
            updateStmt.setString(7, student.getGuardianName());
            updateStmt.setString(8, student.getGuardianPhone());
            updateStmt.setString(9, student.getPreferredRoomType());
            updateStmt.setString(10, student.getAssignedRoom());
            updateStmt.setString(11, student.getSleepType());
            updateStmt.setString(12, student.getStudentId());

            int rowsInserted = updateStmt.executeUpdate();

            if( rowsInserted > 0)
                return true ;

        } catch (SQLException e) {
            System.out.println("ERROR while updating student : " + e);
        }

        return false;

    }


    //logic to delete Student from db
    public boolean deleteStudent(String studentId){

        String deleteSql = "DELETE FROM students WHERE studentId =?";

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement statement = conn.prepareStatement(deleteSql);){
            statement.setString(1,studentId);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0)
                    return true;
        }catch (SQLException e){
            System.out.println("Error while deleting Student : "+e);
        }
        return false;
    }




}