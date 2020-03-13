import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class BasicDrawing extends JFrame {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("A19B0157P");

        DrawingPanel panel = new DrawingPanel();
        jf.add(panel);
        jf.pack();

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

        java.util.Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                panel.repaint();
            }
        }, 0, 20);

    }
}