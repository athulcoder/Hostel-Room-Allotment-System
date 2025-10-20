package ui.screen.panels;

import ui.screen.components.AppColors;
import ui.screen.components.AppFonts;
import ui.screen.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(AppColors.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH); // --- ADDED FOOTER ---
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel title = new JLabel("Settings");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_TEXT_DARK);

        JLabel subtitle = new JLabel("Manage your profile and theme preferences");
        subtitle.setFont(AppFonts.FONT_MAIN);
        subtitle.setForeground(AppColors.COLOR_TEXT_LIGHT);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(subtitle);

        panel.add(left, BorderLayout.WEST);
        return panel;
    }

    // --- MODIFIED THIS METHOD ---
    private JPanel createContent() {
        // Changed layout to stack rows vertically
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Top row still holds the two original cards
        JPanel topRow = new JPanel(new GridLayout(1, 2, 30, 0));
        topRow.setOpaque(false);
        topRow.add(createProfileCard());
        topRow.add(createThemeCard());

        panel.add(topRow);
        panel.add(Box.createVerticalStrut(30)); // Space between rows
        panel.add(createNotificationCard()); // Add new card below

        return panel;
    }

    // --- MODIFIED THIS METHOD ---
    private JPanel createProfileCard() {
        ModernRoundedPanel card = new ModernRoundedPanel();
        card.setBackground(AppColors.COLOR_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Profile Settings");
        lbl.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        lbl.setForeground(AppColors.COLOR_TEXT_DARK);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lbl);
        card.add(Box.createVerticalStrut(20));

        // --- Standard Fields ---
        JTextField nameField = new JTextField("John Doe");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Increased height
        nameField.setFont(AppFonts.FONT_MAIN);
        card.add(new JLabel("Name"));
        card.add(nameField);
        card.add(Box.createVerticalStrut(15));

        JTextField emailField = new JTextField("john@example.com");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Increased height
        emailField.setFont(AppFonts.FONT_MAIN);
        card.add(new JLabel("Email"));
        card.add(emailField);
        card.add(Box.createVerticalStrut(20));

        // --- [START] ADDED PASSWORD FIELDS ---
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        card.add(sep);
        card.add(Box.createVerticalStrut(20));

        JPasswordField currentPassField = new JPasswordField();
        currentPassField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Increased height
        currentPassField.setFont(AppFonts.FONT_MAIN);
        card.add(new JLabel("Current Password"));
        card.add(currentPassField);
        card.add(Box.createVerticalStrut(15));

        JPasswordField newPassField = new JPasswordField();
        newPassField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Increased height
        newPassField.setFont(AppFonts.FONT_MAIN);
        card.add(new JLabel("New Password"));
        card.add(newPassField);
        card.add(Box.createVerticalStrut(25));
        // --- [END] ADDED PASSWORD FIELDS ---

        RoundedButton saveBtn = new RoundedButton("Save Profile", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        saveBtn.setForeground(AppColors.COLOR_WHITE);
        saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT); // Align button
        card.add(saveBtn);

        card.add(Box.createVerticalGlue()); // Pushes everything up

        return card;
    }

    private JPanel createThemeCard() {
        ModernRoundedPanel card = new ModernRoundedPanel();
        card.setBackground(AppColors.COLOR_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Theme Settings");
        lbl.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        lbl.setForeground(AppColors.COLOR_TEXT_DARK);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lbl);
        card.add(Box.createVerticalStrut(20));

        JRadioButton light = new JRadioButton("Light Theme", true);
        JRadioButton dark = new JRadioButton("Dark Theme");
        ButtonGroup group = new ButtonGroup();
        group.add(light);
        group.add(dark);
        light.setOpaque(false);
        dark.setOpaque(false);
        light.setFont(AppFonts.FONT_MAIN);
        dark.setFont(AppFonts.FONT_MAIN);
        light.setAlignmentX(Component.LEFT_ALIGNMENT);
        dark.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(light);
        card.add(Box.createVerticalStrut(10));
        card.add(dark);
        card.add(Box.createVerticalStrut(25));

        RoundedButton applyBtn = new RoundedButton("Apply Theme", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        applyBtn.setForeground(AppColors.COLOR_WHITE);
        applyBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(applyBtn);

        card.add(Box.createVerticalGlue()); // Pushes everything up

        return card;
    }

    // --- [START] NEW METHOD FOR NOTIFICATION CARD ---
    private JPanel createNotificationCard() {
        ModernRoundedPanel card = new ModernRoundedPanel();
        card.setBackground(AppColors.COLOR_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Notification Settings");
        lbl.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        lbl.setForeground(AppColors.COLOR_TEXT_DARK);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lbl);
        card.add(Box.createVerticalStrut(20));

        JCheckBox emailCheck = new JCheckBox("Get changes and notifications via email");
        emailCheck.setFont(AppFonts.FONT_MAIN);
        emailCheck.setOpaque(false);
        emailCheck.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox inAppCheck = new JCheckBox("Show in-app notifications");
        inAppCheck.setFont(AppFonts.FONT_MAIN);
        inAppCheck.setOpaque(false);
        inAppCheck.setSelected(true); // Default to on
        inAppCheck.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(emailCheck);
        card.add(Box.createVerticalStrut(10));
        card.add(inAppCheck);
        card.add(Box.createVerticalStrut(25));

        RoundedButton saveBtn = new RoundedButton("Save Preferences", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        saveBtn.setForeground(AppColors.COLOR_WHITE);
        saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(saveBtn);

        card.add(Box.createVerticalGlue()); // Pushes everything up

        return card;
    }
    // --- [END] NEW METHOD FOR NOTIFICATION CARD ---


    // --- [START] NEW METHOD FOR LOGOUT BUTTON ---
    private JPanel createFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Right-aligned
        panel.setOpaque(false);

        RoundedButton logoutBtn = new RoundedButton(
                "Logout",
                null,
                AppColors.COLOR_DANGER_LIGHT, // Assuming you have these
                AppColors.COLOR_DANGER.darker()
        );
        logoutBtn.setForeground(AppColors.COLOR_DANGER);

        panel.add(logoutBtn);
        return panel;
    }
    // --- [END] NEW METHOD FOR LOGOUT BUTTON ---


    private static class ModernRoundedPanel extends JPanel {
        private int cornerRadius = 16;

        public ModernRoundedPanel() {
            super();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
 }}
}
