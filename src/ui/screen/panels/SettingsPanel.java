package ui.screen.panels;

import ui.screen.components.AppColors;
import ui.screen.components.AppFonts;
import ui.screen.components.RoundedButton;
import ui.screen.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBackground(AppColors.COLOR_BACKGROUND);

        // --- MAIN CONTENT PANEL ---
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(AppColors.COLOR_BACKGROUND);
        mainContentPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- HEADER ---
        mainContentPanel.add(createHeader());
        mainContentPanel.add(Box.createVerticalStrut(20));

        // --- PROFILE SECTION ---
        mainContentPanel.add(createProfileSection());
        mainContentPanel.add(Box.createVerticalStrut(30));

        // --- GENERAL SETTINGS SECTION ---
        mainContentPanel.add(createGeneralSettingsSection());

        // --- SCROLL PANE ---
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Settings");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_TEXT_DARK);

        Insets logoutButtonPadding = new Insets(8, 25, 8, 25);
        RoundedButton logoutButton = new RoundedButton("Logout", null, AppColors.COLOR_DANGER_LIGHT, AppColors.COLOR_DANGER_HOVER);
        logoutButton.setForeground(AppColors.COLOR_DANGER);
        logoutButton.setFont(AppFonts.FONT_BOLD);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createProfileSection() {
        RoundedPanel profilePanel = new RoundedPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        profilePanel.add(createEditableField("Username", "johndoe_official", "This is your unique username for logging in."));
        profilePanel.add(new JSeparator());
        profilePanel.add(createEditableField("Full Name", "John Doe", "Your name as it will appear to others."));
        profilePanel.add(new JSeparator());
        profilePanel.add(createEditableField("Role", "Student", "Your current role within the system."));
        profilePanel.add(new JSeparator());
        profilePanel.add(createEditableField("Phone Number", "+1 123 456 7890", "Your contact number, kept private."));
        profilePanel.add(new JSeparator());
        profilePanel.add(createStaticField("Hostel", "Alpha House"));
        profilePanel.add(new JSeparator());
        profilePanel.add(createPasswordChangeField());

        return profilePanel;
    }

    private JPanel createEditableField(String labelText, String valueText, String hintText) {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 10, 15, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setFont(AppFonts.FONT_BOLD);
        label.setForeground(AppColors.COLOR_TEXT_DARK);

        JLabel hintLabel = new JLabel(hintText);
        hintLabel.setFont(AppFonts.FONT_MAIN.deriveFont(12f));
        hintLabel.setForeground(AppColors.COLOR_TEXT_LIGHT);
        hintLabel.setVisible(false);

        JTextField textField = new JTextField(valueText);
        textField.setFont(AppFonts.FONT_MAIN);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        textField.setBackground(panel.getBackground());

        contentPanel.add(label);
        contentPanel.add(hintLabel);
        contentPanel.add(Box.createVerticalStrut(4));
        contentPanel.add(textField);

        JPanel buttonContainer = new JPanel(new CardLayout());
        buttonContainer.setOpaque(false);
        final String EDIT_CARD = "EDIT";
        final String UPDATE_CARD = "UPDATE";
        final String[] originalValue = new String[1]; // Store original value for cancellation

        // --- Use new constructor for custom padding (less height, more width) ---
        Insets actionButtonPadding = new Insets(4, 18, 4, 18); // Reduced height
        RoundedButton editButton = new RoundedButton("Edit", null, AppColors.COLOR_SIDEBAR, AppColors.COLOR_BORDER);
        editButton.setForeground(AppColors.COLOR_TEXT_DARK);
        editButton.setFont(AppFonts.FONT_BOLD);

        RoundedButton updateButton = new RoundedButton("Update", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        updateButton.setForeground(AppColors.COLOR_WHITE);
        updateButton.setFont(AppFonts.FONT_BOLD);

        RoundedButton cancelButton = new RoundedButton("Cancel", null, AppColors.COLOR_SIDEBAR, AppColors.COLOR_BORDER);
        cancelButton.setForeground(AppColors.COLOR_TEXT_DARK);
        cancelButton.setFont(AppFonts.FONT_BOLD);

        JPanel editModeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        editModeButtonsPanel.setOpaque(false);
        editModeButtonsPanel.add(cancelButton);
        editModeButtonsPanel.add(updateButton);

        buttonContainer.add(editButton, EDIT_CARD);
        buttonContainer.add(editModeButtonsPanel, UPDATE_CARD);

        CardLayout cardLayout = (CardLayout) buttonContainer.getLayout();

        Border editableBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.COLOR_PRIMARY_ACCENT);
        Border nonEditableBorder = BorderFactory.createEmptyBorder(2, 0, 2, 0);

        editButton.addActionListener(e -> {
            originalValue[0] = textField.getText();
            textField.setEditable(true);
            hintLabel.setVisible(true);
            cardLayout.show(buttonContainer, UPDATE_CARD);
            textField.setBorder(editableBorder);
            textField.setBackground(AppColors.COLOR_WHITE);
            textField.requestFocusInWindow();
        });

        updateButton.addActionListener(e -> {
            textField.setEditable(false);
            hintLabel.setVisible(false);
            cardLayout.show(buttonContainer, EDIT_CARD);
            textField.setBorder(nonEditableBorder);
            textField.setBackground(panel.getBackground());
        });

        cancelButton.addActionListener(e -> {
            textField.setText(originalValue[0]);
            textField.setEditable(false);
            hintLabel.setVisible(false);
            cardLayout.show(buttonContainer, EDIT_CARD);
            textField.setBorder(nonEditableBorder);
            textField.setBackground(panel.getBackground());
        });

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonContainer, BorderLayout.EAST);

        return panel;
    }

    private JPanel createStaticField(String labelText, String valueText) {
        JPanel panel = createEditableField(labelText, valueText, "");
        panel.remove(panel.getComponentCount() - 1);
        return panel;
    }

    private JPanel createPasswordChangeField() {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel label = new JLabel("Password");
        label.setFont(AppFonts.FONT_BOLD);
        label.setForeground(AppColors.COLOR_TEXT_DARK);

        Insets changeButtonPadding = new Insets(6, 22, 6, 22);
        RoundedButton changeButton = new RoundedButton("Change", null, AppColors.COLOR_PRIMARY_ACCENT_LIGHT, AppColors.COLOR_PRIMARY_ACCENT);
        changeButton.setForeground(AppColors.COLOR_PRIMARY_ACCENT);
        changeButton.setFont(AppFonts.FONT_BOLD);
        changeButton.addActionListener(e -> showPasswordChangeDialog());

        panel.add(label, BorderLayout.CENTER);
        panel.add(changeButton, BorderLayout.EAST);
        return panel;
    }

    private void showPasswordChangeDialog() {
        // (Implementation remains the same)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Old Password:"), gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("Confirm New Password:"), gbc);

        JPasswordField oldPass = new JPasswordField(15);
        JPasswordField newPass = new JPasswordField(15);
        JPasswordField confirmPass = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        panel.add(oldPass, gbc);
        gbc.gridy = 1;
        panel.add(newPass, gbc);
        gbc.gridy = 2;
        panel.add(confirmPass, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Change Password",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            char[] newPassword = newPass.getPassword();
            char[] confirmedPassword = confirmPass.getPassword();

            if (newPassword.length == 0) {
                JOptionPane.showMessageDialog(this, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Arrays.equals(newPassword, confirmedPassword)) {
                JOptionPane.showMessageDialog(this, "The new passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            Arrays.fill(oldPass.getPassword(), '0');
            Arrays.fill(newPass.getPassword(), '0');
            Arrays.fill(confirmPass.getPassword(), '0');
        }
    }

    private JPanel createGeneralSettingsSection() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setOpaque(false);
        settingsPanel.setLayout(new GridLayout(1, 2, 20, 20));
        settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        settingsPanel.add(createThemeOptions());
        settingsPanel.add(createNotificationOptions());

        return settingsPanel;
    }

    private JPanel createThemeOptions() {
        RoundedPanel themePanel = new RoundedPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(), " Theme ",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        AppFonts.FONT_BOLD.deriveFont(16f), AppColors.COLOR_PRIMARY_ACCENT
                ),
                new EmptyBorder(0, 5, 10, 5)
        ));

        JRadioButton light = new JRadioButton("Light Theme", true);
        light.setOpaque(false);
        light.setFont(AppFonts.FONT_MAIN);
        light.setForeground(AppColors.COLOR_TEXT_DARK);

        JRadioButton dark = new JRadioButton("Dark Theme");
        dark.setOpaque(false);
        dark.setFont(AppFonts.FONT_MAIN);
        dark.setForeground(AppColors.COLOR_TEXT_DARK);

        ButtonGroup group = new ButtonGroup();
        group.add(light);
        group.add(dark);

        themePanel.add(light);
        themePanel.add(Box.createVerticalStrut(5));
        themePanel.add(dark);

        return themePanel;
    }

    private JPanel createNotificationOptions() {
        RoundedPanel notificationPanel = new RoundedPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        notificationPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(), " Notifications ",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        AppFonts.FONT_BOLD.deriveFont(16f), AppColors.COLOR_PRIMARY_ACCENT
                ),
                new EmptyBorder(0, 5, 10, 5)
        ));

        JCheckBox emailCheck = new JCheckBox("Get changes and notifications via email");
        emailCheck.setOpaque(false);
        emailCheck.setFont(AppFonts.FONT_MAIN);
        emailCheck.setForeground(AppColors.COLOR_TEXT_DARK);

        JCheckBox inAppCheck = new JCheckBox("Show in-app notifications", true);
        inAppCheck.setOpaque(false);
        inAppCheck.setFont(AppFonts.FONT_MAIN);
        inAppCheck.setForeground(AppColors.COLOR_TEXT_DARK);

        notificationPanel.add(emailCheck);
        notificationPanel.add(Box.createVerticalStrut(5));
        notificationPanel.add(inAppCheck);
        return notificationPanel;

    }


}