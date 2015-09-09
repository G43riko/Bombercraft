package bombercraft.game;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import bombercraft.Bombercraft;
import bombercraft.Config;
import bombercraft.Profil;
import bombercraft.game.entity.Adductor;
import bombercraft.game.entity.Entity;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.Item;
import bombercraft.game.entity.Lightning;
import bombercraft.game.entity.Path;
import bombercraft.game.entity.Respawner;
import bombercraft.game.entity.Visible;
import bombercraft.game.entity.Worker;
import bombercraft.game.entity.enemy.Bot;
import bombercraft.game.entity.enemy.Enemy;
import bombercraft.game.entity.enemy.EnemyA;
import bombercraft.game.entity.enemy.EnemyB;
import bombercraft.game.entity.enemy.EnemyC;
import bombercraft.game.entity.explosion.Explosion;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.entity.helper.bullet.Bullet;
import bombercraft.game.entity.helper.bullet.BulletArrow;
import bombercraft.game.entity.helper.bullet.BulletBoomerang;
import bombercraft.game.entity.helper.bullet.BulletDefault;
import bombercraft.game.entity.helper.bullet.BulletLaser;
import bombercraft.game.entity.helper.bullet.BulletMagic;
import bombercraft.game.entity.helper.tower.TowerLaser;
import bombercraft.game.entity.helper.tower.TowerMachineGun;
import bombercraft.game.entity.helper.weapon.Weapon;
import bombercraft.game.entity.particles.Emitter;
import bombercraft.game.entity.particles.ParticleEmmiter;
import bombercraft.game.entity.wall.Wall;
import bombercraft.game.entity.wall.WallC;
import bombercraft.game.level.Block;
import bombercraft.game.level.Level;
import bombercraft.gui.GUI;
import bombercraft.gui.NavBar;
import bombercraft.multiplayer.Communicable;
import core.Input;
import utils.AudioPlayer;
import utils.GColision;
import utils.GLog;
import utils.GVector2f;
import utils.Utils;
import utils.json.JSONObject;

public class Game implements GameAble, MouseWheelListener{
	private static HashMap<String, AudioPlayer>	sounds = new HashMap<String, AudioPlayer>(); 
	private MyPlayer				myPlayer;
	private Level					level;
	private boolean					render		= true;
	private boolean					update		= true;
	private boolean					input		= true;
	private GUI						gui;
	private float					zoom		= Config.DEFAULT_ZOOM;
	private BufferedImage			lightMap;
	private BufferedImage			lightMapOrigin;
	private CoreGame				parent;
	private long					renderedEnemies;
	private long					renderedHelpers;
	private long					renderedParticles;
	private HashMap<String, Helper>	helpers		= new HashMap<String, Helper>();
	private HashMap<String, Wall>	walls		= new HashMap<String, Wall>();
	private HashMap<String, Player>	players		= new HashMap<String, Player>();
	private ArrayList<Emitter>		emitters	= new ArrayList<Emitter>();
	private ArrayList<Enemy>		enemies		= new ArrayList<Enemy>();
	private ArrayList<Explosion>	explosions	= new ArrayList<Explosion>();
	private ArrayList<Light>		lights 		= new ArrayList<Light>();
	private ArrayList<Entity>		others		= new ArrayList<Entity>();
	
	private GVector2f respawnerSur;
	static{
		sounds.put("laser", new AudioPlayer("sounds/laser.wav"));
	}
	//CONSTRUCTORS
	
	public Game(Level level, CoreGame parent, String game) {
		this.level = level;
		this.parent = parent;
		level.setGame(this);
		myPlayer = new MyPlayer(this,
								level.getPlayerRespawnZone() , 
								getProfil().getName(), 
								level.getDefaultPlayerInfo().getInt("speed"), 
								level.getDefaultPlayerInfo().getInt("healt"), 
								getProfil().getAvatar(), 
								level.getDefaultPlayerInfo().getInt("range"));
		players.put(myPlayer.getName(), myPlayer);
		parent.getCanvas().addMouseWheelListener(this);
		if(game != null){
			JSONObject o = new JSONObject(game);
			for(int i=0 ; i < o.getInt("numberOfPlayers") ;  i++){
				Player p = new Player(this, new JSONObject(o.getString("player_" + i)));
				players.put(p.getName(), p);
			};
		}
		GLog.write(GLog.CREATE, "Game vytvoren˝");
		gui = new GUI(this);
		
		
		lightMapOrigin = new BufferedImage(getLevel().getMap().getSize().getXi(),
									 getLevel().getMap().getSize().getYi(),
				   					 BufferedImage.TYPE_INT_ARGB);
		
		lights.add(new Light(this, new GVector2f(300,300), new GVector2f(300, 300)));
//		lights.add(new Light(this, new GVector2f(0,0), new GVector2f(200, 200), myPlayer));

//		lights.add(new Light(this, null, 150, 0, 0, 255 ,myPlayer));
//		for(int i=0 ; i<20 ; i++)
//			lights.add(new Light(this, 
//								 getLevel().getMap().getRandomEmptyBlock().getPosition(), 
//								 50 + (int)(Math.random() * 300), 
//								 0, 
//								 0, 
//								 255,
//								 null));
		drawStaticLightMap();
	}

//		
	//OTHERS
	
