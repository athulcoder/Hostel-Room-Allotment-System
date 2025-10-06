package ui;
import ui.screen.LoginScreen;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame{

    CardLayout cardLayout ;
    JPanel mainPanel ;
    public MainUI(){

        setTitle("Hostel Room Allotment System");
        setSize(1000,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);





    }
}
