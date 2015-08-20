package bombercraft.game;

import utils.GColision;
import utils.GLog;
import utils.GVector2f;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import bombercraft.Config;
import bombercraft.game.entity.Enemy;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.Item;
import bombercraft.game.entity.Visible;
import bombercraft.game.entity.helper.TowerLaser;
import bombercraft.game.entity.helper.TowerMachineGun;
import bombercraft.game.level.Block;
import bombercraft.game.level.Level;
import bombercraft.multiplayer.Communicable;
import core.Input;

public class Game implements GameAble, MouseWheelListener{
	MyPlayer myPlayer;
	private Level level;
	private float zoom = 1;
	private CoreGame parent;
	private HashMap<String, Helper> helpers = new HashMap<String, Helper>();
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Game(Level level, CoreGame parent) {
		this.level = level;
		this.parent = parent;
		level.setGame(this);
		GLog.write(GLog.CREATE, "Game vytvorený");
		myPlayer = new MyPlayer(this,new GVector2f(40,40), "Gabo", 4, 10, "player1.png", 2);
		parent.getCanvas().addMouseWheelListener(this);
	}
	
	@Override
	public boolean isVisible(Visible b){
		return !(b.getPosition().getX() * zoom  + Config.BLOCK_DEFAULT_WIDTH  * zoom  < getOffset().getX()  || 
				 b.getPosition().getY() * zoom  + Config.BLOCK_DEFAULT_HEIGHT  * zoom  < getOffset().getY() || 
				   getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * zoom    ||
				   getOffset().getY()+ getCanvas().getHeight() < b.getPosition().getY() * zoom );
	}
	
	@Override
	public float getZoom() {return zoom;}
	public Level getLevel() {return level;}
	public Canvas getCanvas(){return parent.getCanvas();}
	@Override
	public GVector2f getOffset() {return myPlayer.getOffset();}

	public void changeZoom(float value){
		if(!Config.ALLOW_ZOOMING || !level.isReady())
			return;
		
		zoom += value;
		if(level.getMap().getNumberOfBlocks().getX() * Config.BLOCK_DEFAULT_WIDTH * zoom < parent.getCanvas().getWidth()){
			zoom -= value; 
			return;
		}
		
		if(zoom > 5)
			zoom -= value;
		
		System.out.println(zoom);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		changeZoom(e.getWheelRotation() * 0.1f);
	}
	
	@Override
	public void render(Graphics2D g2) {
		level.render(g2);
		myPlayer.render(g2);
		new HashMap<String, Player>(players).entrySet()
											.stream()
											.map(a -> a.getValue())
											.filter(this::isVisible)
											.forEach(a -> a.render(g2));
	
		new HashMap<String, Helper>(helpers).entrySet()
											.stream()
											.map(a -> a.getValue())
//											.filter(this::isVisible)
											.forEach(a -> a.render(g2));
		
		new ArrayList<Enemy>(enemies).stream()
								     .filter(this::isVisible)
								     .forEach(a -> a.render(g2));
		
	}

	@Override
	public void update(float delta) {
		myPlayer.update(delta);
		
		new ArrayList<Enemy>(enemies).stream()
	     							 .filter(this::isVisible)
	     							 .forEach(a -> a.update(delta));
	}
	
	@Override
	public void input() {
		myPlayer.input();
		
		if(Input.isKeyDown(Input.KEY_F))
			getConnection().putHelper(myPlayer);
		
		if(Input.isKeyDown(Input.KEY_E))
			enemies.add(new Enemy(level.getMap().getRandomEmptyBlock().getPosition(),this, 2, 10, 100,1,1));
	}
	
	@Override
	public boolean hasBomb(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Communicable getConnection() {
		return parent.getComunication();
	}


	@Override
	public boolean hasItem(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item getItem(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasHelper(String string) {
		return helpers.containsKey(string);
	}

	@Override
	public void addHelper(GVector2f position, int type) {
		GVector2f sur = position.add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		if(!helpers.containsKey(sur.toString()))
			switch(type){
			case Helper.TOWER_MACHINE_GUN :
				helpers.put(sur.toString(), new TowerMachineGun(sur.mul(Block.SIZE), this));
				break;
			case Helper.TOWER_LASER :
				helpers.put(sur.toString(), new TowerLaser(sur.mul(Block.SIZE), this));
				break;
			}
			
	}

	@Override
	public void addBullet(GVector2f position, int direction, int bulletSpeed, int attack, int bulletDefaultHealt) {
		//TODO Auto-generated method stub
	}
	
	public ArrayList<Enemy> getEnemiesAround(GVector2f position, int range){
		return enemies.parallelStream()
					  .filter(a -> GColision.pointCircleCollision(a.getPosition(), position, range))
					  .collect(Collectors.toCollection(ArrayList<Enemy>::new));
	}
	
}
