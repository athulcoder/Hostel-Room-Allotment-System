package services;

import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentServices {
    RoomDAO roomDAO = new RoomDAO();
    StudentDAO studentDAO = new StudentDAO();
    public Room allocateRoomForNewStudent(Student student){
        List<String> checkedRoomNumber = new ArrayList<>();
        List<Room> availableRooms =roomDAO.getAvailableRoomsByTypeAndFloor(student.getPreferredRoomType(),roomDAO.academicYearMapFloor(student.getAcademicYear()));
        List<Student> preferenceBasedStudents =studentDAO.getStudentsWithPreference(student.getSleepType(), student.getPreferredRoomType());

        for(Room room : availableRooms) {

        }
    return availableRooms.getFirst();
    }

}
