package bombercraft.game.entity.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class Particle extends Entity{
	private GVector2f direction;
	private Color color;
	private int healt;
	private GVector2f size;
	
	//CONTRUCTORS
	
	public Particle(GVector2f position, GameAble parent, Color color, GVector2f direction, GVector2f size, int healt) {
		super(position, parent);
		this.direction = direction;
		this.color = color;
		this.healt = healt;
		this.size = size;
	}

	//OVERRIDES
	
	@Override
	public void update(float delta) {
		position = position.add(direction.mul(getParent().getZoom()));
		healt--;
		if(healt <= 0)
			alive = false;
			
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(size.div(2)).mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = size.mul(getParent().getZoom());
		g2.setColor(color);
		g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
	}
	
	@Override
	public String toJSON() {
		return null;
	}
	
	@Override
	public GVector2f getPosition() {
		return super.getPosition().div(getParent().getZoom());
	}
	
	//GETTERS
	
	public GVector2f getSur() {return position.div(Block.SIZE).toInt();}
	public GVector2f getSize() {return size;}

}
