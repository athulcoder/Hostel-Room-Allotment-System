package ui;

import javax.swing.*;
import java.awt.*;

public class CreateRoomForm extends JPanel {

    public CreateRoomForm() {
        setLayout(new GridBagLayout());
        setBackground(new Color(250, 250, 255)); // Soft background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("🛏️  Create New Room");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(50, 50, 120));

        add(title, gbc);

        gbc.gridy++;
        add(createLabeledField("Room Number:"), gbc);
        gbc.gridy++;
        add(createLabeledField("Room Type:"), gbc);
        gbc.gridy++;
        add(createLabeledField("Floor Number:"), gbc);
        gbc.gridy++;
        add(createLabeledField("Capacity:"), gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitBtn = new JButton("Submit");
        JButton clearBtn = new JButton("Clear");

        styleButton(submitBtn, new Color(34, 139, 34));
        styleButton(clearBtn, new Color(178, 34, 34));

        buttonPanel.setOpaque(false);
        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, gbc);

        // Actions
        submitBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Room Created Successfully!");
        });

        clearBtn.addActionListener(e -> {
            for (Component comp : getComponents()) {
                if (comp instanceof JPanel) {
                    for (Component inner : ((JPanel) comp).getComponents()) {
                        if (inner instanceof JTextField) {
                            ((JTextField) inner).setText("");
                        }
                    }
                }
            }
        });
    }

    private JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField textField = new JTextField(15);

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(100, 35));
    }
}
