package bombercraft.game.entity.wall;

import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.SpriteViewer;

public class WallC extends Wall{

	public WallC(GVector2f position, GameAble parent) {
		super(position, parent);
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		
		
		
		g2.drawImage(SpriteViewer.getImage("tileset_test2.png", getType(), 0), 
					 pos.getXi(), 
					 pos.getYi(), 
					 totalSize.getXi(), 
					 totalSize.getYi(), 
					 null);
	}
	
	@Override
	public void renderWalls(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}

}
