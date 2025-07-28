package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HostelDashboard {

    private final CardLayout mainContentLayout = new CardLayout();
    private final JPanel mainContentPanel = new JPanel(mainContentLayout);

    private final JPanel secondaryNavBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel centerContainer = new JPanel(new BorderLayout());

    private final CardLayout roomsContentLayout = new CardLayout();
    private final JPanel roomsContentPanel = new JPanel(roomsContentLayout);
    public void showDashboard() {
        JFrame frame = new JFrame("Hostel Room Allotment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // ----- Top Navigation Bar -----
        JPanel topNav = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topNav.setBackground(new Color(230, 230, 230));
        topNav.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] mainTabs = {"Dashboard", "Rooms", "Students", "Logout"};
        for (String tab : mainTabs) {
            JButton button = createTabButton(tab);
            topNav.add(button);

            if (tab.equals("Logout")) {
                button.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Logging out..."));
            } else {
                button.addActionListener(e -> {
                    updateSecondaryNav(tab);
                    mainContentLayout.show(mainContentPanel, tab);
                });
            }
        }

        // ----- Content Panels -----
        mainContentPanel.add(getDashboardPanel(), "Dashboard");
        mainContentPanel.add(getRoomsPanel(), "Rooms");
        mainContentPanel.add(getStudentsPanel(), "Students");

        // ----- Secondary Nav Placeholder -----
        secondaryNavBar.setBackground(new Color(245, 245, 245));
        // ----- Center Container (Secondary Tabs + Content) -----
        centerContainer.add(secondaryNavBar, BorderLayout.NORTH);
        centerContainer.add(mainContentPanel, BorderLayout.CENTER);

        // ----- Assemble UI -----
        frame.add(topNav, BorderLayout.NORTH);
        frame.add(centerContainer, BorderLayout.CENTER);

        // Show initial screen
        updateSecondaryNav("Dashboard");
        frame.setVisible(true);
    }

    // ---- Create Button with Small Style ----
    private JButton createTabButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(5, 15, 5, 15));
        return btn;
    }

    // ---- Dynamic Secondary Tabs ----
    private void updateSecondaryNav(String mainTab) {
        secondaryNavBar.removeAll();

        Map<String, String[]> subTabs = new HashMap<>();
        subTabs.put("Rooms", new String[]{"Create Room", "Update Room", "Delete Room", "Get Rooms"});
        subTabs.put("Students", new String[]{"Add Student", "Update Student", "Delete Student", "Get Student"});
        subTabs.put("Dashboard", new String[]{"Overview"});

        String[] functions = subTabs.getOrDefault(mainTab, new String[0]);
        for (String label : functions) {
            JButton btn = createTabButton(label);
            btn.addActionListener(e -> {
                if (label.equals("Create Room")) {
                    roomsContentLayout.show(roomsContentPanel, "Create Room");
                } else {
                    JOptionPane.showMessageDialog(null, "Clicked: " + label);
                }
            });
        }

        secondaryNavBar.revalidate();
        secondaryNavBar.repaint();
    }

    // ---- Content Panels ----
    private JPanel getDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome to the Dashboard", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getRoomsPanel() {
        roomsContentPanel.add(new CreateRoomForm(), "Create Room");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(roomsContentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Student Management Section", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
