package bombercraft;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import utils.math.GVector2f;
import utils.resources.ResourceLoader;

public class PlayerSprite {
private static HashMap<String, PlayerSprite> animations = new HashMap<String, PlayerSprite>(); 
	
	private Image image;
	private int numberOfImages;
	private int numberOfSteps;
	private int step = 0;
	private int delay;
	private int actDelay;
	private GVector2f imageSize;
	
	public PlayerSprite(String name, int numX, int numY,int positions, int delay) {
		this.image = ResourceLoader.loadTexture(name);
		this.delay = delay;
		numberOfImages = numX * numY;
		actDelay = delay;
		numberOfSteps = numberOfImages / positions ;
		imageSize = new GVector2f(image.getWidth(null) / numX, image.getHeight(null) / numY);
	}
	
	public static void setSprite(String name,  int numX, int numY, int positions, int delay){
		animations.put(name, new PlayerSprite(name, numX, numY, positions, delay));
	}

	
	public static void drawPlayer(GVector2f position, 
								  GVector2f size, 
								  Graphics2D g2, 
								  int type, 
								  String name, 
								  boolean isRunning){
		PlayerSprite sprite = animations.get(name);
		
		if(sprite == null)
			return;

		GVector2f position2 = position.add(size);
		int sourceX = sprite.step * sprite.imageSize.getXi();
		int sourceY = type * sprite.imageSize.getXi();
		
		if(!isRunning)
			sourceX = 0;
		
		g2.drawImage(sprite.image, 
					 position.getXi(), position.getYi(), 
					 position2.getXi(), position2.getYi(),
				     sourceX, sourceY, 
				     sourceX + sprite.imageSize.getXi(), sourceY + sprite.imageSize.getYi(),
				     null);
		
		checkTiming(sprite);
	}
	
	private static void checkTiming(PlayerSprite sprite){
		if(sprite.actDelay == 0){
			sprite.actDelay = sprite.delay;
			
			sprite.step++;
			if(sprite.step + 1 == sprite.numberOfSteps)
				sprite.step = 0;
		}
		else
			sprite.actDelay--;
	}

}
