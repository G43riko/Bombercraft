package bombercraft.game.level;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.Config;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import utils.GVector2f;

public class Block extends Entity{
	public final static int GRASS 	= 100;
	public final static int WOOD 	= 101;
	public final static int IRON 	= 102;
	public final static int FORREST = 103;
	public final static int WATER 	= 104;
	public final static int TILE 	= 105;
	public final static int ROCK 	= 106;
	
	public final static int NOTHING = 0;
	public final static int DESTRUCTABLE = 1;
	public final static int INDESTRUCTIBLE = 2;
	
	public final static GVector2f SIZE = new GVector2f(Config.BLOCK_DEFAULT_WIDTH, Config.BLOCK_DEFAULT_HEIGHT);
	private int type;	
	private int healt;
	
	//CONTRUCTORS
	
	public Block(GVector2f position, int type, GameAble parent) {
		super(position, parent);
		this.type = Math.random() > 0.9 ? type : NOTHING;
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f size =SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
		g2.setColor(new Color(type * 85, type * 85, type * 85));
		g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
	}

	@Override
	public String toJSON() {
		return "";
	}
	
	//OTHERS
	
	public boolean hit(int demage){
		healt -= demage;
		return healt <= 0;
	}
	
	//GETTERS
	
	public int getType() {
		return type;
	}

	@Override
	public GVector2f getPosition() {
		return position.mul(SIZE);
	}

	@Override
	public GVector2f getSize() {
		return SIZE;
	}

	@Override
	public GVector2f getSur() {
		return position.div(SIZE).toInt();
	}

}
