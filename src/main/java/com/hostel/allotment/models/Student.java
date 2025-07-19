package src.main.java.com.hostel.allotment.models;

import java.time.LocalDate;

public class Student {

    private String studentId;
    private String name;
    private String gender;
    private int age;

    private String department;
    private String academicYear;

    private String contactNumber;
    private String email;
    private String guardianName;
    private String guardianPhone;

    private String preferredRoomType;
    private String assignedRoom;
    private String sleepType;
    private LocalDate dateOfAdmission;

    // default constructor
    public Student() {

    }

    // constructor
    public Student(String studentId, String name, String gender, int age, String department, String academicYear,
            String contactNumber, String email, String guardianName, String guardianPhone, String preferredRoomType, String assignedRoom, String sleepType) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.academicYear = academicYear;
        this.contactNumber = contactNumber;
        this.email = email;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.preferredRoomType = preferredRoomType;
        this.assignedRoom = assignedRoom;
        this.sleepType = sleepType;

    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public String getPreferredRoomType() {
        return preferredRoomType;
    }

    public String getAssignedRoom() {
        return assignedRoom;
    }

    public String getSleepType() {
        return sleepType;
    }

    // Setters
    public void setName(String name) {
        this.name = name;

    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
