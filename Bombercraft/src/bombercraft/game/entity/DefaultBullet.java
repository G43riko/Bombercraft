package bombercraft.game.entity;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.GVector2f;

public class DefaultBullet extends Bullet{

	public DefaultBullet(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage, int round, int offset) {
		super(position, parent, direction, speed, healt, demage, round, offset);
	}

	@Override
	public String toJSON() {
		return "";
	}

	@Override
	public GVector2f getSur() {
		return getPosition().div(Block.SIZE).toInt();
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}

}
