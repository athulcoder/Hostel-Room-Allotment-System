package ui.screen;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    Dashboard(String username){

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setLayout(new FlowLayout());
        this.add(new JLabel("Welcome "+username));
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
