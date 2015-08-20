package bombercraft.game.entity;

import bombercraft.game.GameAble;
import utils.GVector2f;

public abstract class Helper extends Entity {
	public final static int TOWER_MACHINE_GUN	= 1000;
	public final static int TOWER_LASER 		= 1001;
	public final static int TOWER_ROCKET 		= 1002;
	public final static int TOWER_FLAME_THROWER = 1003;
	public final static int TOWER_SNIPER 		= 1004;
	
	public final static int BOMB_NORMAL 		= 2000;
	public final static int BOMB_ATOM 			= 2001;
	public final static int BOMB_NANO 			= 2002;
	public final static int BOMB_FIRE			= 2003;
	public final static int BOMB_FREEZE 		= 2004;
	
	
	public Helper(GVector2f position, GameAble parent) {
		super(position, parent);
	}

}
