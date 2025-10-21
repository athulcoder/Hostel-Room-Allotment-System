
import dao.AdminDAO;
import dao.HostelDAO;
import models.Admin;
import models.Hostel;
import ui.MainUI;
import utils.DatabaseInitializer;


public class Main {

    public static void main(String[] args) {

        new MainUI();

//        DatabaseInitializer.initializeDatabase();
//        HostelDAO hostelDAO = new HostelDAO();
//        Hostel hostel = new Hostel("Adams House","Boys",12,5,540,"H001");
//        hostelDAO.addHostel(hostel);
//        AdminDAO adminDAO = new AdminDAO();
//        Admin a = new Admin("admin234","123456","Jayachandran","Warden","9645372822","H001");
//        adminDAO.signup(a);
//
    }
}
