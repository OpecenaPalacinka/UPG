import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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

        JButton bttnExit = new JButton("Exit");
        JButton bttnPrint = new JButton("Print ...");

        JPanel buttons = new JPanel();
        buttons.add(bttnExit);
        buttons.add(bttnPrint);

        win.add(buttons, BorderLayout.SOUTH);

        bttnPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrinterJob job = PrinterJob.getPrinterJob();
               if(job.printDialog()){
                   job.setPrintable(panel);
                   try {
                       job.print();
                   } catch (PrinterException e) {
                       e.printStackTrace();
                   }
               }
            }
        });

        bttnExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                win.dispose();
            }
        });
    }
}
