package ui.screen.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
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
        setFont(FONT_BOLD);
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
        super.paintComponent(g);
    }
}
