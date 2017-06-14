package bombercraft.game.entity.enemy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.Config;
import bombercraft.game.Game;
import bombercraft.game.entity.helper.Shootable;
import bombercraft.game.entity.helper.bullet.Bullet;
import bombercraft.game.level.Block;
import utils.Utils;
import utils.math.GVector2f;

public class EnemyC extends Enemy implements Shootable{
	private static HashMap<String, String> data = Bombercraft.getData(Enemy.ENEMY_C);
	
	
	public EnemyC(GVector2f position, Game parent) {
		super(position, 
			  parent, 
			  Integer.valueOf(data.get("speed")), 
			  Integer.valueOf(data.get("bulletSpeed")), 
			  Integer.valueOf(data.get("cadence")), 
			  Integer.valueOf(data.get("demage")), 
			  Integer.valueOf(data.get("healt")));
		
		lastShot = System.currentTimeMillis() - cadence; 
	}
	
	@Override
	public void render(Graphics2D g2) {
		int tempRound = (int)(Config.ENEMY_DEFAULT_ROUND * getParent().getZoom());
		
		GVector2f pos = position.add(Config.ENEMY_DEFAULT_OFFSET).mul(getParent().getZoom()).sub(getParent().getOffset());
		
		GVector2f tempSize = size.mul(getParent().getZoom());
		
		
		g2.setStroke(new BasicStroke(getParent().getZoom() * borderSize));
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), tempSize.getXi(), tempSize.getYi(), tempRound, tempRound);

		g2.setColor(borderColor);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), tempSize.getXi(), tempSize.getYi(), tempRound, tempRound);

		g2.fillOval(pos.getXi() + tempSize.getXi() / 2 - circleSize / 2, 
					pos.getYi() + tempSize.getYi() / 2 - circleSize / 2, 
					circleSize, 
					circleSize);
		
		g2.setStroke(new BasicStroke(getParent().getZoom() * 1));
		g2.setColor(Color.red);
		g2.drawRect(pos.getXi(), pos.getYi() - 6, tempSize.getXi(), 4);
		
		if(healt > 0)
			g2.fillRect(pos.getXi(), pos.getYi() - 6, (int)(tempSize.getX() * healt / maxHealt) , 4);
		
		
	}

	@Override
	public void update(float delta) {
		if(position.mod(Block.SIZE).isNull()){
			GVector2f nextPos = position.add(Utils.getMoveFromDir(direction).mul(Block.SIZE));
			Block b = getParent().getLevel().getMap().getBlockOnPosition(nextPos);
			if(b == null || !b.isWalkable())
				direction = getRandPossibleDir(getParent().getLevel().getMap());
		}
		
		if(canShot())
			fire();
			
		
		if(direction == -1)
			return;
		
		position = position.add(Utils.getMoveFromDir(direction).mul(speed));
	}
	
	public boolean canShot(){
		return System.currentTimeMillis() - lastShot > cadence; 
	}
	
	public void fire(){
		lastShot = System.currentTimeMillis();
		getParent().getConnection().putBullet(this);
	}

	@Override
	public GVector2f getDirection() {
		return Utils.getMoveFromDir(direction);
	}

	@Override
	public int getBulletSpeed() {
		return bulletSpeed;
	}

	@Override
	public int getDemage() {
		return attack;
	}

	@Override
	public String getBulletType() {
		return Bullet.BULLET_BASIC;
	}

	@Override
	public int getBulletHealt() {
		return 1;
	}
}
