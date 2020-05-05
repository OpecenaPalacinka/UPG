import waterflowsim.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Třída, která má nastarost vykreslování podle simulátoru, používá se v hlavní třídě L01_SpusteniSimulatoru
 * @author Jan Pelikán
 */
public class DrawingPanel extends JPanel {
    /**
     * Privátní proměnné celé třídy, velikost mapy (x a y)
     */
    public static double maxX, maxY;
    /**
     * Privátní proměnné celé třídy, začátek souřadnicového systému (inicializuju na nulu
     * (měli by být asi inicialiované na Simulator.getStart().x/y))
     */
    public static double minX, minY;
    /**
     * Privátní proměnná celé třídy, udává počet vodních zdrojů
     */
    private int pocetVodnichZdroju = Simulator.getWaterSources().length;
    /**
     * Privátní proměnná celé třídy, vytváří pole se zdroji vody
     */
    private WaterSourceUpdater[] vodniZdroje = Simulator.getWaterSources();
    /**
     * Proměnná celé třídy, jde o hodnotu škálování
     */
    static double scale;
    /** Privátní proměnná celé třídy, ukládá minimální a maximální výšku terénu*/
    public static double minTeren, maxTeren;
    /** Privátní proměnné celé třídy, ukládají barvu pro výškové zobrazení*/
    public static Color posledniBarva = new Color(0, 25,200);
    /** Privátní proměnné celé třídy, ukládají barvu pro výškové zobrazení*/
    public static Color prvniBarva = new Color(0,posledniBarva.getGreen(),0);
    /** Výška lišty*/
    public static int velikostListy = 25;
    /**
     * Proměnná celé třídy, posun souřadnicového systému
     */
    int posunSouradniceX, posunSouradniceY;
    /** DeltaX */
    public static double deltaX = Simulator.getDelta().x;
    /** DeltaY*/
    public static double deltaY = Simulator.getDelta().y;
    /** Hlavní čára šipky*/
    public static Line2D hlavniCara;

    /**
     * "Poslední" metoda, která dává dohromady všechny metody nad ní, volá metodu drawWaterFlowState
     * Vykresluje výsledek
     * @param g grafický kontext
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        computeModel2WindowTransformation(this.getWidth(),this.getHeight());

        if(scale > 1) {
            g2.setStroke(new BasicStroke(2));
        }

        AffineTransform puvodniTransformace = g2.getTransform();
        g2.translate(posunSouradniceX, posunSouradniceY);
        g2.scale(scale, scale);

        drawWaterFlowState(g2);

        g2.setTransform(puvodniTransformace);
    }

    /**
     * Zkopírováno z CW z cvičení3
     * Metoda dělá šipky podle zadané pozice a vektoru
     * @param position získání pozice pro x1/x2 a y1/y2
     * @param dirFlow získání vektoru pro směr šipky
     * @param g grafický kontext
     */
    public void drawArrow(Point2D position, Vector2D dirFlow, Graphics2D g){
        double x1 = position.getX() ;
        double x2 = position.getX() - dirFlow.x.doubleValue() * 60;
        double y1 = position.getY() ;
        double y2 = position.getY() - dirFlow.y.doubleValue() * 60;
        double vx = x2 - x1;
        double vy = y2 - y1;
        double vLength = Math.sqrt(vx*vx + vy*vy);
        double vNormX = vx / vLength;
        double vNormY = vy / vLength;
        double vArrowX = vNormX * 15;
        double vArrowY = vNormY * 15;
        double kx = -vArrowY;
        double ky = vArrowX;
        kx *= 0.25;
        ky *= 0.25;
        hlavniCara = new Line2D.Double(x1, y1, x2, y2);
        g.draw(hlavniCara);
        Line2D horniSipkaCara = new Line2D.Double(x2, y2, x2 - vArrowX + kx, y2 - vArrowY + ky);
        g.draw(horniSipkaCara);
        Line2D spodniSipkaCara = new Line2D.Double(x2, y2, x2 - vArrowX - kx, y2 - vArrowY - ky);
        g.draw(spodniSipkaCara);
    }

