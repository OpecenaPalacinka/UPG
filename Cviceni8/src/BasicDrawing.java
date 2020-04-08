import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BasicDrawing {

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setTitle("A19B0157P");

        makeGui(win);
        win.pack();

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocationRelativeTo(null);
        win.setVisible(true);


    }

    private static void makeGui(JFrame win) {
        DrawingPanel panel = new DrawingPanel();
        win.setLayout(new BorderLayout());
        win.add(panel, BorderLayout.CENTER);

        //SVGGraphics2D g2 = new SVGGraphics2D(750,750);
        MyGraphics2D g2 = new MyGraphics2D(750,750);
        panel.drawFlower(200,200,g2);
        System.out.println(g2.getSVGElement());

        JButton bttnExit = new JButton("Exit");

        JPanel buttons = new JPanel();
        buttons.add(bttnExit);

        win.add(buttons, BorderLayout.SOUTH);


        bttnExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                win.dispose();
            }
        });
    }
}
