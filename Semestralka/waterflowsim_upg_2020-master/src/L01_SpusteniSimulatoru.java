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
	static double v = 0.002;
	static double zmena = 0.005;

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
		makeGui(jf);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Simulator.nextStep(v);
				jf.repaint();
			}
		}, 0, 100);

	}

	/**
	 * Metoda vytvari dve tlacitka v dolni strane okna, a obsluhuje jejich funkci
	 * @param win hlavni okno programu
	 */
	private static void makeGui(JFrame win) {
		DrawingPanel panel = new DrawingPanel();

		JButton bttnZrychli = new JButton("Zrychli");
		JButton bttnZpomal = new JButton("Zpomal");

		Dimension velikostOkna = new Dimension(Simulator.getDimension().x, (Simulator.getDimension().y + bttnZrychli.getHeight() + DrawingPanel.velikostListy));
		panel.setPreferredSize(velikostOkna);
		win.setLayout(new BorderLayout());
		win.add(panel, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.add(bttnZrychli);
		buttons.add(bttnZpomal);

		win.add(buttons, BorderLayout.SOUTH);

		bttnZpomal.addActionListener(actionEvent -> v = v - zmena);

		bttnZrychli.addActionListener(e -> v = v + zmena);
	}
	
}
