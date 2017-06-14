package bombercraft.game.entity;

import java.awt.Graphics2D;
import java.awt.Image;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.math.GVector2f;
import utils.resources.ResourceLoader;

public class Respawner extends Helper{
	private String type;
	private int interval;
	private long lastRespawn;
	private boolean active = true;
	private static Image image = ResourceLoader.loadTexture("respawner.png");
	
	public Respawner(GameAble parent, GVector2f position, String type, int interval) {
		super(position, parent);
		this.type = type;
		this.interval = interval;
		lastRespawn = System.currentTimeMillis() - interval;
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f size = getSize().mul(getParent().getZoom());
		g2.drawImage(image, pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
	}
	
	@Override
	public void update(float delta) {
		if(active && canRespawn())
			respawn();
	}
	
	private void respawn(){
		getParent().addEnemy(position, type);
		lastRespawn = System.currentTimeMillis();
	}
	
	private boolean canRespawn(){
		return System.currentTimeMillis() - lastRespawn > interval;
	}
	

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}
}
