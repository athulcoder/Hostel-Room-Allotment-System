package ui.screen.panels;

import models.Student;
import ui.screen.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class StudentPanel extends JPanel {

    // --- Embedded Theme Constants ---
    public static final Color COLOR_BACKGROUND = new Color(245, 250, 248);
    public static final Color COLOR_SIDEBAR = new Color(236, 244, 241);
    public static final Color COLOR_PRIMARY_ACCENT = new Color(13, 156, 116);
    public static final Color COLOR_PRIMARY_ACCENT_LIGHT = new Color(224, 243, 239);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_TEXT_DARK = new Color(40, 40, 40);
    public static final Color COLOR_TEXT_LIGHT = new Color(150, 150, 150);
    public static final Color COLOR_BORDER = new Color(220, 220, 220);
    public static final Color COLOR_DANGER = new Color(220, 38, 38);
    public static final Color COLOR_DANGER_LIGHT = new Color(255, 235, 238);
    public static final Color COLOR_DANGER_HOVER = new Color(254, 215, 215);

    public static final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 28);

    private JTable table;
    private DefaultTableModel model;
    private Object[][] data;
    private  JLabel countLabel;
    private Student newStudent;
    //list of student
    private RoundedButton refreshBtn;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentPanel() {
        super(new BorderLayout(0, 20));
        setBackground(COLOR_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createStudentListPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Students");
        title.setFont(FONT_HEADER);
        title.setForeground(COLOR_TEXT_DARK);
        titlePanel.add(title);

        JLabel subtitle = new JLabel("Add, edit, or remove student profiles");
        subtitle.setFont(FONT_MAIN);
        subtitle.setForeground(COLOR_TEXT_LIGHT);
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        RoundedButton addStudentBtn = new RoundedButton("Add Student", IconFactory.createIcon(IconFactory.IconType.ADD), COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());
        addStudentBtn.setForeground(COLOR_WHITE);
        addStudentBtn.addActionListener(e -> showStudentDialog(null)); // Show dialog for adding
        headerPanel.add(addStudentBtn, BorderLayout.EAST);

        topPanel.add(headerPanel);
        topPanel.add(Box.createVerticalStrut(20));

        JPanel actionBar = new JPanel();
        actionBar.setOpaque(false);
        actionBar.setLayout(new BoxLayout(actionBar, BoxLayout.X_AXIS));
        actionBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        actionBar.add(new SearchField("Search students...", IconFactory.createIcon(IconFactory.IconType.SEARCH)));
        actionBar.add(Box.createHorizontalStrut(15));

        RoundedButton deptBtn = new RoundedButton("Department", IconFactory.createIcon(IconFactory.IconType.DEPARTMENT), COLOR_WHITE, COLOR_SIDEBAR);
        deptBtn.setForeground(COLOR_TEXT_DARK);
        actionBar.add(deptBtn);
        actionBar.add(Box.createHorizontalStrut(10));

        RoundedButton yearBtn = new RoundedButton("Year", IconFactory.createIcon(IconFactory.IconType.CALENDAR), COLOR_WHITE, COLOR_SIDEBAR);
        yearBtn.setForeground(COLOR_TEXT_DARK);
        actionBar.add(yearBtn);
        actionBar.add(Box.createHorizontalStrut(10));

        RoundedButton roomBtn = new RoundedButton("Room Type", IconFactory.createIcon(IconFactory.IconType.ROOM_TYPE), COLOR_WHITE, COLOR_SIDEBAR);
        roomBtn.setForeground(COLOR_TEXT_DARK);
        actionBar.add(roomBtn);

        refreshBtn = new RoundedButton("Refresh", IconFactory.createIcon(IconFactory.IconType.SEARCH), COLOR_PRIMARY_ACCENT, COLOR_SIDEBAR);
        refreshBtn.setForeground(COLOR_TEXT_DARK);
        actionBar.add(refreshBtn);
        actionBar.add(Box.createHorizontalStrut(10));
        actionBar.add(Box.createHorizontalGlue());

        topPanel.add(actionBar);
        return topPanel;
    }

    private JPanel createStudentListPanel() {
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(new BorderLayout(0, 15));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("All Students");
        title.setFont(FONT_BOLD);
        title.setForeground(COLOR_TEXT_DARK);
        countLabel = new JLabel();
        countLabel.setFont(FONT_MAIN);
        countLabel.setForeground(COLOR_TEXT_LIGHT);
        header.add(title, BorderLayout.WEST);
        header.add(countLabel, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        String[] columnNames = {
                "studentId",
                "name",
                "gender",
                "age",
                "department",
                "academicYear",
                "contactNumber",
                "email",
                "guardianName",
                "guardianPhone",
                "preferredRoomType",
                "assignedRoom",
                "sleepType",
                "dateOfAdmission",
                "hostelId"
        };

        model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(FONT_MAIN);
        table.setForeground(COLOR_TEXT_DARK);
        table.setSelectionBackground(COLOR_PRIMARY_ACCENT_LIGHT);
        table.setSelectionForeground(COLOR_PRIMARY_ACCENT);
        table.setBorder(null);
        table.setGridColor(COLOR_BORDER);
        table.setShowVerticalLines(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                Object[] rowData = new Object[model.getColumnCount()];
                for (int i = 0; i < model.getColumnCount(); i++) {
                    rowData[i] = model.getValueAt(selectedRow, i);
                }
                showStudentDialog(rowData); // Show dialog for editing
            }
        });

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(FONT_BOLD);
        tableHeader.setForeground(COLOR_TEXT_LIGHT);
        tableHeader.setBackground(COLOR_WHITE);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        table.getColumnModel().getColumn(0).setCellRenderer(new AvatarRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(COLOR_WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showStudentDialog(Object[] data) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, true); // Modal

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
        JTextField nameField = new JTextField(data != null ? data[1].toString() : "");
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Female", "Male"});
        if (data != null) genderCombo.setSelectedItem(data[2]);
        JTextField ageField = new JTextField(data != null ? data[3].toString() : "");
        JComboBox<String> yearCombo = new JComboBox<>(new String[]{"Year 1", "Year 2", "Year 3", "Year 4"});
        if (data != null) yearCombo.setSelectedItem(data[5]);
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"Computer Science", "Economics", "Mechanical Eng.", "Business Admin", "Biology"});
        if (data != null) deptCombo.setSelectedItem(data[4]);
        JComboBox<String> roomTypeCombo = new JComboBox<>(new String[]{"2-sharing", "4-sharing", "6-sharing"});
        if (data != null) roomTypeCombo.setSelectedItem(data[10]);
        JComboBox<String> sleepTypeCombo = new JComboBox<>(new String[]{"Early", "Night"});
        if(data!=null) sleepTypeCombo.setSelectedItem(data[12]);
        JTextField roomField = new JTextField(data != null ? data[11].toString() : ""); // Dummy data
        JTextField contactNumberField = new JTextField(data != null ? data[6].toString() : "");// Dummy data
        JTextField emailField = new JTextField(data != null ? data[7].toString() : "");// Dummy data

        JTextField guardianNameField = new JTextField(data != null ?data[8].toString() : ""); // Dummy data
        JTextField guardianPhoneField = new JTextField(data != null ? data[9].toString() : ""); // Dummy data

        addField(formPanel, gbc, "Name", nameField, 0, 0, 2);
        addField(formPanel, gbc, "Gender", genderCombo, 0, 2, 2);
        addField(formPanel, gbc, "Age", ageField, 1, 0, 1);
        addField(formPanel, gbc, "Academic Year", yearCombo, 1, 2, 1);
        addField(formPanel, gbc, "Department", deptCombo, 2, 0, 2);
        addField(formPanel, gbc, "Preferred Room Type", roomTypeCombo, 2, 2, 2);
        addField(formPanel, gbc, "Sleep Type", sleepTypeCombo, 3, 0, 1);
        addField(formPanel, gbc, "Room", roomField, 3, 2, 1);
        addField(formPanel, gbc, "Contact Number", contactNumberField, 4, 0, 2);
        addField(formPanel, gbc, "Contact Number", emailField, 4, 2, 2);

        addField(formPanel, gbc, "Guardian Name", guardianNameField, 5, 0, 2);
        addField(formPanel, gbc, "Guardian Phone", guardianPhoneField, 5, 2, 2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);


        RoundedButton saveBtn = new RoundedButton("Save", null, COLOR_PRIMARY_ACCENT, COLOR_PRIMARY_ACCENT.brighter());
        saveBtn.setForeground(COLOR_WHITE);
        saveBtn.addActionListener(e -> parentDialog.dispose()); // Add save logic here

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

    // =================================================================================
    // Embedded Custom UI Components
    // =================================================================================

    private static class RoundedPanel extends JPanel {
        public RoundedPanel() {
            super();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(COLOR_WHITE);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2d.dispose();
            super.paintComponent(g);
        }
    }


    private static class SearchField extends JPanel {
        public SearchField(String placeholder, Icon icon) {
            super(new BorderLayout(10, 0));
            setBackground(COLOR_WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));

            JLabel iconLabel = new JLabel(icon);
            JTextField textField = new JTextField(placeholder);
            textField.setFont(FONT_MAIN);
            textField.setForeground(COLOR_TEXT_LIGHT);
            textField.setBorder(null);
            textField.setOpaque(false);

            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textField.getText().equals(placeholder)) {
                        textField.setText("");
                        textField.setForeground(COLOR_TEXT_DARK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().isEmpty()) {
                        textField.setText(placeholder);
                        textField.setForeground(COLOR_TEXT_LIGHT);
                    }
                }
            });

            add(iconLabel, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2d.setColor(COLOR_BORDER);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2d.dispose();
            super.paintComponent(g);
        }
    }

    private static class IconFactory {
        public enum IconType {
            SEARCH, DEPARTMENT, CALENDAR, ROOM_TYPE, ADD, USER_AVATAR
        }

        public static Icon createIcon(IconType type) {
            return new ScalableIcon(type);
        }

        private static class ScalableIcon implements Icon {
            private final IconType type;
            private final int size = 18;

            public ScalableIcon(IconType type) {
                this.type = type;
            }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setColor(c.getForeground());

                int half = size / 2;
                int quarter = size / 4;

                switch (type) {
                    case USER_AVATAR:
                        g2d.drawOval(x + quarter, y + 2, half, half);
                        g2d.drawArc(x + 2, y + half, size - 4, size - 4, 180, 180);
                        break;
                    case SEARCH:
                        g2d.drawOval(x + 2, y + 2, size - 7, size - 7);
                        g2d.drawLine(x + size - 6, y + size - 6, x + size - 2, y + size - 2);
                        break;
                    case DEPARTMENT:
                        g2d.drawRect(x + 2, y + 2, size - 4, size - 4);
                        g2d.drawLine(x + half, y + 2, x + half, y + size - 2);
                        g2d.drawLine(x + 2, y + half, size - 2, y + half);
                        break;
                    case CALENDAR:
                        g2d.drawRect(x + 2, y + 4, size - 4, size - 6);
                        g2d.drawLine(x + 2, y + 8, x + size - 2, y + 8);
                        g2d.drawLine(x + 5, y + 2, x + 5, y + 6);
                        g2d.drawLine(x + size - 5, y + 2, x + size - 5, y + 6);
                        break;
                    case ROOM_TYPE:
                        g2d.drawRect(x + 2, y + 8, size - 4, 8);
                        g2d.drawRect(x + 5, y + 2, size - 10, 6);
                        break;
                    case ADD:
                        g2d.drawLine(x + half, y + 3, x + half, y + size - 3);
                        g2d.drawLine(x + 3, y + half, x + size - 3, y + half);
                        break;
                }
                g2d.dispose();
            }

            @Override public int getIconWidth() { return size; }
            @Override public int getIconHeight() { return size; }
        }
    }

    private static class AvatarRenderer extends DefaultTableCellRenderer {
        private final Icon avatarIcon = IconFactory.createIcon(IconFactory.IconType.USER_AVATAR);

        public AvatarRenderer() {
            setIconTextGap(15);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setIcon(avatarIcon);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            return this;
        }
    }

    //setters for setting data


    public void setStudents(ArrayList<Student> students) {
        this.students = students;

        // Convert list to Object[][]
        model.setRowCount(0);

        for (Student s : students) {
            Object[] row = {
                    s.getStudentId(),
                    s.getName(),
                    s.getGender(),
                    s.getAge(),
                    s.getDepartment(),
                    s.getAcademicYear(),
                    s.getContactNumber(),
                    s.getEmail(),
                    s.getGuardianName(),
                    s.getGuardianPhone(),
                    s.getPreferredRoomType(),
                    s.getAssignedRoom(),
                    s.getSleepType(),
                    s.getDateOfAdmission(),
                    s.getHostelId()
            };
            model.addRow(row);


            countLabel.setText("Showing "+students.size()+" results");
        }
    }

    public RoundedButton getRefreshBtn() {
        return refreshBtn;
    }
}

