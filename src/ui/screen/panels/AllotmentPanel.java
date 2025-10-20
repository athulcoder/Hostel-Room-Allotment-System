package ui.screen.panels;

import models.Student;
import ui.screen.components.AppColors;
import ui.screen.components.AppFonts;
import ui.screen.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * A panel for controlling and monitoring the student allotment process.
 * Features a split view for pending and allotted students.
 */
public class AllotmentPanel extends JPanel {

    // --- UI Components ---

    // Metric Cards
    private JLabel unassignedStudentsCard;
    private JLabel availableCapacityCard;
    private JLabel assignedStudentsCard;

    // Controls
    private RoundedButton refreshBtn;
    private RoundedButton clearRoomsBtn;
    private RoundedButton runAllotmentBtn;

    // --- MODIFIED ---: Two panels for the two lists
    private JPanel pendingListPanel;
    private JPanel allottedListPanel;

    /**
     * Creates the main Allotment Panel.
     */
    public AllotmentPanel() {
        // Main panel setup
        setLayout(new BorderLayout(0, 20));
        setBackground(AppColors.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // Create and add components
        add(createHeader(), BorderLayout.NORTH);
        add(createCenterContent(), BorderLayout.CENTER);
    }

    /**
     * Creates the top header with title and action buttons.
     */
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Left side: Title and Subtitle
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        JLabel title = new JLabel("Allotment Engine");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_TEXT_DARK);
        JLabel subtitle = new JLabel("Assign unallocated students to available rooms automatically");
        subtitle.setFont(AppFonts.FONT_MAIN);
        subtitle.setForeground(AppColors.COLOR_TEXT_LIGHT);
        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(subtitle);

        // Right side: Action Buttons
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        clearRoomsBtn = new RoundedButton("Clear Current Rooms", null, AppColors.COLOR_DANGER_LIGHT, AppColors.COLOR_DANGER_HOVER);
        clearRoomsBtn.setForeground(AppColors.COLOR_DANGER);
        right.add(clearRoomsBtn);
        refreshBtn = new RoundedButton("Refresh Stats", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT_LIGHT);
        refreshBtn.setForeground(AppColors.COLOR_WHITE);
        right.add(refreshBtn);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    /**
     * Creates the main content area (metrics, controls, and the split view).
     */
    private JPanel createCenterContent() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Panel to hold controls and metrics
        JPanel topSection = new JPanel(new BorderLayout(0, 20));
        topSection.setOpaque(false);
        topSection.add(createMetrics(), BorderLayout.NORTH);
        topSection.add(createControlPanel(), BorderLayout.CENTER);

        // Panel for the two lists
        JPanel listSection = new JPanel(new GridLayout(1, 2, 20, 0)); // 1 row, 2 cols, 20px gap
        listSection.setOpaque(false);
        listSection.add(createStudentListPanel("Pending Allotment", true));
        listSection.add(createStudentListPanel("Allotted Students", false));

        panel.add(topSection, BorderLayout.NORTH);
        panel.add(listSection, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the panel holding the metric cards.
     */
    private JPanel createMetrics() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setOpaque(false);
        unassignedStudentsCard = new JLabel("0");
        availableCapacityCard = new JLabel("0");
        assignedStudentsCard = new JLabel("0");
        styleCardLabel(unassignedStudentsCard);
        styleCardLabel(availableCapacityCard);
        styleCardLabel(assignedStudentsCard);
        panel.add(createCard("Unassigned Students", unassignedStudentsCard));
        panel.add(createCard("Available Capacity", availableCapacityCard));
        panel.add(createCard("Students Already Placed", assignedStudentsCard));
        return panel;
    }

    private JPanel createCard(String label, JLabel valueLabel) {
        ModernRoundedPanel card = new ModernRoundedPanel();
        card.setBackground(AppColors.COLOR_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel l = new JLabel(label);
        l.setFont(AppFonts.FONT_MAIN);
        l.setForeground(AppColors.COLOR_TEXT_LIGHT);
        card.add(l);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);
        return card;
    }

    private void styleCardLabel(JLabel label) {
        label.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        label.setForeground(AppColors.COLOR_TEXT_DARK);
    }

