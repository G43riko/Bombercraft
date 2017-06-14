package bombercraft.game.entity.helper.bullet;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.particles.Emitter;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class BulletBoomerang extends Bullet{

	public BulletBoomerang(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage) {
		super(position, parent, direction, speed, healt, demage, Block.SIZE.div(4), Color.red, Emitter.PARTICLE_EXPLOSION_TEST);
	}

	@Override
	public void update(float delta) {
		
		
		if(!alive)
			return;
		
		
//		position = position.add(getDirection().mul(getSpeed()));
		position.addToX(getDirection().mul(getSpeed()).getX());
		Block block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(getSize().div(2)));
		if(block != null && block.getType() != Block.NOTHING){
			hit();
			block.hit(getDemage());
			getParent().getConnection().hitBlock(getPosition(), getDemage());
			
			getDirection().setX(getDirection().getX() * -1);
			position.addToX(getDirection().mul(getSpeed()).getX());
			block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(getSize().div(2)));
		}
		
		position.addToY(getDirection().mul(getSpeed()).getY());
		
		if(block != null && block.getType() != Block.NOTHING){
			hit();
			block.hit(getDemage());
			getParent().getConnection().hitBlock(getPosition(), getDemage());
			
			getDirection().setY(getDirection().getY() * -1);
			position.addToY(getDirection().mul(getSpeed()).getY());
		}
		
		
		
		if(getParent().bulletHitEnemy(this)){
			hit();
		}
		
		if(getHealt() <= 0){
			alive = false;
		}
		
		checkBorders();
		
		
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset()).sub(getSize().div(2));
		
		g2.setColor(Color.green);
		g2.drawArc(pos.getXi(), pos.getYi(), getSize().getXi(), getSize().getYi(), 0, 360);
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
