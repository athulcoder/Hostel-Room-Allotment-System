package services;

import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentServices {
    RoomDAO roomDAO = new RoomDAO();
    StudentDAO studentDAO = new StudentDAO();
    public Room allocateRoomForNewStudent(Student student){
        List<String> checkedRoomNumber = new ArrayList<>();
        List<Room> availableRooms =roomDAO.getAvailableRoomsByTypeAndFloor(student.getPreferredRoomType(),roomDAO.academicYearMapFloor(student.getAcademicYear()));
        List<Student> samePreferenceBasedStudents =studentDAO.getStudentsWithPreference(student.getSleepType(), student.getPreferredRoomType());


//        for(Student s : samePreferenceBasedStudents) {
//
//            // first get the room of the student
//            Room oldStudentRoom = roomDAO.getRoomByNumber(student.getAssignedRoom());
//
//            if(!oldStudentRoom.getRoomFull()){
//                student.setAssignedRoom(oldStudentRoom.getRoomNumber());
//                oldStudentRoom.setOccupancy(oldStudentRoom.getOccupancy()+1);
//                return oldStudentRoom;
//            }
//
//        }
    return availableRooms.getFirst();
    }

}
