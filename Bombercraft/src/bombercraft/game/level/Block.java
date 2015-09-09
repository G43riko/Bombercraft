package bombercraft.game.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import bombercraft.Config;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import utils.GVector2f;
import utils.LineLineIntersect;
import utils.SpriteViewer;
import utils.json.JSONObject;

public class Block extends Entity{
	public final static GVector2f SIZE  = Config.BLOCK_DEFAULT_SIZE;
	public final static String GRASS 	= "GRASS";
	public final static String WOOD 	= "WOOD";
	public final static String IRON 	= "IRON";
	public final static String FORREST  = "FORREST";
	public final static String WATER 	= "WATER";
	public final static String TILE 	= "TILE";
	public final static String ROCK 	= "ROCK";
	public final static String FUTURE 	= "FUTURE";
	public final static String LAVA 	= "LAVA";
	public final static String PATH 	= "PATH";
	public final static String STONE 	= "STONE";
	public final static String NOTHING = "NOTHING";
	
	private static HashMap<String, BlockType> blocks = new HashMap<String, BlockType>();
	
	static{
		blocks.put(NOTHING, new BlockType("block_floor.png", 0));
		blocks.put(GRASS, 	new BlockType("block_grass.png", 0));
		blocks.put(WOOD, 	new BlockType("block_wood.png", 1));
		blocks.put(IRON, 	new BlockType("block_iron.png", 10));
		blocks.put(WATER, 	new BlockType("block_water.png", 0));
//		blocks.put(ROCK, 	new BlockType("block_stone.png", 5));
		blocks.put(PATH, 	new BlockType("block_path.png", 0));
		blocks.put(STONE, 	new BlockType("block_stone.png", 7));
		blocks.put(FUTURE, 	new BlockType("block_future.png", 0));
	}
	
	private String type;	
	private int healt;
	
	//CONTRUCTORS
	
	public Block(JSONObject object, GameAble parent){
		this(new GVector2f(object.getString("position")),
			 object.getString("type"),
			 parent,
			 object.getInt("healt"));
	}
	
	public Block(GVector2f position, int type, GameAble parent){
		this(position, getTypeFromInt(type), parent, blocks.get(getTypeFromInt(type)).getHealt());
	}
	
	public Block(GVector2f position, String type, GameAble parent, int healt) {
		super(position, parent);
		this.healt = healt;
		this.type = type;
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
//		if(type == NOTHING)
//			return;
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
//		if(type == NOTHING){
////			Block t = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() - 1);
////			Block b = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() + 1);
////			Block r = getParent().getLevel().getMap().getBlock(position.getXi() + 1, position.getYi());
////			Block l = getParent().getLevel().getMap().getBlock(position.getXi() - 1, position.getYi());
//			
//			
//			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 0, 5), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
//		}
//		else
			g2.drawImage(blocks.get(type).getImage(), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
		
		
//		if(!isWalkable()){
//			g2.setStroke(new BasicStroke(Config.BLOCK_DEFAULT_BORDER * getParent().getZoom()));
//			g2.setColor(Color.black);
//			/*
//			 * pos    pos1
//			 * 
//			 *     
//			 * pos2   pos3
//			 */
//			GVector2f pos1 = new GVector2f(pos.getXi() + Block.SIZE.getXi() * getParent().getZoom(), pos.getYi());
//			GVector2f pos2 = new GVector2f(pos.getXi(), pos.getYi() + Block.SIZE.getYi() * getParent().getZoom());
//			GVector2f pos3 = pos.add(Block.SIZE.mul(getParent().getZoom()));
//			Map map = getParent().getLevel().getMap();
//			Block b;
//			if((b = map.getBlock(position.getXi(), position.getYi() - 1)) != null && b.isWalkable())
//				g2.drawLine(pos.getXi(), pos.getYi(), pos1.getXi(), pos1.getYi());
//			if((b = map.getBlock(position.getXi() - 1, position.getYi())) != null && b.isWalkable())
//				g2.drawLine(pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi());
//			if((b = map.getBlock(position.getXi(), position.getYi() + 1)) != null && b.isWalkable())
//				g2.drawLine(pos2.getXi(), pos2.getYi(), pos3.getXi(), pos3.getYi());
//			if((b = map.getBlock(position.getXi() + 1, position.getYi())) != null && b.isWalkable())
//				g2.drawLine(pos1.getXi(), pos1.getYi(), pos3.getXi(), pos3.getYi());
//		}
		
		
//		if(type == IRON || type == NOTHING)
//		else if(type == WOOD){
//			g2.drawImage(images.get(type), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
////			g2.drawImage(SpriteViewer.getImage("tileset.png", 1, 1), pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
//		}
//		else{
//			g2.setColor(getColorbyType(type));
//			g2.fillRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
//		}
	}

	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("healt", healt);
		result.put("type", type);
		result.put("position", position);
		
		return result.toString();
	}
	
