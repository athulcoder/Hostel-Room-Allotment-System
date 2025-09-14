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
            if(count>0){
                return false;
            }
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
        //first we have to check whether the student already exists


        //sql query for adding the student to tha database



    return false;
    }

    //update student logic (takes the
    public void updateStudent(Student s){

    }


    //logic to delete Student from db
    public void deleteStudent(String studentId){}




}