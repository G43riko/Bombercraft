package bombercraft.game.level;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import bombercraft.Config;
import core.Interactable;
import utils.math.GVector2f;

public class Minimap implements Interactable{
	private GVector2f position  = Config.MINIMAP_DEFAULT_POSITION;
	private Map map;
	
	private GVector2f blockSize;
	
	public Minimap(Map map) {
		this.map = map;
		blockSize = Config.MINIMAP_DEFAULT_SIZE.div(map.getNumberOfBlocks());
	}
	
	@Override
	public void render(Graphics2D g2) {
		
		for(int i=0 ; i<map.getNumberOfBlocks().getX() ; i++)
			for(int j=0 ; j<map.getNumberOfBlocks().getY() ; j++){
				g2.setColor(Block.getColorbyType(map.getBlock(i, j).getType()));
				g2.fillRect(blockSize.getXi() * i + position.getXi(), blockSize.getYi() * j + position.getYi(), 
							blockSize.getXi(), blockSize.getYi());
			}
		
		if(Config.MINIMAP_DEFAULT_BORDER_WIDTH > 0){
			g2.setStroke(new BasicStroke(Config.MINIMAP_DEFAULT_BORDER_WIDTH));
			g2.setColor(Config.MINIMAP_DEFAULT_BACKGROUND_COLOR);
			g2.drawRect(position.getXi(), position.getYi(), 
						Config.MINIMAP_DEFAULT_SIZE.getXi(), Config.MINIMAP_DEFAULT_SIZE.getYi());
		}
	}
}
