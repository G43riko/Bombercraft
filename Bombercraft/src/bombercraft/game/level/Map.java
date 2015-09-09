package bombercraft.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import bombercraft.Bombercraft;
import bombercraft.game.GameAble;
import core.Interactable;
import utils.GVector2f;
import utils.json.JSONObject;

public class Map implements Interactable{
	private HashMap<String, Block> blocks;
	private GVector2f numberOfBlocks;
	private GameAble parent;
	private boolean render = true;
	private long renderedBlocks;
	private String defaultMap;
	private GVector2f size;
	
	//CONSTRUCTORS
	
	public Map(JSONObject object, GameAble parent){
		this.parent = parent;
		this.numberOfBlocks = new GVector2f(object.getString("numberOfBlocks"));
		loadMap(object);
		size = numberOfBlocks.mul(Block.SIZE);
	};
	
	public Map(GameAble parent){
		this(parent, new GVector2f(40, 40)); //300 x 300 - je max
	}
	
	public Map(GameAble parent, GVector2f numberOfBlocks){
		this.parent = parent;
		this.numberOfBlocks = numberOfBlocks;
		
		createRandomMap();
		defaultMap = toJSON();
		size = numberOfBlocks.mul(Block.SIZE);
	}
	
	//OVERRIDES

	@Override
	public void render(Graphics2D g2) {
		if(!render)
			return;
		
		if(Bombercraft.getViewOption("renderShadow")){
			ArrayList<Block> temp = new HashMap<String, Block>(blocks).entrySet()
				   							  						  .stream()
				   							  						  .map(a -> a.getValue())
				   							  						  .filter(a->a.getType() != Block.NOTHING)
				   							  						  .filter(getParent()::isVisible)
				   							  						  .peek(a -> a.drawShadow(g2, Color.BLACK, 10, 45))
				   							  						  .collect(Collectors.toCollection(ArrayList::new));
			renderedBlocks = temp.stream()
								 .peek(a -> a.render(g2))
								 .count();
		}
		else{
			renderedBlocks = new HashMap<String, Block>(blocks).entrySet()
			      							  				   .stream()
			      							  				   .map(a -> a.getValue())
			      							  				   .filter(getParent()::isVisible)
			      							  				   .peek(a -> a.render(g2))
			      							  				   .filter(a->a.getType() != Block.NOTHING)
			      							  				   .count();
			
			new HashMap<String, Block>(blocks).entrySet()
			   								  .stream()
			   								  .map(a -> a.getValue())
			   								  .filter(a -> !a.isWalkable())
			   								  .forEach(a -> a.renderWalls(g2));
			
			
//			new HashMap<String, Block>(blocks).entrySet()
//				   							  .stream()
//				   							  .map(a -> a.getValue())
//				   							  .filter(getParent()::isVisible)
//				   							  .filter(a -> a.getType() == Block.NOTHING)
//				   							  .forEach(a -> a.drawSprites(g2));
		}
	}

	public String toJSON(){
		JSONObject result = new JSONObject();
		for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
			for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
				result.put("b" + i + "_" + j, getBlock(i, j).toJSON());
			}
		}
		result.put("numberOfBlocks", numberOfBlocks);
		
		return result.toString();
	}
	
	//OTHERS

	public void createRandomMap(){
		render = false;
		blocks = new HashMap<String, Block>();
		
		for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
			for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
				addBlock(i, j, new Block(new GVector2f(i,j),(int)(Math.random() * 10), parent));
			}
		}
		clearRespawnZones(parent.getLevel().getRespawnZones());
		render = true;
	}
	
	public void loadMap(JSONObject object){
		render = false;
		blocks = new HashMap<String, Block>();
		for(int i=0 ; i<numberOfBlocks.getXi() ; i++)
			for(int j=0 ; j<numberOfBlocks.getYi() ; j++)
				addBlock(i, j, new Block(new JSONObject(object.getString("b" + i + "_" + j)), parent));
		render = true;
	}
	
	private void clearRespawnZones(List<GVector2f> zones){
		zones.stream().forEach(a -> {
			remove(a.div(Block.SIZE).toInt());
		});
	}
	
	public void remove(GVector2f sur){
		Block b = getBlock(sur.getXi(), sur.getYi());
		if(b != null && b.getType() != Block.NOTHING)
			b.remove();
	}
	
	public void resetMap() {
		loadMap(new JSONObject(defaultMap));
	}
	
	//ADDERS

	private void addBlock(int i, int j, Block block){
		blocks.put(i + "_" + j, block);
	}
	
	//GETTERS
	
	public long getRenderedBlocks() {return renderedBlocks;}
	public GVector2f getNumberOfBlocks() {return numberOfBlocks;}
	public Block getBlock(String block){return blocks.get(block);}	
	public Block getBlock(int i, int j){return blocks.get(i + "_" + j);}
	public GameAble getParent() {return parent;}
	
	public boolean isWalkable(int i, int j){
		Block b = getBlock(i, j);
		return b != null && b.isWalkable();
	}

	public int[] getPossibleWays(GVector2f sur){
		ArrayList<Integer> result = new ArrayList<Integer>();
		Block b;
		
		b = getBlock(sur.getXi(), sur.getYi() - 1);
		if(b != null && b.isWalkable())
			result.add(0);
		
		b = getBlock(sur.getXi() + 1, sur.getYi());
		if(b != null && b.isWalkable())
			result.add(1);
		
		b = getBlock(sur.getXi(), sur.getYi() + 1);
		if(b != null && b.isWalkable())
			result.add(2);
		
		b = getBlock(sur.getXi() - 1, sur.getYi());
		if(b != null && b.isWalkable())
			result.add(3);
		
		int[] ret = new int[result.size()];
		for(int i=0 ; i<result.size() ; i++)
			ret[i] = result.get(i);
		
		return ret;
	}
	
	public Block getRandomEmptyBlock(){
		ArrayList<Block> b = blocks.entrySet()
								   .stream()
								   .map(a -> a.getValue())
								   .filter(a -> a.getType() == Block.NOTHING)
								   .collect(Collectors.toCollection(ArrayList<Block>::new));
		return b.get((int)(Math.random() * b.size()));
	}
	
	public Block getBlockOnPosition(GVector2f sur){
		GVector2f blockSize = Block.SIZE;
		GVector2f pos = sur.sub(sur.mod(blockSize)).div(blockSize);
		
		return getBlock(pos.getXi(), pos.getYi());
	}

	public ArrayList<Block> getBlocks(){
		if(render)
			return new ArrayList<Block>(blocks.values());
		
		return new ArrayList<Block>();
	}
	
	public GVector2f getSize(){
		return size;
	}

}
