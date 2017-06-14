package bombercraft.game.entity.helper.tower;

import java.awt.Color;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.Shootable;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public abstract class Tower extends Helper implements Shootable{
	protected Entity 	target = null;
	private int 	 	range ;
	private int 		accularity;
	private int 		bulletSpeed = 12;
	private int 		demage;
	private long 		lastShot;
	protected int 		cannonWidth;
	protected int 		cannonLength;
	protected float 	borderSize = 1;
	protected double 	cannonsSpeed = Math.PI / 180;
	protected double 	actAngle = Math.random() * Math.PI * 2;
	protected double	angle = actAngle;
	protected Color 	canonColor = Color.YELLOW;
	protected Color 	borderColor = Color.white;
	protected Color		backgroundColor = Color.cyan;
	protected Color 	rangeColor = new Color(255, 255, 0, 40);
	
	//CONTRUCTORS
	
	public Tower(GVector2f position, GameAble parent, int range, int demage, int accularity, int cannonWidth, int cannonLength) {
		super(position, parent);
		
		this.range = range;
		this.demage = demage;
		this.accularity = accularity;
		this.cannonWidth = cannonWidth;
		this.cannonLength = cannonLength;
	}
	
	//OTHERS
	
	public boolean canShot(){
		return System.currentTimeMillis() - lastShot > accularity;
	}
	
	public void fire(){
		getParent().getConnection().putBullet(this);
		lastShot = System.currentTimeMillis();
	}

	//GETTERS

	public int 			getRange() {return range;}
	public int 			getBulletHealt() {return 1;}
	public int 			getDemage() {return demage;}
	public int 			getBulletSpeed() {return bulletSpeed;}
	public GVector2f 	getDirection() {return new GVector2f(Math.sin(actAngle), Math.cos(actAngle)).negate();}
	public GVector2f 	getSur() {return position.div(Block.SIZE).toInt();}
	public GVector2f 	getSize() {return Block.SIZE; }
}