	public void changeZoom(float value){
		if(!Config.ALLOW_ZOOMING || !level.isReady())
			return;
		
		zoom += value;
		
		if(level.getMap().getNumberOfBlocks().getX() * Block.SIZE.getX() * zoom < parent.getCanvas().getWidth()){
			zoom -= value; 
			return;
		}
		
		if(zoom > 5)
			zoom -= value;
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		changeZoom(e.getWheelRotation() * 0.1f);
	}

	@Override
	public void newGame() {
		resetGame();
		getLevel().getMap().createRandomMap();
		myPlayer.respawn();
//		getConnection().newGame();
	}
	
	@Override
	public void resetGame() {
		render = update = input = false;
		enemies.clear();
		others.clear();
		helpers.clear();
		emitters.clear();
		level.getMap().resetMap();
		render = update = input = true;
		
	}

	@Override
	public boolean bulletHitEnemy(Bullet bullet) {
		return new ArrayList<Enemy>(enemies).parallelStream()
									 		.filter(a -> GColision.pointRectCollision(a.getPosition(), 
											 								   		  a.getSize(), 
											 								   		  bullet.getPosition()))
									 		.peek(a->a.hit(bullet.getDemage())).count() > 0;
	}

	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		players.entrySet()
			   .stream()
			   .forEach(a -> result.put("player_" + result.length(), a.getValue().toJSON()));
		result.put("numberOfPlayers", players.size());
		
		return result.toString();
	}

	@Override
	public void calcPosition() {
		gui.calcPosition();
	}
	
	private void drawStaticLightMap(){
		Graphics2D tg2 = (Graphics2D)lightMapOrigin.getGraphics();
		tg2.setColor(Color.BLACK);
		tg2.fillRect(0, 0, lightMapOrigin.getWidth(), lightMapOrigin.getHeight());
		
		tg2.setComposite(AlphaComposite.DstOut);
		
		
		lights.stream().filter(a->a.isStatic()).forEach(a -> a.render(tg2));
	}
	
	private void drawDynamicLightMap(){
		lightMap = Utils.deepCopy(lightMapOrigin);
//		int x = getCanvas().getWidth();
//		if(getCanvas().getWidth() + getOffset().getXi() > lightMapOrigin.getWidth())
//			x = lightMapOrigin.getWidth() - getOffset().getXi();
//		
//		int y = getCanvas().getHeight();
//		if(getCanvas().getHeight() + getOffset().getYi() > lightMapOrigin.getHeight())
//			y = lightMapOrigin.getHeight() - getOffset().getYi();
//		
//		x = Math.abs(x);
//		y = Math.abs(y);
//		System.out.println(getOffset().getYi() + " " + lightMapOrigin.getHeight() + " " + getCanvas().getHeight());
//		System.out.println(getOffset().getXi() + " " + lightMapOrigin.getWidth() + " " + getCanvas().getWidth());
//		lightMap = Utils.deepCopy(lightMapOrigin.getSubimage(getOffset().getXi(), 
//															 getOffset().getYi(), 
//															 getCanvas().getWidth(), 
//															 getCanvas().getHeight()));
//		
		Graphics2D tg2 = (Graphics2D)lightMap.getGraphics();
//		tg2.setColor(Color.BLACK);
//		tg2.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		
		tg2.setComposite(AlphaComposite.DstOut);
		
		
		lights.stream().filter(a -> !a.isStatic()).forEach(a -> a.render(tg2));
	}
	
	public void fire(int cadenceBonus, GVector2f pos, GVector2f target, int demageBonus, String type){
		if(Weapon.getWeapon(type).canShot(cadenceBonus)){
			Weapon.getWeapon(type).setLastShot();
			pos = pos.sub(Block.SIZE.div(2));
			addBullet(pos, 
					  target.add(getOffset()).div(getZoom()).sub(Block.SIZE.div(2)).sub(pos).Normalized(), 
					  Weapon.getWeapon(type).getBulletSpeed(), 
					  Weapon.getWeapon(type).getDemage() + demageBonus, 
					  Weapon.getWeapon(type).getBulletType(), 
					  Weapon.getWeapon(type).getBulletHealt());
		}
	}
	
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		if(!render)
			return;

		
		
		level.render(g2);
	
		renderedHelpers = new HashMap<String, Helper>(helpers).entrySet()
															  .stream()
															  .map(a -> a.getValue())
