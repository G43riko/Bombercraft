package bombercraft.game.entity.helper;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Enemy;
import bombercraft.game.level.Block;
import utils.GVector2f;

public class TowerMachineGun extends Tower{
	private double angle = -1;
	
	public TowerMachineGun(GVector2f position, GameAble parent) {
		super(position, parent, 200, 1, 100, 5, 40);
		setRandTarget();
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f size = getSize().mul(getParent().getZoom());
		int[] xPos = new int[]{pos.getXi() + size.getXi() / 2 ,
				 			   pos.getXi() + size.getXi(), 
				 			   pos.getXi() + size.getXi() / 2,
				 			   pos.getXi()};
		int[] yPos = new int[]{pos.getYi(),
			     			   pos.getYi() + size.getYi() / 2 ,
			     			   pos.getYi() + size.getYi(), 
			     			   pos.getYi() + size.getYi() / 2};
		
		g2.setColor(color);
		g2.fillPolygon(xPos, yPos, 4);
		
		g2.setStroke(new BasicStroke(borderSize * getParent().getZoom()));
		g2.setColor(borderColor);
		g2.drawPolygon(xPos, yPos, 4);
		
		pos = pos.add(size.div(2));
		
		GVector2f targetPos = new GVector2f();
		if(target != null){
			targetPos = target.getPosition();
			if(targetPos.sub(getParent().getOffset()).dist(pos) > getRange()){
				target = null;
			}
		}
		else {
			if(angle == -1)
				angle = Math.random() * 360;
			targetPos = position.add(new GVector2f(Math.sin(angle), Math.cos(angle)).mul(cannonLength * getParent().getZoom()));
			setRandTarget();
		}	 
		
		GVector2f toMouse = targetPos.add(Block.SIZE.div(2)).sub(getParent().getOffset()).sub(pos).Normalized();
		GVector2f point2 = pos.add(toMouse.mul(cannonLength * getParent().getZoom()));
			
		
		
		g2.setStroke(new BasicStroke(cannonWidth * getParent().getZoom()));
		g2.setColor(canonColor);
		g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());
	}
	
	private void setRandTarget(){
		ArrayList<Enemy> enemies = getParent().getEnemiesAround(position, 200);
		
		if(enemies.size() > 0)
			target = enemies.get((int)(Math.random() * enemies.size()));
	}

	@Override
	public String toJSON() {
		return "";
	}

	


}
