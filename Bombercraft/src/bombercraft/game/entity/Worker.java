package bombercraft.game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.enemy.Bot;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.Utils;

public class Worker extends Bot{
	protected float 		borderSize = 1;
	protected Color 		borderColor = Color.white;
	protected Color 		color  = Color.PINK;
	protected int 			offset = 5;
	protected int 			round = 10;
	
	public Worker(GVector2f position, GameAble parent, int speed, int healt) {
		super(position, parent, speed, healt);
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f totalPos = position.add(offset).mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f totalSize = getSize().sub(offset * 2).mul(getParent().getZoom()) ;
		int r = (int)(round * getParent().getZoom());
		g2.setColor(color);
		g2.fillRoundRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi(), r, r);
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		
		int offset = 3;
		g2.drawLine(totalPos.getXi() + offset, totalPos.getYi() - totalSize.getYi() / 2, 
					totalPos.getXi() + offset, totalPos.getYi() + totalSize.getYi() / 2);
		g2.drawLine(totalPos.getXi() - offset + totalSize.getXi(), totalPos.getYi() - totalSize.getYi() / 2, 
					totalPos.getXi() - offset + totalSize.getXi(), totalPos.getYi() + totalSize.getYi() / 2);
		
		
		
	}
	
	@Override
	public void update(float delta) {
		if(position.mod(Block.SIZE).isNull()){
			GVector2f nextPos = position.add(Utils.getMoveFromDir(direction).mul(Block.SIZE));
			Block b = getParent().getLevel().getMap().getBlockOnPosition(nextPos);
			if(b == null || !b.isWalkable())
				direction = getRandPossibleDir(getParent().getLevel().getMap());
		}
		
		if(direction == -1)
			return;
		
		position = position.add(Utils.getMoveFromDir(direction).mul(speed));
	}

	@Override
	public String toJSON() {
		return null;
	}

}
