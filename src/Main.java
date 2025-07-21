
import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;
import utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();
        StudentDAO studentDAO = new StudentDAO();
        RoomDAO roomDAO = new RoomDAO();


        List<Student> s = studentDAO.getStudentsWithPreference("Early Bird","Double");

        for(Student ss: s){
            System.out.println(ss.getSleepType());
        }
    }

}