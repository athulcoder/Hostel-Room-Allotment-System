
import com.hostel.allotment.models.Student;

public class Main {

    public static void main(String[] args) {

        Student s = new Student();
        s.setName("George");
        String n = s.getName();
        System.out.print(n);
    }
}
