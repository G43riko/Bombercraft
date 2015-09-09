package bombercraft.game.entity.helper.tower;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.enemy.Enemy;
import bombercraft.game.entity.helper.bullet.Bullet;
import utils.GVector2f;

public class TowerMachineGun extends Tower{
	private static HashMap<String, String> data = Bombercraft.getData(Helper.TOWER_MACHINE_GUN);
	
	public TowerMachineGun(GVector2f position, GameAble parent) {
		super(position, 
			  parent, 
			  Integer.parseInt(data.get("range")),
			  Integer.parseInt(data.get("demage")),
			  Integer.parseInt(data.get("cadence")), 
			  Integer.parseInt(data.get("canonWidth")), 
			  Integer.parseInt(data.get("canonLength")));
		setRandTarget();
	}
	@Override
	public void update(float delta) {
		if(target != null && canShot())
			fire();
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
		
		g2.setColor(backgroundColor);
		g2.fillPolygon(xPos, yPos, 4);
		
		g2.setStroke(new BasicStroke(borderSize * getParent().getZoom()));
		g2.setColor(borderColor);
		g2.drawPolygon(xPos, yPos, 4);
		
		pos = pos.add(size.div(2));
		

		
		if(angle - actAngle < cannonsSpeed){
			actAngle = angle;
		}
		else
			actAngle += actAngle<angle ? cannonsSpeed : -cannonsSpeed;
		
		if(target != null){
			GVector2f dir = position.sub(target.getPosition()).Normalized();
			angle = Math.atan2(dir.getX(), dir.getY());
			
			if(target.getPosition().dist(position) > getRange() || !target.isAlive())
				target = null;
		}
		else {
			setRandTarget();
		}	 
		
		
		GVector2f toMouse = getDirection();
		GVector2f point2 = pos.add(toMouse.mul(cannonLength * getParent().getZoom()));
		g2.setStroke(new BasicStroke(cannonWidth * getParent().getZoom()));
		g2.setColor(canonColor);
		g2.drawLine(pos.getXi(), pos.getYi(), point2.getXi(), point2.getYi());
		

		if(Bombercraft.getViewOption("towerRange")){
			g2.setColor(rangeColor);
			g2.setStroke(new BasicStroke(1));
			pos = pos.sub(getRange() * getParent().getZoom());
			int finalSize = (int)(getRange() * 2 * getParent().getZoom());
			g2.drawArc(pos.getXi(), pos.getYi(), finalSize, finalSize, 0, 360);
		}
		
	}
	

	private void setRandTarget(){
		ArrayList<Enemy> enemies = getParent().getEnemiesAround(position, getRange());
		
		if(enemies.size() > 0){
			//target = enemies.get((int)(Math.random() * enemies.size())); -- vyberalo náhodného nie najbližšie
			target = enemies.stream()
							.reduce((a, b) -> a.getPosition().dist(position) > b.getPosition().dist(position) ? a : b).get();
			GVector2f dir = position.sub(target.getPosition()).Normalized();
			angle = Math.atan2(dir.getX(), dir.getY());
		}
	}

	@Override
	public String toJSON() {
		return "";
	}

	@Override
	public String getBulletType() {
		return Bullet.BULLET_BASIC;
	}



}
