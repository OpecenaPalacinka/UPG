
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingPanel extends JPanel {

    private long startTime = System.currentTimeMillis();

    private final int W = 1600;
    private final int H = 700;
    public DrawingPanel() {
        setPreferredSize(new Dimension(W, H));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;

        final int GS = 30;
        g2.setColor(Color.GREEN);
        g2.fillRect(0,this.getHeight()-GS,this.getWidth(),GS);
        g2.translate(0,this.getHeight()-GS);

        drawTraktor(g2);

    }


    public void drawTraktor(Graphics2D g2){
         double r1 = 150;
         double r2 = 100;
         double d = 350;

        final double L = 20;
        final double W = 300;
        final double H = 400;
        final double MW = 250;
        final double MH = 200;
        double U = r2/2;

        long currTime = System.currentTimeMillis();
        long elapsed = currTime - startTime;
        double zacatek = 50*elapsed/1000.0;

        if(zacatek>this.getWidth()){
            zacatek -= this.getWidth()+r1+r2+d;
        }
        g2.translate(zacatek,0);

        g2.setColor(Color.RED);
        g2.fill(new Rectangle2D.Double(L,-U - H,W,H));
        g2.fill(new Rectangle2D.Double(L+W,-U - MH,MW,MH));

        drawWheels(r1,r2,d,g2);

    }

    private void drawWheels(double r1, double r2, double d, Graphics2D g2){

        g2.translate(r1,-r1);
        drawWheel(r1,g2);
        g2.translate(d,r1-r2);
        drawWheel(r2,g2);


    }

    public void drawWheel(double r, Graphics2D g2){

        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Double(-r,-r,2*r,2*r));

        double r2 = 0.8*r; //polomer disku
        g2.setColor(Color.DARK_GRAY);
        g2.fill(new Ellipse2D.Double(-r2,-r2,2*r2,2*r2));

        final double r3 = 20; //polomer oje
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(-r3,-r3,2*r3,2*r3));

        final double d =45;
        final double rb =5;

        AffineTransform oldTR = g2.getTransform();

        long currTime = System.currentTimeMillis();
        long elapsed = currTime - startTime;
        final double SPA = Math.PI;

        g2.rotate(Math.toRadians(45) + SPA * elapsed / 1000.0);
        for (int i = 0;i<4;i++) {
            g2.translate(d, 0);
            g2.fill(new Ellipse2D.Double(-rb, -rb, 2 * rb, 2 * rb));
            g2.translate(-d, 0);
            g2.rotate(Math.toRadians(90));
        }
        g2.setTransform(oldTR);
    }
}
