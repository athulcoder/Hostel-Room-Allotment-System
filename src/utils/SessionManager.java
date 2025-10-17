package utils;

import models.Admin;

public class SessionManager {
    private static Admin currentAdmin;
    SessionManager(){

    }

    public static void login(Admin admin){
        currentAdmin = admin;
    }

    public static Admin getCurrentAdmin(){
        return currentAdmin;
    }

    public static void logout(){
        currentAdmin = null;
    }
}
