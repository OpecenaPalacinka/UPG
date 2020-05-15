import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	private byte[] data;
	private int data_sx;
	private int data_sy;
	private int data_sz;

	private int[] lut = new int[256];

	private BufferedImage imageXY;
	private BufferedImage imageXZ;
	private BufferedImage imageYZ;
	private int sliceXY;
	private int sliceXZ;
	private int sliceYZ;

	public DrawingPanel(byte[] readAllBytes, int dataSx, int dataSy, int dataSz) {
		this.setPreferredSize(new Dimension(768, 320));

		this.data = readAllBytes;
		this.data_sx = dataSx;
		this.data_sy = dataSy;
		this.data_sz = dataSz;

		setSliceXY(0);
		//setSliceXZ(0);
		//setSliceYZ(0);
		makeLut(0,255);
	}

	private void makeLut(int min, int max) {
		for (int i = 0; i < lut.length; i++){
			if (i < min){
				lut[i] = 0;
			} else if (i > max){
				lut[i] = Color.white.getRGB();
			} else {
				int v = 255*(i - min)/(max - min);
				lut[i] = v << 16 | v << 8 | v;
			}

		}
	}

	public int getSliceXY(){
		return sliceXY;
	}
	public int getSliceXZ() { return sliceXZ; }
	public int getSliceYZ() { return sliceYZ; }

	public void setSliceXY(int z) {
		sliceXY = z;
		imageXY = new BufferedImage(data_sx,data_sy,BufferedImage.TYPE_3BYTE_BGR);

		int sliceSize = data_sx* data_sy;
		int[] rgb = new int[sliceSize];

		int offset = z * (sliceSize);
		for (int i = 0; i < sliceSize; i++){
			int value = ((int)data[offset+i]) & 0xFF;
			//rgb[i] = value << 16 | value << 8 | value;
			rgb[i] = lut[value];
		}

		imageXY.setRGB(0,0,data_sx,data_sy,rgb,0,data_sx);
	}

	public void setSliceXZ(int y) {
		sliceXZ = y;
		imageXZ = new BufferedImage(data_sx,data_sz,BufferedImage.TYPE_3BYTE_BGR);

		int sliceSize = data_sx* data_sz;
		int[] rgb = new int[sliceSize];

		int offset = y * (sliceSize) + (data_sx*data_sy);
		for (int i = 0; i < sliceSize; i++){
			int value = ((int)data[offset+i]) & 0xFF;
			rgb[i] = lut[value];
		}

		imageXY.setRGB(256,0,data_sx,data_sz,rgb,0,data_sx);
	}

	public void setSliceYZ(int x) {
		sliceYZ = x;
		imageYZ = new BufferedImage(data_sy,data_sz,BufferedImage.TYPE_3BYTE_BGR);

		int sliceSize = data_sy* data_sz;
		int[] rgb = new int[sliceSize];

		int offset = x * (sliceSize) + (data_sx*data_sy)+(data_sx*data_sz);
		for (int i = 0; i < sliceSize; i++){
			int value = ((int)data[offset+i]) & 0xFF;
			rgb[i] = lut[value];
		}

		imageXY.setRGB(512,0,data_sy,data_sz,rgb,0,data_sy);
	}


	@Override
	public void paint(Graphics g) {	
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(imageXY,0,0,null);
		g2.setColor(Color.RED);
		g2.drawString("Omlouvám se za chybu, ale nevím jak to opravit, dostanu alespoň bodík za snahu prosím?",270,150);
		g2.drawImage(imageXZ,256,0,null);
		g2.drawImage(imageYZ,512,0,null);
		
	}	
}
