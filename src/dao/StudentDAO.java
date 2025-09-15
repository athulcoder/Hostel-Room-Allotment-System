package dao;

import models.Student;
import utils.DatabaseInitializer;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public ArrayList<Student> getStudentByName(String name){
        String sql = "SELECT * FROM students WHERE name=?";
        ArrayList<Student> students = new ArrayList<Student>();
        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement statement = conn.prepareStatement(sql);){
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Student student = new Student();
                student.setStudentId(rs.getString("studentId"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setDepartment(rs.getString("department"));
                student.setAcademicYear(rs.getString("academicYear"));
                student.setContactNumber(rs.getString("contactNumber"));
                student.setEmail(rs.getString("email"));
                student.setGuardianName(rs.getString("guardianName"));
                student.setGuardianPhone(rs.getString("guardianPhone"));
                student.setPreferredRoomType(rs.getString("preferredRoomType"));
                student.setAssignedRoom(rs.getString("assignedRoom"));
                student.setSleepType(rs.getString("sleepType"));
                student.setDateOfAdmission(LocalDateTime.parse(rs.getString("dateOfAdmission")));
                student.setHostelId(rs.getString("hostelId"));

                students.add(student);

            }
            return students;


        }catch (SQLException e){
            System.out.println("Error while  fetching students by name: "+e);
        }
        return students;
    }

    public ArrayList<Student> getAllStudents(String hostelId){
        String sql = "SELECT * FROM students WHERE hostelId = ?";
        ArrayList<Student> students = new ArrayList<Student>();
        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,hostelId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){

                Student student = new Student();
                student.setStudentId(rs.getString("studentId"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setDepartment(rs.getString("department"));
                student.setAcademicYear(rs.getString("academicYear"));
                student.setContactNumber(rs.getString("contactNumber"));
                student.setEmail(rs.getString("email"));
                student.setGuardianName(rs.getString("guardianName"));
                student.setGuardianPhone(rs.getString("guardianPhone"));
                student.setPreferredRoomType(rs.getString("preferredRoomType"));
                student.setAssignedRoom(rs.getString("assignedRoom"));
                student.setSleepType(rs.getString("sleepType"));
                student.setDateOfAdmission(LocalDateTime.parse(rs.getString("dateOfAdmission")));
                student.setHostelId(rs.getString("hostelId"));

                students.add(student);
            }
        }catch (SQLException e ){
            System.out.println("Error while fetching all students details : "+e);
        }

        return students;
    }




}