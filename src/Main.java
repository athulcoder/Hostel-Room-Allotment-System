
import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;
import services.StudentServices;
import utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Database initialising
        DatabaseInitializer.initializeDatabase();
        StudentDAO studentDAO = new StudentDAO();
        RoomDAO roomDAO = new RoomDAO();


// Floor 1
        Room room1 = new Room("A101", "Single", 1, 1);
        Room room2 = new Room("A102", "2 bed", 1, 2);
        Room room3 = new Room("A103", "4 bed", 1, 4);

// Floor 2
        Room room4 = new Room("B201", "Single", 2, 1);
        Room room5 = new Room("B202", "2 bed", 2, 2);
        Room room6 = new Room("B203", "4 bed", 2, 4);

// Floor 3
        Room room7 = new Room("C301", "Single", 3, 1);
        Room room8 = new Room("C302", "2 bed", 3, 2);
        Room room9 = new Room("C303", "4 bed", 3, 4);

// Floor 4
        Room room10 = new Room("D401", "Single", 4, 1);
        Room room11 = new Room("D402", "2 bed", 4, 2);
        Room room12 = new Room("D403", "4 bed", 4, 4);

// Floor 5
        Room room13 = new Room("E501", "Single", 5, 1);
        Room room14 = new Room("E502", "2 bed", 5, 2);
        Room room15 = new Room("E503", "4 bed", 5, 4);


        roomDAO.createRoom(room1);
        roomDAO.createRoom(room2);
        roomDAO.createRoom(room3);

        roomDAO.createRoom(room4);
        roomDAO.createRoom(room5);
        roomDAO.createRoom(room6);

        roomDAO.createRoom(room7);
        roomDAO.createRoom(room8);
        roomDAO.createRoom(room9);

        roomDAO.createRoom(room10);
        roomDAO.createRoom(room11);
        roomDAO.createRoom(room12);

        roomDAO.createRoom(room13);
        roomDAO.createRoom(room14);
        roomDAO.createRoom(room15);

        Student s11 = new Student("24cs211", "Daniel Harris", "Male", 20, "CSE", "2nd Year",
                "9123456711", "daniel.harris@example.com", "Mr. Harris", "9001122222",
                "4 bed", "", "Balanced");

        Student s12 = new Student("24ee212", "Matthew Cooper", "Male", 21, "EEE", "3rd Year",
                "9123456712", "matthew.cooper@example.com", "Mr. Cooper", "9001123333",
                "Single", "", "Night Owl");

        Student s13 = new Student("24ce213", "Sebastian Flores", "Male", 18, "CE", "1st Year",
                "9123456713", "sebastian.flores@example.com", "Mr. Flores", "9001124444",
                "2 bed", "", "Early Bird");

        Student s14 = new Student("24it214", "Jack Russell", "Male", 19, "IT", "2nd Year",
                "9123456714", "jack.russell@example.com", "Mr. Russell", "9001125555",
                "4 bed", "", "Night Owl");

        Student s15 = new Student("24me215", "Owen Peterson", "Male", 22, "ME", "Final Year",
                "9123456715", "owen.peterson@example.com", "Mr. Peterson", "9001126666",
                "2 bed", "", "Balanced");


        StudentServices services = new StudentServices();
        services.saveNewStudent(s11);
        services.saveNewStudent(s12);
        services.saveNewStudent(s13);
        services.saveNewStudent(s14);
        services.saveNewStudent(s15);
    }

}