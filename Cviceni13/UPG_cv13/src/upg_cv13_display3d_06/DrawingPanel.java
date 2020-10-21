/**
 * Uvod do pocitacove grafiky, cviceni 13
 * 
 * Jednoduche zobrazeni 3D objektu
 *
 */

package upg_cv13_display3d_06;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Pole souradnic 3D bodu 0, 1, 2, ..., N-1
	// ve formatu x0, y0, z0,  x1, y1, z1,  x2, y2, z2, ...,  x(N-1), y(N-1), z(N-1).
	// V poli je tedy 3*N cisel
	ArrayList<Double> coordinates = new ArrayList<Double>();

	// Rozsahy souradnic x, y, z 
	// z pole coordinates.
	// Kazdy rozsah je pole [min, max]
	double[] xRange, yRange, zRange;

	// Stred modelu
	double xMean, yMean, zMean;
	
	// Pole indexu do pole coordinates tvorici trojuhelniky 0, 1, 2, ..., T-1
	// Kazdy trojuhelnik je zadan indexy A, B, C.
	// Struktura pole je A0, B0, C0,  A1, B1, C1,  A2, B2, C2, ..., A(T-1), B(T-1), C(T-1) 
	// V poli je tedy 3*T cisel
	ArrayList<Integer> faces = new ArrayList<Integer>();

	// konstanta pro prepocet ze souradnic modelu na souradnice obrazovky
	double scale;
	
	// mezera od okraje okna (v pixelech)
	double gap = 10;
	
	// sirka vykreslovaneho bodu (v pixelech)
	double pointSize = 3;
	
	/**
	 * Prepocet bodu [x, y, z] v souradnem systemu modelu
	 * na souradnice [screenX, screenY] na obrazovce,
	 * stred obrazku je v (0,0)
	 */
	double[] xyzToScreenXY(double x, double y, double z) {
		// Prepocet (x,y) -> (screenX, screenY)
		// (Souradnice Z se zahazuje)
		double screenX, screenY;
		screenX =  (x - xMean) * scale;
		screenY = -(y - yMean) * scale;
		return new double[] {screenX, screenY};
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		// Smazeme pozadi
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Pro prepocet souradneho systemu modelu na souradny system okna
		double screenWidth = this.getWidth() - 2*gap;
		double screenHeight = this.getHeight() - 2*gap;
		scale = Math.min(screenWidth / (xRange[1] - xRange[0]), 
			 	         screenHeight / (yRange[1] - yRange[0]));
		xMean = (xRange[0] + xRange[1])/2;
		yMean = (yRange[0] + yRange[1])/2;
		zMean = (zRange[0] + zRange[1])/2;
		
		// Posun (0,0) do stredu okna
		g2.translate(this.getWidth() / 2,  this.getHeight() / 2);
		
		//
		// Vykresleni vsech trojuhelniku
		//
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(0.2f));
		for (int i = 0; i < faces.size(); i += 3) {
			// Indexy, kde zacinaji souradnice bodu A, B, C v poli coordinates.
			// (Je to cislo vrcholu * 3, protoze kazdy vrchol ma 3 souradnice) 
			int iA = 3 * faces.get(i);
			int iB = 3 * faces.get(i + 1);
			int iC = 3 * faces.get(i + 2);
			
			// Obrazovkove souradnice bodu A, B, C
			double xy1[] = xyzToScreenXY(coordinates.get(iA), coordinates.get(iA+1), coordinates.get(iA+2));
			double xy2[] = xyzToScreenXY(coordinates.get(iB), coordinates.get(iB+1), coordinates.get(iB+2));
			double xy3[] = xyzToScreenXY(coordinates.get(iC), coordinates.get(iC+1), coordinates.get(iC+2));
			
			// Vykresleni trojuhelniku
			g2.drawPolygon(new int[] {(int)xy1[0],  (int)xy2[0], (int)xy3[0]}, 
					       new int[] {(int)xy1[1],  (int)xy2[1], (int)xy3[1]}, 3);
		}
		
		//
		// Vykresleni vrcholu
		//
		g2.setColor(Color.BLACK);
		for (int i = 0; i < coordinates.size(); i += 3) {
			double xy[] = xyzToScreenXY(coordinates.get(i), coordinates.get(i+1), coordinates.get(i+2));
			g2.fill(new Rectangle2D.Double(
					xy[0] - pointSize / 2,
					xy[1] - pointSize / 2,
					pointSize, pointSize));
		}		
	}
}
