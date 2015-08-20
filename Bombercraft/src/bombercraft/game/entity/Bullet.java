package bombercraft.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.GVector2f;
import bombercraft.game.GameAble;
import bombercraft.game.level.Block;

public abstract class Bullet extends Entity{
	private GVector2f size; 
	private GVector2f direction;
	private int offset = 10;
	private int round = 15;
	private int speed;
	private int healt;
	private int demage;
	private Color color;
	
	public Bullet(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage, int round, int offset) {
		super(position, parent);
		this.direction = direction;
		this.demage = demage;
		this.speed = speed;
		this.healt = healt;
		size = size.sub(offset * 2);
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset()).add(offset);
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), round, round);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), round, round);
	}
	
	@Override
	public void update(float delta) {
		Block block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(Block.SIZE.div(2)));
		if(block != null && block.getType() != Block.NOTHING){
			healt--;
			block.hit(demage);
		}
		
		if(healt <= 0)
			alive = false;
		
		
		if(alive)
			position = position.add(direction.mul(speed));
		
	}
}
