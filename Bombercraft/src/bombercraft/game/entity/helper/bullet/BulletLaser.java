package bombercraft.game.entity.helper.bullet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.particles.Emitter;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class BulletLaser extends Bullet{
	int width = 3;
	
	public BulletLaser(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage) {
		super(position, parent, direction, speed, healt, demage, Block.SIZE, Color.red, Emitter.PARTICLE_EXPLOSION_TEST);
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f pos2 = pos.sub(getDirection().mul(getSpeed()));
		
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(width));
		g2.drawLine(pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi());
		
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return "";
	}



}
