import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BasicDrawing extends JFrame {

    private static void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName + ".png"));
        } catch (IOException e) {
            System.out.println("Nepodarilo se zapsat obrazek `" + fileName + "'.");
        }
    }

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

        jf.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("CONVERTING ...");
                try {
                    //ZDE spustte vse potrebne
                    Runtime.getRuntime()
                            .exec("gm convert logo: logo.jpg") //zmente z prikladu na rozumne
                            .waitFor();


                    Runtime.getRuntime()
                            .exec("ffmpeg convert logo: logo.jpg") //zmente z prikladu na rozumne
                            .waitFor();

                    //a na samy zaver vysledek zobrazime
                    Runtime.getRuntime()
                            .exec("cmd /c start /wait vystup.mp4") //zmente z prikladu na rozumne
                            .waitFor();

                    System.out.println("EVERYTHING DONE. EXIT.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("WINDOW CLOSED");

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }
        });
    }
}
