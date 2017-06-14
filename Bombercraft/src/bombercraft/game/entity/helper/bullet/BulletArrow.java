package bombercraft.game.entity.helper.bullet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.particles.Emitter;
import utils.math.GVector2f;

public class BulletArrow extends Bullet {

	public BulletArrow(GVector2f position, 
					   GameAble parent, 
					   GVector2f direction, 
					   int speed, 
					   int healt, 
					   int demage){
		super(position, 
			  parent, 
			  direction, 
			  speed, 
			  healt, 
			  demage, 
			  new GVector2f(2, 40), 
			  Color.orange, 
			  Emitter.PARTICLE_EXPLOSION_BOW_HIT);
	}

	@Override
	public String toJSON() {
		return null;
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f pos2 = pos.sub(getDirection().mul(getSpeed()));
		
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getSize().getX()));
		g2.drawLine(pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi());
	}
}
