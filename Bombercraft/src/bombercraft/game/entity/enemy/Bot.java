package bombercraft.game.entity.enemy;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import bombercraft.game.level.Map;
import utils.GVector2f;

public abstract class Bot extends Entity{
	public final static String WORKER_A = "workerA";
	public final static String WORKER_B = "workerB";
	public static final String WORKER_C = "workerC";
	public final static String ENEMY_A = "enemyA";
	public final static String ENEMY_B = "enemyB";
	public static final String ENEMY_C = "enemyC";
	
	protected final static int UNDEFINED = -2;
	protected int 			direction = UNDEFINED;
	protected int 			speed;
	protected int 			healt;
	protected int 			maxHealt;
	protected int 			maxSpeed;
	
	

	public Bot(GVector2f position, GameAble parent, int speed, int healt) {
		super(position, parent);

		maxSpeed = this.speed = speed;
		maxHealt = this.healt = healt;

		if(direction == UNDEFINED)
			this.direction = getRandPossibleDir(parent.getLevel().getMap());
	}
	
	protected int getRandPossibleDir(Map mapa){
		int[] ret = mapa.getPossibleWays(getSur());
		
		int dir = -1;
		
		if(ret.length > 0)
			dir = ret[(int)(Math.random() * ret.length)];
		
		return dir;
	}


	public void hit(int demage){
		healt -= demage;
		if(healt <= 0)
			alive = false;
	}

	//GETTERS
	
	public GVector2f getSur() {return position.div(Block.SIZE.mul(getParent().getZoom())).toInt();}
	public GVector2f getSize() {return Block.SIZE;}

}
