import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.Double;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements Printable {
    public DrawingPanel() {
        this.setPreferredSize(new Dimension(600, 480));
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2.translate(this.getWidth()/2,this.getHeight()/2);
        drawPacman(200,g2);
    }

    private void drawPacman(double r, Graphics2D g2) {
        double ro = r * 0.25;
        double ri = ro * 0.5;
        double ecx = 0;
        double ecy = -r * 0.5;

        Path2D eye = new Path2D.Double();
        eye.moveTo(ecx + ro, ecy);
        eye.lineTo(ecx, ecy + ro);
        eye.lineTo(ecx - ro, ecy);
        eye.lineTo(ecx, ecy - ro);
        eye.closePath();

        eye.moveTo(ecx + ri, ecy);
        eye.lineTo(ecx, ecy - ri);
        eye.lineTo(ecx - ri, ecy);
        eye.lineTo(ecx, ecy + ri);
        eye.closePath();

        Area pacman = new Area(
                new Ellipse2D.Double(-r,-r,2*r,2*r));
        pacman.subtract(new Area(eye));

        Path2D trojuhelník = new Path2D.Double();
        trojuhelník.moveTo(2*r,-0.5*r);
        trojuhelník.lineTo(0,0);
        trojuhelník.lineTo(2*r,0.5*r);
        trojuhelník.closePath();

        pacman.subtract(new Area(trojuhelník));


       g2.setPaint(new RadialGradientPaint(
               new Point2D.Double(0,0), (float) (2*r), new float[] {0,1},
               new Color[] {Color.YELLOW, Color.red}
       ));
       g2.fill(pacman);

        g2.setPaint(new LinearGradientPaint(
                new Point2D.Double(0,0),
                new Point2D.Double(0,r),
                new float[] {0,0.8f,1},
                new Color[] {
                        new Color(0,0,0,0),
                        new Color(0,0,0,0.2f),
                        new Color(0,0,0,1f)
                }
        ));
        g2.fill(pacman);

        g2.setClip(pacman);
        g2.setColor(Color.RED);

        final int delta = 50;
        for(int i = (int)-r;i < r;i+=delta){
            for (int x = (int)-r;x < r;x+=delta){
                g2.fillOval(x,i,10,10);
            }
        }

    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex > 0){
            return NO_SUCH_PAGE;
        }

        Graphics2D g2 = (Graphics2D)graphics;
        g2.translate(pageFormat.getWidth()/2, pageFormat.getHeight()/2);


        drawPacman(50*(72/25.4),g2);

        return 0;
    }
}

