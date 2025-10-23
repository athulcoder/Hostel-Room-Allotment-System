package controllers;

import dao.AdminDAO;
import models.Admin;
import ui.MainUI;
import ui.screen.Dashboard;
import ui.screen.panels.SettingsPanel;
import utils.SessionManager;

public class SettingsController {
    private final MainUI mainUI;
    private SettingsPanel view;
    private final AdminDAO adminDAO;


    public SettingsController(SettingsPanel view, MainUI mainUI){
        this.mainUI = mainUI;
        this.view = view;
        adminDAO = new AdminDAO();
        view.getLogoutButton().addActionListener(e->handleLogout());
    }


    private void handleLogout(){

        adminDAO.logout(SessionManager.getCurrentAdmin().getUsername());
        mainUI.showScreen("login-screen");
        mainUI.removeDashboard();
        SessionManager.logout();

    }


    public static void updateAdmin(Admin admin){

    }
}
