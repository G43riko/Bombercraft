package bombercraft.game.entity.helper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import core.Input;
import utils.GVector2f;
import utils.LineLineIntersect;

public class TowerLaser extends Tower{
	private GVector2f point2;
	public TowerLaser(GVector2f position, GameAble parent) {
		super(position, parent, 400, 2, 50, 8, 50);
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f size = getSize().mul(getParent().getZoom());
		g2.setColor(Color.GREEN);
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
		g2.setColor(Color.white);
		g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());
		
		GVector2f tempPos = point2;
		point2 = pos.add(toMouse);
		
		g2.setStroke(new BasicStroke(1 * getParent().getZoom()));
		g2.setColor(Color.red);
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

	@Override
	public String toJSON() {
		return "";
	}
}
