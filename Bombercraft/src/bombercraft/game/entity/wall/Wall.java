package bombercraft.game.entity.wall;

import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.GVector2f;

public abstract class Wall extends Entity{

	public Wall(GVector2f position, GameAble parent) {
		super(position, parent);
	}

	public abstract void renderWalls(Graphics2D g2);
	
	protected int getType(){
		boolean[] n = new boolean[]{
				getParent().hasWall(getPosition().getXi(), getPosition().getYi() - Block.SIZE.getY()),
				getParent().hasWall(getPosition().getXi() + Block.SIZE.getX(), getPosition().getYi()),
				getParent().hasWall(getPosition().getXi(), getPosition().getYi() + Block.SIZE.getY()),
				getParent().hasWall(getPosition().getXi() - Block.SIZE.getX(), getPosition().getYi())
			};
			//0
			int i = 0;
			//4
			if(n[0] && n[1] && n[2] && n[3])
				i = 15;
			//1
			if(n[0] && !n[1] && !n[2] && !n[3])
				i = 1;
			if(!n[0] && !n[1] && !n[2] && n[3])
				i = 2;
			if(!n[0] && !n[1] && n[2] && !n[3])
				i = 3;
			if(!n[0] && n[1] && !n[2] && !n[3])
				i = 4;
			//2
			if(n[0] && !n[1] && !n[2] && n[3])
				i = 5;
			if(n[0] && n[1] && !n[2] && !n[3])
				i = 6;
			if(!n[0] && n[1] && n[2] && !n[3])
				i = 7;
			if(!n[0] && !n[1] && n[2] && n[3])
				i = 8;
			if(n[0] && !n[1] && n[2] && !n[3])
				i = 13;
			if(!n[0] && n[1] && !n[2] && n[3])
				i = 14;
			//3
			if(!n[0] && n[1] && n[2] && n[3])
				i = 10;
			if(n[0] && n[1] && n[2] && !n[3])
				i = 12;
			if(n[0] && n[1] && !n[2] && n[3])
				i = 9;
			if(n[0] && !n[1] && n[2] && n[3])
				i = 11;
			return i;
	}
}
