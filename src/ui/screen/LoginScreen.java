package ui.screen;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    JTextField username;
    JPasswordField password;
    public LoginScreen() {
        this.setTitle("Hostel Room Allotment System");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // center window on screen

        // ==== Login form panel ====
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0xbce0f7));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // padding

        JLabel usernameLabel = new JLabel("Username");
        username = new JTextField();
        username.setFont(new Font("Segoe UI", Font.BOLD, 19));
        JLabel passwordLabel = new JLabel("Password");
        password = new JPasswordField();
        password.setFont(new Font("Segoe UI", Font.BOLD, 19));


        // set sizes and alignments
        username.setMaximumSize(new Dimension(300, 30));
        password.setMaximumSize(new Dimension(300, 30));

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setAlignmentX(Component.CENTER_ALIGNMENT);

        //padding
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // outer border
                BorderFactory.createEmptyBorder(5, 10, 5, 10)           // padding: top, left, bottom, right
        ));

        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // outer border
                BorderFactory.createEmptyBorder(5, 10, 5, 10)           // padding: top, left, bottom, right
        ));


        // add spacing
        panel.add(Box.createVerticalStrut(10));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(username);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(password);
        panel.add(Box.createVerticalStrut(20));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 30));
        loginButton.setMaximumSize(new Dimension(300, 30));
        loginButton.setBackground(new Color(0x67c8e6));

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginButton);
        loginButton.addActionListener(e->login());
        this.add(panel);
        this.setResizable(false);
        this.setVisible(true);
    }


    void login(){
        System.out.println(username.getText());
        System.out.println(password.getPassword());
        this.dispose();
        new
    }


}
