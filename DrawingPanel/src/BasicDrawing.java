import javax.swing.JFrame;
import java.awt.*;

public class BasicDrawing extends JFrame{

    public static void main(String[] args){
        new BasicDrawing().setVisible(true);
    }

    public BasicDrawing(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("A19B0157P");
        getContentPane().add(new DrawingPanel());
        pack();
    }
}