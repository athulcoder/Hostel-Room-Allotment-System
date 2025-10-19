package ui.screen.panels;

import controllers.StudentController;
import controllers.StudentFormController;
import models.Student;
import ui.screen.components.RoundedButton;
import ui.screen.components.RoundedPanel;
import ui.screen.components.StudentForm;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ui.screen.components.AppColors.*;
import static ui.screen.components.AppFonts.*;

public class StudentPanel extends JPanel {

    // --- Embedded Theme Constants ---


    private JTable table;
    private DefaultTableModel model;
    private Object[][] data;
    private  JLabel countLabel;
    private Student newStudent;
    //list of student
    private RoundedButton refreshBtn;
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


    // save Button
    private RoundedButton saveBtn;


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
        addStudentBtn.addActionListener(e ->
                {
                   StudentForm form = new StudentForm(null,this);
                   new StudentFormController(form);
                    form.showStudentDialog();
                    refreshBtn.doClick();

                }
                // Show dialog for adding
        );
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

        refreshBtn = new RoundedButton("Refresh", IconFactory.createIcon(IconFactory.IconType.REFRESH), COLOR_PRIMARY_ACCENT, COLOR_SIDEBAR);
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);


        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(FONT_MAIN);
        table.setForeground(COLOR_TEXT_DARK);
        table.setSelectionBackground(COLOR_PRIMARY_ACCENT_LIGHT);
        table.setSelectionForeground(COLOR_PRIMARY_ACCENT);
        table.setBorder(null);
        table.setGridColor(COLOR_BORDER);
        table.setShowVerticalLines(false);

        for (int i = 0; i < table.getColumnCount(); i++) {
            if(i==1) continue;
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                Object[] rowData = new Object[model.getColumnCount()];
                for (int i = 0; i < model.getColumnCount(); i++) {

                    rowData[i] = model.getValueAt(selectedRow, i);
                }
                StudentForm form = new StudentForm(rowData,this);
                new StudentFormController(form);
                form.showStudentDialog();
                refreshBtn.doClick();
                // Show dialog for editing
            }
        });

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(FONT_BOLD);
        tableHeader.setForeground(COLOR_TEXT_LIGHT);
        tableHeader.setBackground(COLOR_WHITE);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setMinWidth(50);

        for(int i =1; i<colModel.getColumnCount();i++){
            colModel.getColumn(i).setMinWidth(160);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(COLOR_WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }





    // =================================================================================
    // Embedded Custom UI Components
    // =================================================================================



        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(COLOR_WHITE);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2d.dispose();
            super.paintComponent(g);
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
            SEARCH, DEPARTMENT, CALENDAR, ROOM_TYPE, ADD, USER_AVATAR,DASHBOARD, STUDENTS, ROOMS, ALLOTMENTS, SETTINGS, REFRESH,
            ALERTS, ADMIN, ROOMS_AVAILABLE, APP_LOGO
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
                    case DASHBOARD:
                        g2d.drawRect(x + 2, y + 2, size - 4, size - 4);
                        g2d.fillRect(x + 5, y + 5, quarter, quarter);
                        g2d.fillRect(x + size - quarter - 5, y + 5, quarter, quarter);
                        g2d.fillRect(x + 5, y + size - quarter - 5, quarter, quarter);
                        g2d.fillRect(x + size - quarter - 5, y + size - quarter - 5, quarter, quarter);
                        break;
                    case STUDENTS:
                    case ADMIN:
                        g2d.drawOval(x + quarter, y + 2, half, half);
                        g2d.drawArc(x + 2, y + half, size - 4, size - 4, 180, 180);
                        break;
                    case ROOMS:
                        g2d.drawRect(x + 2, y + 6, size - 4, size - 8);
                        g2d.drawLine(x + 2, y + 10, size - 2, y + 10);
                        g2d.fillRect(x + 5, y + 3, quarter, 4);
                        break;
                    case ALLOTMENTS:
                        g2d.drawOval(x + 2, y + 2, 8, 8);
                        g2d.drawLine(x + 8, y + 8, x + size - 2, y + size - 2);
                        g2d.drawLine(x + size - 6, y + size - 2, x + size - 2, y + size - 2);
                        g2d.drawLine(x + size - 2, y + size - 6, x + size - 2, y + size - 2);
                        break;
                    case SETTINGS:
                        g2d.drawOval(x + quarter, y + quarter, half, half);
                        for(int i = 0; i < 8; i++) {
                            double angle = Math.toRadians(i * 45);
                            g2d.drawLine(
                                    (int)(x + half + Math.cos(angle) * (half - 2)),
                                    (int)(y + half + Math.sin(angle) * (half - 2)),
                                    (int)(x + half + Math.cos(angle) * (half + 2)),
                                    (int)(y + half + Math.sin(angle) * (half + 2))
                            );
                        }
                        break;
                    case REFRESH:
                        g2d.drawArc(x + 2, y + 2, size - 4, size - 4, 30, 300);
                        g2d.fillPolygon(new int[]{x + half, x + half - 4, x + half + 4}, new int[]{y, y + 6, y + 6}, 3);
                        break;
                    case ALERTS:
                        g2d.drawArc(x + 2, y + 2, size - 4, 12, 0, 180);
                        g2d.drawLine(x + 2, y + 8, x + size - 2, y + 8);
                        g2d.drawArc(x + 6, y + 12, 6, 4, 180, 180);
                        break;
                    case ROOMS_AVAILABLE:
                        g2d.drawRect(x + 2, y + 6, size - 4, size - 8);
                        g2d.drawLine(x + 2, y + 10, size - 2, y + 10);
                        g2d.fillRect(x + 5, y + 3, quarter, 4);
                        g2d.drawLine(x + size - 6, y + 3, x + size - 2, y + 7);
                        g2d.drawLine(x + size - 6, y + 7, x + size - 2, y + 3);
                        break;
                    case APP_LOGO:
                        g2d.setColor(COLOR_PRIMARY_ACCENT);
                        g2d.fillOval(x, y, size, size);
                        g2d.setColor(COLOR_WHITE);
                        g2d.fillPolygon(new int[]{x+5, x+half, x+size-5}, new int[]{y+size-5, y+5, y+size-5}, 3);
                        break;

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

