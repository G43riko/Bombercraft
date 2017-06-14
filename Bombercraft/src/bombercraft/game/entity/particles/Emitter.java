package bombercraft.game.entity.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.json.JSONObject;
import utils.math.GVector2f;

public abstract class Emitter extends Entity{
	private static HashMap<String, String> predefinedParticles = new HashMap<String, String>();
	public final static String PARTICLE_EMITTER_TEST = "emmiterTest";
	public final static String PARTICLE_EXPLOSION_TEST = "explosionTest";
	public final static String PARTICLE_EXPLOSION_BOW_HIT = "explosionBowHit";
	public static final String PARTICLE_EXPLOSION_DEFAULT_HIT = "explosionDefaultHit";
	public static final String PARTICLE_EMITTER_GREEN_MAGIC = "particleEmmiterGreenMagic";
	public static final String PARTICLE_EXPLOSION_BLUE_SPARK = "particleExplosionBlueSpark";
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	protected Color color;
	protected float particlePerFrame;
	protected GVector2f speed; // x - value, y - randomness
	protected GVector2f rotation; // - speed, y - randomness
	protected GVector2f healt; // - normal, y - randomness
	protected GVector2f direction; // x - start angle, y - end angle
	protected GVector2f size; 
	protected int sizeRandomness;
	protected GVector2f positionRandomness; 
	protected long renderedParticles;
	private int particlesOnStart;
	static{
		initDefault();
	}
	
	//CONTRUCTORS
	
	public Emitter(String type, GVector2f position, GameAble parent) {
		super(position, parent);
		
		loadDataFromJSON(new JSONObject(predefinedParticles.get(type)));
		
		if(type == PARTICLE_EXPLOSION_BLUE_SPARK)
			switch((int)(Math.random()*6)){
				case 0 :
					color = Color.WHITE;
					break;
				case 1 :
					color = Color.RED;
					break;
				case 2 :
					color = Color.ORANGE;
					break;
				case 3 :
					color = Color.YELLOW;
					break;
				case 4 :
					color = Color.LIGHT_GRAY;
					break;
				case 5 :
					color = Color.BLUE;
					break;
			}
		
		createParticles(particlesOnStart);
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		renderedParticles = new ArrayList<Particle>(particles).stream()
															  .filter(a->getParent().isVisible(a))
															  .peek(a->a.render(g2))
															  .count();
	}
	
	protected void createParticles(int numOfParticles){
		for(int i=0 ; i<numOfParticles ; i++)
			particles.add(new Particle(position.add(new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(positionRandomness)), 
									   getParent(), 
									   color, 
									   new GVector2f(Math.random() - 0.5, Math.random() - 0.5).mul(speed.getX()), 
									   size.add(sizeRandomness * (float)(Math.random() - 0.5)), 
									   (int)(healt.getXi() + healt.getYi() * (Math.random() - 0.5))));
	}

	private void loadDataFromJSON(JSONObject object){
		this.color = new Color(object.getInt("color"));
		this.speed = new GVector2f(object.getString("speed"));
		this.rotation = new GVector2f(object.getString("rotation"));
		this.healt = new GVector2f(object.getString("healt"));
		this.direction = new GVector2f(object.getString("speed"));
		this.size = new GVector2f(object.getString("size"));
		this.sizeRandomness = object.getInt("sizeRandomness");
		this.positionRandomness = new GVector2f(object.getString("positionRandomness"));
		this.particlePerFrame = (float)object.getDouble("particlePerFrame");
		this.particlesOnStart = object.getInt("particlesOnStart");
	}
	
	
	private static void initDefault() {
		JSONObject object = new JSONObject();
		object.put("color", Color.MAGENTA.getRGB());
		object.put("speed", new GVector2f(2, 0).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(100, 100).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(3, 3).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 1);
		object.put("particlesOnStart", 0);
		predefinedParticles.put(PARTICLE_EMITTER_TEST, object.toString());
		
		object = new JSONObject();
		object.put("color", Color.RED.getRGB());
		object.put("speed", new GVector2f(2, 0).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(20, 40).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(3, 3).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 0);
		object.put("particlesOnStart", 10);
		predefinedParticles.put(PARTICLE_EXPLOSION_TEST, object.toString());
		
		object = new JSONObject();
		object.put("color", Color.BLUE.getRGB());
		object.put("speed", new GVector2f(4, 4).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(20, 40).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(3, 3).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 0);
		object.put("particlesOnStart", 5);
		predefinedParticles.put(PARTICLE_EXPLOSION_BLUE_SPARK, object.toString());
		
		object = new JSONObject();
		object.put("color", Color.ORANGE.getRGB());
		object.put("speed", new GVector2f(2, 0).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(30, 30).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(3, 3).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 0);
		object.put("particlesOnStart", 10);
		predefinedParticles.put(PARTICLE_EXPLOSION_BOW_HIT, object.toString());
		
		object = new JSONObject();
		object.put("color", Color.BLUE.getRGB());
		object.put("speed", new GVector2f(2, 0).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(20, 30).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(6, 4).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 0);
		object.put("particlesOnStart", 10);
		predefinedParticles.put(PARTICLE_EXPLOSION_DEFAULT_HIT, object.toString());
		
		object = new JSONObject();
		object.put("color", Color.GREEN.getRGB());
		object.put("speed", new GVector2f(1, 0).toString());
		object.put("rotation", new GVector2f(0, 0).toString());
		object.put("healt", new GVector2f(10, 30).toString());
		object.put("direction", new GVector2f(0, 360).toString());
		object.put("size", new GVector2f(5, 4).toString());
		object.put("sizeRandomness", 1);
		object.put("positionRandomness", new GVector2f(10, 10).toString());
		object.put("particlePerFrame", 1);
		object.put("particlesOnStart", 0);
		predefinedParticles.put(PARTICLE_EMITTER_GREEN_MAGIC, object.toString());
	}

	public GVector2f getSur() {return getPosition().div(Block.SIZE).toInt();}
	public long getRenderedParticles() {return renderedParticles;}
	public GVector2f getSize() {return size;}
	public boolean isAlive() {return alive || !particles.isEmpty();}

}
