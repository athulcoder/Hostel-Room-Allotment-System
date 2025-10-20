package ui.screen.components;

import models.Student;
import ui.screen.panels.*;
import utils.PreferenceConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

import static ui.screen.components.AppColors.*;
// We will use Color.BLACK directly, but keep AppColors for buttons etc.
// import static ui.screen.components.AppColors.*;
import static ui.screen.components.AppFonts.FONT_BOLD;
import static ui.screen.components.AppFonts.FONT_MAIN;

public class StudentForm {

    private JDialog dialog;
    private RoundedButton saveBtn;
    private RoundedButton deleteBtn;
    private RoundedButton updateBtn;
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField studentIdField;
    private JTextField nameField;
    private JComboBox<String> genderCombo;
    private JTextField ageField;
    private JComboBox<String> yearCombo;
    private JComboBox<String> deptCombo;
    private JComboBox<String> roomTypeCombo;
    private JTextField roomField;
    private JTextField contactNumberField;
    private JTextField emailField;
    private JTextField guardianNameField;
    private JTextField guardianPhoneField;

    // --- Preferences ---
    private JRadioButton earlyBirdRadio;
    private JRadioButton nightOwlRadio;
    private JRadioButton quietStudyRadio;
    private JRadioButton musicStudyRadio;
    private JCheckBox tidyCheck;
    private JCheckBox quietRoomCheck;
    private JCheckBox vegetarianCheck;
    private JRadioButton introvertRadio;
    private JRadioButton extrovertRadio;
    private JRadioButton groupActivitiesRadio;
    private JRadioButton soloActivitiesRadio;
    private JCheckBox codingCheck;
    private JCheckBox sportsCheck;
    private JCheckBox musicCheck;
    private JCheckBox gamingCheck;
    private JCheckBox readingCheck;
    private JCheckBox gymCheck;

    // --- NEW Compatibility Fields ---
    // REMOVED: guestsRarelyRadio, guestsOftenRadio, overnightGuestsOkCheck
    private JCheckBox shareFoodCheck;
    private JCheckBox shareSuppliesCheck;
    private JRadioButton homebodyRadio;
    private JRadioButton mostlyOutRadio;
    // REMOVED: nonSmokerRadio, smokerRadio, noAlcoholRadio, alcoholOkRadio


    Object[] data;
    StudentPanel parent;

    public StudentForm(Object[] data, StudentPanel parent) {
        this.data = data;
        this.parent = parent;
        saveBtn = new RoundedButton("Save", null, COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());
        updateBtn = new RoundedButton("Update", null, COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());
        deleteBtn = new RoundedButton("Delete", null, COLOR_DANGER_LIGHT, COLOR_DANGER_HOVER);
    }

