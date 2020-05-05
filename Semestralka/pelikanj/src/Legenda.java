import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Třída, která má za úkol vykreslit legendu
 *
 * @author Jan Pelikán
 */
public class Legenda extends JPanel {

    /**
     * Konstruktor, který nastavuje velikost vytvořeného okna
     */
    public Legenda(){
        setPreferredSize(new Dimension(Simulator.getDimension().x,25));
    }

    /**
     * Metoda vykresluje listu
     * @param g grafický kontext
     */
    public void paint(Graphics g){
        lista(g);
    }

    /**
     * Metoda vykresluje v horni casti barevnou listu (legendu), kde zobrazuje barvy podle vysky
     * @param g graficky kontext
     */
    public void lista (Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        DrawingPanel.prvniBarva.getGreen();
        //Inspirace ze cvičení 6
        LinearGradientPaint gradientPaint = new LinearGradientPaint(
                new Point2D.Double(0,0),
                new Point2D.Double(getWidth(),0),
                new float[]{0,1},
                new Color[]{DrawingPanel.posledniBarva,new Color(31,231, 57)}
        );
        g2.setPaint(gradientPaint);
        g2.fillRect(0,0,getWidth(),DrawingPanel.velikostListy);
        g2.translate(0,DrawingPanel.velikostListy);
        g2.setFont(new Font("Calibri",Font.BOLD,20));
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(DrawingPanel.minTeren+"m.n.m.",0,0);
        g2.drawString(DrawingPanel.maxTeren+"m.n.m.",getWidth()-g2.getFontMetrics().stringWidth(DrawingPanel.maxTeren+"m.n.m."),0);
    }

}