    /**
     * Metoda vykreslí aktuální stav toku vody v krajině, poskytnutý simulátorem, prostřednictvím
     * níže popsaných metod drawTerrain a drawWaterLayer.
     * @param g grafický kontext
     */
    public void drawWaterFlowState(Graphics2D g){
        drawTerrain(g);
        drawWaterLayer(g);
    }

    /**
     * Vykresluje barvu mapy podle vysky v danem bode
     * @param g grafický kontext
     */
    public void drawTerrain(Graphics2D g){
        Cell[] cells = Simulator.getData();
        minTeren = cells[0].getTerrainLevel();
        maxTeren = cells[0].getTerrainLevel();
        //hledám nejmenší a největší terén
        for (Cell cell : cells) {
            double pruchod = cell.getTerrainLevel();
            if (pruchod < minTeren) {
                minTeren = pruchod;
            }
            if (pruchod > maxTeren) {
                maxTeren = pruchod;
            }
        }

        int[] teren = new int[cells.length];
        double rozdil = maxTeren-minTeren;

        for (int i =0;i<cells.length;i++){
            //cviceni6
            int modra = (int) (cells[i].getTerrainLevel()/rozdil*posledniBarva.getBlue());
            teren[i] = (modra<<8) | (posledniBarva.getGreen() << 2);
        }

        BufferedImage terenObraz = new BufferedImage((int)maxX,(int)maxY, BufferedImage.TYPE_3BYTE_BGR);
        terenObraz.setRGB(0,0,(int)maxX,(int)maxY,teren,0,(int)maxX);
        g.drawImage(terenObraz,0,0,(int) maxX,(int) maxY,null);
    }

    /**
     * Vykresluje vodní plochy hlavně pomocí metody isDry, volá zde metodu drawWaterSources pro
     * vykreslení hlavních vodních zdrojů
     * @param g grafický kontext
     */
    public void drawWaterLayer(Graphics2D g){
        int bunka;
        int bunkaNasledujici;
        double vetsi = Math.max(deltaX,deltaY);
        double mensi = Math.min(deltaX,deltaY);
        double pomer = vetsi/mensi;
        g.setColor(new Color(0,120,200));
        Path2D water = new Path2D.Double();
        for (int i = 0; i < maxY-1; i++) {
            for (int y = 0; y < maxX-1; y++) {
                bunka = i * (int) maxX + y;
                bunkaNasledujici = (i + 1) * (int) maxX + (y + 1);
                Cell bunka1 = Simulator.getData()[bunka];
                Cell bunka2 = Simulator.getData()[bunkaNasledujici];

                if (!bunka1.isDry()) {
                    if (!bunka2.isDry()) {
                        if (vetsi == deltaX) {
                            water.moveTo(y + Simulator.getDelta().x * pomer,
                                    i + Simulator.getDelta().y);
                            water.lineTo(y + Simulator.getDelta().x * pomer,
                                    i + Simulator.getDelta().y);
                        }
                        if(vetsi == deltaY){
                            water.moveTo(y + Simulator.getDelta().x,
                                    i + Simulator.getDelta().y * pomer );
                            water.lineTo(y + Simulator.getDelta().x,
                                    i + Simulator.getDelta().y * pomer);
                        }
                        if (deltaY == deltaX){
                            water.moveTo(y + Simulator.getDelta().x,
                                    i + Simulator.getDelta().y);
                            water.lineTo(y + Simulator.getDelta().x,
                                    i + Simulator.getDelta().y);
                        }
                    }
                }
            }
        }
        g.draw(water);
        if (pocetVodnichZdroju > 0) {
            drawWaterSources(g);
        }
    }

