package bombercraft.game.entity.helper.tower;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

import bombercraft.Bombercraft;
import bombercraft.game.GameAble;
import bombercraft.game.entity.helper.bullet.Bullet;
import bombercraft.game.level.Block;
import core.Input;
import utils.GVector2f;

public class TowerLaser extends Tower{
	private GVector2f point2;
	private Color laserColor = Color.red;
	private int laserWidth = 1;
	
	public TowerLaser(GVector2f position, GameAble parent) {
		super(position, parent, 400, 2, 50, 8, 50);
		backgroundColor = Color.GREEN;
		canonColor = Color.white;
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f size = getSize().mul(getParent().getZoom());
		g2.setColor(backgroundColor);
		g2.fillPolygon(new int[]{pos.getXi() + size.getXi() / 2 ,
								 pos.getXi() + size.getXi(), 
								 pos.getXi() + size.getXi() / 2,
								 pos.getXi()},
					   new int[]{pos.getYi(),
							     pos.getYi() + size.getYi() / 2 ,
							     pos.getYi() + size.getYi(), 
							     pos.getYi() + size.getYi() / 2}, 4);
		
		pos = pos.add(size.div(2));
		GVector2f toMouse = Input.getMousePosition().sub(pos);
		point2 = pos.add(toMouse.Normalized().mul(cannonLength * getParent().getZoom()));
		
		g2.setStroke(new BasicStroke(cannonWidth * getParent().getZoom()));
		g2.setColor(canonColor);
		g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());
		
		GVector2f tempPos = point2;
		point2 = pos.add(toMouse);
	
		if(Bombercraft.getViewOption("towerLasers")){
			g2.setStroke(new BasicStroke(laserWidth * getParent().getZoom()));
			g2.setColor(laserColor);
			ArrayList<GVector2f> possibles = getParent().getLevel()
														.getMap()
														.getBlocks()
														.stream()
														.filter(a -> a.getType() != Block.NOTHING)
														.map(a -> a.getInterSect(tempPos.add(getParent().getOffset()), 
																				  point2.add(getParent().getOffset())))
														.filter(a -> a != null)
														.collect(Collectors.toCollection(ArrayList::new));
											   
			if(possibles.size() > 0)
				point2 = possibles.stream()
								  .reduce((a, b) -> a.dist(tempPos) < b.dist(tempPos) ? a : b)
								  .get()
								  .sub(getParent().getOffset());
			
			g2.drawLine(tempPos.getXi(), tempPos.getYi(), point2.getXi(), point2.getYi());
		}
	}

	@Override
	public String toJSON() {
		return "";
	}

	@Override
	public String getBulletType() {
		return Bullet.BULLET_LASER;
	}

}
