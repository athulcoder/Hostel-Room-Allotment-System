
import dao.StudentDAO;
import models.Student;

public class Main {

    public static void main(String[] args) {

        Student s = new Student();
        s.setName("Rohan");

        StudentDAO sdao = new StudentDAO();
        sdao.saveStudent(s);

    }
}
