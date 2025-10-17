
import dao.AdminDAO;
import dao.RoomDAO;
import dao.StudentDAO;
import models.Admin;
import models.Room;
import models.Student;
import ui.MainUI;
import ui.screen.LoginScreen;
import utils.DatabaseInitializer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        new MainUI();

//        AdminDAO a = new AdminDAO();
//        Admin ad = a.login("admin234", "123456");
//        System.out.println(ad.getUsername() +
//                ad.getLastLoginTime() +
//                ad.getName());

//    System.out.println(a.logout("admin234"));
    }


}