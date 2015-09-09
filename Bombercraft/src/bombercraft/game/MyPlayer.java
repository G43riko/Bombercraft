package bombercraft.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Config;
import bombercraft.game.level.Block;
import core.Input;
import utils.GVector2f;

public class MyPlayer extends Player{
	private GVector2f	offset;
	private GVector2f	move			= new GVector2f();
	private GVector2f	totalMove		= new GVector2f();
	private boolean		showSelector	= true;
	private int			selectorWidth	= Config.PLAYER_DEFAULT_SELECTOR_WIDTH;
	private Color		selectorColor	= Config.PLAYER_DEFAULT_SELECTOR_COLOR;
	
	
	private HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>(); 
		
	public MyPlayer(GameAble parent, GVector2f position, String name, int speed, int healt, String image, int range) {
		super(parent, position, name, speed, healt, image, range);
		resetOffset();
		
		keys.put(Input.KEY_W, false);
		keys.put(Input.KEY_A, false);
		keys.put(Input.KEY_S, false);
		keys.put(Input.KEY_D, false);
	}
	
	private void resetOffset(){
		offset = new GVector2f(getParent().getCanvas().getWidth(),getParent().getCanvas().getHeight()).div(-2);;
	}
	
	@Override
	public void input(){
		move = new GVector2f();
		
		if(!keys.get(Input.KEY_W) && Input.isKeyDown(Input.KEY_W))
			setDirection(2);
		keys.put(Input.KEY_W, Input.isKeyDown(Input.KEY_W));
		
		
		if(!keys.get(Input.KEY_S) && Input.isKeyDown(Input.KEY_S))
			setDirection(3);
		keys.put(Input.KEY_S, Input.isKeyDown(Input.KEY_S));

		
		if(!keys.get(Input.KEY_A) && Input.isKeyDown(Input.KEY_A))
			setDirection(0);
		keys.put(Input.KEY_A, Input.isKeyDown(Input.KEY_A));
		
		
		if(!keys.get(Input.KEY_D) && Input.isKeyDown(Input.KEY_D))
			setDirection(1);
		keys.put(Input.KEY_D, Input.isKeyDown(Input.KEY_D));
			
		
		if(keys.get(Input.KEY_W))
			move.addToY(-1);
		if(keys.get(Input.KEY_S))
			move.addToY(1);
		if(keys.get(Input.KEY_A))
			move.addToX(-1);
		if(keys.get(Input.KEY_D))
			move.addToX(1);
		
		
		setMoving(!move.isNull());
		
		
		if(move.getX() < 0 && move.getY() == 0)
			setDirection(0);
		else if(move.getX() > 0 && move.getY() == 0)
			setDirection(1);
		else if(move.getX() == 0 && move.getY() < 0)
			setDirection(2);
		else if(move.getX() == 0 && move.getY() > 0)
			setDirection(3);
		

	}

	public void update(float delta){
		if(position == null)
			return;

		totalMove = totalMove.add(move);
		
		position.addToX(move.getX() * getSpeed() * delta);
		if(isInBlock())
			position.addToX(-move.getX() * getSpeed() * delta);
		
		position.addToY(move.getY() * getSpeed() * delta);
		if(isInBlock())
			position.addToY(-move.getY() * getSpeed() * delta);
		
		checkBorders();
		checkOffset();

		if(!move.isNull())
			getParent().getConnection().playerNewPos(this);
//		if(getParent().hasItem(getSur().toString())){
//			int type = getParent().getItem(getSur().toString()).getType();
			
//			eatItemType(type);
//			getParent().getConnection().eatItem(getSur(), type);
//		}
	}
	
	private boolean isInBlock(){
		float topOffset = 20;
		float bottomOffset = 35;
		float rightOffset = 11;
		float leftOffset = 9;
		
		GVector2f t = position.add(new GVector2f(Block.SIZE.getX(), Block.SIZE.getY() - topOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f b = position.add(new GVector2f(Block.SIZE.getX(), Block.SIZE.getY() + bottomOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f r = position.add(new GVector2f(Block.SIZE.getX() - rightOffset, Block.SIZE.getY()).div(2)).div(Block.SIZE).toInt();
		GVector2f l = position.add(new GVector2f(Block.SIZE.getX() + leftOffset , Block.SIZE.getY()).div(2)).div(Block.SIZE).toInt();
		
		try{
			return !getParent().getLevel().getMap().getBlock(t.getXi(), t.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(b.getXi(), b.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(r.getXi(), r.getYi()).isWalkable() ||
				   !getParent().getLevel().getMap().getBlock(l.getXi(), l.getYi()).isWalkable();
		}catch(NullPointerException e){
			return true;
		}
	}
	
	public void checkOffset(){
		
		GVector2f pos = getPosition().mul(getParent().getZoom()).add(Block.SIZE.mul(getParent().getZoom() / 2));
		
		offset.setX(pos.getX() - getParent().getCanvas().getWidth() / 2);
		offset.setY(pos.getY() - getParent().getCanvas().getHeight() / 2);

		GVector2f nums = getParent().getLevel().getMap().getNumberOfBlocks();
		
		//skontroluje posun
		if(offset.getX() < 0)
			offset.setX(0);
        
        if(offset.getX() > (nums.getX() * Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom()) - getParent().getCanvas().getWidth())
        	offset.setX((nums.getX() * Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom()) - getParent().getCanvas().getWidth());
        
        if(offset.getY() < 0)
        	offset.setY(0);
        
        if(offset.getY() > (nums.getY() * Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom()) - getParent().getCanvas().getHeight())
        	offset.setY((nums.getY() * Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom()) - getParent().getCanvas().getHeight()); 
        
	}
	
	private void checkBorders(){
		if(position.getX() * getParent().getZoom() < 0)
			position.setX(0);
        
        if(position.getY() * getParent().getZoom() < 0)
        	position.setY(0);
        
        while(getParent() == null || getParent().getLevel() == null || getParent().getLevel().getMap() == null)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        
        GVector2f nums = getParent().getLevel().getMap().getNumberOfBlocks();
        
        if(position.getX() * getParent().getZoom() + Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom() > nums.getX() * Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom())
        	position.setX((nums.getX() * Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom() - Config.BLOCK_DEFAULT_SIZE.getX() * getParent().getZoom()) / getParent().getZoom());
        
        if(position.getY() * getParent().getZoom() +Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom() > nums.getY() * Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom())
        	position.setY((nums.getY() * Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom() - Config.BLOCK_DEFAULT_SIZE.getY() * getParent().getZoom())  / getParent().getZoom());
	}
	
	public void clearTotalMove(){
		totalMove = new GVector2f(); 
	}
	
	public void drawSelector(Graphics2D g2){
		GVector2f pos = getSelectorPos().mul(getParent().getZoom()).sub(getParent().getOffset());
		GVector2f size = Block.SIZE.mul(getParent().getZoom());
		g2.setStroke(new BasicStroke(selectorWidth));
		g2.setColor(selectorColor);
		g2.drawRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi());
		
		g2.fillRect(pos.getXi() - 2, pos.getYi() - 2, 4, 4);
	}

	public boolean showSelector() {
		return showSelector;
	}

	public GVector2f getOffset() {return offset;}
	public GVector2f getBulletDirection() {
		GVector2f sur = position.add(Block.SIZE.mul(getParent().getZoom() / 2));
		GVector2f dir = Input.getMousePosition().add(getOffset()).sub(sur).Normalized();
		return dir;
	}
}