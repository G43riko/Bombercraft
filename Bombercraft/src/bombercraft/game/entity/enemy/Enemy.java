package bombercraft.game.entity.enemy;

import java.awt.Color;

import bombercraft.Config;
import bombercraft.game.Game;
import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.math.GVector2f;
/*   |
 * --+-- B
 * 	 |
 * 
 *   O   C
 *   
 *  \ /
 *   X   D
 *  / \
 *  
 *  +-+
 *  | |  E
 *  +-+
 *  
 *  /\
 *  ||   F
 *  \/
 *  
 *  /\
 * /  \  G
 * \  /
 *  \/
 */
public abstract class Enemy extends Bot{
	
	public final static GVector2f size = Block.SIZE.sub(Config.ENEMY_DEFAULT_OFFSET * 2);
	
	protected static int 	circleSize = 14;
	protected static int 	signWidth = 2;
	
	protected long 			lastShot;
	protected int 			bulletSpeed;
	protected int 			cadence;
	protected int 			attack;
	protected float 		borderSize = 1;
	protected Color 		borderColor = Color.white;
	protected Color 		color  = Color.CYAN;
	
	public Enemy(GVector2f position, Game parent, int speed, int bulletSpeed, int cadence, int attack, int healt) {
		this(position, 
			 parent, 
			 speed, 
			 bulletSpeed, 
			 cadence, 
			 attack,
			 System.currentTimeMillis() - cadence, 
			 UNDEFINED,
			 healt);
	}
	
	public Enemy(GVector2f position, GameAble parent, int speed, int bulletSpeed, int cadence, int attack, long lastShot, int direction, int healt){
		super(position, parent, speed, healt);
		
		this.bulletSpeed = bulletSpeed;
		this.lastShot = lastShot;
		this.position = position;
		this.cadence = cadence;
		this.attack = attack;
		
		if(direction != UNDEFINED)
			this.direction = direction;
		
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