//															  .filter(this::isVisible)
															  .peek(a -> a.render(g2))
															  .count();
		
		new HashMap<String, Wall>(walls).entrySet()
		  								.stream()
		  								.map(a -> a.getValue())
		  								.filter(this::isVisible)
		  								.forEach(a -> a.render(g2));
		
		new HashMap<String, Wall>(walls).entrySet()
										.stream()
										.map(a -> a.getValue())
										.filter(this::isVisible)
										.forEach(a -> a.renderWalls(g2));
		
		renderedEnemies = new ArrayList<Enemy>(enemies).stream()
								       				   .filter(this::isVisible)
								       				   .peek(a -> a.render(g2))
								       				   .count();
		
		new HashMap<String, Player>(players).entrySet()
											.stream()
											.map(a -> a.getValue())
											.filter(this::isVisible)
											.forEach(a -> a.render(g2));
		renderedParticles = new ArrayList<Emitter>(emitters).stream()
					.peek(a -> a.render(g2))
					.mapToLong(a -> a.getRenderedParticles())
					.sum();
		new ArrayList<Entity>(others).stream()
									 .filter(a -> isVisible(a) || a instanceof Lightning || a instanceof Explosion || a instanceof Path)
									 .forEach(a -> a.render(g2));
		
		new ArrayList<Explosion>(explosions).stream().forEach(a -> a.render(g2));
		
		
		if(myPlayer.showSelector())
			myPlayer.drawSelector(g2);

		if(Bombercraft.getViewOption("renderLights")){
			if(Bombercraft.getViewOption("renderOnlyLightMap")){
				g2.setColor(Color.white);
				g2.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
			}
//			drawLightMap();
//			drawDynamicLightMap();
			g2.drawImage(lightMapOrigin, -getOffset().getXi(),-getOffset().getYi(), null);
		}
		
		gui.render(g2);
		
		

