package bombercraft.game.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GVector2f;

public class Map implements Interactable{
	private HashMap<String, Block> blocks;
	private GVector2f numberOfBlocks;
	private GameAble parent;
	
	//CONSTRUCTORS
	
	public Map(GameAble parent){
		this.parent = parent;
		createRandomMap(40, 40);
	}
	
	private void createRandomMap(int x, int y){
		blocks = new HashMap<String, Block>();
		numberOfBlocks = new GVector2f(x, y);
		
		for(int i=0 ; i<x ; i++){
			for(int j=0 ; j<y ; j++){
				addBlock(i, j, new Block(new GVector2f(i,j),(int)(Math.random() * 3), parent));
			}
		}
	}
	
	//OVERRIDES

	@Override
	public void render(Graphics2D g2) {
		blocks.entrySet()
		      .stream()
		      .map(a -> a.getValue())
		      .filter(getParent()::isVisible)
		      .forEach(a -> a.render(g2));
	}

	//OTHERS
	
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
	
	public GVector2f getNumberOfBlocks() {return numberOfBlocks;}
	public Block getBlock(String block){return blocks.get(block);}	
	public Block getBlock(int i, int j){return blocks.get(i + "_" + j);}
	public GameAble getParent() {return parent;}
}
