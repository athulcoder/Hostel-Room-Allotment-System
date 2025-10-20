package service;

// Make sure to import your models and DAO packages
import models.Room;
import models.Student;
import dao.RoomDAO;
import dao.StudentDAO;
import utils.PreferenceConstants; // <-- IMPORTING YOUR CONSTANTS

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllotmentEngine {

    // Base score for placing a student in an empty room
    private static final int EMPTY_ROOM_SCORE_BONUS = 50;

    // DAOs should be instantiated or injected
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
        // Assuming getAllRooms() now correctly fetches by hostelId
        // If not, you may need to adjust this call
        List<Room> allRooms = roomDAO.getAllRooms();
        List<Student> allStudents = studentDAO.getAllStudents(hostelId);

        // 2. Filter for unassigned students
        List<Student> unassignedStudents = allStudents.stream()
                .filter(s -> s.getAssignedRoom() == null || s.getAssignedRoom().isEmpty())
                .collect(Collectors.toList()); // Use toList() or collect(Collectors.toList())

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
            // We must re-fetch 'allRooms' or use the full list, as their
            // occupancy state changes during this loop.
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
                // We pass 'allStudents' to find current occupants
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

                // --- THIS IS THE NEW HARD CONSTRAINT CHECK ---
                // Get the first occupant to check the Academic Year
                Student firstOccupant = occupants.get(0);
                if (!firstOccupant.getAcademicYear().equals(newStudent.getAcademicYear())) {
                    // This room is for a different Academic Year. Skip it.
                    continue;
                }
                // --- END OF NEW CHECK ---

                // This room is valid, calculate average compatibility.
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
        studentDAO.updateStudent(student); // Assuming this updates the DB

        // 2. Update Room in our local list AND the database
        room.setOccupancy(room.getOccupancy() + 1);
        roomDAO.updateRoom(room); // Assuming this updates the DB

        System.out.println("SUCCESS: Allotted student " + student.getStudentId() +
                " to room " + room.getRoomNumber() +
                " (Occupancy: " + room.getOccupancy() + "/" + room.getCapacity() + ")");
    }


    /**
     * Calculates a compatibility score between two students.
     * **THIS IS THE UPDATED METHOD USING CONSTANTS AND OVERLAP LOGIC.**
     *
     * @return A score (e.g., 0-100)
     */
    private int calculateCompatibility(Student s1, Student s2) {
        if (s1 == null || s2 == null) return 0;

        int score = 0;

        // --- !! CUSTOMIZE YOUR SCORING HERE !! ---

        // Example: Sleep type is very important (using constants)
        if (s1.getSleepType() != null && s1.getSleepType().equals(s2.getSleepType())) {
            score += 30;
        }

        // Example: Study preference is important (using constants)
        if (s1.getStudyPreference() != null && s2.getStudyPreference() != null) {
            if (s1.getStudyPreference().equals(PreferenceConstants.QUIET_STUDY) &&
                    s2.getStudyPreference().equals(PreferenceConstants.QUIET_STUDY)) {
                score += 20;
            } else if (s1.getStudyPreference().equals(PreferenceConstants.MUSIC_STUDY) &&
                    s2.getStudyPreference().equals(PreferenceConstants.MUSIC_STUDY)) {
                score += 20;
            }
        }

        // Example: Vegetarian
        if (s1.isVegetarian() == s2.isVegetarian()) {
            score += 10;
        }

        // Example: Social preference
        if (s1.getSocialPreference() != null && s1.getSocialPreference().equals(s2.getSocialPreference())) {
            score += 10;
        }

        // Example: Room Presence
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

    /**
     * Helper method to calculate a score based on common elements in two
     * comma-separated strings (e.g., "Coding, Gaming" and "Music, Gaming").
     *
     * @param csv1           First student's preference string (e.g., "Tidy, Quiet")
     * @param csv2           Second student's preference string (e.g., "Tidy")
     * @param pointsPerMatch Points to award for each common item
     * @return Total score for the overlap
     */
    private int calculateOverlapScore(String csv1, String csv2, int pointsPerMatch) {
        if (csv1 == null || csv1.isEmpty() || csv2 == null || csv2.isEmpty()) {
            return 0;
        }

        // Split strings by ", " and put them into a Set for fast lookup
        Set<String> set1 = new HashSet<>(Arrays.asList(csv1.split(", ")));
        Set<String> set2 = new HashSet<>(Arrays.asList(csv2.split(", ")));

        // Find the intersection (common elements)
        set1.retainAll(set2);

        // Return score based on the number of common elements
        return set1.size() * pointsPerMatch;
    }
}