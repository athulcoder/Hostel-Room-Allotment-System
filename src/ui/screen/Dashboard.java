package ui.screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * A modern, visually appealing dashboard for a campus management system.
 * This class is a JPanel, designed to be placed within a larger frame or CardLayout.
 * It features a custom color scheme, icons, and interactive components.
 *
 * @author Gemini
 */
public class Dashboard extends JPanel {

    // --- UI Customization (public for access by panel classes) ---
    public static final Color COLOR_BACKGROUND = new Color(245, 250, 248);
    public static final Color COLOR_SIDEBAR = new Color(236, 244, 241);
    public static final Color COLOR_PRIMARY_ACCENT = new Color(13, 156, 116);
    public static final Color COLOR_PRIMARY_ACCENT_LIGHT = new Color(224, 243, 239);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_TEXT_DARK = new Color(40, 40, 40);
    public static final Color COLOR_TEXT_LIGHT = new Color(150, 150, 150);
    public static final Color COLOR_BORDER = new Color(220, 220, 220);

    public static final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_CARD_VALUE = new Font("Segoe UI", Font.BOLD, 36);

    private JPanel contentSwitchPanel;
    private CardLayout cardLayout;
    private final Map<String, SidebarButton> sidebarButtons = new HashMap<>();

    public Dashboard() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND);

        // --- Sidebar Panel ---
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // --- Main Content Area (with CardLayout for switching panels) ---
        cardLayout = new CardLayout();
        contentSwitchPanel = new JPanel(cardLayout);
        contentSwitchPanel.setBackground(COLOR_BACKGROUND);

        // --- Create and Add Different Content Panels ---
        JPanel dashboardView = new DashboardPanel();
        JPanel studentsView = new PlaceholderPanel("Students Management");
        JPanel roomsView = new PlaceholderPanel("Rooms Management");
        JPanel allotmentsView = new PlaceholderPanel("Allotments Management");
        JPanel settingsView = new PlaceholderPanel("Application Settings");

        contentSwitchPanel.add(dashboardView, "Dashboard");
        contentSwitchPanel.add(studentsView, "Students");
        contentSwitchPanel.add(roomsView, "Rooms");
        contentSwitchPanel.add(allotmentsView, "Allotments");
        contentSwitchPanel.add(settingsView, "Settings");

        add(contentSwitchPanel, BorderLayout.CENTER);

        // Set initial active button and view
        setActiveSidebarButton("Dashboard");
        cardLayout.show(contentSwitchPanel, "Dashboard");
    }

    /**
     * Creates the sidebar navigation panel.
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(240, 0)); // Width is respected, height is managed by BorderLayout
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER));

        JLabel appTitle = new JLabel("Campus Manager");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setForeground(COLOR_TEXT_DARK);
        appTitle.setIcon(IconFactory.createIcon(IconFactory.IconType.APP_LOGO));
        appTitle.setIconTextGap(10);
        appTitle.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        sidebar.add(appTitle);

        JLabel navTitle = new JLabel("NAVIGATION");
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        navTitle.setForeground(COLOR_TEXT_LIGHT);
        navTitle.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 20));
        sidebar.add(navTitle);

        addSidebarButton(sidebar, "Dashboard", IconFactory.createIcon(IconFactory.IconType.DASHBOARD));
        addSidebarButton(sidebar, "Students", IconFactory.createIcon(IconFactory.IconType.STUDENTS));
        addSidebarButton(sidebar, "Rooms", IconFactory.createIcon(IconFactory.IconType.ROOMS));
        addSidebarButton(sidebar, "Allotments", IconFactory.createIcon(IconFactory.IconType.ALLOTMENTS));
        addSidebarButton(sidebar, "Settings", IconFactory.createIcon(IconFactory.IconType.SETTINGS));

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    /**
     * Helper to create and add a sidebar button. The listener now also switches the view.
     */
    private void addSidebarButton(JPanel parent, String text, Icon icon) {
        SidebarButton button = new SidebarButton(text, icon);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setActiveSidebarButton(text);
                cardLayout.show(contentSwitchPanel, text);
            }
        });
        parent.add(button);
        sidebarButtons.put(text, button);
    }

    /**
     * Sets the visual state for the active sidebar button.
     */
    private void setActiveSidebarButton(String text) {
        sidebarButtons.values().forEach(btn -> btn.setActive(false));
        SidebarButton activeButton = sidebarButtons.get(text);
        if (activeButton != null) {
            activeButton.setActive(true);
        }
    }

    // =================================================================================
    // INNER CLASS: Main Dashboard Content Panel
    // =================================================================================
    /**
     * A dedicated panel for the "Overview" dashboard content.
     */
    public static class DashboardPanel extends JPanel {
        public DashboardPanel() {
            super(new BorderLayout());
            setBackground(Dashboard.COLOR_BACKGROUND);
            setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

            // Header Section
            JPanel header = createHeader();
            add(header, BorderLayout.NORTH);

            // Overview Cards Section
            JPanel cardsPanel = createOverviewCards();

            // Panel to hold main content, placing cards at the top
            JPanel mainContentArea = new JPanel(new BorderLayout());
            mainContentArea.setBackground(Dashboard.COLOR_BACKGROUND);
            mainContentArea.add(cardsPanel, BorderLayout.NORTH);

            add(mainContentArea, BorderLayout.CENTER);
        }

        private JPanel createHeader() {
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Dashboard.COLOR_BACKGROUND);
            header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Dashboard.COLOR_BACKGROUND);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

            JLabel title = new JLabel("Overview");
            title.setFont(Dashboard.FONT_HEADER);
            title.setForeground(Dashboard.COLOR_TEXT_DARK);
            titlePanel.add(title);

            JLabel subtitle = new JLabel("Quick glance at key campus metrics");
            subtitle.setFont(Dashboard.FONT_MAIN);
            subtitle.setForeground(Dashboard.COLOR_TEXT_LIGHT);
            titlePanel.add(subtitle);

            header.add(titlePanel, BorderLayout.WEST);

            JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            actionsPanel.setBackground(Dashboard.COLOR_BACKGROUND);
            actionsPanel.add(createStyledButton("Alerts", IconFactory.createIcon(IconFactory.IconType.ALERTS)));
            actionsPanel.add(createStyledButton("Admin", IconFactory.createIcon(IconFactory.IconType.ADMIN)));
            actionsPanel.add(createPrimaryButton("Refresh", IconFactory.createIcon(IconFactory.IconType.REFRESH)));
            header.add(actionsPanel, BorderLayout.EAST);

            return header;
        }

        private JPanel createOverviewCards() {
            // FlowLayout allows cards to wrap on smaller screen sizes, making it responsive
            JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
            cardsPanel.setBackground(Dashboard.COLOR_BACKGROUND);
            cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

            cardsPanel.add(new StatCard("Total Students", "1,250", IconFactory.createIcon(IconFactory.IconType.STUDENTS)));
            cardsPanel.add(new StatCard("Total Rooms", "1,000", IconFactory.createIcon(IconFactory.IconType.ROOMS)));
            cardsPanel.add(new StatCard("Rooms Allotted", "820", IconFactory.createIcon(IconFactory.IconType.ALLOTMENTS)));
            cardsPanel.add(new StatCard("Available Rooms", "180", IconFactory.createIcon(IconFactory.IconType.ROOMS_AVAILABLE)));

            return cardsPanel;
        }

        private JButton createStyledButton(String text, Icon icon) {
            Color hoverColor = new Color(205, 235, 228);
            RoundedButton button = new RoundedButton(text, icon, Dashboard.COLOR_PRIMARY_ACCENT_LIGHT, hoverColor);
            button.setForeground(Dashboard.COLOR_TEXT_DARK);
            return button;
        }

        private JButton createPrimaryButton(String text, Icon icon) {
            Color hoverColor = Dashboard.COLOR_PRIMARY_ACCENT.brighter();
            RoundedButton button = new RoundedButton(text, icon, Dashboard.COLOR_PRIMARY_ACCENT, hoverColor);
            button.setForeground(Dashboard.COLOR_WHITE);
            return button;
        }

        /** Custom JButton with rounded corners and hover effects. */
        private static class RoundedButton extends JButton {
            private final Color originalBgColor;
            private final Color hoverBgColor;
            private Color currentBgColor;
            private final int cornerRadius = 20;

            public RoundedButton(String text, Icon icon, Color background, Color hover) {
                super(text);
                this.originalBgColor = background;
                this.hoverBgColor = hover;
                this.currentBgColor = background;

                setIcon(icon);
                setFont(Dashboard.FONT_BOLD);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentBgColor = hoverBgColor;
                        repaint();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        currentBgColor = originalBgColor;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(currentBgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
                g2.dispose();

                super.paintComponent(g); // Paints the text and icon
            }
        }

        /** Custom JPanel for displaying a statistic card. */
        private static class StatCard extends JPanel {
            public StatCard(String title, String value, Icon icon) {
                super(new BorderLayout());
                setBackground(Dashboard.COLOR_WHITE);
                setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
                setPreferredSize(new Dimension(220, 110)); // Set a reasonable size

                JLabel lblTitle = new JLabel(title);
                lblTitle.setFont(Dashboard.FONT_BOLD);
                lblTitle.setForeground(Dashboard.COLOR_TEXT_LIGHT);

                JLabel lblValue = new JLabel(value);
                lblValue.setFont(Dashboard.FONT_CARD_VALUE);
                lblValue.setForeground(Dashboard.COLOR_TEXT_DARK);

                JPanel textPanel = new JPanel();
                textPanel.setBackground(Dashboard.COLOR_WHITE);
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.add(lblTitle);
                textPanel.add(Box.createVerticalStrut(5));
                textPanel.add(lblValue);

                add(textPanel, BorderLayout.CENTER);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fill(new RoundRectangle2D.Float(5, 5, getWidth() - 10, getHeight() - 10, 20, 20));
                g2d.setColor(getBackground());
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2d.setColor(Dashboard.COLOR_BORDER);
                g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                g2d.dispose();
            }
        }
    }

    // =================================================================================
    // INNER CLASS: Placeholder Panel
    // =================================================================================
    /**
     * A simple panel to show when a navigation item is clicked.
     */
    public static class PlaceholderPanel extends JPanel {
        public PlaceholderPanel(String title) {
            setBackground(Dashboard.COLOR_BACKGROUND);
            JLabel label = new JLabel(title);
            label.setFont(Dashboard.FONT_HEADER);
            label.setForeground(Dashboard.COLOR_TEXT_LIGHT);
            add(label);
        }
    }

    /**
     * A custom component for sidebar navigation buttons.
     */
    private static class SidebarButton extends JPanel {
        private boolean isActive = false;
        private boolean isHovered = false;
        private final JLabel label;

        public SidebarButton(String text, Icon icon) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            label = new JLabel(text);
            label.setFont(FONT_BOLD);
            label.setIcon(icon);
            label.setIconTextGap(15);
            add(label);

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
            updateColors();
        }

        public void setActive(boolean active) { this.isActive = active; updateColors(); repaint(); }

        private void updateColors() {
            if (isActive) {
                setBackground(COLOR_PRIMARY_ACCENT_LIGHT);
                label.setForeground(COLOR_PRIMARY_ACCENT);
            } else if (isHovered) {
                setBackground(new Color(228, 236, 233));
                label.setForeground(COLOR_TEXT_DARK);
            } else {
                setBackground(COLOR_SIDEBAR);
                label.setForeground(COLOR_TEXT_DARK);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            updateColors();
            super.paintComponent(g);
            if (isActive) {
                g.setColor(COLOR_PRIMARY_ACCENT);
                g.fillRect(0, 0, 5, getHeight());
            }
        }
    }

    /**
     * A factory class to create simple, scalable icons programmatically.
     */
    private static class IconFactory {
        enum IconType {
            DASHBOARD, STUDENTS, ROOMS, ALLOTMENTS, SETTINGS, REFRESH,
            ALERTS, ADMIN, ROOMS_AVAILABLE, APP_LOGO
        }

        public static Icon createIcon(IconType type) { return new ScalableIcon(type); }

        private static class ScalableIcon implements Icon {
            private final IconType type;
            private final int size = 18;

            public ScalableIcon(IconType type) { this.type = type; }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                Color iconColor = c.getParent() instanceof SidebarButton && ((SidebarButton)c.getParent()).isActive ?
                        COLOR_PRIMARY_ACCENT : COLOR_TEXT_DARK;
                if (c instanceof RoundedButton) {
                    // Specific handling for icon color inside RoundedButtons
                    if ( ((RoundedButton)c).getBackground().equals(COLOR_PRIMARY_ACCENT) ) {
                        iconColor = COLOR_WHITE;
                    } else {
                        iconColor = COLOR_TEXT_DARK;
                    }
                }
                g2d.setColor(iconColor);

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
                }
                g2d.dispose();
            }

            @Override public int getIconWidth() { return size; }
            @Override public int getIconHeight() { return size; }
        }
    }
}

