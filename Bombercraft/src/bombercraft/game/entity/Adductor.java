package bombercraft.game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.ResourceLoader;

public class Adductor extends Helper{
	private int maxDistance;
	private static Image image = ResourceLoader.loadTexture("adductor.png");
	public Adductor(GVector2f position, GameAble parent) {
		super(position, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().mul(getParent().getZoom());
		g2.drawImage(image, pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), null);
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
