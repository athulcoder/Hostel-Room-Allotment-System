package ui.screen.panels;

import dao.RoomDAO;
import models.Room;
import models.Student;
import ui.screen.Dashboard;
import ui.screen.components.AppColors;
import ui.screen.components.AppFonts;
import ui.screen.components.RoundedButton;
import utils.SessionManager;
// We will not import their RoundedPanel as it lacks painting logic.
// Instead, we create our own inner class that works.

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public class RoomPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable roomsTable;


    //card data
    private JLabel totalRoomCard;
    private JLabel roomAllottedCard;
    private JLabel totalStudentCard;
    private JLabel availableRoomsCard;


    private RoundedButton refreshBtn;
    Object[][] data;
    ArrayList<Room> rooms;
    public RoomPanel() {
        // Main panel setup
        setLayout(new BorderLayout(0, 20)); // Increased gap
        setBackground(AppColors.COLOR_BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30)); // More padding

        // Create and add components
        add(createHeader(), BorderLayout.NORTH);
        add(createCenterContent(), BorderLayout.CENTER);
    }

    /**
     * Creates the top header with title, subtitle, and action buttons.
     */
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Transparent background

        // Left side: Title and Subtitle
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel title = new JLabel("Room Management");
        title.setFont(AppFonts.FONT_HEADER);
        title.setForeground(AppColors.COLOR_TEXT_DARK);

        JLabel subtitle = new JLabel("Add, edit, delete rooms and view availability");
        subtitle.setFont(AppFonts.FONT_MAIN);
        subtitle.setForeground(AppColors.COLOR_TEXT_LIGHT);

        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(subtitle);

        // Right side: Search, Filters, and Add Button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        // Search Field (Modern Style)
        JTextField search = new JTextField("  Search rooms...");
        search.setFont(AppFonts.FONT_MAIN);
        search.setForeground(AppColors.COLOR_TEXT_LIGHT);
        search.setPreferredSize(new Dimension(220, 40));
        search.setBackground(AppColors.COLOR_WHITE);
        search.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AppColors.COLOR_BORDER, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        // Add placeholder text functionality
        search.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (search.getText().equals("  Search rooms...")) {
                    search.setText("");
                    search.setForeground(AppColors.COLOR_TEXT_DARK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (search.getText().isEmpty()) {
                    search.setText("  Search rooms...");
                    search.setForeground(AppColors.COLOR_TEXT_LIGHT);
                }
            }
        });

        // Add Room Button

        RoundedButton addBtn = new RoundedButton(
                "+ Add Room",
                null,
                AppColors.COLOR_PRIMARY_ACCENT,
                AppColors.COLOR_PRIMARY_ACCENT.darker()
        );
        addBtn.setForeground(AppColors.COLOR_WHITE);
        addBtn.addActionListener(e -> addNewRoom());
        addBtn.setForeground(AppColors.COLOR_WHITE);
        right.add(search);
        // We can add styled JComboBoxes here if needed
        right.add(Box.createHorizontalStrut(10));
        right.add(addBtn);

        //refresh button
        refreshBtn = new RoundedButton(
                "Refresh", null,
                AppColors.COLOR_PRIMARY_ACCENT,
                AppColors.COLOR_PRIMARY_ACCENT_LIGHT
        );
        refreshBtn.setForeground(AppColors.COLOR_WHITE);
        refreshBtn.setForeground(AppColors.COLOR_WHITE);
        right.add(Box.createHorizontalStrut(10));
        right.add(refreshBtn);


        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }


    /*
    Creating value JLabel for each Card

     */
    private JLabel createJLabelForCard(JLabel label){
        label = new JLabel();
        label.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        label.setForeground(AppColors.COLOR_TEXT_DARK);
        return label;
    }



    /**
     * Creates the panel holding the metric cards.
     */
    private JPanel createMetrics() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 20, 0));
        panel.setOpaque(false);


        //adding the value here for each card on top
        JPanel card1 = createCard("Total Room");
        totalRoomCard = new JLabel();
        totalRoomCard.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        totalRoomCard.setForeground(AppColors.COLOR_TEXT_DARK);
        card1.add(totalRoomCard);

        JPanel card2 =createCard("Rooms Allotted");
        roomAllottedCard = new JLabel();
        roomAllottedCard.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        roomAllottedCard.setForeground(AppColors.COLOR_TEXT_DARK);
        card2.add(roomAllottedCard);

        JPanel card3= createCard("Available Rooms");
        availableRoomsCard = new JLabel();
        availableRoomsCard.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        availableRoomsCard.setForeground(AppColors.COLOR_TEXT_DARK);
        card3.add(availableRoomsCard);

        JPanel card4 = createCard("Total Students");
        totalStudentCard = new JLabel();
        totalStudentCard.setFont(AppFonts.FONT_HEADER.deriveFont(32f));
        totalStudentCard.setForeground(AppColors.COLOR_TEXT_DARK);
        card4.add(totalStudentCard);



        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        panel.add(card4);

        return panel;
    }

    /**
     * Helper method to create a single styled metric card.
     */
    private JPanel createCard(String label) {
        ModernRoundedPanel card = new ModernRoundedPanel();
        card.setBackground(AppColors.COLOR_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label);
        l.setFont(AppFonts.FONT_MAIN);
        l.setForeground(AppColors.COLOR_TEXT_LIGHT);

        card.add(l);
        card.add(Box.createVerticalStrut(8));
        return card;
    }

    /**
     * Creates the main content area (metrics + table).
     */
    private JPanel createCenterContent() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);
        panel.add(createMetrics(), BorderLayout.NORTH);
        panel.add(createTablePanel(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the panel containing the "All Rooms" table.
     */
    private JPanel createTablePanel() {
        // Main container with rounded corners
        ModernRoundedPanel panel = new ModernRoundedPanel();
        panel.setBackground(AppColors.COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 15));

        // Header for the table
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 5, 0, 5)); // Align with table padding

        JLabel title = new JLabel("All Rooms");
        title.setFont(AppFonts.FONT_BOLD.deriveFont(18f));
        title.setForeground(AppColors.COLOR_TEXT_DARK);
        header.add(title, BorderLayout.WEST);

        // Legend
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        legend.setOpaque(false);
        legend.add(createLegendItem(new Color(34, 197, 94), "Available"));
        legend.add(createLegendItem(new Color(249, 115, 22), "Partially filled"));
        legend.add(createLegendItem(new Color(239, 68, 68), "Full"));

        header.add(legend, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        //Table Cols
        String[] cols = {"", "Room ID", "Room Type", "Capacity", "Occupancy", "Floor Number", "Action"};

        tableModel = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only "Actions" column is "editable"
            }
        };

        roomsTable = new JTable(tableModel);
        setupTableStyle();

        // Add renderers and editors
        TableColumnModel colModel = roomsTable.getColumnModel();
        colModel.getColumn(0).setCellRenderer( new StatusRenderer());
        colModel.getColumn(0).setMaxWidth(40);

        colModel.getColumn(6).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        colModel.getColumn(6).setMinWidth(180); // Give buttons space

        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.getViewport().setBackground(AppColors.COLOR_WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Applies modern styling to the JTable.
     */
    private void setupTableStyle() {
        roomsTable.setRowHeight(50);
        roomsTable.setOpaque(false);
        roomsTable.setShowGrid(false);
        roomsTable.setIntercellSpacing(new Dimension(0, 0));
        roomsTable.setFont(AppFonts.FONT_MAIN);
        roomsTable.setForeground(AppColors.COLOR_TEXT_DARK);
        roomsTable.setSelectionBackground(AppColors.COLOR_PRIMARY_ACCENT_LIGHT);
        roomsTable.setSelectionForeground(AppColors.COLOR_TEXT_DARK);

        // Style the header
        JTableHeader header = roomsTable.getTableHeader();
        header.setFont(AppFonts.FONT_BOLD);
        header.setForeground(AppColors.COLOR_TEXT_LIGHT);
        header.setBackground(AppColors.COLOR_WHITE);
        header.setOpaque(false);
        header.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 2, 0, AppColors.COLOR_BORDER), // Bottom border
                new EmptyBorder(10, 5, 10, 5)
        ));
    }

    /**
     * Helper to create a single legend item (e.g., "• Available").
     */
    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        item.setOpaque(false);

        // A simple colored panel for the dot
        JPanel dot = new JPanel();
        dot.setBackground(color);
        dot.setPreferredSize(new Dimension(10, 10));

        JLabel label = new JLabel(text);
        label.setFont(AppFonts.FONT_MAIN);
        label.setForeground(AppColors.COLOR_TEXT_LIGHT);

        item.add(dot);
        item.add(label);
        return item;
    }

    /**
     * Handles the logic for showing the "Add New Room" dialog.
     */
    private void addNewRoom() {
        RoomDialog dialog = new RoomDialog(
                SwingUtilities.getWindowAncestor(this),
                "Add New Room",
                null // 'null' data signifies a new room
        );
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            tableModel.addRow(dialog.getRoomData());
            refreshBtn.doClick();
        }
    }

    /**
     * Handles the logic for showing the "Edit Room" dialog.
     */
    private void editRoom(int row) {
        Object[] rowData = new Object[6];
        rowData[0] = tableModel.getValueAt(row, 0); // Status
        rowData[1] = tableModel.getValueAt(row, 1); // ID
        rowData[2] = tableModel.getValueAt(row, 2); // Type
        rowData[3] = tableModel.getValueAt(row, 3); // Capacity
        rowData[4] = tableModel.getValueAt(row, 4); // Occupancy
        rowData[5] = tableModel.getValueAt(row, 5); // floor

        RoomDialog dialog = new RoomDialog(
                SwingUtilities.getWindowAncestor(this),
                "Edit Room - " + rowData[1],
                rowData // Pass existing data to populate the form
        );
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Object[] updatedData = dialog.getRoomData();
            refreshBtn.doClick();
            // Update the model row
            for (int i = 0; i < updatedData.length; i++) {
                tableModel.setValueAt(updatedData[i], row, i);
            }
        }
        refreshBtn.doClick();
    }

    /**
     * Handles the logic for deleting a room.
     */
    private void deleteRoom(int row) {
        String roomID = tableModel.getValueAt(row, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete room " + roomID + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            RoomDAO roomDAO = new RoomDAO();
            roomDAO.deleteRoom(roomID);
            tableModel.removeRow(row);
            refreshBtn.doClick();
        }
    }


    // -----------------------------------------------------------------
    // INNER CLASS: ModernRoundedPanel (Replaces user's broken one)
    // -----------------------------------------------------------------
    /**
     * A JPanel that paints its background with rounded corners.
     */
    private class ModernRoundedPanel extends JPanel {
        private int cornerRadius = 16;

        public ModernRoundedPanel() {
            super();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint the background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            g2.dispose();

            // Let the superclass paint children
            super.paintComponent(g);
        }
    }

    // -----------------------------------------------------------------
    // INNER CLASS: StatusRenderer (For Table Dots)
    // -----------------------------------------------------------------
    /**
     * Renders the colored dot in the first table column.
     */
    private class StatusRenderer extends JPanel implements TableCellRenderer {
        private Color dotColor = Color.GRAY;

        public StatusRenderer() {
            setOpaque(true);
        }


        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {


            String status = "Available";
            if(value.equals(true)){
                status="Full";
            }

            switch (status) {
                case "Available":
                    dotColor = new Color(34, 197, 94); // Green
                    break;
//                case "Partial":
//                    dotColor = new Color(249, 115, 22); // Orange
//                    break;
                case "Full":
                    dotColor = new Color(239, 68, 68); // Red
                    break;
                default:
                    dotColor = Color.LIGHT_GRAY;
            }

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(AppColors.COLOR_WHITE);
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = (getWidth() - 10) / 2;
            int y = (getHeight() - 10) / 2;
            g2.setColor(dotColor);
            g2.fillOval(x, y, 10, 10);
            g2.dispose();
        }
    }

    // -----------------------------------------------------------------
    // INNER CLASS: ButtonRenderer (For Table Buttons)
    // -----------------------------------------------------------------
    /**
     * Renders the "Edit" and "Delete" RoundedButtons in the table.
     */
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final RoundedButton editBtn;
        private final RoundedButton delBtn;

        public ButtonRenderer() {
            setOpaque(true);
            setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));

            editBtn = new RoundedButton(
                    "Edit",
                    null,
                    AppColors.COLOR_PRIMARY_ACCENT_LIGHT,
                    AppColors.COLOR_PRIMARY_ACCENT.darker()
            );
            editBtn.setForeground(AppColors.COLOR_PRIMARY_ACCENT);

            delBtn = new RoundedButton(
                    "Delete",
                    null,
                    AppColors.COLOR_DANGER_LIGHT,
                    AppColors.COLOR_DANGER_HOVER
            );
            delBtn.setForeground(AppColors.COLOR_DANGER);

            add(editBtn);
            add(delBtn);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(AppColors.COLOR_WHITE);
            }
            // Adjust vertical alignment
            setBorder(new EmptyBorder((table.getRowHeight() - 40) / 2, 0, 0, 0));
            return this;
        }
    }

    // -----------------------------------------------------------------
    // INNER CLASS: ButtonEditor (Handles Table Button Clicks)
    // -----------------------------------------------------------------
    /**
     * Handles the click events for the "Edit" and "Delete" buttons.
     */
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
            panel.setOpaque(true);

            RoundedButton editBtn = new RoundedButton(
                    "Edit",
                    null,
                    AppColors.COLOR_PRIMARY_ACCENT_LIGHT,
                    AppColors.COLOR_PRIMARY_ACCENT.darker()
            );
            editBtn.setForeground(AppColors.COLOR_PRIMARY_ACCENT);

            RoundedButton delBtn = new RoundedButton(
                    "Delete",
                    null,
                    AppColors.COLOR_DANGER_LIGHT,
                    AppColors.COLOR_DANGER_HOVER
            );
            delBtn.setForeground(AppColors.COLOR_DANGER);

            editBtn.addActionListener(e -> {
                fireEditingStopped();
                editRoom(row);

            });

            delBtn.addActionListener(e -> {
                fireEditingStopped();
                deleteRoom(row);
            });

            panel.add(editBtn);
            panel.add(delBtn);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            panel.setBackground(isSelected ? table.getSelectionBackground() : AppColors.COLOR_WHITE);
            panel.setBorder(new EmptyBorder((table.getRowHeight() - 40) / 2, 0, 0, 0));
            return panel;
        }

        public Object getCellEditorValue() {
            return "";
        }
    }


    // -----------------------------------------------------------------
    // INNER CLASS: RoomDialog (The Popup Form)
    // -----------------------------------------------------------------
    /**
     * A modal JDialog for adding or editing room details.
     */
    private class RoomDialog extends JDialog {
        private boolean saved = false;

        private JTextField roomIdField;
        private JComboBox<String> roomTypeCombo;
        private JTextField capacityField;
        private JTextField occupancyField;
        private JTextField floorField;
        private Object[] initialData;

        public RoomDialog(Window owner, String title, Object[] data) {
            super(owner, title, ModalityType.APPLICATION_MODAL);
            this.initialData = data; // Store data for editing

            setSize(450, 480);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout());
            getContentPane().setBackground(AppColors.COLOR_WHITE);

            // --- Form Panel ---
            JPanel formPanel = new JPanel();
            formPanel.setOpaque(false);
            formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            formPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 5, 8, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            // Init components
            roomIdField = new JTextField(20);
            roomTypeCombo = new JComboBox<>(new String[]{"2 sharing", "4 sharing","6 sharing"});
            capacityField = new JTextField(20);
            occupancyField = new JTextField(20);
            floorField = new JTextField(20);

            // Style components
            styleField(roomIdField);
            styleField(capacityField);
            styleField(occupancyField);
            styleField(roomTypeCombo);
            styleField(floorField);

            // Add components to form
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(createLabel("Room ID"), gbc);
            gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
            formPanel.add(roomIdField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            formPanel.add(createLabel("Room Type"), gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            formPanel.add(roomTypeCombo, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(createLabel("Capacity"), gbc);
            gbc.gridx = 1; gbc.gridy = 2;
            formPanel.add(capacityField, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            formPanel.add(createLabel("Occupancy"), gbc);
            gbc.gridx = 1; gbc.gridy = 3;
            formPanel.add(occupancyField, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            formPanel.add(createLabel("floor Number"), gbc);
            gbc.gridx = 1; gbc.gridy = 4;
            formPanel.add(floorField, gbc);

            // Add spacer
            gbc.gridy = 4; gbc.weighty = 1.0;
            formPanel.add(Box.createVerticalGlue(), gbc);

            // --- Button Panel ---
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(new EmptyBorder(15, 30, 20, 30));

            RoundedButton cancelBtn = new RoundedButton(
                    "Cancel",
                    null,
                    AppColors.COLOR_BACKGROUND,
                    AppColors.COLOR_BORDER
            );
            cancelBtn.setForeground(AppColors.COLOR_TEXT_DARK);
            cancelBtn.addActionListener(e -> dispose());

            RoundedButton saveBtn = new RoundedButton(
                    "Save Room",
                    null,
                    AppColors.COLOR_PRIMARY_ACCENT,
                    AppColors.COLOR_PRIMARY_ACCENT.darker()
            );
            saveBtn.setForeground(AppColors.COLOR_WHITE);
            saveBtn.addActionListener(e -> onSave());

            buttonPanel.add(cancelBtn);
            buttonPanel.add(saveBtn);

            add(formPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            // Populate form if this is an edit
            if (initialData != null) {
                roomIdField.setText(initialData[1].toString());
                roomIdField.setEnabled(false);
                roomTypeCombo.setSelectedItem(initialData[2].toString());
                capacityField.setText(initialData[3].toString());
                occupancyField.setText(initialData[4].toString());
                floorField.setText(initialData[5].toString());
            }
        }

        // Helper to style form fields
        private void styleField(JComponent field) {
            field.setFont(AppFonts.FONT_MAIN);
            field.setPreferredSize(new Dimension(200, 40));
            if (field instanceof JTextField) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(AppColors.COLOR_BORDER, 1),
                        new EmptyBorder(5, 10, 5, 10)
                ));
            }
        }

        // Helper to create styled labels
        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(AppFonts.FONT_BOLD);
            label.setForeground(AppColors.COLOR_TEXT_DARK);
            return label;
        }

        // --- Public Methods ---
        public boolean isSaved() {
            return saved;
        }

        public Object[] getRoomData() {
            // This is called *after* onSave()
            return initialData;
        }

        // --- Internal Logic ---
        private void onSave() {
            try {
                String id = roomIdField.getText();
                String type = (String) roomTypeCombo.getSelectedItem();
                int capacity = Integer.parseInt(capacityField.getText());
                int occupancy = Integer.parseInt(occupancyField.getText());
                int floorNumber = Integer.parseInt(floorField.getText());

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Room ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (occupancy > capacity) {
                    JOptionPane.showMessageDialog(this, "Occupancy cannot exceed capacity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Determine status
                String status ="";
                if (occupancy == capacity) {
                    status = "Full";
                     // Cap occupancy
                }
                else{
                    status ="Available";
                }

                // Store the result
                //Create dao object to communicate with db
                RoomDAO roomDAO = new RoomDAO();
                Room room = new Room(roomIdField.getText(),String.valueOf(roomTypeCombo.getSelectedItem()),Integer.parseInt(floorField.getText()),Integer.parseInt(capacityField.getText()), SessionManager.getCurrentAdmin().getHostelId());
                    room.setOccupancy(occupancy);
                //if there was initial data that mean there was already room
                if(initialData !=null){
                    roomDAO.updateRoom(room);
                }
                else{
                    roomDAO.createRoom(room);
                }

                this.initialData = new Object[]{status, id, type, capacity, occupancy,floorNumber};
                this.saved = true;


                dispose();// Close the dialog

            } catch (NumberFormatException ex) {
                System.out.println(ex.getStackTrace());
                JOptionPane.showMessageDialog(this, "Capacity and Occupancy must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }



    public JLabel getTotalRoomCard(){
        return totalRoomCard;
    }
    public JLabel getRoomAllottedCard(){
        return roomAllottedCard;
    }
    public JLabel getTotalStudents(){
        return totalStudentCard;
    }
    public JLabel getAvailableRoomsCard(){
        return availableRoomsCard;
    }
    //setters to set the header cards data

    public void setTotalRoomCard(int totalRoom){
        totalRoomCard.setText(String.valueOf(totalRoom));
    }

    public void setRoomAllottedCard(int roomAllotted){
        roomAllottedCard.setText(String.valueOf(roomAllotted));
    }

    public void setTotalStudents(int totalStudents){
        totalStudentCard.setText(String.valueOf(totalStudents));
    }

    public void setAvailableRoomsCard(int availableRooms){
        availableRoomsCard.setText(String.valueOf(availableRooms));
    }


    public void setRooms(ArrayList<Room> rooms){
        this.rooms = rooms;

        tableModel.setRowCount(0);

        for (Room room : rooms) {
            Object[] row = {
                    room.getRoomFull(),
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getCapacity(),
                    room.getOccupancy(),
                    room.getFloorNumber()
            };

            tableModel.addRow(row);
        }

    }

    public RoundedButton getRefreshBtn(){
        return refreshBtn;
    }
}