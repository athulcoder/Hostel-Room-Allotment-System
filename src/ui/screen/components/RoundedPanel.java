package ui.screen.components;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that draws a rounded white background with a subtle border.
 */
public class RoundedPanel extends JPanel {
    private final int cornerRadius = 24;
    private final Color borderColor = AppColors.COLOR_BORDER;

    public RoundedPanel() {
        super();
        setOpaque(false);
        // Add padding to the inside of the panel
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2.setColor(AppColors.COLOR_WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Paint border
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        g2.dispose();
    }
}
