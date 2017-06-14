package bombercraft.game.entity.wall;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class WallB extends Wall{
	private int offsetSize = 5;
	private int bridgeSize = 10;
	private int reserve = 1;
	public WallB(GVector2f position, GameAble parent) {
		super(position, parent);
	}
	
	@Override
	public void render(Graphics2D g2) {
		int offset = (int)(offsetSize * getParent().getZoom());
		
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		
		GVector2f p0 = pos.add(offset);
		GVector2f p1 = pos.add(offset);
		GVector2f p2 = pos.add(totalSize).sub(offset);
		GVector2f p3 = pos.add(offset);
		
		GVector2f p00 = pos.add(offset * 2 + reserve);
		GVector2f p11 = pos.add(offset * 2);
		GVector2f p22 = pos.add(totalSize).sub(offset * 2 + reserve);
		GVector2f p33 = pos.add(offset * 2);
		
		p1.addToX(totalSize.getX() - offset * 2);
		p3.addToY(totalSize.getY() - offset * 2);
		
		p11.addToX(totalSize.getX() - offset * 4 - reserve);
		p33.addToY(totalSize.getY() - offset * 4 - reserve);
		
		
		
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillPolygon(new int[]{p00.getXi(), p11.getXi() , p1.getXi(), p0.getXi()},
				   	   new int[]{p00.getYi(), p11.getYi(), p1.getYi(),  p0.getYi()}, 
				   	   4);
		g2.fillPolygon(new int[]{p11.getXi(), p22.getXi(), p2.getXi(), p1.getXi()},
			   	   	   new int[]{p11.getYi(), p22.getYi(), p2.getYi(),  p1.getYi()},
			   	   	   4);
		
		g2.setColor(Color.DARK_GRAY);
		g2.fillPolygon(new int[]{p33.getXi(), p3.getXi(), p2.getXi(), p22.getXi()},
					   new int[]{p33.getYi(), p3.getYi(), p2.getYi(), p22.getYi()},
		   	   	   	   4);
		g2.fillPolygon(new int[]{p00.getXi(), p0.getXi(), p3.getXi(), p33.getXi()},
					   new int[]{p00.getYi(), p0.getYi(), p3.getYi(), p33.getYi()},
		   	   	   		4);
			
		
			
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

	@Override
	public void renderWalls(Graphics2D g2) {
		int offset = (int)(offsetSize * getParent().getZoom());
		int bridge = (int)(bridgeSize * getParent().getZoom());
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		
		boolean[] neightboards = new boolean[]{
			getParent().hasWall(getPosition().getXi(), getPosition().getYi() - Block.SIZE.getY()),
			getParent().hasWall(getPosition().getXi() + Block.SIZE.getX(), getPosition().getYi()),
			getParent().hasWall(getPosition().getXi(), getPosition().getYi() + Block.SIZE.getY()),
			getParent().hasWall(getPosition().getXi() - Block.SIZE.getX(), getPosition().getYi())
		};
		
		g2.setColor(Color.GRAY);
		
		g2.fillRect(pos.getXi() + offset * 2,
					pos.getYi() + offset * 2, 
					totalSize.getXi() - offset * 4, 
					totalSize.getYi() - offset * 4);
		
		if(neightboards[0]){
			g2.setColor(Color.GRAY);
			g2.fillRect(pos.getXi() + totalSize.getXi() / 2 - bridge / 2 , 
						pos.getYi() - offset * 2 - reserve , 
						bridge, 
						offset * 4 + reserve * 2);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillPolygon(new int[]{
								pos.getXi() + totalSize.getXi() / 2 + bridge / 2,
								pos.getXi() + totalSize.getXi() / 2 + bridge / 2 + offset,
								pos.getXi() + totalSize.getXi() / 2 + bridge / 2 + offset,
								pos.getXi() + totalSize.getXi() / 2 + bridge / 2},
						   new int[]{
								pos.getYi() - offset * 2,
								pos.getYi() - offset * 2 + offset,
								pos.getYi() + offset * 2,
								pos.getYi() + offset * 2}, 
						   4);
			g2.setColor(Color.DARK_GRAY);
			g2.fillPolygon(new int[]{
								pos.getXi() + totalSize.getXi() / 2 - bridge / 2,
								pos.getXi() + totalSize.getXi() / 2 - bridge / 2 - offset,
								pos.getXi() + totalSize.getXi() / 2 - bridge / 2 - offset,
								pos.getXi() + totalSize.getXi() / 2 - bridge / 2 },
						   new int[]{
								pos.getYi() - offset * 2,
								pos.getYi() - offset * 2,
								pos.getYi() + offset,
								pos.getYi() + offset * 2}, 
						   4);
//			
			
			
		}
		if(neightboards[1]){
			g2.setColor(Color.GRAY);
			g2.fillRect(pos.getXi() + totalSize.getXi() - offset * 2 - reserve, 
						pos.getYi() + totalSize.getYi() / 2 - bridge / 2 , 
						offset * 4 + reserve * 2, 
						bridge);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillPolygon(new int[]{
								pos.getXi() + totalSize.getXi() - offset * 2 - reserve,
								pos.getXi() + totalSize.getXi() + offset + reserve,
								pos.getXi() + totalSize.getXi() + offset * 2 + reserve ,
								pos.getXi() + totalSize.getXi() - offset * 2 - reserve},
						   new int[]{
								pos.getYi() + offset * 2,
								pos.getYi() + offset * 2,
								pos.getYi() + totalSize.getYi() / 2 - bridge / 2,
								pos.getYi() + totalSize.getYi() / 2 - bridge / 2}, 
						   4);
			g2.setColor(Color.DARK_GRAY);
			g2.fillPolygon(new int[]{
								pos.getXi() + totalSize.getXi() - offset - reserve,
								pos.getXi() + totalSize.getXi() + offset * 2 + reserve,
								pos.getXi() + totalSize.getXi() + offset + reserve,
								pos.getXi() + totalSize.getXi() - offset * 2 - reserve},
						   new int[]{
								pos.getYi() + totalSize.getYi() / 2 + offset * 2 + reserve,
								pos.getYi() + totalSize.getYi() / 2 + offset * 2 + reserve,
								pos.getYi() + totalSize.getYi() / 2 + bridge / 2,
								pos.getYi() + totalSize.getYi() / 2 + bridge / 2}, 
						   4);
		}
	}

}
