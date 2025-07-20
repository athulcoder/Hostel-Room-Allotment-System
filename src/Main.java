
import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();
        Student s = new Student();
        s.setName("Rohan");

        StudentDAO sdao = new StudentDAO();
        sdao.saveStudent(s);

    }
}
