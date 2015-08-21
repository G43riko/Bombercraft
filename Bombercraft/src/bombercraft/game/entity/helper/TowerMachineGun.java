package bombercraft.game.entity.helper;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Enemy;
import utils.GVector2f;

public class TowerMachineGun extends Tower{
	
	public TowerMachineGun(GVector2f position, GameAble parent) {
		super(position, parent, 200, 1, 100, 5, 40);
		setRandTarget();
	}
	
	@Override
	public void update(float delta) {
		
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
		
		if(target != null){
			GVector2f dir = position.sub(target.getPosition()).Normalized();
			angle = Math.atan2(dir.getX(), dir.getY());
			
			if(target.getPosition().dist(position) > getRange() || !target.isAlive())
				target = null;
		}
		else {
			if(angle == -1)
				angle = Math.random() * 360;
			setRandTarget();
		}	 
		
		GVector2f toMouse = getDirection();
		GVector2f point2 = pos.add(toMouse.mul(cannonLength * getParent().getZoom()));
			
		if(Math.random() < 0.01)
			shot();
		
		g2.setStroke(new BasicStroke(cannonWidth * getParent().getZoom()));
		g2.setColor(canonColor);
		g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());
	}
	
	private void setRandTarget(){
		ArrayList<Enemy> enemies = getParent().getEnemiesAround(position, getRange());
		
		if(enemies.size() > 0){
			//target = enemies.get((int)(Math.random() * enemies.size())); -- vyberalo náhodného nie najbližšie
			target = enemies.stream()
							.reduce((a, b) -> a.getPosition().dist(position) > b.getPosition().dist(position) ? a : b).get();
			GVector2f dir = position.sub(target.getPosition()).Normalized();
			angle = Math.toDegrees(Math.atan2(dir.getX(), dir.getY()));
		}
	}

	@Override
	public String toJSON() {
		return "";
	}



}
