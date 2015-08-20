package bombercraft.game.entity.helper;

import utils.GVector2f;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Helper;

public abstract class Bomb extends Helper{
	protected int range;
	public Bomb(GVector2f position, GameAble parent) {
		super(position, parent);
	}

}
