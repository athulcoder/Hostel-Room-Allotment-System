

import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {


        DatabaseInitializer.initializeDatabase();

        Student s = new Student(
                "S134",                 // studentId
                "Rahul Kiran",          // name
                "Male",                 // gender
                20,                     // age
                "Mechanical Engineering", // department
                "2nd Year",             // academicYear
                "9876543210",           // contactNumber
                "bony@example.com",     // email
                "Kiran Mohan",             // guardianName
                "9123456789",           // guardianPhone
                "Four Sharing",       // preferredRoomType
                "B340",             // assignedRoom
                "Early Sleeper"         // sleepType
        );

        // manually we are getting the adminsHostelId and setting that to student hostelId
        //example if admins hostelId ="H001"
        s.setHostelId("H001");
        //and then we are passing that to the doa function
        StudentDAO studentDAO = new StudentDAO();
        if(studentDAO.addStudent(s))
            System.out.println("Student successfully added");
        else
            System.out.println("Student already exists");
    }

}