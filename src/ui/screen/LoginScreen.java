package ui.screen;

import ui.MainUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Custom Rounded TextField
class RoundedTextField extends JTextField {
    private final int arcWidth = 20;
    private final int arcHeight = 20;

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(180, 180, 180));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.dispose();
    }
}

// Custom Rounded PasswordField
class RoundedPasswordField extends JPasswordField {
    private final int arcWidth = 20;
    private final int arcHeight = 20;

    public RoundedPasswordField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(180, 180, 180));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.dispose();
    }
}

public class LoginScreen extends JPanel implements ActionListener {

    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private MainUI mainUI; // reference to parent frame (for navigation)

    public LoginScreen(MainUI mainUI) {
        this.mainUI = mainUI;

        setLayout(new BorderLayout());

        // Gradient background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58, 123, 213),
                        0, getHeight(), new Color(58, 213, 178)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Card-style login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(350, 400));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Admin Login Panel");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new RoundedTextField(15);
        passwordField = new RoundedPasswordField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(58, 123, 213));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);

        // Hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(40, 105, 190));
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(58, 123, 213));
            }
        });

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPanel.add(title);
        loginPanel.add(Box.createVerticalStrut(30));

        //username and password label
        // Create username row
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        usernamePanel.setOpaque(false); // transparent background (keeps gradient visible)
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

// Create password row
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        passwordPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

// Add all to main loginPanel (vertical)
        loginPanel.add(title);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(messageLabel);


        backgroundPanel.add(loginPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("1234")) {
            messageLabel.setForeground(new Color(0, 150, 0));
            messageLabel.setText("Login Successful!");
            mainUI.showScreen("dashboard");
            // Example: switch to another screen in MainUI later
            // mainUI.showScreen("dashboard");
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid Username or Password!");
        }
    }
}
