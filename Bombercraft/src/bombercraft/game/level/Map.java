package bombercraft.game.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GVector2f;

public class Map implements Interactable{
	private HashMap<String, Block> blocks;
	private GVector2f numberOfBlocks;
	private GameAble parent;
	private boolean render = true;
	
	//CONSTRUCTORS
	
	public Map(GameAble parent){
		this.parent = parent;
		
		numberOfBlocks = new GVector2f(80, 80);
		
		createRandomMap();
	}
	
	public void createRandomMap(){
		render = false;
		blocks = new HashMap<String, Block>();
		
		for(int i=0 ; i<numberOfBlocks.getXi() ; i++){
			for(int j=0 ; j<numberOfBlocks.getYi() ; j++){
				addBlock(i, j, new Block(new GVector2f(i,j),(int)(Math.random() * 3), parent));
			}
		}
		clearRespawnZones(parent.getLevel().getRespawnZones());
		render = true;
	}
	
	//OVERRIDES

	@Override
	public void render(Graphics2D g2) {
		if(!render)
			return;
		new HashMap<String, Block>(blocks).entrySet()
		      							  .stream()
		      							  .map(a -> a.getValue())
		      							  .filter(getParent()::isVisible)
		      							  .forEach(a -> a.render(g2));
	}

	//OTHERS
	
	private void clearRespawnZones(List<GVector2f> zones){
		zones.stream().forEach(a -> {
			GVector2f resp = a.div(Block.SIZE);
			blocks.put(resp.toString(), new Block(resp, Block.NOTHING, parent));
		});
	}
	
	public int[] getPossibleWays(GVector2f sur){
		ArrayList<Integer> result = new ArrayList<Integer>();
		Block b;
		
		b = getBlock(sur.getXi(), sur.getYi() - 1);
		if(b != null && b.getType() == Block.NOTHING)
			result.add(0);
		
		b = getBlock(sur.getXi() + 1, sur.getYi());
		if(b != null && b.getType() == Block.NOTHING)
			result.add(1);
		
		b = getBlock(sur.getXi(), sur.getYi() + 1);
		if(b != null && b.getType() == Block.NOTHING)
			result.add(2);
		
		b = getBlock(sur.getXi() - 1, sur.getYi());
		if(b != null && b.getType() == Block.NOTHING)
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
	
	//ADDERS

	private void addBlock(int i, int j, Block block){
		blocks.put(i + "_" + j, block);
	}
	
	//GETTERS
	public ArrayList<Block> getBlocks(){
		if(render)
			return new ArrayList<Block>(blocks.values());
		
		return new ArrayList<Block>();
	}
	
	public GVector2f getNumberOfBlocks() {return numberOfBlocks;}
	public Block getBlock(String block){return blocks.get(block);}	
	public Block getBlock(int i, int j){return blocks.get(i + "_" + j);}
	public GameAble getParent() {return parent;}
}
