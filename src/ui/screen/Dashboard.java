package ui.screen;

import models.Admin;
import utils.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard screen with sidebar navigation and card layout content area.
 */
public class Dashboard extends JPanel {

    private CardLayout contentLayout;
    private JPanel contentPanel;

    public Dashboard() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // light gray background

        // ===== Sidebar =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(33, 45, 62));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Dashboard");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        sidebar.add(title);

        // Buttons for navigation
        String[] pages = {"Dashboard", "Students", "Rooms", "Allotment", "Settings"};
        for (String page : pages) {
            JButton btn = createNavButton(page);
            sidebar.add(btn);

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contentLayout.show(contentPanel, page.toLowerCase());
                }
            });
        }

        add(sidebar, BorderLayout.WEST);

        // ===== Main Content Area =====
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Color.WHITE);

        // Add all pages to the card layout
        //sample text
        String welcome ="";
        try{
           Admin admin = SessionManager.getCurrentAdmin();
           welcome =admin.getName();
           System.out.println(welcome);
        }catch (Exception e){
            System.out.println("ERROR " + e.getMessage());
        }

        contentPanel.add(createPage("Dashboard Overview", welcome), "dashboard");
        contentPanel.add(createPage("Student Management", "Add, edit or remove students."), "students");
        contentPanel.add(createPage("Rooms Overview", "Manage available rooms."), "rooms");
        contentPanel.add(createPage("Room Allotment", "Allocate rooms efficiently."), "allotment");
        contentPanel.add(createPage("Settings", "Update system settings."), "settings");

        add(contentPanel, BorderLayout.CENTER);

        // Default page
        contentLayout.show(contentPanel, "dashboard");
    }

    /** Creates styled navigation button */
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185)); // hover color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 73, 94));
            }
        });

        return btn;
    }

    /** Creates a simple page with title and description */
    private JPanel createPage(String title, String desc) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblDesc.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 0));

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblDesc, BorderLayout.CENTER);

        return panel;
    }
}
