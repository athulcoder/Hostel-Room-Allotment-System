package models;


import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private LocalDateTime dateOfAdmission;
    private String hostelId;


    // newly added attributes to calculate the compatibility

    private String studyPreference;     // "Prefers Quiet Study" or "Studies with Music"
    private String lifestyle;           // "Tidy / Organized", "Prefers Quiet Room"
    private boolean vegetarian;         // true/false
    private String socialPreference;    // "Introvert" or "Extrovert"
    private String activityPreference;  // "Group" or "Solo"
    private String hobbies;             // "Coding, Gaming, Music"
    private String sharingHabits;       // "Share Food, Share Supplies"
    private String roomPresence;        // "Mostly In" or "Mostly Out"



    // default constructor
    public Student() {

    }

    // constructor
    public Student(String studentId, String name, String gender, int age, String department, String academicYear,
            String contactNumber, String email, String guardianName, String guardianPhone, String preferredRoomType, String assignedRoom, String sleepType) {
        this.studentId = studentId.toLowerCase();
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
        this.dateOfAdmission = LocalDateTime.now();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId.toLowerCase();
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
    public String getDateOfAdmission() {

        return dateOfAdmission.toString();
    }
    public String getDateOfAdmissionFormated(){
        return dateOfAdmission.getDayOfMonth() +" " + dateOfAdmission.getMonth() + " "+dateOfAdmission.getYear() +"  "+dateOfAdmission.getHour() +":"+dateOfAdmission.getMinute() +":"+dateOfAdmission.getSecond();

    }
    public String getHostelId(){
        return hostelId;
    }


    public String getStudyPreference(){return studyPreference;}
    public String getLifestyle(){return lifestyle;}
    public String getSocialPreference(){return socialPreference;}
    public String getHobbies(){return hobbies;}
    public String getActivityPreference(){return activityPreference;}
    public String getSharingHabits(){return sharingHabits;}
    public String getRoomPresence(){return roomPresence;}
    public boolean isVegetarian() {return vegetarian;}

// Setters

    public void setStudentId(String studentId){
        this.studentId = studentId.toLowerCase();
    }
    public void setName(String name) {
        this.name = name;

    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public void setPreferredRoomType(String preferredRoomType) {
        this.preferredRoomType = preferredRoomType;
    }

    public void setAssignedRoom(String assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    public void setSleepType(String sleepType) {
        this.sleepType = sleepType;
    }

    public void setHostelId(String hostelId){
        this.hostelId = hostelId;
    }

    public void setDateOfAdmission(LocalDateTime dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public void setStudyPreference(String studyPreference){ this.studyPreference = studyPreference;}
    public void setLifestyle(String lifestyle){ this.lifestyle = lifestyle;}
    public void setLifestyle(ArrayList<String> lifestyle){ this.lifestyle = String.join(", ", lifestyle);}
    public void setVegetarian(boolean vegetarian){ this.vegetarian = vegetarian;}
    public void setSocialPreference(String socialPreference){ this.socialPreference = socialPreference;}
    public void setActivityPreference(String activityPreference){ this.activityPreference = activityPreference;}
    public void setHobbies(String hobbies){ this.hobbies = hobbies;}
    public void setHobbies(ArrayList<String> hobbies){this.hobbies = String.join(", ", hobbies);}
    public void setSharingHabits(String sharingHabits){ this.sharingHabits=sharingHabits;}
    public void setSharingHabits(ArrayList<String> sharingHabits){  this.sharingHabits = String.join(", ", sharingHabits);}
    public void setRoomPresence(String roomPresence){ this.roomPresence = roomPresence;}

}
