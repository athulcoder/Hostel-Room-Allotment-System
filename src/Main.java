
import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();

        StudentDAO dao = new StudentDAO();
        Student existingStudent = new Student(
                "24cs101", "Alice Updated", "Female", 19, "CSE", "2nd Year",
                "9999999999", "al9999ice@updated.com", "New Guardian", "9000000001",
                "Double", "B134", "Early Bird"
        );

        boolean success = dao.updateStudent(existingStudent);
        System.out.println(success ? "✅ Updated!" : "❌ Failed to update.");

    }
}
