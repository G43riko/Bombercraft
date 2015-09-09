package bombercraft.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import bombercraft.game.GameAble;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.PathFinder;

public class Path extends Entity{
	private ArrayList<GVector2f> points;
	
	public Path(GameAble parent, GVector2f start, GVector2f end) {
		super(new GVector2f(), parent);
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		
		getParent().getLevel()
				   .getMap()
				   .getBlocks()
				   .stream()
				   .forEach(a -> h.put(a.getPosition().div(Block.SIZE).toString(), a.isWalkable() ? 0 : 1 ));
		new Thread(new Runnable(){
			public void run() {
				points = PathFinder.findPath(h, start.toString(), end.toString(), false);
				if(!points.isEmpty()){
					points.add(0, end);
					points.add(start);
				}
			}
		}).start();
	}

	@Override
	public void render(Graphics2D g2) {
		if(points == null)
			return ;
		g2.setColor(Color.blue);
		for(int i = 1 ; i < points.size() ; i++){
			GVector2f a = points.get(i).mul(Block.SIZE).add(Block.SIZE.div(2)).mul(getParent().getZoom()).sub(getParent().getOffset());
			GVector2f b = points.get(i - 1).mul(Block.SIZE).add(Block.SIZE.div(2)).mul(getParent().getZoom()).sub(getParent().getOffset());
			g2.drawLine(a.getXi(), a.getYi(), b.getXi(), b.getYi());
		}
	}
	
	@Override
	public String toJSON() {
		return null;
	}

	@Override
	public GVector2f getSur() {
		return null;
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE.mul(getParent().getLevel().getMap().getNumberOfBlocks()).mul(getParent().getZoom());
	}

}
