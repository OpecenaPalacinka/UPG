import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


public class BasicDrawing {
	private static final String DATA_FILE_NAME = "mrt8_angio2.raw";
	private static final int DATA_SX = 256;
	private static final int DATA_SY = 320;
	private static final int DATA_SZ = 128;

	public static void main(String[] args) throws IOException {
		JFrame win = new JFrame();
		win.setTitle("A19B0157P");
		
		DrawingPanel panel = new DrawingPanel(
				Files.readAllBytes(Path.of(DATA_FILE_NAME)),
				DATA_SX,DATA_SY,DATA_SZ
		);
		win.add(panel);
		win.pack();
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
						
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				int z = (panel.getSliceXY() + 1) % DATA_SZ;
				int y = (panel.getSliceYZ() + 1) % DATA_SY;
				int x = (panel.getSliceXZ() + 1) % DATA_SX;
				panel.setSliceXY(z);
				panel.setSliceXZ(y);
				panel.setSliceYZ(x);
				panel.repaint();
			}
		}, 0, 100);
		
	}

}
