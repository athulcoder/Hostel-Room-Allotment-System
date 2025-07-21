
import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();

        StudentDAO dao = new StudentDAO();

        List<Student> students= new ArrayList<Student>();
        students = dao.getStudentsByRoom("B202");

        for(int i = 0; i< students.toArray().length; i++) {

            System.out.println(students.get(i).getName());
        }
        }

    }

