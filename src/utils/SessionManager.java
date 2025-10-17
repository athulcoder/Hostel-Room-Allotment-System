package utils;

import models.Admin;

public class SessionManager {
    private static Admin currentAdmin;
    SessionManager(){

    }

    public static void login(Admin admin){
        currentAdmin = admin;
        System.out.println("✅ Set in SessionManager instance: " + SessionManager.class.getClassLoader());    }

    public static Admin getCurrentAdmin(){
        System.out.println("🧩 Getting from SessionManager instance: " + SessionManager.class.getClassLoader());
        return currentAdmin;
    }

    public static void logout(){
        currentAdmin = null;
        System.out.println("cleared: " );
    }
}
