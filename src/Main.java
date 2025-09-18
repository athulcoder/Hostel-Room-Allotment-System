

import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;
import utils.DatabaseInitializer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        DatabaseInitializer.initializeDatabase();

        RoomDAO roomDAO = new RoomDAO();
        Room room = new Room("527","2-bed",5,2,"H001");
        roomDAO.createRoom(room);

    }

}