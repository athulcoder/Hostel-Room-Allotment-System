
import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();
        Student student1 = new Student(
                "24cs101", "Alice Thomas", "Female", 18, "CSE", "1st Year",
                "9876543210", "alice@example.com", "Thomas", "9123456789",
                "Single", "A101", "Night Owl"
        );

        Student student2 = new Student(
                "24cs102", "Rahul Kumar", "Male", 19, "CSE", "2nd Year",
                "9887766554", "rahul@example.com", "Mr. Kumar", "9191919191",
                "Double", "B202", "Early Bird"
        );

        Student student3 = new Student(
                "24ec103", "Sneha Rao", "Female", 20, "ECE", "3rd Year",
                "9012345678", "sneha@example.com", "Mrs. Rao", "9000011111",
                "Single", "C105", "Balanced"
        );

        Student student4 = new Student(
                "24it104", "Vikram Desai", "Male", 18, "IT", "1st Year",
                "9080706050", "vikram@example.com", "Mr. Desai", "8899776655",
                "Single", "D303", "Night Owl"
        );

        Student student5 = new Student(
                "24me105", "Divya Nair", "Female", 21, "ME", "Final Year",
                "9123456789", "divya@example.com", "Mr. Nair", "9000000000",
                "Double", "E101", "Early Bird"
        );

        // Save using DAO
        StudentDAO dao = new StudentDAO();
        dao.saveStudent(student1);
        dao.saveStudent(student2);
        dao.saveStudent(student3);
        dao.saveStudent(student4);
        dao.saveStudent(student5);

        System.out.println("✅ Dummy students inserted without dateOfAdmission.");

    }

    }
