
import models.Admin;

public class Main {

    public static void main(String[] args) {


        Admin admin2 = new Admin("aaron", "12345", "Aaron", "hostel warden", "123456789", "deijdajh");

        System.out.println(admin2.getUsername());
        admin2.setUsername("Nandana");
        System.out.println(admin2.getUsername());

    }

}