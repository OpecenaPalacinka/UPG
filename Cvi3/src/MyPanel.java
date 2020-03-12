import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;

public class MyPanel extends JPanel{
    private final int W = 600;
    private final int H = 400;
    public MyPanel(){
        setPreferredSize(new Dimension(W,H));
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        /**
       double pomerX = (double)getWidth()/W;
       double pomerY = (double)getHeight()/H;
       double pomer = Math.min(pomerX,pomerY);

        g2d.setStroke(new BasicStroke(5));
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));

        g.drawOval(0,0,getWidth(),getHeight());
        g.drawOval(0,0,Math.min(getWidth(),getHeight()),Math.min(getWidth(),getHeight()));

       g.drawRect((int)(10*pomer),(int)(10*pomer),(int)(200*pomer),(int)(200*pomer));
        */
        drawArrow(g2d,25,30,134,227,7,12);

       /**
        g.drawLine(25,30,134,227);
        g.drawString("UPG", 50,50);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawLine(225,30,334,227);
        g.drawString("UPG", 250,50);

        */
    }
    public void drawArrow(Graphics2D g2d, double x1, double y1, double x2, double y2, double k, double l){
        g2d.draw(new Line2D.Double(x1,y1,x2,y2));
        for(int i = 0; i<100;i+=10) {
        double ux = x2-x1;
        double uy = y2-y1;
        double len = Math.hypot(ux,uy);
        ux/=len;
        uy/=len;
        double nx = uy;
        double ny = -ux;
        double cx = x2-ux*l;
        double cy = y2-uy*l;
        double d1x = cx+nx*k;
        double d1y = cy+ny*k;
        double d2x = cx-nx*k;
        double d2y = cy-ny*k;


            g2d.draw(new Line2D.Double(d1x-i, d1y-i, x2-i, y2-i));
            g2d.draw(new Line2D.Double(d2x-i, d2y-i, x2-i, y2-i));
        }
    }
}