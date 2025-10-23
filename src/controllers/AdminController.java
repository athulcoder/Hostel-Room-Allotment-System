package controllers;

import dao.AdminDAO;
import models.Admin;
import ui.MainUI;
import ui.screen.LoginScreen;
import utils.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminController {
    private LoginScreen view;
    private AdminDAO adminDAO;
    private MainUI mainUI;

    public AdminController(LoginScreen view, MainUI mainUI){
        this.view = view;
        this.mainUI = mainUI;
        adminDAO = new AdminDAO();


        view.getLoginButton().addActionListener(e-> handleLogin());
    }


    private void handleLogin(){


                String username = view.getUsername();
                String password = new String(view.getPassword());

                // resetting the usernameField and passwordField
                view.setUsername("");
                view.setPassword("");
                    Admin admin = adminDAO.login(username,password);

                    if (admin !=null) {

                        SessionManager.login(admin);
                        mainUI.showDashboard();
                    } else {
                        view.setErrorMessage("Invalid credentials ");
                    }
                }


        }



