

import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        DatabaseInitializer.initializeDatabase();

        StudentDAO studentDAO = new StudentDAO();

        ArrayList<Student> students = studentDAO.getStudentByRoomNumber("D527");

        for(Student s : students)
            System.out.println(s.getName()+" "+ s.getEmail());

    }

}