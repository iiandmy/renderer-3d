package panel;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
