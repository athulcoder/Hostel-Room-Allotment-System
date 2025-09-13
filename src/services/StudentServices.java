package services;

import models.Room;
import models.Student;

import java.util.ArrayList;
import java.util.List;

import static utils.RoomList.containsRoom;

public class StudentServices {
    RoomDAO roomDAO = new RoomDAO();
    StudentDAO studentDAO = new StudentDAO();


    private Room allocateRoomForNewStudent(Student student){
        List<String> checkedRoomNumber = new ArrayList<>();

        List<Room> availableRooms =roomDAO.getAvailableRoomsByTypeAndFloor(student.getPreferredRoomType(),roomDAO.academicYearMapFloor(student.getAcademicYear()));
        List<Student> samePreferenceBasedStudents =studentDAO.getStudentsWithPreference(student.getSleepType(), student.getPreferredRoomType());

        System.out.println(availableRooms.isEmpty());
        System.out.println(roomDAO.academicYearMapFloor(student.getAcademicYear()));
        for(Student s : samePreferenceBasedStudents) {

            // first get the room of the student
            Room oldStudentRoom = roomDAO.getRoomByNumber(student.getAssignedRoom());
            if(oldStudentRoom == null )continue;

            if(!oldStudentRoom.getRoomFull() && containsRoom(availableRooms,oldStudentRoom)){
                student.setAssignedRoom(oldStudentRoom.getRoomNumber());
                oldStudentRoom.setOccupancy(oldStudentRoom.getOccupancy()+1);
                return oldStudentRoom;

            }

        }
        if (!availableRooms.isEmpty()) {
            Room fallback = availableRooms.get(0);
            student.setAssignedRoom(fallback.getRoomNumber());
            fallback.setOccupancy(fallback.getOccupancy() + 1);
            fallback.setRoomFull(fallback.getOccupancy() == fallback.getCapacity());
            return fallback;
        }

        // No room available
        return null;

    }
    // save new student
    public void saveNewStudent(Student student){
        Room room = allocateRoomForNewStudent(student);
        if(room ==null){
            System.out.println("Room not available");
            return;
        }

            student.setAssignedRoom(room.getRoomNumber());
            roomDAO.allocateRoomToStudent(room);
            studentDAO.saveStudent(student);

    }
}
