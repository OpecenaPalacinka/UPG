import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BasicDrawing {

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setTitle("A19B0157P");

       // DrawingPanel panel = new DrawingPanel();
       // win.add(panel);
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