    /**
     * Metoda prostřednictvím metody drawWaterFlowLabel vykreslí všechny vodní zdroje v
     * krajině, poskytnuté metodou simulátoru getWaterSources
     * @param g grafický kontext
     */
    public void drawWaterSources(Graphics2D g){
        for (WaterSourceUpdater waterSourceUpdater: vodniZdroje) {
            Point2D poziceSipky = new Point2D.Double(waterSourceUpdater.getIndex() % maxX, waterSourceUpdater.getIndex() / maxX);
            drawWaterFlowLabel(poziceSipky, Simulator.getGradient(waterSourceUpdater.getIndex()), waterSourceUpdater.getName(), g);
        }
    }

    /**
     * Metoda vykreslí na zadané pozici (v pixelech v souřadném systému okna) šipku ve směru
     * dirFlow znázorňující směr toku vody a dále název vodního toku (name).
     * @param position Pozice
     * @param dirFlow Vektor směru kudy má směřovat šipka
     * @param name Jméno řeky
     * @param g grafický kontext
     */
    public void drawWaterFlowLabel(Point2D position, Vector2D dirFlow, String name, Graphics2D g){
        AffineTransform bezTransformu = g.getTransform();
        g.translate((int)position.getX(),(int)position.getY());
        double rotace = Math.atan(dirFlow.y.doubleValue() / dirFlow.x.doubleValue());
        if(dirFlow.x.doubleValue()>0 && dirFlow.y.doubleValue()!=0){
            rotace = rotace + Math.PI;
        }
        g.rotate(rotace);
        g.setColor(new Color(255,255,0));
        g.setFont(new Font("Calibri",Font.BOLD,18));
        drawArrow(new Point2D.Double(0,0), new Vector2D(-1,0), g);

        g.setColor(Color.BLACK);
        if(dirFlow.x.doubleValue()>0){
            g.rotate(Math.PI);
            g.drawString(name, 5-g.getFontMetrics().stringWidth(name), 15);
        } else {
            g.drawString(name, 5, 15);
        }
        g.setTransform(bezTransformu);
    }

    /**
     * Metoda stanoví minimální a maximální souřadnice v metrech ve směru X a Y a uloží je do
     * stavových proměnných.
     */
    public void computeModelDimensions(){
       /* Se zakomentovaným minX/Y se celý obrázek zvláštně posouval, s 0 to funguje dobře */
       // minX = Simulator.getStart().x;
       // minY = Simulator.getStart().y;
        minX = 0;
        minY = 0;
        maxX = Simulator.getDimension().x;
        maxY = Simulator.getDimension().y;

    }

    /**
     * Metoda inicializuje stavové proměnné používané pro přepočet souřadnic modelu (v metrech)
     * na souřadnice okna (v pixelech). Metoda určí vhodnou změnu měřítka a posun ve směru X a
     * Y tak, aby bylo zaručeno, že se veškeré souřadnice modelu transformují do okna o rozměrech width, height, a to
     * včetně „přesahů“ grafických reprezentací elementů umístěných na extrémních souřadnicích
     * Převážná část je zkopírovaná ze cvičení6.
     * @param width šířka
     * @param height výška
     */
    public void computeModel2WindowTransformation(int width, int height){
        computeModelDimensions();
        double scalex = width / (maxX - minX);
        double scaley = height / (maxY - minY);
        scale = Math.min(scalex, scaley);

        int nimW = (int) ((maxX - minX) * scale);
        int nimH = (int) ((maxY - minY) * scale);

        posunSouradniceX = (width - nimW) / 2;
        posunSouradniceY = (height - nimH) / 2;
    }

    /**
     * Metoda převede souřadnice modelu na souřadnice okna s využitím hodnot stavových
     * proměnných určených v metodě computeModel2WindowTransformation. Vrací bod m.
     * @param m Point2D bod
     * @return Změněný bod m z parametrů
     */
    public static Point2D model2window(Point2D m){
        m.setLocation((int) (((m.getX() - minX) / scale) / deltaX),(int) (((m.getY() - minY) / scale) / deltaY));
        return m;
    }

}