//		tg2.dispose();
	}

	@Override
	public void update(float delta) {
		if(!update)
			return;
		myPlayer.update(delta);

		new HashMap<String, Helper>(helpers).entrySet()
											.stream()
											.peek(a -> a.getValue().update(delta))
											.filter(a -> !a.getValue().isAlive())
											.forEach(a -> helpers.remove(a.getKey()));
		
		enemies = new ArrayList<Enemy>(enemies).stream()
	     							 		   .filter(a -> a.isAlive())
	     							 		   .peek(a -> a.update(delta))
	     							 		   .collect(Collectors.toCollection(ArrayList::new));
		
		others = new ArrayList<Entity>(others).stream()
											  .peek(a -> a.update(delta))
											  .filter(a -> a.isAlive())
											  .collect(Collectors.toCollection(ArrayList::new));
		
		if(Bombercraft.getViewOption("renderLights")){
			lights = new ArrayList<Light>(lights).stream()
												 .filter(a -> a.isAlive())
												 .peek(a -> a.update(delta))
												 .collect(Collectors.toCollection(ArrayList::new));
		}
		
		
		emitters = new ArrayList<Emitter>(emitters).stream()
											  	   .peek(a -> a.update(delta))
											  	   .collect(Collectors.toCollection(ArrayList::new));
		
		explosions = new ArrayList<Explosion>(explosions).stream()
														 .filter(a -> a.isAlive())
			  	   									     .peek(a -> a.update(delta))
			  	   									     .collect(Collectors.toCollection(ArrayList::new));
		
		gui.update(delta);
		
		
	} 
	
	@Override
	public void input() {
		if(!input)
			return;
		myPlayer.input();
		
		if(Input.isKeyDown(Input.KEY_LCONTROL))
			getConnection().putHelper(myPlayer);
		
		if(Input.isKeyDown(Input.KEY_LSHIFT))
			walls.put(myPlayer.getSelectorPos().toString(), new WallC(myPlayer.getSelectorPos(), this));
		
		if(Input.isKeyDown(Input.KEY_E))
			others.add(new Worker(myPlayer.getPosition().add(Block.SIZE.div(2)).div(Block.SIZE).toInt().mul(Block.SIZE), this, 4, 5));
			/*
			switch((int)(Math.random() * 3)){
				case 0 :
					addEnemy(level.getMap().getRandomEmptyBlock().getPosition(), Enemy.ENEMY_A);
					break;
				case 1 :
					addEnemy(level.getMap().getRandomEmptyBlock().getPosition(), Enemy.ENEMY_B);
					break;
				case 2 :
					addEnemy(level.getMap().getRandomEmptyBlock().getPosition(), Enemy.ENEMY_C);
					break;
			}
			*/
		gui.input();
		
		
		
		if(Input.isKeyDown(Input.KEY_LALT))
			explosions.add(new Explosion(myPlayer.getSelectorPos().add(Block.SIZE.div(2)), this, new GVector2f(40,40), Color.BLACK, 10));
		
	}

	//GETTERS

	public float 					getZoom() {return zoom;}
	public Level 					getLevel() {return level;}
	public MyPlayer 				getMyPlayer() {return myPlayer;}
	public Item 					getItem(String string) {return null;}
	public NavBar 					getNavBar() {return gui.getNavBar();}
	public Canvas 					getCanvas(){return parent.getCanvas();}
	public Profil 					getProfil() {return parent.getProfil();}
	public GVector2f 				getPosition() {return new GVector2f();}
	public GVector2f 				getOffset() {return myPlayer.getOffset();}
	public HashMap<String, Player> 	getPlayers() { return players; }
	public Communicable 			getConnection() {return parent.getComunication();}
	public GVector2f 				getSize() {return new GVector2f(getCanvas().getWidth(), getCanvas().getHeight());}
	
	public boolean hasHelper(String string) {return helpers.containsKey(string);}
	
	public boolean isVisible(Visible b){
		return !(b.getPosition().getX() * getZoom()  + Config.BLOCK_DEFAULT_SIZE.getX() * getZoom()  < getOffset().getX() || 
				 b.getPosition().getY() * getZoom()  + Config.BLOCK_DEFAULT_SIZE.getY() * getZoom()  < getOffset().getY() || 
				   getOffset().getX() + getCanvas().getWidth() < b.getPosition().getX() * getZoom()    ||
				   getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY() * getZoom() );
	}

	public ArrayList<String> getLogInfos(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("FPS: " + parent.getFPS());
		result.add("UPS: " + parent.getUPS());
		result.add("loops: " + parent.getLoops());
		result.add("send messages: " + Bombercraft.totalMessages.getXi());
		result.add("recieve messages: " + Bombercraft.totalMessages.getYi());
		result.add("helpers: " + renderedHelpers + "/" + helpers.size());
		result.add("enemies: " + renderedEnemies + "/" + enemies.size());
		result.add("particles: " + renderedParticles);
		result.add("blocks: " + level.getMap().getRenderedBlocks() + "/" + (int)level.getMap().getNumberOfBlocks().mul());
		result.add("Zoom: " + getZoom());
		return result;
	}

	public ArrayList<Enemy> getEnemiesAround(GVector2f position, int range){
		return enemies.parallelStream()
					  .filter(a -> GColision.pointCircleCollision(a.getPosition(), position, range))
					  .collect(Collectors.toCollection(ArrayList<Enemy>::new));
	}

	public boolean hasWall(float i, float j){
		return walls.containsKey(new GVector2f(i, j).toString());
	}
	
	//ADDERES
	
	@Override
	public void addHelper(GVector2f selectorSur, int cadenceBonus, GVector2f pos, int demageBonus,  String type, GVector2f target){
		switch(type){
			case Helper.TOWER_MACHINE_GUN :
				addTower(selectorSur, Helper.TOWER_MACHINE_GUN );
				break;
			case Helper.TOWER_LASER :
				addTower(selectorSur, Helper.TOWER_LASER );
				break;
			case Helper.OTHER_RESPAWNER : //TODO upraviù
				addRespawner(selectorSur, Enemy.ENEMY_B, 1000);
				break;
			case Helper.OTHER_ADDUCTOR : //TODO upraviù
				addAdductor(selectorSur);
				break;
			case Helper.BOMB_NORMAL :
				addBomb(pos, Config.BOMB_DEFAULT_RANGE, Config.BOMB_DEFAULT_TIME, Config.BOMB_DEFAULT_DEMAGE + demageBonus);
				break;
			case Helper.SHOVEL :
				helpers.remove(selectorSur.toString());
				level.getMap().remove(selectorSur);
				break;
			case Helper.WEAPON_LASER :
				fire(cadenceBonus, pos, target, demageBonus, Helper.WEAPON_LASER);
				break;
			case Helper.WEAPON_BOW :
				fire(cadenceBonus, pos, target, demageBonus, Helper.WEAPON_BOW);
				break;
			case Helper.WEAPON_LIGHTNING :
				addLighting(5, target, pos);
				break;
			case Helper.WEAPON_BOOMERANG :
				fire(cadenceBonus, pos, target, demageBonus, Helper.WEAPON_BOOMERANG);
				break;
			case Helper.WEAPON_STICK :
				fire(cadenceBonus, pos, target, demageBonus, Helper.WEAPON_STICK);
				break;
			case Block.WOOD :
				addBlock(selectorSur, Block.WOOD);
				break;
			case Block.IRON :
				addBlock(selectorSur, Block.IRON);
				break;
		}
			
	}

	public void addLighting(int num, GVector2f target, GVector2f pos) {
		for(int i=0 ; i< num ; i++){
			emitters.add(new ParticleEmmiter(Emitter.PARTICLE_EXPLOSION_BLUE_SPARK, target.div(getZoom()).add(getOffset()), this));
			others.add(new Lightning(pos.mul(getZoom()).sub(getOffset()), target, (int)(4 * Math.random() ), this));
		}
	}

	public void addRespawner(GVector2f sur, String type, int interval){
		respawnerSur = sur;
		if(!helpers.containsKey(sur.toString()))
			if(getLevel().getMap().isWalkable(sur.getXi(), sur.getYi()))
				helpers.put(sur.toString(), new Respawner(this, 
														 sur.mul(Block.SIZE),
														 type, 
														 interval));
	}
	
	public void addAdductor(GVector2f sur){
		if(!helpers.containsKey(sur.toString()))
			if(getLevel().getMap().isWalkable(sur.getXi(), sur.getYi())){
				helpers.put(sur.toString(), new Adductor(sur.mul(Block.SIZE),this));
				if(respawnerSur != null)
					others.add(new Path(this, respawnerSur, sur));
			}
	}
	
	public void addTower(GVector2f sur, String type){
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
	
	public void addBlock(GVector2f sur, String type) {
		getLevel().getMap().getBlock(sur.getXi(), sur.getYi()).build(type);
	}

	@Override
	public void addBullet(GVector2f position, GVector2f direction, int speed, int attack, String bulletType, int bullettHealt) {
		switch(bulletType){
			case Bullet.BULLET_BASIC :
				others.add(new BulletDefault(position, this, direction, speed, bullettHealt, attack, 10, 15, Color.blue));
				break;
			case Bullet.BULLET_LASER :
				others.add(new BulletLaser(position, this, direction, speed, bullettHealt, attack));
				break;
			case Bullet.BULLET_ARROW :
				others.add(new BulletArrow(position, this, direction, speed, bullettHealt, attack));
				break;
			case Bullet.BULLET_MAGIC :
				Bullet b = new BulletMagic(position, this, direction, speed, bullettHealt, attack);
				lights.add(new Light(this, new GVector2f(0,0), new GVector2f(40, 40), b));
				emitters.add(new ParticleEmmiter(Emitter.PARTICLE_EMITTER_GREEN_MAGIC, b.getPosition(), this, b));
				others.add(b);
				break;
			case Bullet.BULLET_BOOMERANG :
				others.add(new BulletBoomerang(position, this, direction, speed, bullettHealt, attack));
				break;
		}
	}

	@Override
	public void addBomb(GVector2f position, int range, int time, int demage) {
		GVector2f sur = position.div(Block.SIZE).toInt();
		if(!helpers.containsKey(sur.toString()))
			helpers.put(sur.toString(), new Bomb(sur.mul(Block.SIZE), this, time, range, demage));
	}

	@Override
	public void addEmmiter(GVector2f position, String type) {
		emitters.add(new ParticleEmmiter(type, position,  this));
	}
	
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number){
		explosions.add(new Explosion(position, this, size, color, number));
	}
	
	public void addPlayer(String name, String image){
		players.put(name, new Player(this, 
									 level.getPlayerRespawnZone() , 
									 name, 
									 level.getDefaultPlayerInfo().getInt("speed"), 
									 level.getDefaultPlayerInfo().getInt("healt"), 
									 "player1.png", 
									 level.getDefaultPlayerInfo().getInt("range")));
	}

	@Override
	public void addEnemy(GVector2f position, String type) {
		switch(type){
			case Bot.ENEMY_A:
				enemies.add(new EnemyA(position, this));
				break;
			case Bot.ENEMY_B:
				enemies.add(new EnemyB(position, this));
				break;
			case Bot.ENEMY_C:
				enemies.add(new EnemyC(position, this));
				break;
		}
	}
}
