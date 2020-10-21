/**
 * Uvod do pocitacove grafiky, cviceni 13
 * 
 * Jednoduche zobrazeni 3D objektu
 *
 */

package upg_cv13_display3d_01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

	// Pole indexu do pole coordinates tvorici trojuhelniky 0, 1, 2, ..., T-1
	// Kazdy trojuhelnik je zadan indexy A, B, C.
	// Struktura pole je A0, B0, C0,  A1, B1, C1,  A2, B2, C2, ..., A(T-1), B(T-1), C(T-1) 
	// V poli je tedy 3*T cisel
	ArrayList<Integer> faces = new ArrayList<Integer>();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		// Smazeme pozadi
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
