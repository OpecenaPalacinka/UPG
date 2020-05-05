import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import waterflowsim.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hlavní třída celého programu, v příkazové řádce načítá číslo, podle kterého se zjistí který scénář
 * spustit. Obsahuje pouze metodu main, která spouští celý program.
 * @author Jan Pelikán
 * @version 1.2.35
 */
public class L01_SpusteniSimulatoru extends JFrame {
	public static Timer myTimer = new Timer();
	static double v = 0.002;
	static double zmena = 0.0005;
	static double cas = 0;
	static int redukce=0;
	public static DefaultCategoryDataset[] datasets;
	static JButton bttnZrychli = new JButton("Zrychli");
	public static Point2D pressed;
	static boolean pauza = false;

	/**
	 * Hlavní třída, spouští různé scénáře podle parametrů zadaných do příkazové řádky
	 * @param args parametry příkazové řádky
	 *
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

		datasets = new DefaultCategoryDataset[Simulator.getData().length];

		for(int i = 0; i <Simulator.getData().length;i++){
			datasets[i] = new DefaultCategoryDataset();
		}

		myTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!pauza){
				Simulator.nextStep(v);
				cas += v;
				if (redukce%10 == 0){
					for (int i = 0; i < Simulator.getData().length; i++) {
						datasets[i].addValue((Number)Simulator.getData()[i].getWaterLevel(),"BUŇKA",cas);
					}
				}
				redukce++;
				jf.repaint();
			}
			}
		}, 0, 100);


	}

	/**
	 * Metoda vykresluje graf, kde získává data z jednoho bodu v výšce vody
	 * @param event kliknutí myši
	 * @return nový graf
	 */
	public static JFreeChart makePointChart(MouseEvent event){
		Point2D pozice = DrawingPanel.model2window(new Point2D.Double(event.getX(),event.getY()));
		int presnaPozice = (int)((pozice.getY()*Simulator.getDimension().x) + pozice.getX());
		DefaultCategoryDataset dataset = datasets[presnaPozice];

		JFreeChart chart = ChartFactory.createLineChart("Výška vody v daném bodě","Čas simulace v s.",
				"Výška hladiny vody",dataset);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.DARK_GRAY);
		CategoryItemRenderer categoryItemRenderer = plot.getRenderer();
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		categoryItemRenderer.setDefaultItemLabelGenerator(
				new StandardCategoryItemLabelGenerator(
						"{2}", nf));
		categoryItemRenderer.setDefaultItemLabelsVisible(true);
		categoryItemRenderer.setDefaultItemLabelFont(new Font("Calibri",Font.BOLD,12));
		return chart;
	}

	/**
	 * Metoda vykresluje graf výšky hladiny vody z vybrané oblasti
	 * @param eventKonec release tlačítka myši
	 * @return graf
	 */
	public static JFreeChart makeAreaChart(MouseEvent eventKonec) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int counter = datasets[0].getColumnCount();
		int zacatekX = Math.min(eventKonec.getX(),(int) pressed.getX());
		int zacatekY = Math.min(eventKonec.getY(),(int) pressed.getY());
		int konecX = Math.max(eventKonec.getX(),(int) pressed.getX());
		int konecY = Math.max(eventKonec.getY(),(int) pressed.getY());
		for (int i = 0; i < counter; i++) {
			double pomocnej = 0;
			int pocitadlo =0;
			for (int j = zacatekX; j < konecX ; j++) {
				for (int k = zacatekY; k < konecY; k++) {
					Point2D pozice = DrawingPanel.model2window(new Point2D.Double(j,k));
					int presnaPozice = (int)((pozice.getY()*Simulator.getDimension().x) +pozice.getX());
					if ((double)datasets[presnaPozice].getValue(0,i) > 0){
						pomocnej += (double) datasets[presnaPozice].getValue(0,i);
						pocitadlo++;
					}
				}
			}
			if(pocitadlo == 0) {
				dataset.addValue(0,"Oblast",datasets[0].getColumnKey(i));
			} else {
				dataset.addValue((Number)(pomocnej/pocitadlo),"Oblast",datasets[0].getColumnKey(i));
			}
		}
		datasets[0].addChangeListener(new DatasetChangeListener() {
			int newCounter = counter;
			@Override
			public void datasetChanged(DatasetChangeEvent datasetChangeEvent) {
				double pomocnej = 0;
				int pocitadlo = 0;
				for (int i = zacatekX; i < konecX ; i++) {
					for (int j = zacatekY; j < konecY; j++) {
						Point2D pozice = DrawingPanel.model2window(new Point2D.Double(i, j));
						int presnaPozice = (int) ((pozice.getY() * Simulator.getDimension().x) + pozice.getX());
						if ((double) datasets[presnaPozice].getValue(0, newCounter - 1) > 0) {
							pomocnej += (double) datasets[presnaPozice].getValue(0, newCounter - 1);
							pocitadlo++;
						}
					}
				}
				if(pocitadlo == 0) {
					dataset.addValue(0,"Oblast",datasets[0].getColumnKey(newCounter-1));
				} else {
					dataset.addValue((Number)(pomocnej/pocitadlo),"Oblast",datasets[0].getColumnKey(newCounter-1));
				}
				newCounter++;
			}
		});
	JFreeChart oblastChart = ChartFactory.createLineChart("Výška hladiny v oblasti","Čas simulace","Výška hladiny",dataset);
	CategoryPlot plot = oblastChart.getCategoryPlot();
	plot.setBackgroundPaint(Color.WHITE);
	plot.setRangeGridlinePaint(Color.DARK_GRAY);
	CategoryItemRenderer categoryItemRenderer = plot.getRenderer();
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	categoryItemRenderer.setDefaultItemLabelGenerator(
				new StandardCategoryItemLabelGenerator(
						"{2}", nf));
	categoryItemRenderer.setDefaultItemLabelsVisible(true);
	categoryItemRenderer.setDefaultItemLabelFont(new Font("Calibri",Font.BOLD,12));
	return oblastChart;


	}

	/**
	 * Metoda vytvari dve tlacitka v dolni strane okna, a obsluhuje jejich funkci
	 * @param win hlavni okno programu
	 */
	private static void makeGui(JFrame win) {
		DrawingPanel panel = new DrawingPanel();

		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				JFrame jFrame = new JFrame();
				ChartPanel chartPanel = new ChartPanel(makePointChart(mouseEvent));
				jFrame.add(chartPanel);
				jFrame.setSize(chartPanel.getSize());
				jFrame.pack();
				jFrame.setLocationRelativeTo(null);
				jFrame.setVisible(true);

			}
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				pressed = mouseEvent.getPoint();
			}
			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
				if(pressed.getX() != mouseEvent.getX() && pressed.getY() != mouseEvent.getY()) {
					JFrame jFrame = new JFrame();
					ChartPanel chartPanel = new ChartPanel(makeAreaChart(mouseEvent));
					jFrame.add(chartPanel);
					jFrame.setSize(chartPanel.getSize());
					jFrame.pack();
					jFrame.setLocationRelativeTo(null);
					jFrame.setVisible(true);
				}

			}
			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}
			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});


		JButton bttnZpomal = new JButton("Zpomal");
		JButton bttnZastav = new JButton("Zastavit");
		JButton bttnPokrac = new JButton("Pokračovat");
		JButton bttnLegen = new JButton("Legenda");

		Dimension velikostOkna = new Dimension(Simulator.getDimension().x, (Simulator.getDimension().y + bttnZrychli.getHeight()));
		panel.setPreferredSize(velikostOkna);
		win.setLayout(new BorderLayout());
		win.add(panel, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.add(bttnZrychli);
		buttons.add(bttnZpomal);
		buttons.add(bttnZastav);
		buttons.add(bttnPokrac);
		buttons.add(bttnLegen);

		win.add(buttons, BorderLayout.NORTH);

		bttnZpomal.addActionListener(actionEvent -> {
			if(v-zmena<1e-8){
				v = 1e-7;
				System.out.println("Zadávám nejmenší rychlost, pomaleji už to nejde.");
			} else {
				v = v - zmena;
			}
			pauza = false;
		});

		bttnZrychli.addActionListener(e -> {
			if (v+zmena>1){
				v =0.9999999999999999;
				System.out.println("Zadávám maximální rychlost, rychleji už to nejde.");
			} else {
				v = v + zmena;
			}
			pauza = false;
		} );

		bttnZastav.addActionListener(actionEvent -> pauza = true);

		bttnPokrac.addActionListener(actionEvent -> pauza=false);

		bttnLegen.addActionListener(actionEvent -> {
			JFrame jFrame = new JFrame();
			Legenda legenda = new Legenda();
			jFrame.add(legenda);
			jFrame.setResizable(false);
			jFrame.pack();
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
		});
	}
	
}
