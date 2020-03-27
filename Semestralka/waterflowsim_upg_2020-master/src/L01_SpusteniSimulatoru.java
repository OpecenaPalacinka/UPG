import waterflowsim.Simulator;
import waterflowsim.Scenarios;
import waterflowsim.Cell;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class L01_SpusteniSimulatoru extends JFrame {


	public static void main(String[] args) {
		Scenarios[] scenarios = Simulator.getScenarios();

		// Nahrani a spusteni prvniho scenare
		Simulator.runScenario(0);

		JFrame jf = new JFrame();
		jf.setTitle("A19B0157P");
		DrawingPanel panel = new DrawingPanel();
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
