/**
 * Uvod do pocitacove grafiky, cviceni 13
 * 
 * Jednoduche zobrazeni 3D objektu ulozeneho ve formatu Wavefront OBJ
 *
 */

package upg_cv13_display3d_06;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;

public class BasicDrawing {

	// static String fileName = "simple.obj";
	static String fileName = "teapot.obj";
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		// Vlastni graficky obsah
		DrawingPanel drawingPanel = new DrawingPanel();
		drawingPanel.setPreferredSize(new Dimension(640, 480));
		frame.add(drawingPanel);

		// Nacteni Wavefront OBJ modelu
		readObjFile(fileName, drawingPanel.coordinates, drawingPanel.faces);
		
		// Zjisteni mezi souradnic x, y, z
		drawingPanel.xRange = findRange(drawingPanel.coordinates, 0);
		drawingPanel.yRange = findRange(drawingPanel.coordinates, 1);
		drawingPanel.zRange = findRange(drawingPanel.coordinates, 2);
		
		// Standardni manipulace s oknem
		frame.setTitle("Soubor " + fileName);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
	}
	
	/**
	 * Nacteni jednducheho Wavefront OBJ souboru. 
	 * Predpokladaji se jen elementy 
	 *   v (souradnice X, Y, Z vrcholu)
	 *   f (indexy A, B, C trojuhelniku)
	 *   # (komentar)
	 *   
	 * @param fileName jmeno vstupniho souboru
	 * @param coordinates pole souradnic vsech vrcholu v poradi x, y, z, x, y, z, ...
	 * @param faces indexy do pole coordinates, NA ROZDIL OD OBJ SE INDEXUJE OD 0
	 */
	public static void readObjFile(String fileName, ArrayList<Double> coordinates, ArrayList<Integer> faces) {
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	line = line.trim();
		    	
		    	// Zahodit prazdne radky a komentare
		    	if (line.length() == 0 || line.charAt(0) == '#')
		    		continue;

		    	// Rozdelit radku na pole, oddeleni bilymi znaky
	    		String fields[] = line.split("[ \t]+");
		    	
	    		// Zpracovani elementu
	    		if (fields[0].compareToIgnoreCase("v") == 0) {
	    			// Vrchol (predpokladaji se 3 souradnice)
		    		for (int i = 1; i < fields.length; i++) {
		    			coordinates.add(Double.parseDouble(fields[i]));
		    		}
		    	} else if (fields[0].compareToIgnoreCase("f") == 0) {
		    		// Stena (predpokladaji se 3 indexy)
		    		for (int i = 1; i < fields.length; i++) {
		    			faces.add(Integer.parseInt(fields[i]) - 1); // -1, protoze OBJ indexuje od 1
		    		}
		    	}
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}		
	}
	
	/**
	 * Nalezeni minima a maxima z cisel a[n], a[n+3], a[n+6], ...
	 * K hledani rozsahu souradnic x, y, z v poli slozenem z cisel
	 * x0, y0, z0, x1, y1, z1, x2, y2, z2, ... 
	 * @param a vstupni pole souradnic
	 * @param n pocatecni index
	 * @return pole [minimum, maximum]
	 */
	public static double[] findRange(ArrayList<Double>a, int n) {
		double cMin = Double.POSITIVE_INFINITY;
		double cMax = -Double.POSITIVE_INFINITY;
		for (int i = n; i < a.size(); i += 3) {
			cMin = Math.min(cMin, a.get(i));
			cMax = Math.max(cMax, a.get(i));
		}
		return new double[]{cMin, cMax};
	}	
}
