package bombercraft.game.entity.helper;

import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.Bombercraft;
import bombercraft.Config;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Helper;
import bombercraft.game.level.Block;
import utils.math.GVector2f;

public class Bomb extends Helper{
	private long addedAt;
	private int range;
	private int time;
	private int[] dirRange;
	private int demage;
	private boolean atom = false;
	
	public Bomb(GVector2f position, GameAble parent, int time, int range, int demage) {
		super(position, parent);
		this.time = time;
		this.range = range;
		this.demage = demage;
		
		addedAt = System.currentTimeMillis();
		dirRange = new int[]{range,range,range,range};
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = Block.SIZE.mul(getParent().getZoom());
		
		if(Bombercraft.getViewOption("renderBombRange")){
			g2.setColor(Config.BOMB_DEFAULT_RANGE_COLOR);
			if(atom){
				GVector2f area = Block.SIZE.mul(range * 2 + 1);
				GVector2f areaPos = pos.sub(area.div(2)).add(Block.SIZE.div(2));
				g2.fillArc(areaPos.getXi(), areaPos.getYi(), area.getXi(), area.getYi(), 0, 360);
			}
			else{
				g2.fillRect(pos.getXi() - Block.SIZE.getXi()  * dirRange[3], 
						    pos.getYi(), 
						    Block.SIZE.getXi()  * dirRange[3] , 
						    Block.SIZE.getYi());
				g2.fillRect(pos.getXi() + Block.SIZE.getXi(), 
						    pos.getYi(), 
						    Block.SIZE.getXi()  * dirRange[1], 
						    Block.SIZE.getYi());
				
				g2.fillRect(pos.getXi(), 
					    	pos.getYi() - Block.SIZE.getYi() * dirRange[0], 
					    	Block.SIZE.getXi(), 
					    	Block.SIZE.getYi() * (dirRange[2] + dirRange[0] + 1));
			}
		}
		
		g2.setColor(Color.black);
		g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
		
		g2.setColor(Color.white);
		g2.drawArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
		
		
		
	}
	
	@Override
	public void update(float delta) {
		if(System.currentTimeMillis() - addedAt >= time)
			explode();
	}

	@Override
	public String toJSON() {
		return "";
	}
	
	public void explode(){
		alive = false;
		getParent().getConnection().bombExplode(this);
		getParent().addExplosion(position.add(Block.SIZE.div(2)), Block.SIZE, Color.black, 10);
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
