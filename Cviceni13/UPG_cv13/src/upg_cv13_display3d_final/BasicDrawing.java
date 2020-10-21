/**
 * Uvod do pocitacove grafiky, cviceni 13
 * 
 * Jednoduche zobrazeni 3D objektu ulozeneho ve formatu Wavefront OBJ
 *
 */

package upg_cv13_display3d_final;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;

public class BasicDrawing {

	// static String fileName = "simple.obj";
	// static String fileName = "teapot.obj";
	static String fileName = "lion.obj";
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		// Vlastni graficky obsah
		DrawingPanel drawingPanel = new DrawingPanel();
		drawingPanel.setPreferredSize(new Dimension(640, 480));
		frame.add(drawingPanel);

		// Nacteni Wavefront OBJ modelu
		readObjFile(fileName, drawingPanel.coordinates, drawingPanel.faces);
		computeNormals(drawingPanel.coordinates, drawingPanel.faces,
				drawingPanel.faceNormals);
		
		writeX3DOMFile(fileName + ".html", drawingPanel.coordinates, drawingPanel.faces);
		
		
		// Zjisteni mezi souradnic x, y, z
		drawingPanel.xRange = findRange(drawingPanel.coordinates, 0);
		drawingPanel.yRange = findRange(drawingPanel.coordinates, 1);
		drawingPanel.zRange = findRange(drawingPanel.coordinates, 2);
		
		// Rotace objektem na stisk klavesy
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				switch (e.getKeyChar()) {
				case '+':
					drawingPanel.angle = drawingPanel.angle + 0.1;
					break;
				case '-':
					drawingPanel.angle = drawingPanel.angle - 0.1;
					break;
				}
				drawingPanel.repaint();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) { }
		});

		// Standardni manipulace s oknem
		frame.setTitle("Soubor " + fileName);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
	}
	
	private static void writeX3DOMFile(String fileName2,
			ArrayList<Double> coordinates, ArrayList<Integer> faces) {
		
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName2), charset)) {
			writer.write("<html><head><script type=\"text/javascript\" src=\"http://www.x3dom.org/download/x3dom.js\"> </script></head><body>\n");			
			writer.write("<x3d width=\"640\" height=\"480\">\n");
			writer.write("<scene>\n");			
			writer.write("<shape>\n");
			
			//writer.write("<box/>");
			writer.write("<appearance>\n");
			writer.write("<material diffuseColor=\"1 0 0 \"/>\n");
			writer.write("</appearance>\n");

			writer.write("<IndexedFaceSet coordIndex=\"");
			
			for (int i = 0; i < faces.size(); i += 3) {
				writer.write(String.format(Locale.US,
						"%d %d %d -1\n", 
						faces.get(i),
						faces.get(i + 1),
						faces.get(i + 2)
						));
			}
			
			writer.write("\">");
			writer.write("<Coordinate point=\"");
			
			for (int i = 0; i < coordinates.size(); i += 3) {
				writer.write(String.format(Locale.US,
						"%f %f %f\n", 
						coordinates.get(i),
						coordinates.get(i + 1),
						coordinates.get(i + 2)
						));
			}
						
			writer.write("\">");
			writer.write("</IndexedFaceSet>");
			writer.write("</shape>\n");
			writer.write("</scene>\n");
			writer.write("</x3d>");
			writer.write("</body></html>");
			writer.close();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	private static void computeNormals(
			ArrayList<Double> coordinates, 
			ArrayList<Integer> faces,
			ArrayList<Double> facesNormals) {

		double[] u = new double[3];
		double[] v = new double[3];
		double[] n = new double[3];
		for (int i = 0; i < faces.size(); i += 3) {
			int iA = 3*faces.get(i); //index do pole coordinates, kde je x souradnice vrcholu A
			int iB = 3*faces.get(i+1);
			int iC = 3*faces.get(i+2);
			
			for (int j = 0; j < 3; j++) {
				u[j] = coordinates.get(iB + j) - coordinates.get(iA + j);
				v[j] = coordinates.get(iC + j) - coordinates.get(iA + j);
			}
			
			n[0] = u[1]*v[2] - v[1]*u[2];
			n[1] = u[2]*v[0] - v[2]*u[0];
			n[2] = u[0]*v[1] - v[0]*u[1];
			double nlen = Math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2]);
			
			for (int j = 0; j < 3; j++) {
				facesNormals.add(n[j]/nlen);
			}			
		}		
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
