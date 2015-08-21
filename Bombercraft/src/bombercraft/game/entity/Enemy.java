package bombercraft.game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import utils.GVector2f;
import utils.Utils;
import bombercraft.Config;
import bombercraft.game.Game;
import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import bombercraft.game.level.Map;

public class Enemy extends Entity{
	private static GVector2f size = Block.SIZE.sub(Config.ENEMY_DEFAULT_OFFSET * 2);
	private int direction;
	private int speed;
	private int bulletSpeed;
	private int cadence;
	private long lastShot;
	private int attack;
	private int healt;
	private int maxHealt;
	private int maxSpeed;
	
	private float borderSize = 1;
	private Color borderColor = Color.white;
	private Color color;
	
	public Enemy(GVector2f position, Game parent, int speed, int bulletSpeed, int cadence, int attack, int healt) {
		this(position, 
			 parent, 
			 speed, 
			 bulletSpeed, 
			 cadence, 
			 attack,
			 System.currentTimeMillis() - cadence, 
			 -2,
			 healt);
	}
	
	public Enemy(GVector2f position, GameAble parent, int speed, int bulletSpeed, int cadence, int attack, long lastShot, int direction, int healt){
		super(position, parent);
		
		this.bulletSpeed = bulletSpeed;
		this.direction = direction;
		this.lastShot = lastShot;
		this.position = position;
		this.cadence = cadence;
		this.attack = attack;
		this.speed = maxSpeed = speed;
		this.healt = maxHealt = healt;
		
		color = Color.CYAN;
		
		if(direction == -2)
			this.direction = getRandPossibleDir(parent.getLevel().getMap());
	}

	private int getRandPossibleDir(Map mapa){
		int[] ret = mapa.getPossibleWays(getSur());
		
		int dir = -1;
		
		if(ret.length > 0)
			dir = ret[(int)(Math.random() * ret.length)];
		
		return dir;
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

		g2.setStroke(new BasicStroke(getParent().getZoom() * 1));
		g2.setColor(Color.red);
		g2.drawRect(pos.getXi(), pos.getYi() - 6, tempSize.getXi(), 4);
		
		if(healt > 0)
			g2.fillRect(pos.getXi(), pos.getYi() - 6, (int)(tempSize.getX() * healt / maxHealt) , 4);
	}
	
	//dorobi�
	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE.mul(getParent().getZoom())).toInt();
	}
	
	//dorobi�
	@Override
	public GVector2f getSize() {
		return Block.SIZE;
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
								  Config.BULLET_DEFAULT_HEALT);
			
			lastShot = System.currentTimeMillis();
		}
	}
	
	public void hit(int demage){
		healt -= demage;
		if(healt <= 0)
			alive = false;
	}

	@Override
	public String toJSON() {
		return null;
	} 
}
