package ui.screen.panels;

import ui.screen.Dashboard;
import ui.screen.components.AppColors;
import ui.screen.components.AppFonts;
import ui.screen.components.RoundedButton;
import ui.screen.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Arrays;

public class SettingsPanel extends JPanel {


    private RoundedButton logoutButton;


    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBackground(AppColors.COLOR_BACKGROUND);

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(AppColors.COLOR_BACKGROUND);
        mainContentPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        mainContentPanel.add(createHeader());
        mainContentPanel.add(Box.createVerticalStrut(20));

        mainContentPanel.add(createProfileSection());
        mainContentPanel.add(Box.createVerticalStrut(30));

        mainContentPanel.add(createGeneralSettingsSection());

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

        Insets buttonPadding = new Insets(4, 18, 4, 18);
       logoutButton = new RoundedButton("Logout", null, AppColors.COLOR_DANGER, AppColors.COLOR_DANGER_HOVER);

        logoutButton.setForeground(AppColors.COLOR_WHITE);
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
        textField.setBackground(AppColors.COLOR_BACKGROUND); // no gray
        textField.setDisabledTextColor(AppColors.COLOR_TEXT_DARK);

        contentPanel.add(label);
        contentPanel.add(hintLabel);
        contentPanel.add(Box.createVerticalStrut(4));
        contentPanel.add(textField);

        JPanel buttonContainer = new JPanel(new CardLayout());
        buttonContainer.setOpaque(false);
        final String EDIT_CARD = "EDIT";
        final String UPDATE_CARD = "UPDATE";
        final String[] originalValue = new String[1];

        Insets smallBtnPadding = new Insets(4, 14, 4, 14);

        RoundedButton editButton = new RoundedButton("Edit", null,
                AppColors.COLOR_SIDEBAR, AppColors.COLOR_BORDER);
        editButton.setForeground(AppColors.COLOR_TEXT_DARK);
        editButton.setFont(AppFonts.FONT_BOLD);

        RoundedButton updateButton = new RoundedButton("Update", null,
                AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        updateButton.setForeground(AppColors.COLOR_WHITE);
        updateButton.setFont(AppFonts.FONT_BOLD);

        RoundedButton cancelButton = new RoundedButton("Cancel", null,
                AppColors.COLOR_SIDEBAR, AppColors.COLOR_BORDER);
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
            textField.setBackground(AppColors.COLOR_BACKGROUND);
        });

        cancelButton.addActionListener(e -> {
            textField.setText(originalValue[0]);
            textField.setEditable(false);
            hintLabel.setVisible(false);
            cardLayout.show(buttonContainer, EDIT_CARD);
            textField.setBorder(nonEditableBorder);
            textField.setBackground(AppColors.COLOR_BACKGROUND);
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

        Insets btnPadding = new Insets(4, 14, 4, 14);
        RoundedButton changeButton = new RoundedButton("Change", null,
                AppColors.COLOR_PRIMARY_ACCENT_LIGHT, AppColors.COLOR_PRIMARY_ACCENT);
        changeButton.setForeground(AppColors.COLOR_PRIMARY_ACCENT);
        changeButton.setFont(AppFonts.FONT_BOLD);
        changeButton.addActionListener(e -> showPasswordChangeDialog());

        panel.add(label, BorderLayout.CENTER);
        panel.add(changeButton, BorderLayout.EAST);
        return panel;
    }

    private void showPasswordChangeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Change Password", true);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(AppColors.COLOR_PRIMARY_ACCENT, 2));

        RoundedPanel container = new RoundedPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(AppColors.COLOR_BACKGROUND);
        container.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Change Password");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_PRIMARY_ACCENT);
        title.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setOpaque(false);

        JLabel oldLbl = new JLabel("Old Password:");
        JLabel newLbl = new JLabel("New Password:");
        JLabel confirmLbl = new JLabel("Confirm New Password:");
        for (JLabel lbl : new JLabel[]{oldLbl, newLbl, confirmLbl}) {
            lbl.setFont(AppFonts.FONT_MAIN);
            lbl.setForeground(AppColors.COLOR_TEXT_DARK);
        }

        JPasswordField oldPass = new JPasswordField();
        JPasswordField newPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();

        form.add(oldLbl);
        form.add(oldPass);
        form.add(newLbl);
        form.add(newPass);
        form.add(confirmLbl);
        form.add(confirmPass);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);

        Insets smallPadding = new Insets(4, 16, 4, 16);
        RoundedButton cancel = new RoundedButton("Cancel", null, AppColors.COLOR_SIDEBAR, AppColors.COLOR_BORDER);
        cancel.setForeground(AppColors.COLOR_TEXT_DARK);

        RoundedButton save = new RoundedButton("Save", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        save.setForeground(AppColors.COLOR_WHITE);

        buttons.add(cancel);
        buttons.add(save);

        container.add(title, BorderLayout.NORTH);
        container.add(form, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        dialog.add(container);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        cancel.addActionListener(e -> dialog.dispose());

        save.addActionListener(e -> {
            char[] newPassword = newPass.getPassword();
            char[] confirmedPassword = confirmPass.getPassword();

            if (newPassword.length == 0) {
                JOptionPane.showMessageDialog(dialog, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Arrays.equals(newPassword, confirmedPassword)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }

            Arrays.fill(oldPass.getPassword(), '0');
            Arrays.fill(newPass.getPassword(), '0');
            Arrays.fill(confirmPass.getPassword(), '0');
        });

        dialog.setVisible(true);
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


    public RoundedButton getLogoutButton() {
        return logoutButton;
    }
}
