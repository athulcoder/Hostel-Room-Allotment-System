package ui.screen;

import ui.MainUI;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    public LoginScreen(MainUI mainUI) {
        // fixed size
        setPreferredSize(new Dimension(400, 300));

        // custom background (gradient)
        setLayout(  new GridBagLayout()) ;
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(255, 255, 255));

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUser.setForeground(Color.WHITE);

        JTextField txtUser = new JTextField(15);
        styleTextField(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPass.setForeground(Color.WHITE);

        JPasswordField txtPass = new JPasswordField(15);
        styleTextField(txtPass);

        JButton btnLogin = new JButton("Login");
        styleButton(btnLogin, new Color(52, 152, 219));

        JButton btnClear = new JButton("Clear");
        styleButton(btnClear, new Color(231, 76, 60));

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; add(lblUser, gbc);
        gbc.gridx = 1; add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblPass, gbc);
        gbc.gridx = 1; add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(btnLogin, gbc);
        gbc.gridx = 1; add(btnClear, gbc);

        // Button actions
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            // TODO: Replace with DAO
            if(user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                mainUI.switchScreen("dashboard"); // switch to dashboard
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        });

        btnClear.addActionListener(e -> {
            txtUser.setText("");
            txtPass.setText("");
        });
    }

    // Paint gradient background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(58, 123, 213);
        Color color2 = new Color(58, 213, 170);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    // Utility: style text fields
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    // Utility: style buttons
    private void styleButton(JButton button, Color bg) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
