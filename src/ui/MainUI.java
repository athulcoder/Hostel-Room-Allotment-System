package ui;
import ui.screen.LoginScreen;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    public MainUI() {
        setTitle("My Project");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        mainPanel.add(new LoginScreen(this), "login");
//        mainPanel.add(new DashboardScreen(this), "dashboard");
//        mainPanel.add(new StudentScreen(this), "student");
//        mainPanel.add(new RoomScreen(this), "room");

        add(mainPanel);
        setVisible(true);
        setVisible(true);

        // Start with login
        cardLayout.show(mainPanel, "login");
    }

    public void switchScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
