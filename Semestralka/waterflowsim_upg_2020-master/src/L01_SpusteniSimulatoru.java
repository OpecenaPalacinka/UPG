import waterflowsim.Simulator;
import waterflowsim.Scenarios;
import waterflowsim.Cell;

import javax.swing.*;
import java.util.List;

public class L01_SpusteniSimulatoru extends JFrame {

	public static void main(String[] args) {
		Scenarios[] scenarios = Simulator.getScenarios();
		
		// Vypis existujicich scenaru
		for(Scenarios sc: scenarios) {
			System.out.println(sc);
		}
		
		// Nahrani a spusteni prvniho scenare
		Simulator.runScenario(0);

		// Ziskani prvni bunky z plochy
		Cell cell = Simulator.getData()[0];

		// Spusteni simulace, v kazdem kroku cyklu se posune o preddefinovanou
		// dobu.
		for(int i=0; i<100; i++) {
			Simulator.nextStep(0.02);
			System.out.println(cell);
		}

		// Ziskani vsech vodnich zdroju
		// a pruchod pres vsechny zdroje	
		waterflowsim.WaterSourceUpdater[] zdroje = Simulator.getWaterSources();
		for(waterflowsim.WaterSourceUpdater up: zdroje){
			System.out.print(up.getName());
			System.out.println(" na indexu v poli: "+up.getIndex());
		}

		JFrame jf = new JFrame();
		jf.setTitle("A19B0157P");

		DrawingPanel panel = new DrawingPanel();
		jf.add(panel);
		jf.pack();

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}


}
