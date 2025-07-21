
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

        RoomDAO roomDAO = new RoomDAO();
       Room r = roomDAO.getRoomByNumber("C340");

        System.out.println(r.getRoomType()+" "+ r.getRoomNumber());

    }

}