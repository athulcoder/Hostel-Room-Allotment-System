package ui.screen.components;

import javax.swing.*;

public class RoundedPanel extends JPanel {
    public RoundedPanel() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
    }

}