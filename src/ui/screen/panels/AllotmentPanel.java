package ui.screen.panels;

import ui.screen.components.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class AllotmentPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable allotmentTable;
    private RoundedButton refreshBtn;
    private RoundedButton runAllotmentBtn;

    private JComboBox<String> yearBox, roomTypeBox, genderBox;

    public AllotmentPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(AppColors.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        add(createHeader(), BorderLayout.NORTH);

        // --- CENTER SCROLL PANE ---
        // This makes the entire content area (weights + table) scrollable
        JScrollPane scrollPane = new JScrollPane(createCenterContent());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Faster scrolling
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // --- LEFT TITLE ---
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Room Allotment Panel");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_TEXT_DARK);
        JLabel subtitle = new JLabel("Auto-allot compatible roommates based on preferences");
        subtitle.setFont(AppFonts.FONT_MAIN);
        subtitle.setForeground(AppColors.COLOR_TEXT_LIGHT);
        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(subtitle);

        // --- RIGHT FILTER PANEL ---
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        yearBox = new JComboBox<>(new String[]{"All Years", "1st Year", "2nd Year", "3rd Year", "4th Year"});
        roomTypeBox = new JComboBox<>(new String[]{"All Types", "2-Sharing", "4-Sharing", "6-Sharing"});
        genderBox = new JComboBox<>(new String[]{"All", "Male", "Female"});

        for (JComboBox<?> combo : new JComboBox[]{yearBox, roomTypeBox, genderBox}) {
            combo.setFont(AppFonts.FONT_MAIN);
            combo.setPreferredSize(new Dimension(130, 35));
            combo.setBackground(AppColors.COLOR_WHITE);
        }

        runAllotmentBtn = new RoundedButton(
                "⚙ Run AI Allotment",
                null,
                AppColors.COLOR_PRIMARY_ACCENT,
                AppColors.COLOR_PRIMARY_ACCENT.darker()
        );
        runAllotmentBtn.setForeground(Color.WHITE);
        runAllotmentBtn.addActionListener(e -> onRunAllotment());

        refreshBtn = new RoundedButton("↻ Refresh", null, AppColors.COLOR_PRIMARY_ACCENT_LIGHT, AppColors.COLOR_PRIMARY_ACCENT);
        refreshBtn.setForeground(Color.WHITE);

        right.add(yearBox);
        right.add(roomTypeBox);
        right.add(genderBox);
        right.add(runAllotmentBtn);
        right.add(refreshBtn);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    private JPanel createCenterContent() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Add weight control panel at top
        panel.add(createWeightPanel(), BorderLayout.NORTH);
        panel.add(createTablePanel(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * A modernized preference weight control panel.
     */
    private JPanel createWeightPanel() {
        ModernRoundedPanel weightPanel = new ModernRoundedPanel();
        weightPanel.setBackground(AppColors.COLOR_WHITE);
        // Use BorderLayout to separate title from the grid
        weightPanel.setLayout(new BorderLayout(0, 15));

        JLabel lbl = new JLabel("⚖ Preference Weight Controls:");
        lbl.setFont(AppFonts.FONT_BOLD.deriveFont(16f));
        lbl.setForeground(AppColors.COLOR_TEXT_DARK);
        // Add padding below the title
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        weightPanel.add(lbl, BorderLayout.NORTH); // Title at the top

        // Create a grid for the sliders
        // 0 rows = as many as needed, 4 columns, with 20px h-gap and 15px v-gap
        JPanel sliderGrid = new JPanel(new GridLayout(0, 4, 20, 15));
        sliderGrid.setOpaque(false);

        // Add the new slider panels
        sliderGrid.add(createWeightSliderPanel("Sleep"));
        sliderGrid.add(createWeightSliderPanel("Study"));
        sliderGrid.add(createWeightSliderPanel("Social"));
        sliderGrid.add(createWeightSliderPanel("Hobbies"));
        sliderGrid.add(createWeightSliderPanel("Lifestyle"));
        sliderGrid.add(createWeightSliderPanel("Activity"));
        sliderGrid.add(createWeightSliderPanel("Sharing"));
        sliderGrid.add(createWeightSliderPanel("Presence"));

        weightPanel.add(sliderGrid, BorderLayout.CENTER); // Grid in the center

        return weightPanel;
    }

    /**
     * Creates a self-contained panel for a single slider with its label.
     */
    private JPanel createWeightSliderPanel(String name) {
        // This panel holds the label and the slider
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false); // Transparent background

        JLabel label = new JLabel(name);
        label.setFont(AppFonts.FONT_BOLD);
        label.setForeground(AppColors.COLOR_TEXT_DARK);
        panel.add(label, BorderLayout.NORTH); // Label on top

        JSlider slider = new JSlider(0, 100, 20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(25);
        slider.setOpaque(false); // Transparent background
        // No more TitledBorder

        panel.add(slider, BorderLayout.CENTER); // Slider below label

        return panel;
    }

    private JPanel createTablePanel() {
        ModernRoundedPanel panel = new ModernRoundedPanel();
        panel.setBackground(AppColors.COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 15));

        JLabel title = new JLabel("🧩 Allotment Results");
        title.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        title.setForeground(AppColors.COLOR_TEXT_DARK);

        panel.add(title, BorderLayout.NORTH);

        String[] cols = {"Room No", "Students", "Compatibility %", "Room Type", "Status", "Action"};
        tableModel = new DefaultTableModel(new Object[0][0], cols) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5; // Only the Action column is editable
            }
        };

        allotmentTable = new JTable(tableModel);
        allotmentTable.setRowHeight(55); // Give buttons space
        allotmentTable.setFont(AppFonts.FONT_MAIN);
        allotmentTable.setShowGrid(false);
        allotmentTable.setIntercellSpacing(new Dimension(0, 0));
        allotmentTable.setSelectionBackground(AppColors.COLOR_PRIMARY_ACCENT_LIGHT);

        JTableHeader header = allotmentTable.getTableHeader();
        header.setFont(AppFonts.FONT_BOLD);
        header.setBackground(AppColors.COLOR_WHITE);
        header.setBorder(new MatteBorder(0, 0, 2, 0, AppColors.COLOR_BORDER));

        allotmentTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        allotmentTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Example data
        tableModel.addRow(new Object[]{"R101", "Athul, Alex", "92%", "2-Sharing", "Confirmed", ""});
        tableModel.addRow(new Object[]{"R102", "Bony, Navneeth", "88%", "2-Sharing", "Pending", ""});

        JScrollPane scrollPane = new JScrollPane(allotmentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ---------- ACTIONS ----------
    private void onRunAllotment() {
        JOptionPane.showMessageDialog(this,
                "AI Room Allotment process initiated...\n\n" +
                        "Filters:\nYear: " + yearBox.getSelectedItem() +
                        "\nRoom Type: " + roomTypeBox.getSelectedItem() +
                        "\nGender: " + genderBox.getSelectedItem(),
                "Allotment Running", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------- Button Renderer & Editor (with GridBagLayout for alignment) ----------
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        final RoundedButton confirmBtn, editBtn, deleteBtn, recalcBtn;

        ButtonRenderer() {
            setOpaque(true);
            // Use GridBagLayout to center buttons horizontally and vertically
            setLayout(new GridBagLayout());

            confirmBtn = new RoundedButton("✓ Confirm", null, AppColors.COLOR_SUCCESS_LIGHT, AppColors.COLOR_SUCCESS_HOVER);
            confirmBtn.setForeground(AppColors.COLOR_SUCCESS);
            editBtn = new RoundedButton("Edit", null, AppColors.COLOR_PRIMARY_ACCENT_LIGHT, AppColors.COLOR_PRIMARY_ACCENT);
            deleteBtn = new RoundedButton("Delete", null, AppColors.COLOR_DANGER_LIGHT, AppColors.COLOR_DANGER);
            recalcBtn = new RoundedButton("↻", null, AppColors.COLOR_BACKGROUND, AppColors.COLOR_BORDER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setBackground(isSelected ? table.getSelectionBackground() : AppColors.COLOR_WHITE);
            removeAll(); // Clear old components

            String status = table.getValueAt(row, 4).toString();

            // Constraints to add padding between buttons
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 4, 0, 4); // 4px horizontal padding

            if ("Confirmed".equals(status)) {
                add(editBtn, gbc);
            } else {
                add(confirmBtn, gbc);
            }
            add(recalcBtn, gbc);
            add(deleteBtn, gbc);

            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        JPanel panel;
        int row;
        RoundedButton confirmBtn, recalcBtn, editBtn, deleteBtn;
        GridBagConstraints gbc; // Reusable constraints

        ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            // Use GridBagLayout to center buttons
            panel = new JPanel(new GridBagLayout());
            panel.setOpaque(true);

            // Define constraints once
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 4, 0, 4); // 4px horizontal padding

            confirmBtn = new RoundedButton("✓ Confirm", null, AppColors.COLOR_SUCCESS_LIGHT, AppColors.COLOR_SUCCESS_HOVER);
            confirmBtn.addActionListener(e -> {
                fireEditingStopped();
                tableModel.setValueAt("Confirmed", row, 4);
            });

            recalcBtn = new RoundedButton("↻", null, AppColors.COLOR_BACKGROUND, AppColors.COLOR_BORDER);
            recalcBtn.addActionListener(e -> {
                fireEditingStopped();
                JOptionPane.showMessageDialog(null, "Recalculating for " + tableModel.getValueAt(row, 1));
            });

            editBtn = new RoundedButton("Edit", null, AppColors.COLOR_PRIMARY_ACCENT_LIGHT, AppColors.COLOR_PRIMARY_ACCENT);
            deleteBtn = new RoundedButton("Delete", null, AppColors.COLOR_DANGER_LIGHT, AppColors.COLOR_DANGER);
            deleteBtn.addActionListener(e -> {
                fireEditingStopped();
                // Check if the row still exists before removing
                if(row < tableModel.getRowCount()) {
                    tableModel.removeRow(row);
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
            this.row = row;
            panel.removeAll(); // Clear panel for reuse

            String status = table.getValueAt(row, 4).toString();

            // Add buttons using the defined constraints
            if ("Confirmed".equals(status)) {
                panel.add(editBtn, gbc);
            } else {
                panel.add(confirmBtn, gbc);
            }
            panel.add(recalcBtn, gbc);
            panel.add(deleteBtn, gbc);

            return panel;
        }
    }

    // ModernRoundedPanel remains the same
    private static class ModernRoundedPanel extends JPanel {
        private int cornerRadius = 16;
        public ModernRoundedPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
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