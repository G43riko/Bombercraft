package bombercraft.game.entity.helper.bullet;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.particles.Emitter;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class BulletDefault extends Bullet{
	private int offset	= 10;
	private int round	= 15;
	
	public BulletDefault(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage, int round, int offset, Color color) {
		super(position, parent, direction, speed, healt, demage, Block.SIZE.sub(offset * 2), color, Emitter.PARTICLE_EXPLOSION_DEFAULT_HIT);
		this.offset = offset;
		this.round = round;
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset()).add(offset);
		
		g2.setColor(getColor());
		g2.fillRoundRect(pos.getXi(), pos.getYi(), getSize().getXi(), getSize().getYi(), round, round);
	}
	
	@Override
	public String toJSON() {
		return "";
	}

}
