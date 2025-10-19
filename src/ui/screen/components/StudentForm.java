package ui.screen.components;

import models.Student;
import ui.screen.panels.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.screen.components.AppColors.*;
import static ui.screen.components.AppFonts.FONT_BOLD;
import static ui.screen.components.AppFonts.FONT_MAIN;

public class StudentForm {



    private JDialog dialog;
    private RoundedButton saveBtn;
    private RoundedButton updateBtn;
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField studentIdField;
    private JTextField nameField;
    private JComboBox<String> genderCombo;
    private JTextField ageField;
    private JComboBox<String> yearCombo;
    private JComboBox<String> deptCombo;
    private JComboBox<String> roomTypeCombo;
    private JComboBox<String> sleepTypeCombo;
    private JTextField roomField;
    private JTextField contactNumberField;
    private JTextField emailField;
    private JTextField guardianNameField;
    private JTextField guardianPhoneField;

    Object[] data;
    StudentPanel parent;

    public StudentForm(Object[] data, StudentPanel parent){
            this.data = data;
            this.parent = parent;
            saveBtn = new RoundedButton("Save", null, COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());
            updateBtn = new RoundedButton("Update", null, COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());

    }


    //add field
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent component, int y, int x, int width) {
        gbc.gridy = y;
        gbc.gridx = x;
        gbc.gridwidth = width;
        gbc.weightx = 1.0;

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(COLOR_TEXT_LIGHT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.add(lbl);
        fieldPanel.add(Box.createVerticalStrut(5));

        component.setFont(FONT_MAIN);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.add(component);

        panel.add(fieldPanel, gbc);
    }


    public void showStudentDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(parent);

        dialog = new JDialog((Frame) parentWindow, true); // Modal

        JPanel detailsPanel = createStudentDetailsPanel(dialog, data);

        dialog.setTitle(data == null ? "Add New Student" : "Edit Student Details");
        dialog.getContentPane().add(detailsPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentWindow);
        dialog.setVisible(true);
    }

    private JPanel createStudentDetailsPanel(JDialog parentDialog, Object[] data) {
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel(data == null ? "New Student Profile" : "Student Details");
        title.setFont(FONT_BOLD);
        title.setForeground(COLOR_TEXT_DARK);
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and populate fields
        studentIdField = new JTextField(data!=null?data[0].toString():"");
        if(data!=null)studentIdField.setEnabled(false);
        nameField = new JTextField(data != null ? data[1].toString() : "");
        genderCombo = new JComboBox<>(new String[]{"Female", "Male"});
        if (data != null) genderCombo.setSelectedItem(data[2]);
        ageField = new JTextField(data != null ? data[3].toString() : "");
        yearCombo = new JComboBox<>(new String[]{"Year 1", "Year 2", "Year 3", "Year 4"});
        if (data != null) yearCombo.setSelectedItem(data[5]);
        deptCombo = new JComboBox<>(new String[]{"Computer Science", "Economics", "Mechanical Eng.", "Business Admin", "Biology"});
        if (data != null) deptCombo.setSelectedItem(data[4]);
        roomTypeCombo = new JComboBox<>(new String[]{"2-sharing", "4-sharing", "6-sharing"});
        if (data != null) roomTypeCombo.setSelectedItem(data[10]);
        sleepTypeCombo = new JComboBox<>(new String[]{"Early", "Night"});
        if(data!=null) sleepTypeCombo.setSelectedItem(data[12]);
        roomField = new JTextField(data != null ? data[11].toString() : ""); // Dummy data
        contactNumberField = new JTextField(data != null ? data[6].toString() : "");// Dummy data
        emailField = new JTextField(data != null ? data[7].toString() : "");// Dummy data

        guardianNameField = new JTextField(data != null ?data[8].toString() : ""); // Dummy data
        guardianPhoneField = new JTextField(data != null ? data[9].toString() : ""); // Dummy data



        addField(formPanel, gbc, "StudentID", studentIdField, 0, 0, 2);
        addField(formPanel, gbc, "Name", nameField, 0, 2, 2);
        addField(formPanel, gbc, "Gender", genderCombo, 1, 0, 1);
        addField(formPanel, gbc, "Age", ageField, 1, 1, 2);
        addField(formPanel, gbc, "Academic Year", yearCombo, 1, 3, 1);
        addField(formPanel, gbc, "Department", deptCombo, 2, 0, 2);
        addField(formPanel, gbc, "Preferred Room Type", roomTypeCombo, 2, 2, 2);
        addField(formPanel, gbc, "Sleep Type", sleepTypeCombo, 3, 0, 1);
        addField(formPanel, gbc, "Room", roomField, 3, 2, 1);
        addField(formPanel, gbc, "Phone", contactNumberField, 4, 0, 2);
        addField(formPanel, gbc, "Email", emailField, 4, 2, 2);
        addField(formPanel, gbc, "Guardian Name", guardianNameField, 5, 0, 2);
        addField(formPanel, gbc, "Guardian Phone", guardianPhoneField, 5, 2, 2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);



        saveBtn.setForeground(COLOR_WHITE);


        updateBtn.setForeground(COLOR_WHITE);

        saveBtn.setVisible(data==null);
        updateBtn.setVisible(data!=null);
        //logic to save student



        RoundedButton deleteBtn = new RoundedButton("Delete", null, COLOR_DANGER_LIGHT, COLOR_DANGER_HOVER);
        deleteBtn.setForeground(COLOR_DANGER);
        deleteBtn.addActionListener(e -> parentDialog.dispose()); // Add delete logic here
        deleteBtn.setVisible(data != null); // Only show delete for existing students

        RoundedButton cancelBtn = new RoundedButton("Cancel", null, COLOR_WHITE, COLOR_SIDEBAR);
        cancelBtn.setForeground(COLOR_TEXT_DARK);
        cancelBtn.addActionListener(e -> parentDialog.dispose());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(updateBtn);



        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 8, 5);
        formPanel.add(buttonPanel, gbc);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(450, (int)panel.getPreferredSize().getHeight()));
        return panel;

    }


    //getters for the form
    public JTextField getStudentIdField(){
        return studentIdField;
    }
    public JTextField getNameField(){
        return nameField;
    }
    public JComboBox<String> getGenderCombo(){
        return genderCombo;
    }
    public JComboBox<String> getYearCombo(){
        return yearCombo;
    }
    public JComboBox<String> getDeptCombo(){
        return yearCombo;
    }
    public JComboBox<String> getRoomTypeCombo(){
        return roomTypeCombo;
    }
    public JComboBox<String> getSleepTypeCombo(){
        return sleepTypeCombo;
    }
    public JTextField getAgeField(){
        return  ageField;
    }
    public JTextField getRoomField(){
        return roomField;
    }
    public JTextField getEmailField(){
        return emailField;

    }

    public JTextField getContactNumberField(){
        return contactNumberField;
    }
    public JTextField getGuardianNameField(){
        return guardianNameField;
    }
    public JTextField getGuardianPhoneField(){
        return guardianPhoneField;
    }

    public RoundedButton getSaveBtn(){
        return saveBtn;
    }
    public RoundedButton getUpdateBtn(){
        return updateBtn;
    }
    public JDialog getDialog(){
        return dialog;
    }
}
