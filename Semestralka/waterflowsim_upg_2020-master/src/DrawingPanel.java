import waterflowsim.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {

    private int w;
    private int h;

    public void drawWaterFlowState(Graphics2D g){
        drawTerrain(g);
        drawWaterLayer(g);
    }
    public void drawTerrain(Graphics2D g){
    }
    public void drawWaterLayer(Graphics2D g){
        BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
        Cell[] cells = Simulator.getData();
        int[] poleBarev = new int[Simulator.getData().length];

        for (int i = 0; i < Simulator.getData().length;i++){
            if(cells[i].isDry()){
                poleBarev[i] = 255;
            } else {
                poleBarev[i] = 255*255*255;
            }
        }

        image.setRGB(0,0,w,h,poleBarev,0,w);

        drawWaterSources(g);
    }
    public void drawWaterSources(Graphics2D g){
        waterflowsim.WaterSourceUpdater[] vodniZdroje = Simulator.getWaterSources();
        for(waterflowsim.WaterSourceUpdater updater: vodniZdroje){
            drawWaterFlowLabel(updater.getIndex(), Simulator.getGradient(5), updater.getName(), g);
        }
    }
    public void drawWaterFlowLabel(Point2D position, Vector2D directionFlow, String name, Graphics2D g){

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

    public void computeModelDimensions(){

    }
    public void computeModel2WindowTransformation(int width, int height){

    }
    public Point2D model2window(Point2D m){

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        computeModel2WindowTransformation();
        drawWaterFlowState(g2);
    }
}