package service;


import models.Room;
import models.Student;
import dao.RoomDAO;
import dao.StudentDAO;
import utils.PreferenceConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllotmentEngine {


    private static final int EMPTY_ROOM_SCORE_BONUS = 50;


    RoomDAO roomDAO = new RoomDAO();
    StudentDAO studentDAO = new StudentDAO();


    public void runAllotment(String hostelId) {

        List<Room> allRooms = roomDAO.getAllRooms();
        List<Student> allStudents = studentDAO.getAllStudents(hostelId);


        List<Student> unassignedStudents = allStudents.stream()
                .filter(s -> s.getAssignedRoom() == null || s.getAssignedRoom().isEmpty())
                .toList();

        System.out.println("Found " + unassignedStudents.size() + " unassigned students.");

        // 3. Group students by HARD CONSTRAINTS (Academic Year + Preferred Room Type)
        Map<String, List<Student>> studentGroups = unassignedStudents.stream()
                .collect(Collectors.groupingBy(s ->
                        s.getAcademicYear() + "_" + s.getPreferredRoomType()
                ));






        for (Map.Entry<String, List<Student>> entry : studentGroups.entrySet()) {
            String[] keyParts = entry.getKey().split("_");
            String academicYear = keyParts[0];
            String roomType = keyParts[1];
            List<Student> studentsInGroup = entry.getValue();

            System.out.println("\n--- Processing Group ---");
            System.out.println("Academic Year: " + academicYear);
            System.out.println("Room Type: " + roomType);
            System.out.println("Students in group: " + studentsInGroup.size());


            List<Room> matchingRooms = allRooms.stream()
                    .filter(r -> r.getRoomType().equals(roomType) && !r.getRoomFull())
                    .collect(Collectors.toList());


            if (matchingRooms.isEmpty()) {
                System.out.println("WARNING: No available rooms of type '" + roomType + "' found. This group will be skipped.");
                continue;
            }


            // 5. Allot students in this group based on compatibility
            for (Student student : studentsInGroup) {

                Room bestRoom = findBestRoomForStudent(student, matchingRooms, allStudents);

                if (bestRoom == null) {
                    System.out.println("WARNING: No space left for student " + student.getStudentId() + " in any matching room.");
                } else {
                    // Allot the student
                    allotStudentToRoom(student, bestRoom);
                }
            }
        }
        System.out.println("\n==================================================");
        System.out.println("Allotment process finished.");
    }


    private Room findBestRoomForStudent(Student newStudent, List<Room> potentialRooms, List<Student> allStudents) {
        Room bestRoom = null;
        double maxScore = -1.0;

        for (Room room : potentialRooms) {
            // Check if room is full (it might have been filled by this engine run)
            if (room.getRoomFull()) {
                continue;
            }

            // Find all students *currently* assigned to this room
            List<Student> occupants = allStudents.stream()
                    .filter(s -> room.getRoomNumber().equals(s.getAssignedRoom()))
                    .collect(Collectors.toList());

            double currentRoomScore;

            if (occupants.isEmpty()) {
                // This is an empty room. Give it a base score.
                currentRoomScore = EMPTY_ROOM_SCORE_BONUS;
            } else {

                Student firstOccupant = occupants.get(0);
                if (!firstOccupant.getAcademicYear().equals(newStudent.getAcademicYear())) {

                    continue;
                }

                double totalScore = 0;
                for (Student occupant : occupants) {
                    totalScore += calculateCompatibility(newStudent, occupant);
                }
                currentRoomScore = totalScore / occupants.size();
            }

            // If this room is the best one found so far, save it.
            if (currentRoomScore > maxScore) {
                maxScore = currentRoomScore;
                bestRoom = room;
            }
        }
        return bestRoom; // This will be null if no rooms had space
    }


    private void allotStudentToRoom(Student student, Room room) {
        // 1. Update Student
        student.setAssignedRoom(room.getRoomNumber());
        studentDAO.updateStudent(student);

        // 2. Update Room in our local list AND the database
        room.setOccupancy(room.getOccupancy() + 1);
        roomDAO.updateRoom(room);

        System.out.println("SUCCESS: Allotted student " + student.getStudentId() +
                " to room " + room.getRoomNumber() +
                " (Occupancy: " + room.getOccupancy() + "/" + room.getCapacity() + ")");
    }



    private int calculateCompatibility(Student s1, Student s2) {
        if (s1 == null || s2 == null) return 0;

        int score = 0;



        if (s1.getSleepType() != null && s1.getSleepType().equals(s2.getSleepType())) {
            score += 30;
        }


        if (s1.getStudyPreference() != null && s2.getStudyPreference() != null) {
            if (s1.getStudyPreference().equals(PreferenceConstants.QUIET_STUDY) &&
                    s2.getStudyPreference().equals(PreferenceConstants.QUIET_STUDY)) {
                score += 20;
            } else if (s1.getStudyPreference().equals(PreferenceConstants.MUSIC_STUDY) &&
                    s2.getStudyPreference().equals(PreferenceConstants.MUSIC_STUDY)) {
                score += 20;
            }
        }


        if (s1.isVegetarian() == s2.isVegetarian()) {
            score += 10;
        }


        if (s1.getSocialPreference() != null && s1.getSocialPreference().equals(s2.getSocialPreference())) {
            score += 10;
        }


        if (s1.getRoomPresence() != null && s1.getRoomPresence().equals(s2.getRoomPresence())) {
            score += 5;
        }

        // --- NEW LOGIC FOR MULTI-VALUE FIELDS ---
        // Give 5 points for each matching lifestyle habit
        score += calculateOverlapScore(s1.getLifestyle(), s2.getLifestyle(), 5);

        // Give 3 points for each matching hobby
        score += calculateOverlapScore(s1.getHobbies(), s2.getHobbies(), 3);

        // Give 2 points for each matching sharing habit
        score += calculateOverlapScore(s1.getSharingHabits(), s2.getSharingHabits(), 2);

        return score;
    }


    private int calculateOverlapScore(String csv1, String csv2, int pointsPerMatch) {
        if (csv1 == null || csv1.isEmpty() || csv2 == null || csv2.isEmpty()) {
            return 0;
        }


        Set<String> set1 = new HashSet<>(Arrays.asList(csv1.split(", ")));
        Set<String> set2 = new HashSet<>(Arrays.asList(csv2.split(", ")));


        set1.retainAll(set2);

        // Return score based on the number of common elements
        return set1.size() * pointsPerMatch;
    }
}