    /**
     * Creates the panel with the "Start" button and warning.
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 5, 10, 5));
        runAllotmentBtn = new RoundedButton("Start Allotment Process", null, AppColors.COLOR_PRIMARY_ACCENT, AppColors.COLOR_PRIMARY_ACCENT.darker());
        runAllotmentBtn.setForeground(AppColors.COLOR_WHITE);
        runAllotmentBtn.setFont(AppFonts.FONT_BOLD.deriveFont(16f));
        runAllotmentBtn.setPreferredSize(new Dimension(280, 50));
        JLabel warningLabel = new JLabel("<html><b>Warning:</b> This process can take several minutes and cannot be undone." + "<br>Please ensure all student preferences are up-to-date.</html>");
        warningLabel.setFont(AppFonts.FONT_MAIN);
        warningLabel.setForeground(AppColors.COLOR_TEXT_LIGHT);
        warningLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        warningLabel.setIconTextGap(10);
        panel.add(runAllotmentBtn, BorderLayout.WEST);
        panel.add(warningLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Generic factory method to create either the pending or allotted list panel.
     */
    private JPanel createStudentListPanel(String title, boolean isPendingPanel) {
        ModernRoundedPanel panel = new ModernRoundedPanel();
        panel.setBackground(AppColors.COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 15));
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 5, 0, 5));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        titleLabel.setForeground(AppColors.COLOR_TEXT_DARK);
        header.add(titleLabel, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        JPanel cardListContainer = new JPanel();
        cardListContainer.setLayout(new BoxLayout(cardListContainer, BoxLayout.Y_AXIS));
        cardListContainer.setBackground(AppColors.COLOR_WHITE);
        cardListContainer.setBorder(new EmptyBorder(5, 5, 5, 10));

        if (isPendingPanel) {
            this.pendingListPanel = cardListContainer;
        } else {
            this.allottedListPanel = cardListContainer;
        }

        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.add(cardListContainer, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(cardWrapper);
        scrollPane.getViewport().setBackground(AppColors.COLOR_WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates a card for a PENDING student.
     */
    private JPanel createStudentCard(Student student, Color background, Color border) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setOpaque(true);
        card.setBackground(background);
        card.setBorder(new CompoundBorder(new LineBorder(border, 1), new EmptyBorder(10, 15, 10, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        // Top: Name and ID
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel nameLabel = new JLabel(student.getName());
        nameLabel.setFont(AppFonts.FONT_BOLD.deriveFont(16f));
        nameLabel.setForeground(AppColors.COLOR_TEXT_DARK);
        JLabel idLabel = new JLabel(student.getStudentId().toUpperCase());
        idLabel.setFont(AppFonts.FONT_MAIN);
        idLabel.setForeground(AppColors.COLOR_TEXT_LIGHT);
        topPanel.add(nameLabel, BorderLayout.WEST);
        topPanel.add(idLabel, BorderLayout.EAST);

        // Middle: Room Info
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JLabel yearLabel = new JLabel("Year: " + student.getAcademicYear() + " | Prefers: " + student.getPreferredRoomType());
        yearLabel.setFont(AppFonts.FONT_MAIN);
        yearLabel.setForeground(AppColors.COLOR_TEXT_DARK);
        centerPanel.add(yearLabel, BorderLayout.WEST);

        // --- Show Room Number ONLY if allotted ---
        if (student.getAssignedRoom() != null && !student.getAssignedRoom().isEmpty()) {
            JLabel roomLabel = new JLabel("Room: " + student.getAssignedRoom());
            roomLabel.setFont(AppFonts.FONT_BOLD);
            roomLabel.setForeground(AppColors.COLOR_PRIMARY_ACCENT);
            centerPanel.add(roomLabel, BorderLayout.EAST);
        }

        // Bottom: Preferences
        String veg = student.isVegetarian() ? "Veg" : "Non-Veg";
        String prefs = "Sleep: " + student.getSleepType() + " | Study: " + student.getStudyPreference() + " | " + veg;
        JLabel prefsLabel = new JLabel(prefs);
        prefsLabel.setFont(AppFonts.FONT_MAIN.deriveFont(Font.ITALIC));
        prefsLabel.setForeground(AppColors.COLOR_TEXT_LIGHT);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(prefsLabel, BorderLayout.SOUTH);

        return card;
    }

    // -----------------------------------------------------------------
    // PUBLIC API (for your Controller/Dashboard to use)
    // -----------------------------------------------------------------

    public RoundedButton getRefreshButton() { return refreshBtn; }
    public RoundedButton getClearRoomsButton() { return clearRoomsBtn; }
    public RoundedButton getRunAllotmentButton() { return runAllotmentBtn; }

    public void setUnassignedStudents(int count) { unassignedStudentsCard.setText(String.valueOf(count)); }
    public void setAvailableCapacity(int count) { availableCapacityCard.setText(String.valueOf(count)); }
    public void setAssignedStudents(int count) { assignedStudentsCard.setText(String.valueOf(count)); }

    /**
     * Populates the PENDING students list with cards.
     */
    public void setUnassignedStudentsList(List<Student> students) {
        populateList(pendingListPanel, students, false);
    }

    /**
     * Populates the ALLOTTED students list with cards.
     */
    public void setAllottedStudentsList(List<Student> students) {
        populateList(allottedListPanel, students, true);
    }

    /**
     * Helper method to fill a JPanel with student cards.
     */
    private void populateList(JPanel listPanel, List<Student> students, boolean isAllotted) {
        listPanel.removeAll();
        if (students == null || students.isEmpty()) {
            String message = isAllotted ? "No students are currently allotted." : "No unassigned students found.";
            JLabel emptyLabel = new JLabel(message);
            emptyLabel.setFont(AppFonts.FONT_MAIN);
            emptyLabel.setForeground(AppColors.COLOR_TEXT_LIGHT);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            listPanel.add(emptyLabel);
        } else {
            Color background = isAllotted ? new Color(230, 245, 230) : AppColors.COLOR_BACKGROUND;
            Color border = isAllotted ? new Color(34, 197, 94) : AppColors.COLOR_BORDER;
            for (Student s : students) {
                listPanel.add(createStudentCard(s, background, border));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    public void setUIEnabled(boolean enabled) {
        runAllotmentBtn.setEnabled(enabled);
        refreshBtn.setEnabled(enabled);
        clearRoomsBtn.setEnabled(enabled);
        if (enabled) {
            runAllotmentBtn.setText("Start Allotment Process");
            runAllotmentBtn.setBackground(AppColors.COLOR_PRIMARY_ACCENT);
        } else {
            runAllotmentBtn.setText("Running... Please Wait");
            runAllotmentBtn.setBackground(AppColors.COLOR_TEXT_LIGHT);
        }
    }

    private class ModernRoundedPanel extends JPanel {
        private final int cornerRadius = 16;
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
        }
    }
}
