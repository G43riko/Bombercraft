package bombercraft.game;

import java.awt.Graphics2D;

import bombercraft.SpritesAnimation;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.GVector2f;

public class Player extends Entity{
	private int speed;
	private int healt;
	private int range;
	private int direction = 2;

	public void setDirection(int direction) {
		this.direction = direction;
	}

	private String name;
	private String image;
	private boolean moving;
	
	public Player(GameAble parent, GVector2f position, String name, int speed, int healt, String image, int range) {
		super(position, parent);
		this.speed = speed;
		this.healt = healt;
		this.range = range;
		this.name = name;
		setImage(image);
	}
	
	public void move(GVector2f move){
		position = position.add(move.mul(speed));
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(getParent() == null || position == null)
			return;
		
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

		GVector2f size = Block.SIZE.mul(getParent().getZoom());
		
		SpritesAnimation.drawPlayer(pos, size, g2, getDirection(), getImage(), isMoving());
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
		SpritesAnimation.setSprite(image, 5, 5, 4, 6);
	}
	
	@Override
	public String toJSON() {
		return "";
	}

	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE).toInt();
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isMoving() {
		return moving;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
}
