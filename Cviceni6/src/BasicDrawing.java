
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BasicDrawing extends JFrame {
    public static void main(String[] args) throws IOException {
        JFrame jf = new JFrame();
        jf.setTitle("A19B0157P");

        DrawingPanel panel = new DrawingPanel();
        jf.add(panel);

        panel.loadImage("1000x1000.jfif");
        panel.saveImage("pokus.jfif",2000,1750);

        jf.pack();

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

}