import java.awt.*;


import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
    public DrawingPanel() {
        this.setPreferredSize(new Dimension(600, 480));
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());


    }

}