	//OTHERS
	
	public boolean hit(int demage){
		healt -= demage;
		boolean res = healt <= 0;
		if(res)
			remove();
		return res;
	}

	public void remove() {
		getParent().addExplosion(getPosition().add(Block.SIZE.div(2)), 
								 Block.SIZE, 
								 blocks.get(type).getMinimapColor(), 
								 5);
		type = NOTHING; 
		healt = 0;
	}

	public void drawSprites(Graphics2D g2){
		Block t = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() - 1);
		Block b = getParent().getLevel().getMap().getBlock(position.getXi(), position.getYi() + 1);
		Block r = getParent().getLevel().getMap().getBlock(position.getXi() + 1, position.getYi());
		Block l = getParent().getLevel().getMap().getBlock(position.getXi() - 1, position.getYi());
		
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
		if(t != null && t.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 8, 4), pos.getXi(), pos.getYi() - Block.SIZE.getYi(), size.getXi(), size.getYi(), null);
		
		if(b != null && b.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 2, 4), pos.getXi(), pos.getYi() + Block.SIZE.getYi(), size.getXi(), size.getYi(), null);
		
		if(r != null && r.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 1, 4), pos.getXi() + Block.SIZE.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
		
		if(l != null && l.getType() != type)
			g2.drawImage(SpriteViewer.getImage("tileset2-b.png", 4, 4), pos.getXi() - Block.SIZE.getXi(), pos.getYi(), size.getXi(), size.getYi(), null);
	}
	
	public void drawShadow(Graphics2D g2, Color color, int length, int angle) {
		if(type == NOTHING)
			return;
		
		double uhol = Math.toRadians(angle + 90); 
		GVector2f offset = new GVector2f(-Math.cos(uhol), Math.sin(uhol)).mul(length);
		GVector2f pos = position.mul(Block.SIZE).sub(getParent().getOffset());
		g2.setColor(color);
		
		/*   2---3
		 *  /    |
		 * 1     4
		 *      /
		 *     5
		 * 		
		 */
		int[] xPos = new int[]{
			(int)(pos.getX()),
			(int)(pos.add(offset).getX()),
			(int)(pos.add(offset).getX() + Block.SIZE.getX()),
			(int)(pos.add(offset).getX() + Block.SIZE.getX()),
			(int)(pos.getXi() + Block.SIZE.getX())
		};
		
		int[] yPos = new int[]{
				(int)(pos.getY()),
				(int)(pos.sub(offset).getY()),
				(int)(pos.sub(offset).getY()),
				(int)(pos.getY() + Block.SIZE.getY() - offset.getY()),
				(int)(pos.getY() + Block.SIZE.getY())
			};
		
		g2.fillPolygon(xPos, yPos, 5);
	}

	public void build(String type) {
		this.type = type;
		this.healt = blocks.get(type).getHealt();
	}

	//GETTERS
	
	public String getType() {return type;}
	public GVector2f getSur() {return position.div(SIZE).toInt();}
	public GVector2f getPosition() {return position.mul(SIZE);}
	public boolean isWalkable() {return type == WATER || type == PATH || type == NOTHING || type == LAVA || type == FUTURE || type == TILE || type == GRASS;}
	public GVector2f getSize() {return SIZE;}
	public int getHealt() {return healt;}
	
	public GVector2f getInterSect(GVector2f ss, GVector2f se){
		ArrayList<GVector2f> res = new ArrayList<GVector2f>();
		
		GVector2f p = position.mul(Block.SIZE);
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(Block.SIZE.getX(), 0)), p));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(0, Block.SIZE.getY())), p));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(Block.SIZE.getX(), 0)), p.add(Block.SIZE)));
		res.add(LineLineIntersect.linesIntersetc(ss, se, p.add(new GVector2f(0, Block.SIZE.getY())), p.add(Block.SIZE)));
		
		res = res.stream().filter(a -> a != null).collect(Collectors.toCollection(ArrayList::new));
		if(res.size() == 0)
			return null;

		return res.stream().reduce((a, b) -> a.dist(ss) < b.dist(ss) ? a : b).get();
	}

	public static String getTypeFromInt(int num){
		switch(num){
			case 1 :
				return WOOD;
			case 2 :
				return IRON;
//			case 3 :
//				return GRASS;
//			case 4 :
//				return WATER;
//			case 5 :
//				return FUTURE;
//			case 6 :
//				return PATH;
//			case 7 :
//				return STONE;
			default :
				return NOTHING;
		}
	}

	public static Color getColorbyType(String type){
		return blocks.get(type).getMinimapColor();
	}

	public void renderWalls(Graphics2D g2) {
		Map map = getParent().getLevel().getMap();
		boolean t = map.isWalkable(position.getXi(), position.getYi() - 1);
		boolean b = map.isWalkable(position.getXi(), position.getYi() + 1);
		boolean l = map.isWalkable(position.getXi() - 1, position.getYi());
		boolean r = map.isWalkable(position.getXi() + 1, position.getYi());
		
		GVector2f size = SIZE.mul(getParent().getZoom());
		GVector2f pos = position.mul(size).sub(getParent().getOffset());
		
		int offset = (int)(5 * getParent().getZoom());
		
		GVector2f p0 = pos.sub(offset);
		GVector2f p1 = pos.add(size).sub(new GVector2f(-offset, offset + size.getY()));
		GVector2f p2 = pos.add(size).add(offset);
		GVector2f p3 = pos.add(size).sub(new GVector2f(offset + size.getX(), -offset));
			
		Block block0 = map.getBlock(position.getXi() - 1, position.getYi() - 1);
		Block block1 = map.getBlock(position.getXi() + 1, position.getYi() - 1);
		Block block2 = map.getBlock(position.getXi() + 1, position.getYi() + 1);
		Block block3 = map.getBlock(position.getXi() - 1, position.getYi() + 1);
		boolean val0 = block0 != null && !block0.isWalkable();
		boolean val1 = block1 != null && !block1.isWalkable();
		boolean val2 = block2 != null && !block2.isWalkable();
		boolean val3 = block3 != null && !block3.isWalkable();
		
		
		boolean walls3D = true;
		if(walls3D){
			g2.setColor(Color.DARK_GRAY);
			if(t)
				g2.fillPolygon(new int[]{pos.getXi(), 
										 pos.getXi() + size.getXi(), 
										 p1.getXi() + (val1 ? - offset : 0), 
										 p0.getXi() + (true ? + offset * 2 : 0)},
					   	   	   new int[]{pos.getYi(), pos.getYi(), p1.getYi(),  p0.getYi()}, 
					   	   	   4);
			g2.setColor(Color.LIGHT_GRAY);
			if(r)
				g2.fillPolygon(new int[]{pos.getXi() + size.getXi(), pos.getXi() + size.getXi(), p2.getXi(), p1.getXi()},
				   	   	   	   new int[]{pos.getYi(), 
				   	   	   			   	 pos.getYi() + size.getYi(), 
				   	   	   			   	 p2.getYi() + (true ? - offset * 2 : 0),  
				   	   	   			   	 p1.getYi() + (val1 ? + offset : 0)},
				   	   	   	   4);
			
		}
		else{
			int i = offset * 2;
			g2.setColor(Color.LIGHT_GRAY);
			if(t)
				g2.fillPolygon(new int[]{pos.getXi(), 
										 pos.getXi() + size.getXi(), 
										 p1.getXi() + (val1 ? - i : 0), 
										 p0.getXi() + (val0 ? + i : 0)},
					   	   	   new int[]{pos.getYi(), pos.getYi(), p1.getYi(),  p0.getYi()}, 
					   	   	   4);
			if(r)
				g2.fillPolygon(new int[]{pos.getXi() + size.getXi(), pos.getXi() + size.getXi(), p2.getXi(), p1.getXi()},
				   	   	   	   new int[]{pos.getYi(), 
				   	   	   			   	 pos.getYi() + size.getYi(), 
				   	   	   			   	 p2.getYi() + (val2 ? - i : 0),  
				   	   	   			   	 p1.getYi() + (val1 ? + i : 0)},
				   	   	   	   4);
			
			g2.setColor(Color.DARK_GRAY);
			if(b)
				g2.fillPolygon(new int[]{pos.getXi(), 
										 p3.getXi() + (val3 ? + i : 0), 
										 p2.getXi() + (val2 ? - i : 0), 
										 pos.getXi() + size.getXi()},
							   new int[]{pos.getYi() + size.getYi(), p3.getYi(), p2.getYi(), pos.getYi() + size.getYi()},
				   	   	   	   4);
			if(l)
				g2.fillPolygon(new int[]{pos.getXi(), p0.getXi(), p3.getXi(), pos.getXi()},
							   new int[]{pos.getYi(), 
									   	 p0.getYi() + (val0 ? + i : 0), 
									   	 p3.getYi() + (val3 ? - i : 0), 
									   	 pos.getYi() + size.getYi()},
				   	   	   		4);
		}
	}
}
	
	
