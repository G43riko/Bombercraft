package bombercraft.game.entity.wall;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class WallA extends Wall{
	private boolean[] neightboards;
	GVector2f p0, p1, p2, p3;
	int offset;
	
	public WallA(GVector2f position, GameAble parent) {
		super(position, parent);
		
	}
	
	private void calcPoints(GVector2f pos, GVector2f size){
		p0 = pos.add(offset);
		p1 = pos.add(offset);
		p2 = pos.add(size).sub(offset);
		p3 = pos.add(offset);
		
		p1.addToX(size.getX() - offset * 2);
		p3.addToY(size.getY() - offset * 2);
		
		if(neightboards[0])
			p0.addToY(-offset * 2);
		if(neightboards[3])
			p0.addToX(-offset * 2);
		
		if(neightboards[0])
			p1.addToY(-offset * 2);
		if(neightboards[1])
			p1.addToX(+offset * 2);
		
		if(neightboards[2])
			p2.addToY(+offset * 2);
		if(neightboards[1])
			p2.addToX(+offset * 2);
		
		if(neightboards[2])
			p3.addToY(+offset * 2);
		if(neightboards[3])
			p3.addToX(-offset * 2);
	}
	
	public void renderWalls(Graphics2D g2){
		offset = (int)(5 * getParent().getZoom());
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		
		boolean[] tempNeightboards = new boolean[]{
			getParent().hasWall(getPosition().getXi(), getPosition().getYi() - Block.SIZE.getY()),
			getParent().hasWall(getPosition().getXi() + Block.SIZE.getX(), getPosition().getYi()),
			getParent().hasWall(getPosition().getXi(), getPosition().getYi() + Block.SIZE.getY()),
			getParent().hasWall(getPosition().getXi() - Block.SIZE.getX(), getPosition().getYi())
		};

//		if(!Arrays.equals(neightboards, tempNeightboards)){
			neightboards = tempNeightboards;
			calcPoints(pos, totalSize);
//		}
		
		renderBorders(g2, pos, totalSize);
		
	}
	
	private void renderBorders(Graphics2D g2, GVector2f pos, GVector2f size){
		g2.setColor(Color.LIGHT_GRAY);
		if(!neightboards[0])
			g2.fillPolygon(new int[]{pos.getXi(), pos.getXi() + size.getXi(), p1.getXi(), p0.getXi()},
					   	   new int[]{pos.getYi(), pos.getYi(), p1.getYi(),  p0.getYi()}, 
					   	   4);
		if(!neightboards[1])
			g2.fillPolygon(new int[]{pos.getXi() + size.getXi(), pos.getXi() + size.getXi(), p2.getXi(), p1.getXi()},
				   	   	   new int[]{pos.getYi(), pos.getYi() + size.getYi(), p2.getYi(),  p1.getYi()},
				   	   	   4);
		
		g2.setColor(Color.DARK_GRAY);
		if(!neightboards[2])
			g2.fillPolygon(new int[]{pos.getXi() , p3.getXi(), p2.getXi(), pos.getXi() + size.getXi()},
						   new int[]{pos.getYi() + size.getYi(), p3.getYi(), p2.getYi(), pos.getYi() + size.getYi()},
			   	   	   	   4);
		if(!neightboards[3])
			g2.fillPolygon(new int[]{pos.getXi(), p0.getXi(), p3.getXi(), pos.getXi()},
						   new int[]{pos.getYi(), p0.getYi(), p3.getYi(), pos.getYi() + size.getYi()},
			   	   	   		4);
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(p0 == null)
			return;
		
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		
		
		
		g2.setColor(Color.GRAY);
//		g2.drawImage(SpriteViewer.getImage("tileset.png", 1, 0), 
//					 pos.getXi(), 
//					 pos.getYi(), 
//					 totalSize.getXi(), 
//					 totalSize.getXi(), null);
		g2.fillRect(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getXi());
		
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
