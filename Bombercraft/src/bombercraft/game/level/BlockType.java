package bombercraft.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import utils.resources.ResourceLoader;


public class BlockType {
	private int healt;
	private Color minimapColor;
	private Image image;
	
	//CONTRUCTORS
	
	public BlockType(String fileName, int healt) {
		this.healt = healt;
		image = ResourceLoader.loadTexture(fileName);

		
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();

	    minimapColor = averageColor(bimage, 0, 0, image.getWidth(null), image.getHeight(null));
	}

	//OTHERS
	
	private static Color averageColor(BufferedImage bi, int x0, int y0, int w, int h) {
	    int x1 = x0 + w;
	    int y1 = y0 + h;
	    int sumr = 0, sumg = 0, sumb = 0;
	    for (int x = x0; x < x1; x++) {
	        for (int y = y0; y < y1; y++) {
	            Color pixel = new Color(bi.getRGB(x, y));
	            sumr += pixel.getRed();
	            sumg += pixel.getGreen();
	            sumb += pixel.getBlue();
	        }
	    }
	    int num = w * h;
	    System.out.println((sumr / num)+ " " + (sumg / num) + " " + (sumb / num));
	    return new Color(sumr / num, sumg / num, sumb / num);
	}
	
	//GETTERS
	
	public int getHealt() {return healt;}
	public Color getMinimapColor() {return minimapColor;}
	public Image getImage() {return image;}
}
