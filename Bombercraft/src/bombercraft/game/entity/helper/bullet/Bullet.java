package bombercraft.game.entity.helper.bullet;

import java.awt.Color;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.GVector2f;

public abstract class Bullet extends Entity{
	public final static String BULLET_BASIC 	= "bulletBasic";
	public final static String BULLET_LASER 	= "bulletLaser";
	public final static String BULLET_ARROW 	= "bulletArrow";
	public final static String BULLET_MAGIC 	= "bulletMagic";
	public final static String BULLET_BOOMERANG = "bulletBoomerang";
	
	protected String emitterOnHit;// = Emitter.PARTICLE_EXPLOSION_TEST;
	
	private GVector2f 	size; 
	private GVector2f 	direction;
	private int 		speed;
	private int 		healt;
	private int 		demage;
	private Color 		color;
	
	//CONTRUCTORS
	
	public Bullet(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage, GVector2f size, Color color, String emmiterOnHit) {
		super(position, parent);
		
		this.emitterOnHit = emmiterOnHit;
		this.direction = direction;
		this.demage = demage;
		this.speed = speed;
		this.healt = healt;
		this.color = color;
		this.size = size;
	}

	//OVERRIDES
	
	@Override
	public void update(float delta) {
		Block block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(Block.SIZE.div(2)));
		
		if(block != null && !block.isWalkable()){
			hit();
			block.hit(demage);
			getParent().getConnection().hitBlock(getPosition(), demage);
		}
		else if(getParent().bulletHitEnemy(this)){
			hit();
		}
		
		if(healt <= 0){
			alive = false;
		}
		
		checkBorders();
		
		if(alive)
			position = position.add(direction.mul(speed));
		
	}
	
	//OTHERS
	
	protected void hit(){
		healt--;
		if(emitterOnHit != null)
			getParent().getConnection().putEmmiter(emitterOnHit, position/*.add(size.div(2))*/);
	}

	protected void checkBorders(){
		GVector2f a = position.add(size);
		GVector2f b = getParent().getLevel().getMap().getNumberOfBlocks().mul(Block.SIZE).div(getParent().getZoom());
		if(a.getX() < 0 ||
		   a.getY() < 0 ||
		   position.getX() > b.getX() ||
		   position.getY() > b.getY())
			alive = false;
	}

	//GETTERS
	
	@Override
	public GVector2f getSur() {return getPosition().div(Block.SIZE).toInt();}
	public GVector2f getDirection() {return direction;}
	public GVector2f getSize() {return size;}
	public int getDemage() {return demage;}
	public Color getColor() {return color;}
	public int getSpeed() {return speed;}
	public int getHealt() {return healt;}

}
