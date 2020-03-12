import javax.swing.JFrame;

public class Window extends JFrame{

    public static void main(String[] args){
        new Window().setVisible(true);
    }

    public Window(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("UPG 2019/2020");
        getContentPane().add(new MyPanel());
        pack();
    }
}