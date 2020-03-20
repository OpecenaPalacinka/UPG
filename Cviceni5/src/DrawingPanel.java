
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;


public class DrawingPanel extends JPanel {
    private int N = 5; // počet cípů hvězdy
    private double R = 200; // velikost hvězdy
    private Path2D star = null;

    private final int W = 640;
    private final int H = 480;
    public DrawingPanel() {
        setPreferredSize(new Dimension(W, H));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(star!=null && star.contains(mouseEvent.getPoint())){
                    R=R*0.5;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }


    @Override
    public void paint(Graphics g) {
        //super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,this.getWidth(),this.getHeight());

        drawStar(g2);

    }

    public void drawStar(Graphics2D g2) {
        g2.setColor(Color.DARK_GRAY);

        double R2 = R * 0.5;
        double fi = 2 * Math.PI / N;


        star = new Path2D.Double();
        star.moveTo(R, 0);

        for (int i = 0; i < N; i++) {
            double x = R2 * Math.cos(i * fi + fi * 0.5);
            double y = R2 * Math.sin(i * fi + fi * 0.5);
            star.lineTo(x, y);

            x=R * Math.cos((i+1)*fi);
            y=R * Math.sin((i+1)*fi);
            star.lineTo(x, y);

        }

        AffineTransform at = new AffineTransform();

        at.translate(this.getWidth()/2, this.getHeight()/2);
        at.rotate(-Math.PI*0.5);

        star.transform(at);
        g2.fill(star);

    }

    public void makeLarger() {
        this.R *=1.25;
        this.repaint();
    }

    public void reset() {
        this.R=200;
        this.repaint();
    }
}
