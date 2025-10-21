package ui.screen;

import dao.AdminDAO;
import models.Admin;
import ui.MainUI;
import utils.SessionManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

// --- New Custom Rounded & Shadowed Panel ---
/**
 * A custom JPanel that draws a rounded white background with a soft drop shadow.
 */
class RoundedShadowPanel extends JPanel {
    private final int arcWidth = 25;
    private final int arcHeight = 25;
    private final int shadowSize = 8;
    private final Color shadowColor = new Color(0, 0, 0, 50);

    public RoundedShadowPanel() {
        super();
        setOpaque(false); // We will paint our own background
        // Add padding to make space for the shadow
        setBorder(BorderFactory.createEmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // --- Draw the Shadow ---
        // Create a rounded rectangle for the shadow, slightly offset
        RoundRectangle2D shadowRect = new RoundRectangle2D.Float(
                shadowSize, shadowSize,
                width - shadowSize * 2, height - shadowSize * 2,
                arcWidth, arcHeight
        );
        g2.setColor(shadowColor);
        // Use Area to create a soft, blurred shadow effect (optional, simple fill is ok too)
        // This is a simple way to "fake" a blur by drawing multiple transparent layers
        for (int i = 0; i < shadowSize; i++) {
            g2.fill(new RoundRectangle2D.Float(
                    shadowSize - i / 2.0f, shadowSize - i / 2.0f,
                    width - shadowSize * 2 + i, height - shadowSize * 2 + i,
                    arcWidth, arcHeight
            ));
        }

        // --- Draw the White Card ---
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(
                shadowSize, shadowSize,
                width - shadowSize * 2, height - shadowSize * 2,
                arcWidth, arcHeight
        ));

        g2.dispose();
    }
}


// --- Updated Custom Rounded TextField ---
/**
 * A custom JTextField with rounded borders, an icon, and placeholder text.
 */
class RoundedTextField extends JTextField {
    private final int arcWidth = 25;
    private final int arcHeight = 25;
    private ImageIcon icon;
    private String placeholder;
    private Color borderColor = new Color(220, 220, 220);
    // --- CHANGED --- Updated focus color to a modern blue
    private final Color focusColor = new Color(88, 101, 242);

    public RoundedTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        setOpaque(false);
        // Set padding: top, left (for icon), bottom, right
        setBorder(BorderFactory.createEmptyBorder(10, 45, 10, 15));
        setForeground(new Color(51, 51, 51));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                borderColor = focusColor;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                borderColor = new Color(220, 220, 220);
                repaint();
            }
        });
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Paint icon
        if (icon != null) {
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, 15, iconY);
        }

        // Paint placeholder
        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
        }

        // Let the superclass paint the text
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.dispose();
    }
}

// --- Updated Custom Rounded PasswordField ---
/**
 * A custom JPasswordField with rounded borders, an icon, and placeholder text.
 */
class RoundedPasswordField extends JPasswordField {
    private final int arcWidth = 25;
    private final int arcHeight = 25;
    private ImageIcon icon;
    private String placeholder;
    private Color borderColor = new Color(220, 220, 220);
    // --- CHANGED --- Updated focus color to a modern blue
    private final Color focusColor = new Color(88, 101, 242);

    public RoundedPasswordField(String placeholder) {
        super();
        this.placeholder = placeholder;
        setOpaque(false);
        // Set padding: top, left (for icon), bottom, right
        setBorder(BorderFactory.createEmptyBorder(10, 45, 10, 15));
        setForeground(new Color(51, 51, 51));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                borderColor = focusColor;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                borderColor = new Color(220, 220, 220);
                repaint();
            }
        });
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Paint icon
        if (icon != null) {
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, 15, iconY);
        }

        // Paint placeholder
        if (getPassword().length == 0 && !isFocusOwner()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
        }

        // Let the superclass paint the text
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2.dispose();
    }
}

// --- New Custom Rounded Button ---
/**
 * A custom JButton with rounded corners and hover effects.
 */
class RoundedButton extends JButton {
    private final int arcWidth = 25;
    private final int arcHeight = 25;
    private Color backgroundColor;
    private Color hoverColor;

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }
        });
    }

    @Override
    public void setBackground(Color bg) {
        // Store the original color
        if (backgroundColor == null) {
            this.backgroundColor = bg;
        }
        super.setBackground(bg);
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        super.paintComponent(g);
        g2.dispose();
    }
}


public class LoginScreen extends JPanel {

    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedButton loginButton; // Use new custom button
    private JLabel messageLabel;
    private MainUI mainUI;

