
import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;
import services.StudentServices;
import ui.HostelDashboard;
import utils.DatabaseInitializer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();
        StudentDAO studentDAO = new StudentDAO();
        RoomDAO roomDAO = new RoomDAO();


    }

}