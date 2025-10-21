package ui.screen.components;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class SettingsIcon implements Icon {

    public static final int PENCIL = 0;
    public static final int SAVE = 1;

    private final int type;
    private final int size = 20;

    public SettingsIcon(int type) {
        this.type = type;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2.translate(x, y);

        switch (type) {
            case PENCIL:
                drawPencil(g2);
                break;
            case SAVE:
                drawSave(g2);
                break;
        }

        g2.dispose();
    }

    private void drawPencil(Graphics2D g2) {
        g2.setColor(AppColors.COLOR_TEXT_LIGHT);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Path2D.Double pencil = new Path2D.Double();
        pencil.moveTo(size * 0.2, size * 0.85);
        pencil.lineTo(size * 0.8, size * 0.25);
        pencil.lineTo(size * 0.9, size * 0.15);
        pencil.lineTo(size * 0.85, size * 0.1);
        pencil.lineTo(size * 0.75, size * 0.2);
        pencil.lineTo(size * 0.15, size * 0.8);
        pencil.closePath();
        g2.draw(pencil);

        g2.drawLine((int)(size * 0.15), (int)(size * 0.8), (int)(size * 0.1), (int)(size * 0.9));
    }

    private void drawSave(Graphics2D g2) {
        g2.setColor(AppColors.COLOR_PRIMARY_ACCENT);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Floppy disk shape
        g2.draw(new RoundRectangle2D.Double(2, 2, size - 4, size - 4, 4, 4));
        g2.fill(new Rectangle2D.Double(6, 2, size - 12, size/2.5));
        g2.draw(new Rectangle2D.Double(4, size - 8, size - 8, 6));

    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