    // --- CHANGED --- New Blue/Reddish Gradient Theme ---
    private final Color COLOR_BACKGROUND_START = new Color(220, 230, 255); // Light Blue
    private final Color COLOR_BACKGROUND_END = new Color(255, 225, 230);   // Light Reddish/Pink
    private final Color COLOR_BUTTON = new Color(88, 101, 242);         // Modern Blue
    private final Color COLOR_BUTTON_HOVER = new Color(71, 82, 196);    // Darker Blue
    private final Color COLOR_TEXT_DARK = new Color(51, 51, 51);
    private final Color COLOR_TEXT_LIGHT = new Color(136, 136, 136);
    private final Color COLOR_ERROR = new Color(211, 47, 47);

    // --- Modern Fonts (Using safe, cross-platform "SansSerif") ---
    private final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 28);
    private final Font FONT_DESCRIPTION = new Font("SansSerif", Font.PLAIN, 14);
    private final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 16);
    private final Font FONT_FIELD = new Font("SansSerif", Font.PLAIN, 16);
    private final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 16);
    private final Font FONT_MESSAGE = new Font("SansSerif", Font.PLAIN, 14);


    public LoginScreen(MainUI mainUI) {
        this.mainUI = mainUI;
        initComponents();
    }

    /**
     * Helper method to load and scale icons.
     */
    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            URL resourceUrl = getClass().getResource(path);
            if (resourceUrl == null) {
                System.err.println("Icon resource not found: " + path);
                return null;
            }
            BufferedImage img = ImageIO.read(resourceUrl);
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            System.err.println("Error loading icon: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // 1. Gradient Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, COLOR_BACKGROUND_START,
                        0, getHeight(), COLOR_BACKGROUND_END
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // Use GBL to center the card
        add(backgroundPanel, BorderLayout.CENTER);

        // 2. White Login Card Panel (Using our new custom panel)
        RoundedShadowPanel loginPanel = new RoundedShadowPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setPreferredSize(new Dimension(420, 520)); // Adjusted size
        // Padding is handled by the panel's border and an inner padding panel

        // Create an inner panel for content, to respect the shadow border
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));


        // 3. Add Logo
        // IMPORTANT: Update this path to your new leaf logo
        ImageIcon logoIcon = loadIcon("/icons/leaf_logo.png", 60, 60);
        JLabel logoLabel = new JLabel();
        if (logoIcon != null) {
            logoLabel.setIcon(logoIcon);
        }

        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(logoLabel);
        contentPanel.add(Box.createVerticalStrut(15));

        // 4. Add Title
        JLabel title = new JLabel("System Login");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10));

        // 5. Add Description
        JLabel description = new JLabel("<html><center>Welcome to the Admin Management Portal. " +
                "Please sign in to continue.</center></html>");
        description.setFont(FONT_DESCRIPTION);
        description.setForeground(COLOR_TEXT_LIGHT);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(description);
        contentPanel.add(Box.createVerticalStrut(30));


        // 6. Create Input Fields Panel (Label-over-field layout)
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setOpaque(false);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        // --- Username Row ---
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(FONT_LABEL);
        usernameLabel.setForeground(COLOR_TEXT_DARK);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // --- CHANGED ---
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(Box.createVerticalStrut(8));

        usernameField = new RoundedTextField("Enter your username");
        usernameField.setFont(FONT_FIELD);
        usernameField.setIcon(loadIcon("/icons/user_icon.png", 18, 18)); // Update path
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT); // --- CHANGED ---
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // Control height
        fieldsPanel.add(usernameField);
        fieldsPanel.add(Box.createVerticalStrut(15));

        // --- Password Row ---
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(FONT_LABEL);
        passwordLabel.setForeground(COLOR_TEXT_DARK);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // --- CHANGED ---
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(Box.createVerticalStrut(8));

        passwordField = new RoundedPasswordField("Enter your password");
        passwordField.setFont(FONT_FIELD);
        passwordField.setIcon(loadIcon("/icons/pass_icon.png", 18, 18)); // Update path
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT); // --- CHANGED ---
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // Control height
        fieldsPanel.add(passwordField);

        contentPanel.add(fieldsPanel);
        contentPanel.add(Box.createVerticalStrut(30));


        // 7. Add Login Button (Using new RoundedButton)
        loginButton = new RoundedButton("Login");
        loginButton.setFont(FONT_BUTTON);
        loginButton.setBackground(COLOR_BUTTON);
        loginButton.setHoverColor(COLOR_BUTTON_HOVER);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        contentPanel.add(loginButton);
        contentPanel.add(Box.createVerticalStrut(20));

        // 8. Add Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(COLOR_ERROR); // Use new error color
        messageLabel.setFont(FONT_MESSAGE);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(messageLabel);

        // 9. Add the content panel to the shadow card
        loginPanel.add(contentPanel);

        // 10. Add the shadow card to the background
        backgroundPanel.add(loginPanel);
    }


    // --- Public Getter Methods (Unchanged) ---

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void setErrorMessage(String message) {
        messageLabel.setText("<html><center>" + message + "</center></html>"); // Allow multi-line errors
    }
}