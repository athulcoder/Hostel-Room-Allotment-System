package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Admin {
    private String username;
    private String password;
    private String name;
    private String role;
    private  String phoneNumber;
    private LocalDateTime lastLoginTime;
    private Boolean isActive;
    private String hostelId;
    //constructor




    public Admin(String username, String password, String name , String role, String phoneNumber,String hostelId){
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.lastLoginTime = LocalDateTime.now();
        this.isActive = false;
        this.hostelId = hostelId;
    }

    public Admin() {

    }


    //Getters

    public String getUsername(){
        return  username;
    }
    public String getPassword()
    {
        return password;
    }

    public String getName()
    {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public boolean getIsActive() {
        return isActive;
    }
    public String getHostelId(){
        return  hostelId;
    }





    //Setters
    public void setUsername(String username){
        this.username = username;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;
    }
    public void setIsActive(Boolean active){
        this.isActive = active;
    }
    public void setLastLoginTime(LocalDateTime lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public void setHostelId(String hostelId){
        this.hostelId = hostelId;
    }



}
