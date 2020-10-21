import java.awt.Dimension;
import javax.swing.JFrame;

public class BasicDrawing {

    static String fileName = "simple.obj";

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // Vlastni graficky obsah
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(640, 480));
        frame.add(drawingPanel);

        // Standardni manipulace s oknem
        frame.setTitle("A19B0157P");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