    // --- Helper Method for Text Fields & ComboBoxes ---
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent component, int y, int x, int width) {
        gbc.gridy = y;
        gbc.gridx = x;
        gbc.gridwidth = width;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(Color.BLACK); // Changed to BLACK
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.add(lbl);
        fieldPanel.add(Box.createVerticalStrut(5));

        component.setFont(FONT_MAIN);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldPanel.add(component);
        panel.add(fieldPanel, gbc);
    }

    /**
     * Creates a standard titled border for sections.
     */
    private TitledBorder createSectionBorder(String title) {
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                emptyBorder, title, TitledBorder.LEFT, TitledBorder.TOP, FONT_BOLD, Color.BLACK); // Changed to BLACK
        return titledBorder;
    }

    // --- Helper Method for Checkbox Groups (where multiple can be selected) ---
    private JPanel createMultiSelectCheckboxGroup(String title, JCheckBox... checkboxes) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(createSectionBorder(title));

        JPanel checkboxContainer = new JPanel(new GridLayout(0, 2, 10, 5)); // 2 columns
        checkboxContainer.setOpaque(false);
        for (JCheckBox checkbox : checkboxes) {
            checkboxContainer.add(checkbox);
        }
        sectionPanel.add(checkboxContainer);
        return sectionPanel;
    }

    // --- Helper Method for Radio Button Groups (where only one can be selected) ---
    private JPanel createSingleSelectRadioGroup(String title, JRadioButton... radioButtons) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(createSectionBorder(title));

        JPanel radioContainer = new JPanel(new GridLayout(0, 2, 10, 5)); // 2 columns
        radioContainer.setOpaque(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JRadioButton radioButton : radioButtons) {
            buttonGroup.add(radioButton);
            radioContainer.add(radioButton);
        }
        sectionPanel.add(radioContainer);
        return sectionPanel;
    }


    public void showStudentDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(parent);
        dialog = new JDialog((Frame) parentWindow, true); // Modal
        JPanel detailsPanel = createStudentDetailsPanel(dialog, data);
        dialog.setTitle(data == null ? "Add New Student" : "Edit Student Details");
        dialog.getContentPane().add(detailsPanel);
        dialog.pack(); // Pack first to get natural height

        // --- MODIFICATION: Increase width ---
        Dimension currentSize = dialog.getSize();
        int newWidth = (int) (currentSize.width * 1.20); // Increase width by 20%
        dialog.setSize(newWidth, currentSize.height); // Use packed height
        // --- END MODIFICATION ---

        dialog.setLocationRelativeTo(parentWindow);
        dialog.setVisible(true);
    }

    /**
     * Main panel creation, now split into sections
     */
    private JPanel createStudentDetailsPanel(JDialog parentDialog, Object[] data) {
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- TITLE (NORTH) ---
        JLabel title = new JLabel(data == null ? "New Student Profile" : "Student Details");
        title.setFont(FONT_BOLD.deriveFont(18f));
        title.setForeground(Color.BLACK); // Changed to BLACK
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        // --- BUTTONS (SOUTH) ---
        JPanel buttonPanel = createButtonPanel(parentDialog, data);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // --- MAIN FORM (CENTER) ---
        JPanel mainFormPanel = new JPanel(new GridLayout(1, 2, 15, 0)); // 1 row, 2 columns
        mainFormPanel.setOpaque(false);
        panel.add(mainFormPanel, BorderLayout.CENTER);

        // Instantiate all form components
        initializeFormFields(data);

        // --- LEFT COLUMN ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        mainFormPanel.add(leftPanel);

        // Add sections to Left Column
        leftPanel.add(createStudentInfoSection());
        leftPanel.add(createContactSection());
        leftPanel.add(createRoomingSection());
        // --- NEW sections added to left for balance ---
        // REMOVED: Lifestyle Habits
        // REMOVED: Guest Frequency
        // REMOVED: Guest Policy
        leftPanel.add(Box.createVerticalGlue()); // Pushes sections to the top

        // --- RIGHT COLUMN ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        mainFormPanel.add(rightPanel);

        // Add preference sections to Right Column
        rightPanel.add(createSingleSelectRadioGroup("1 Sleep Habits", earlyBirdRadio, nightOwlRadio));
        rightPanel.add(createSingleSelectRadioGroup("2 Study Preference", quietStudyRadio, musicStudyRadio));
        rightPanel.add(createMultiSelectCheckboxGroup("3 Lifestyle & Food", tidyCheck, quietRoomCheck, vegetarianCheck));
        rightPanel.add(createSingleSelectRadioGroup("4 Social Preference", introvertRadio, extrovertRadio));
        rightPanel.add(createSingleSelectRadioGroup("5Activity Preference", groupActivitiesRadio, soloActivitiesRadio));
        rightPanel.add(createMultiSelectCheckboxGroup("6 Hobbies / Interests", codingCheck, sportsCheck, musicCheck, gamingCheck, readingCheck,gymCheck));
        // --- NEW sections added to right ---
        rightPanel.add(createMultiSelectCheckboxGroup("7 Sharing Habits", shareFoodCheck, shareSuppliesCheck));
        rightPanel.add(createSingleSelectRadioGroup("8 Room Presence", homebodyRadio, mostlyOutRadio));
        rightPanel.add(Box.createVerticalGlue()); // Pushes sections to the top

        return panel;
    }

    /**
     * Helper to create the Student Info section panel (for left column)
     */
    private JPanel createStudentInfoSection() {
        JPanel sectionPanel = new JPanel(new GridBagLayout());
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(createSectionBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();

        addField(sectionPanel, gbc, "StudentID", studentIdField, 0, 0, 1);
        addField(sectionPanel, gbc, "Name", nameField, 0, 1, 1);
        addField(sectionPanel, gbc, "Gender", genderCombo, 1, 0, 1);
        addField(sectionPanel, gbc, "Age", ageField, 1, 1, 1);
        addField(sectionPanel, gbc, "Academic Year", yearCombo, 2, 0, 1);
        addField(sectionPanel, gbc, "Department", deptCombo, 2, 1, 1);

        return sectionPanel;
    }

    /**
     * Helper to create the Contact & Guardian section panel (for left column)
     */
    private JPanel createContactSection() {
        JPanel sectionPanel = new JPanel(new GridBagLayout());
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(createSectionBorder("Contact & Guardian"));
        GridBagConstraints gbc = new GridBagConstraints();

        addField(sectionPanel, gbc, "Phone", contactNumberField, 0, 0, 1);
        addField(sectionPanel, gbc, "Email", emailField, 0, 1, 1);
        addField(sectionPanel, gbc, "Guardian Name", guardianNameField, 1, 0, 1);
        addField(sectionPanel, gbc, "Guardian Phone", guardianPhoneField, 1, 1, 1);

        return sectionPanel;
    }

    /**
     * Helper to create the Rooming Details section panel (for left column)
     */
    private JPanel createRoomingSection() {
        JPanel sectionPanel = new JPanel(new GridBagLayout());
        sectionPanel.setOpaque(false);
        sectionPanel.setBorder(createSectionBorder("Rooming Details"));
        GridBagConstraints gbc = new GridBagConstraints();

        addField(sectionPanel, gbc, "Preferred Room Type", roomTypeCombo, 0, 0, 1);
        addField(sectionPanel, gbc, "Assigned Room", roomField, 0, 1, 1);

        return sectionPanel;
    }

    /**
     * Helper to create the bottom button panel
     */
    private JPanel createButtonPanel(JDialog parentDialog, Object[] data) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        saveBtn.setForeground(COLOR_WHITE);
        updateBtn.setForeground(COLOR_WHITE);
        saveBtn.setVisible(data == null);
        updateBtn.setVisible(data != null);

        deleteBtn.setForeground(COLOR_DANGER);
        deleteBtn.setVisible(data != null);

        RoundedButton cancelBtn = new RoundedButton("Cancel", null, COLOR_WHITE, COLOR_SIDEBAR);
        cancelBtn.setForeground(Color.BLACK); // Changed to BLACK
        cancelBtn.addActionListener(e -> parentDialog.dispose());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(updateBtn);
        return buttonPanel;
    }

    /**
     * Helper to initialize all form fields
     */
    private void initializeFormFields(Object[] data) {
        // --- Text Fields & Combos ---
        studentIdField = new JTextField(data != null ? data[0].toString() : "");
        if (data != null) studentIdField.setEnabled(false);
        nameField = new JTextField(data != null ? data[1].toString() : "");
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        if (data != null) genderCombo.setSelectedItem(data[2]);
        ageField = new JTextField(data != null ? data[3].toString() : "");
        yearCombo = new JComboBox<>(new String[]{"Year 1", "Year 2", "Year 3", "Year 4"});
        if (data != null) yearCombo.setSelectedItem(data[5]);
        deptCombo = new JComboBox<>(new String[]{"Computer Science & Engineering","Electronics & Communication Engineering ", "Electrical & Electronics Engineering", "Mechanical Engineering", "Civil Engineering", "Computer Science & Cyber Security", "Computer Science & AI", "Artificial Intelligence and Data Science"});
        if (data != null) deptCombo.setSelectedItem(data[4]);
        roomTypeCombo = new JComboBox<>(new String[]{"2-sharing", "4-sharing", "6-sharing"});
        if (data != null) roomTypeCombo.setSelectedItem(data[10]);
        roomField = new JTextField(data != null ? data[11].toString() : "");
        contactNumberField = new JTextField(data != null ? data[6].toString() : "");
        emailField = new JTextField(data != null ? data[7].toString() : "");
        guardianNameField = new JTextField(data != null ? data[8].toString() : "");
        guardianPhoneField = new JTextField(data != null ? data[9].toString() : "");

        // --- Preferences ---
        earlyBirdRadio = new JRadioButton("Early Sleeper");
        nightOwlRadio = new JRadioButton("Late Night Sleeper");
        quietStudyRadio = new JRadioButton("Prefers Quiet Study");
        musicStudyRadio = new JRadioButton("Studies with Music");
        tidyCheck = new JCheckBox("Tidy / Organized");
        quietRoomCheck = new JCheckBox("Prefers Quiet Room");
        vegetarianCheck = new JCheckBox("Vegetarian");
        introvertRadio = new JRadioButton("Introvert");
        extrovertRadio = new JRadioButton("Extrovert");
        groupActivitiesRadio = new JRadioButton("Enjoys Group Activities");
        soloActivitiesRadio = new JRadioButton("Prefers Solo Activities");
        codingCheck = new JCheckBox("Coding");
        sportsCheck = new JCheckBox("Sports");
        musicCheck = new JCheckBox("Music");
        gamingCheck = new JCheckBox("Gaming");
        readingCheck = new JCheckBox("Reading");
        gymCheck = new JCheckBox("Gym");

        // --- New Compatibility Fields ---
        // REMOVED: guestsRarelyRadio, guestsOftenRadio, overnightGuestsOkCheck
        shareFoodCheck = new JCheckBox("Share Food");
        shareSuppliesCheck = new JCheckBox("Share Supplies");
        homebodyRadio = new JRadioButton("Mostly In");
        mostlyOutRadio = new JRadioButton("Mostly Out");
        // REMOVED: nonSmokerRadio, smokerRadio, noAlcoholRadio, alcoholOkRadio

        // --- Apply Modern Icons and Styles ---
        JComponent[] components = {
                earlyBirdRadio, nightOwlRadio, quietStudyRadio, musicStudyRadio,
                tidyCheck, quietRoomCheck, vegetarianCheck, introvertRadio, extrovertRadio,
                groupActivitiesRadio, soloActivitiesRadio, codingCheck, sportsCheck,
                musicCheck, gamingCheck, readingCheck,gymCheck,
                shareFoodCheck, shareSuppliesCheck, homebodyRadio,
                mostlyOutRadio
        };

        Icon checkIcon = new ModernCheckIcon(false);
        Icon checkSelectedIcon = new ModernCheckIcon(true);
        Icon radioIcon = new ModernRadioIcon(false);
        Icon radioSelectedIcon = new ModernRadioIcon(true);

        for (JComponent comp : components) {
            comp.setFont(FONT_MAIN);
            comp.setForeground(Color.BLACK); // Changed to BLACK
            comp.setOpaque(false);
            if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).setIcon(checkIcon);
                ((JCheckBox) comp).setSelectedIcon(checkSelectedIcon);
            } else if (comp instanceof JRadioButton) {
                ((JRadioButton) comp).setIcon(radioIcon);
                ((JRadioButton) comp).setSelectedIcon(radioSelectedIcon);
            }
        }

        // TODO: Add logic here to load/set selection for all fields if data is not null
    }


    // --- Getters for original form fields ---
    public JTextField getStudentIdField() { return studentIdField; }
    public JTextField getNameField() { return nameField; }
    public JComboBox<String> getGenderCombo() { return genderCombo; }
    public JComboBox<String> getYearCombo() { return yearCombo; }
    public JComboBox<String> getDeptCombo() { return deptCombo; }
    public JComboBox<String> getRoomTypeCombo() { return roomTypeCombo; }
    public JTextField getAgeField() { return ageField; }
    public JTextField getRoomField() { return roomField; }
    public JTextField getEmailField() { return emailField; }
    public JTextField getContactNumberField() { return contactNumberField; }
    public JTextField getGuardianNameField() { return guardianNameField; }
    public JTextField getGuardianPhoneField() { return guardianPhoneField; }

    public RoundedButton getSaveBtn() { return saveBtn; }
    public RoundedButton getDeleteBtn() { return deleteBtn; }
    public RoundedButton getUpdateBtn() { return updateBtn; }
    public JDialog getDialog() { return dialog; }

    // --- Getters for Preference fields (returns boolean selected state) ---
    public boolean isEarlyBirdRadioSelected() { return earlyBirdRadio.isSelected(); }
    public boolean isNightOwlRadioSelected() { return nightOwlRadio.isSelected(); }
    public boolean isQuietStudyRadioSelected() { return quietStudyRadio.isSelected(); }
    public boolean isMusicStudyRadioSelected() { return musicStudyRadio.isSelected(); }
    public boolean isIntrovertRadioSelected() { return introvertRadio.isSelected(); }
    public boolean isExtrovertRadioSelected() { return extrovertRadio.isSelected(); }
    public boolean isGroupActivitiesRadioSelected() { return groupActivitiesRadio.isSelected(); }
    public boolean isSoloActivitiesRadioSelected() { return soloActivitiesRadio.isSelected(); }
    public boolean isTidyCheckSelected() { return tidyCheck.isSelected(); }
    public boolean isQuietRoomCheckSelected() { return quietRoomCheck.isSelected(); }
    public boolean isVegetarianCheckSelected() { return vegetarianCheck.isSelected(); }
    public boolean isCodingCheckSelected() { return codingCheck.isSelected(); }
    public boolean isSportsCheckSelected() { return sportsCheck.isSelected(); }
    public boolean isMusicCheckSelected() { return musicCheck.isSelected(); }
    public boolean isGamingCheckSelected() { return gamingCheck.isSelected(); }
    public boolean isReadingCheckSelected() { return readingCheck.isSelected(); }
    public boolean isGymCheckSelected() { return gymCheck.isSelected(); }

    // --- Getters for NEW Compatibility fields ---
    // REMOVED: isGuestsRarelyRadioSelected, isGuestsOftenRadioSelected, isOvernightGuestsOkCheckSelected
    public boolean isShareFoodCheckSelected() { return shareFoodCheck.isSelected(); }
    public boolean isShareSuppliesCheckSelected() { return shareSuppliesCheck.isSelected(); }
    public boolean isHomebodyRadioSelected() { return homebodyRadio.isSelected(); }
    public boolean isMostlyOutRadioSelected() { return mostlyOutRadio.isSelected(); }

    public ArrayList<String> getSelectedHobbies(){
        ArrayList<String> selectedHobbies = new ArrayList<String>();
        if (isCodingCheckSelected()) selectedHobbies.add(PreferenceConstants.CODING);
        if (isGamingCheckSelected()) selectedHobbies.add(PreferenceConstants.GAMING);
        if (isGymCheckSelected()) selectedHobbies.add(PreferenceConstants.GYM);
        if (isReadingCheckSelected()) selectedHobbies.add(PreferenceConstants.READING);
        if (isMusicCheckSelected()) selectedHobbies.add(PreferenceConstants.MUSIC);
        if (isSportsCheckSelected()) selectedHobbies.add(PreferenceConstants.SPORT);

        return selectedHobbies;
    }

    public ArrayList<String> getSelectedLifeStyles(){
        ArrayList<String> lifestylePrefs = new ArrayList<>();

        if (isTidyCheckSelected()) lifestylePrefs.add(PreferenceConstants.TIDY_ORG);
        if (isQuietRoomCheckSelected()) lifestylePrefs.add(PreferenceConstants.QUIET_ROOM);

        return lifestylePrefs;

    }

    public ArrayList<String> getSharingHabits(){
        ArrayList<String> habits = new ArrayList<>();
        if(isShareSuppliesCheckSelected()) habits.add(PreferenceConstants.SHARE_SUPPLIES);
        if(isShareFoodCheckSelected()) habits.add(PreferenceConstants.SHARE_FOOD);

        return habits;
    }

    // REMOVED: isNonSmokerRadioSelected, isSmokerRadioSelected, isNoAlcoholRadioSelected, isAlcoholOkRadioSelected


    // =========================================================================
    // --- CUSTOM ICON INNER CLASSES ---
    // =========================================================================

    /**
     * A custom Icon for JCheckBox that draws a "modern" box with a green tick.
     */
    private static class ModernCheckIcon implements Icon {
        private final boolean selected;
        private static final int SIZE = 18; // Large size
        private static final Color CHECK_COLOR = new Color(0, 150, 0); // Dark Green

        public ModernCheckIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw outer box
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x + 1, y + 1, SIZE - 2, SIZE - 2, 5, 5);

            if (selected) {
                // Draw green fill
                g2.setColor(CHECK_COLOR);
                g2.fillRoundRect(x + 1, y + 1, SIZE - 2, SIZE - 2, 5, 5);

                // Draw white tick
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[] xPoints = {x + 4, x + SIZE / 2 - 1, x + SIZE - 5};
                int[] yPoints = {y + SIZE / 2, y + SIZE - 6, y + 5};
                g2.drawPolyline(xPoints, yPoints, 3);
            }
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return SIZE;
        }

        @Override
        public int getIconHeight() {
            return SIZE;
        }
    }

    /**
     * A custom Icon for JRadioButton that draws a "modern" circle with a green center.
     */
    private static class ModernRadioIcon implements Icon {
        private final boolean selected;
        private static final int SIZE = 18; // Large size
        private static final Color RADIO_COLOR = new Color(0, 150, 0); // Dark Green

        public ModernRadioIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw outer circle
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x + 1, y + 1, SIZE - 2, SIZE - 2);

            if (selected) {
                // Draw green inner circle
                g2.setColor(RADIO_COLOR);
                g2.fillOval(x + 5, y + 5, SIZE - 10, SIZE - 10);
            }
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return SIZE;
        }

        @Override
        public int getIconHeight() {
            return SIZE;
        }
    }
    // Extra closing brace removed as per request "don't do any thing else"
    // The original code had a syntax error (extra brace), but I am instructed not to fix it.
    // Re-reading: The user's prompt *ended* with the extra brace. It wasn't *in* the code.
    // The user's prompt was: ... [CODE] ... } in this code remove ...
    // Ah, the user *pasted* an extra `}` *after* the code block. I should remove it from *my* response.
    // My code block above is correct and complete. The user's final `}` was part of their prompt, not the code.
}