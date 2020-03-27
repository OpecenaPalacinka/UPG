import waterflowsim.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class DrawingPanel extends JPanel {

    private double minX,maxX;
    private double minY,maxY;
    private double hodnotaX, hodnotaY;
    private int velikostWidth = 1000;
    private int	velikostHeight = 1000;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;

        computeModel2WindowTransformation(Simulator.getDimension().x,Simulator.getDimension().y);

        int width = this.getWidth();
        int height = this.getHeight();

        double scaleX = width/hodnotaX;
        double scaleY = height/hodnotaY;
        double scale = Math.min(scaleX,scaleY);

        /*
        double startX = (width - scale*width)/4;
        double startY = (height - scale*height)/4;

         */

        g2.scale(scale,scale);

        drawWaterFlowState(g2);
    }

    public void drawWaterFlowState(Graphics2D g){
        drawTerrain(g);
        drawWaterLayer(g);
    }

    public void drawTerrain(Graphics2D g){
    }

    public void drawWaterLayer(Graphics2D g){
        computeModelDimensions();
            drawWaterSources(g);
            Path2D water = new Path2D.Double();
            int cellBefore;
            int cellAfter;
            g.setColor(Color.BLUE);
            for(int i = 0; i < maxY-1; i++) {
                for(int j = 0; j < maxX-1; j++) {
                    cellBefore = i * (int)maxX + j;
                    cellAfter = (i + 1) * (int)maxX + (j + 1);
                    Cell cell = Simulator.getData()[cellBefore];
                    Cell cell2 = Simulator.getData()[cellAfter];
                    if(!cell.isDry()) {
                        if(!cell2.isDry()) {
                            water.lineTo(j + Simulator.getDelta().x, i + Simulator.getDelta().y);
                        }else {
                            water.moveTo(j + Simulator.getDelta().x, i + Simulator.getDelta().y);
                        }
                    }else {
                        water.moveTo(j + Simulator.getDelta().x, i + Simulator.getDelta().y);
                    }
                }
            }
            g.draw(water);

        }

    public void drawWaterSources(Graphics2D g){
        waterflowsim.WaterSourceUpdater[] vodniZdroje = Simulator.getWaterSources();
        for(waterflowsim.WaterSourceUpdater updater: vodniZdroje){
          Point2D uvodniBod = new Point2D.Double(hodnotaX,hodnotaY);
            drawWaterFlowLabel(uvodniBod,Simulator.getGradient(updater.getIndex()),updater.getName(),g);
        }

    }
    public void drawWaterFlowLabel(Point2D position, Vector2D dirFlow, String name, Graphics2D g){
        double x1 = position.getX();
        double x2 = position.getX()-dirFlow.x.doubleValue();
        double y1 = position.getY();
        double y2 = position.getY()-dirFlow.y.doubleValue();

        // Spocitame slozky vektoru od (x1, y1) k (x2, y2)
        double vx = x2 - x1;
        double vy = y2 - y1;

        // Spocitame vektoru v, tj usecky od (x1, y1) k (x2, y2).
        // K vypoctu druhe mocniny idealne pouzivame nasobeni, ne funkci pow
        // (je mnohem pomalejsi).
        double vLength = Math.sqrt(vx*vx + vy*vy);

        // Z vektoru v udelame vektor jednotkove delky
        double vNormX = vx / vLength;
        double vNormY = vy / vLength;

        // Vektor v protahneme na delku arrowLength
        double vArrowX = vNormX * 20;
        double vArrowY = vNormY * 20;

        // Spocitame vektor kolmy k (vx, vy)
        // Z nej pak odvodime koncove body carek tvoricich sipku.
        double kx = -vArrowY;
        double ky = vArrowX;

        // Upravime delku vektoru k, aby byla sipka hezci
        kx *= 0.25;
        ky = 0.25;

        // Cara od (x1, y1) k (x2, y2)
        g.draw(new Line2D.Double(x1, y1, position.getX()+dirFlow.x.doubleValue()*50, position.getY()+dirFlow.y.doubleValue()*50));

        // Sipka na konci
        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX + kx, y2 - vArrowY + ky));
        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX - kx, y2 - vArrowY - ky));
    }

    public void computeModelDimensions(){
        minX= Simulator.getStart().x;
        minY= Simulator.getStart().y;
        maxX=Simulator.getDimension().x.doubleValue();
        maxY=Simulator.getDimension().y.doubleValue();
    }

    public void computeModel2WindowTransformation(int width, int height){
        computeModelDimensions();
        hodnotaX = maxX-minX;
        hodnotaY = maxY-minY;
    }

    public Point2D model2window(Point2D m){
        computeModel2WindowTransformation((int)hodnotaX,(int)hodnotaY);
        m.setLocation(hodnotaX,hodnotaY);
        return m;
    }



}