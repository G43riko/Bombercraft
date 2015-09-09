package bombercraft.game.entity.enemy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.Config;
import bombercraft.game.Game;
import bombercraft.game.entity.helper.bullet.Bullet;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.Utils;

public class EnemyB extends Enemy{
	private static HashMap<String, String> data = Bombercraft.getData(Enemy.ENEMY_B);
	
	public EnemyB(GVector2f position, Game parent) {
		super(position, 
			  parent, 
			  Integer.valueOf(data.get("speed")), 
			  Integer.valueOf(data.get("bulletSpeed")), 
			  Integer.valueOf(data.get("cadence")), 
			  Integer.valueOf(data.get("demage")), 
			  Integer.valueOf(data.get("healt")));
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
		g2.setStroke(new BasicStroke(getParent().getZoom() * 1));
		g2.drawRoundRect(pos.getXi(), pos.getYi(), tempSize.getXi(), tempSize.getYi(), tempRound, tempRound);
		
		g2.setStroke(new BasicStroke(getParent().getZoom() * signWidth));
		tempRound = (int)(2 * getParent().getZoom());
		g2.drawLine(pos.getXi() + tempRound, 
					pos.getYi() + tempRound, 
					pos.getXi() + tempSize.getXi() - tempRound, 
					pos.getYi() + tempSize.getYi() - tempRound);
		g2.drawLine(pos.getXi() + tempSize.getXi() - tempRound, 
					pos.getYi() + tempRound, 
					pos.getXi() + tempRound, 
					pos.getYi() + tempSize.getYi() - tempRound);

		g2.setColor(Color.red);
		g2.drawRect(pos.getXi(), pos.getYi() - 6, tempSize.getXi(), 4);
		
		if(healt > 0)
			g2.fillRect(pos.getXi(), pos.getYi() - 6, (int)(tempSize.getX() * healt / maxHealt) , 4);
	}
	
	@Override
	public void update(float delta) {
		if(position.mod(Block.SIZE).isNull())
			direction = getRandPossibleDir(getParent().getLevel().getMap());
		
		if(direction == -1)
			return;
		
		position = position.add(Utils.getMoveFromDir(direction).mul(speed));
	}
	
	public void fire() {
		if(direction >= 0 && (System.currentTimeMillis() - lastShot) >= cadence){
			getParent().addBullet(position, 
								  Utils.getMoveFromDir(direction), 
								  bulletSpeed, 
								  attack,
								  Bullet.BULLET_BASIC,
								  Config.BULLET_DEFAULT_HEALT);
			
			lastShot = System.currentTimeMillis();
		}
	}

}
