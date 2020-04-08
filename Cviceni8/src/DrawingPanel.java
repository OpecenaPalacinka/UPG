import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

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

        drawFlower(200,200,g2);

    }

    public void drawFlower(int tx, int ty, Graphics2D g2) {
        g2.translate(tx,ty);

        g2.setStroke(new BasicStroke(8));
        g2.setColor(Color.GREEN);
        g2.drawLine(0,0,0,500);

        final int R = 40;

        g2.setColor(Color.YELLOW);
        g2.fillOval(-R,-R,2*R,2*R);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        g2.drawOval(-R,-R,2*R,2*R);

        final int angle = 20;
        final int TX = 105;
        final int TY = 0;
        final int RX = 60;
        final int RY = 10;

        for(int i = 0; i<360/angle;i++){
            AffineTransform at = g2.getTransform();
            g2.rotate(Math.toRadians(i*angle));
            g2.translate(TX,TY);

            g2.setColor(Color.WHITE);
            g2.fillOval(-RX,-RY,2*RX,2*RY);

            g2.setColor(Color.BLACK);
            g2.drawOval(-RX,-RY,2*RX,2*RY);

            g2.setTransform(at);
        }
    }
}
