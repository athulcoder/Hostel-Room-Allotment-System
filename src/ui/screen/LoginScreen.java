package  ui.screen;


import javax.swing.*;

public class LoginScreen extends JFrame{


    public LoginScreen(){
        this.setSize(500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Hostel Room Allotment System");

//        Panel for (username , password)
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");




        this.setVisible(true);
    }
}