import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BasicDrawing extends JFrame {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("A19B0157P");

        makeGui(jf);

        jf.pack();

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    private static void makeGui(JFrame jf) {
        DrawingPanel panel = new DrawingPanel();
        jf.setLayout(new BorderLayout());
        jf.add(panel,BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton buttonLarger = new JButton("Larger");
        JButton buttonExit = new JButton("Exit");

        buttons.add(buttonLarger);
        buttons.add(buttonExit);
        jf.add(buttons,BorderLayout.SOUTH);



        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jf.dispose();
            }
        });
        buttonLarger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.makeLarger();
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                if(keyEvent.getKeyChar()=='+'){
                    panel.makeLarger();
                }
                if(keyEvent.getKeyChar()=='R' || keyEvent.getKeyChar()=='r'){
                    panel.reset();
                }


                return false;
            }
        });
    }
}