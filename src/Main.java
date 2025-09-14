

import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {


        DatabaseInitializer.initializeDatabase();

        Student s = new Student(
                "S134",                 // studentId
                "Athul Sabu",          // name
                "Male",                 // gender
                19,                     // age
                "CSE", // department
                "2nd Year",             // academicYear
                "9876543210",           // contactNumber
                "athul@example.com",     // email
                "Sabu Varghese",             // guardianName
                "9947811091",           // guardianPhone
                "Four Sharing",       // preferredRoomType
                "D527",             // assignedRoom
                "Early Sleeper"         // sleepType
        );

        // manually we are getting the adminsHostelId and setting that to student hostelId
        //example if admins hostelId ="H001"
        s.setHostelId("H001");
        //and then we are passing that to the doa function
        StudentDAO studentDAO = new StudentDAO();
        if(studentDAO.updateStudent(s))
            System.out.println("Student was updated");
        else

            System.out.println("Not data found to update");

    }

}