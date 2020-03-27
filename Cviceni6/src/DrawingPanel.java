
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingPanel extends JPanel {
    private BufferedImage image = null;

    private final int W = 640;
    private final int H = 480;
    public DrawingPanel() {
        setPreferredSize(new Dimension(W, H));

    }

    public void loadImage(String path) throws IOException {
       this.image = imageProcessing(ImageIO.read(new File(path)));
       this.repaint();
    }

    public void saveImage(String path, int W, int H) throws IOException {
        BufferedImage img = new BufferedImage(W,H,BufferedImage.TYPE_3BYTE_BGR);

        drawJungle(img.createGraphics(),W,H);

        ImageIO.write(img,"jpeg", new File(path));

    }

    private BufferedImage imageProcessing(BufferedImage im) {
        int imW = im.getWidth();
        int imH = im.getHeight();

        int[] rgbArray = new int[imW*imH];
        im.getRGB(0,0,imW,imH,rgbArray,0,imW);
        for(int i = 0;i <rgbArray.length;i++){
            int rgb = rgbArray[i];
            int red = (rgb & 0xFF0000) >> 16;
            int green = (rgb & 0x00FF00) >> 8;
            int blue = (rgb & 0x0000FF);

            int gsc = (3*red + 6*green + blue)/10;

            rgbArray[i] = (gsc << 16) | (gsc <<8) | gsc;
        }

        BufferedImage out = new BufferedImage(imW,imH,BufferedImage.TYPE_3BYTE_BGR);
        out.setRGB(0,0,imW,imH,rgbArray,0,imW);
        return out;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        drawJungle(g2,this.getWidth(),this.getHeight());

    }

    private void drawJungle(Graphics2D g2, int W, int H){
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,W,H);

        int imW = this.image.getWidth();
        int imH = this.image.getHeight();

        double scalex = ((double) W)/imW;
        double scaley = ((double) H)/imH;
        double scale = Math.min(scalex,scaley);

        int nimW = (int) (imW * scale);
        int nimH = (int) (imH*scale);

        int startX = (W-nimW)/2;
        int startY = (H-nimH)/2;

        g2.drawImage(this.image,startX,startY, nimW, nimH,null);

        g2.setColor(Color.YELLOW);
        g2.fill(new Ellipse2D.Double(
                startX + nimW*0.45,
                startY + nimH*0.1,
                nimH*0.15,
                nimH*0.15
        ));
    }

}

