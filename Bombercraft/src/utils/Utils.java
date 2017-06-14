package utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import utils.math.GVector2f;

public class Utils {
	public static  GVector2f getMoveFromDir(int dir){
		switch(dir){
			case 0:
				return new GVector2f(00, -1);
			case 1:
				return new GVector2f(01, 00);
			case 2:
				return new GVector2f(00, 01);
			case 3:
				return new GVector2f(-1, 00);
			default:
				return new GVector2f();
		}
	}

	public static GVector2f getNormalMoveFromDir(int dir) {
		switch(dir){
			case 0:
				return new GVector2f(-1, 0);
			case 1:
				return new GVector2f(01, 00);
			case 2:
				return new GVector2f(00, -1);
			case 3:
				return new GVector2f(00, 01);
			default:
				return new GVector2f();
		}
	}
	
	public static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static BufferedImage deepCopy2(final BufferedImage src) {
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(),src.getType());
	    int[] srcbuf = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
	    int[] dstbuf = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
	    int width = src.getWidth();
	    int height = src.getHeight();
	    int dstoffs = 0 + 0 * dst.getWidth();
	    int srcoffs = 0;
	    for (int y = 0 ; y < height ; y++ , dstoffs+= dst.getWidth(), srcoffs += width ) {
	        System.arraycopy(srcbuf, srcoffs , dstbuf, dstoffs, width);
	    }
	    return dst;
	}
}
