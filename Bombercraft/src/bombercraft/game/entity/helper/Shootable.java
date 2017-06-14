package bombercraft.game.entity.helper;

import utils.math.GVector2f;

public interface Shootable {
	public GVector2f 	getPosition();
	public GVector2f 	getDirection();
	public int 			getDemage();
	public int 			getBulletSpeed();
	public int 			getBulletHealt();
	public String		getBulletType();
}
