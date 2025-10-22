package ui;

import controllers.AdminController;
import dao.AdminDAO;
import ui.screen.Dashboard;
import ui.screen.LoginScreen;
import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Dashboard dashboard;

    public MainUI() {
        setTitle("Hostel Room Allotment System");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window


        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add screens
        LoginScreen loginScreen = new LoginScreen(this); // Pass MainUI reference for screen switching later
        new AdminController(loginScreen, this);

        mainPanel.add(loginScreen, "login-screen");

        // Add the main panel to frame
        add(mainPanel);

        // Initially show login screen
        cardLayout.show(mainPanel, "login-screen");

        setVisible(true);
    }

    // Method to switch between screens
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public void showDashboard(){

        if(dashboard==null){
            dashboard = new Dashboard(this);

            mainPanel.add("dashboard",dashboard);
            showScreen("dashboard");


        }
    }

}
