package bombercraft.game.entity.helper;

import java.awt.Color;

import utils.GVector2f;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.entity.Helper;
import bombercraft.game.level.Block;

public abstract class Tower extends Helper{
	protected Entity target = null;
	private int range = 200;
	private int accularity;
	private int demage;
	protected int cannonWidth;
	protected int cannonLength;
	protected Color canonColor = Color.YELLOW;
	
	protected float borderSize = 1;
	protected Color borderColor = Color.white;
	protected Color color = Color.cyan;
	
	
	
	public Tower(GVector2f position, GameAble parent, int range, int demage, int accularity, int cannonWidth, int cannonLength) {
		super(position, parent);
		this.range = range;
		this.demage = demage;
		this.accularity = accularity;
		this.cannonWidth = cannonWidth;
		this.cannonLength = cannonLength;
	}
	
	public int getRange() {
		return range;
	}

	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE).toInt();
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}
}
