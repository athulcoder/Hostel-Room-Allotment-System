package service;// Make sure to import your models and DAO packages
import models.Room;
import models.Student;
import dao.RoomDAO;
import dao.StudentDAO;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllotmentEngine {

    // Base score for placing a student in an empty room
    // This encourages filling empty rooms before packing existing ones
    private static final int EMPTY_ROOM_SCORE_BONUS = 50;
    RoomDAO roomDAO = new RoomDAO();
    StudentDAO studentDAO = new StudentDAO();
    /**
     * Main method to run the entire allotment process for a specific hostel.
     * @param hostelId The ID of the hostel to process.
     */
    public void runAllotment(String hostelId) {
        System.out.println("Starting allotment process for hostel: " + hostelId);
        System.out.println("==================================================");

        // 1. Fetch all data from DAOs

        List<Room> allRooms = roomDAO.getAllRooms();
        List<Student> allStudents = studentDAO.getAllStudents(hostelId);

        // 2. Filter for unassigned students and available rooms
        List<Student> unassignedStudents = allStudents.stream()
                .filter(s -> s.getAssignedRoom() == null || s.getAssignedRoom().isEmpty())
                .toList();

        System.out.println("Found " + unassignedStudents.size() + " unassigned students.");

        // 3. Group students by HARD CONSTRAINTS (Academic Year + Preferred Room Type)
        Map<String, List<Student>> studentGroups = unassignedStudents.stream()
                .collect(Collectors.groupingBy(s ->
                        s.getAcademicYear() + "_" + s.getPreferredRoomType()
                ));

        // 4. Process each group one by one
        for (Map.Entry<String, List<Student>> entry : studentGroups.entrySet()) {
            String[] keyParts = entry.getKey().split("_");
            String academicYear = keyParts[0];
            String roomType = keyParts[1];
            List<Student> studentsInGroup = entry.getValue();

            System.out.println("\n--- Processing Group ---");
            System.out.println("Academic Year: " + academicYear);
            System.out.println("Room Type: " + roomType);
            System.out.println("Students in group: " + studentsInGroup.size());

            // Find all rooms that match this group's hard constraints
            List<Room> matchingRooms = allRooms.stream()
                    .filter(r -> r.getRoomType().equals(roomType) && !r.getRoomFull())
                    .collect(Collectors.toList());

            if (matchingRooms.isEmpty()) {
                System.out.println("WARNING: No available rooms of type '" + roomType + "' found. This group will be skipped.");
                continue;
            }

            // 5. Allot students in this group based on compatibility
            for (Student student : studentsInGroup) {
                // Find the best possible room for this student
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

    /**
     * Finds the best available room for a student based on compatibility with
     * existing occupants.
     *
     * @param newStudent      The student to be placed.
     * @param potentialRooms  A list of rooms that match the student's hard constraints.
     * @param allStudents     A list of ALL students (used to find occupants).
     * @return The best Room object, or null if no suitable room is found.
     */
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
                // This room has occupants. Calculate average compatibility.
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

    /**
     * Assigns a student to a room and updates the DAO.
     */
    private void allotStudentToRoom(Student student, Room room) {
        // 1. Update Student
        student.setAssignedRoom(room.getRoomNumber());
        studentDAO.updateStudent(student);

        // 2. Update Room
        room.setOccupancy(room.getOccupancy() + 1);
        // The setOccupancy method in your model already handles setting isFull
        roomDAO.updateRoom(room);

        System.out.println("SUCCESS: Allotted student " + student.getStudentId() +
                " to room " + room.getRoomNumber() +
                " (Occupancy: " + room.getOccupancy() + "/" + room.getCapacity() + ")");
    }


    /**
     * Calculates a compatibility score between two students.
     * **THIS IS THE METHOD YOU SHOULD CUSTOMIZE.**
     *
     * @return A score (e.g., 0-100)
     */
    private int calculateCompatibility(Student s1, Student s2) {
        if (s1 == null || s2 == null) return 0;

        int score = 0;

        // --- !! CUSTOMIZE YOUR SCORING HERE !! ---

        // Example: Sleep type is very important
        if (s1.getSleepType() != null && s1.getSleepType().equals(s2.getSleepType())) {
            score += 30;
        }

        // Example: Study preference is important
        if (s1.getStudyPreference() != null && s1.getStudyPreference().equals(s2.getStudyPreference())) {
            score += 20;
        }

        // Example: Lifestyle
        if (s1.getLifestyle() != null && s1.getLifestyle().equals(s2.getLifestyle())) {
            score += 15;
        }

        // Example: Vegetarian
        if (s1.isVegetarian() == s2.isVegetarian()) {
            score += 10;
        }

        // Example: Social preference
        if (s1.getSocialPreference() != null && s1.getSocialPreference().equals(s2.getSocialPreference())) {
            score += 10;
        }

        // You can add more complex logic for hobbies (e.g., check for common hobbies)
        // ...

        return score;
    }




}