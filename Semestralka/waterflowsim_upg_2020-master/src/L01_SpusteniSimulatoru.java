import waterflowsim.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hlavní třída celého programu, v příkazové řádce načítá číslo, podle kterého se zjistí který scénář
 * spustit. Obsahuje pouze metodu main, která spouští celý program.
 * @author Jan Pelikán
 * @version 1.1.54
 */
public class L01_SpusteniSimulatoru extends JFrame {

	/**
	 * Hlavní třída, spouští různé scénáře podle parametrů zadaných do příkazové řádky
	 * @param args parametry příkazové řádky
	 */
	public static void main(String[] args) {

		if (args.length > 0 && Integer.parseInt(args[0])>3){
			System.out.println("Zadali jste moc velké číslo, spouštím prvním scénář!");
			Simulator.runScenario(0);
		} else if (args.length > 0){
			Simulator.runScenario(Integer.parseInt(args[0]));
		}
		else {
			Simulator.runScenario(0);
		}

		JFrame jf = new JFrame();
		jf.setTitle("Seminární práce - A19B0157P");
		DrawingPanel panel = new DrawingPanel();
		Dimension velikostOkna = new Dimension(Simulator.getDimension().x, Simulator.getDimension().y);
		panel.setPreferredSize(velikostOkna);
		jf.add(panel);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Simulator.nextStep(0.02);
				panel.repaint();
			}
		}, 0, 100);

	}

}
