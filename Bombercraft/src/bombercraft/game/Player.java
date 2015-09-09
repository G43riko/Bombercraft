package bombercraft.game;

import java.awt.Graphics2D;

import bombercraft.PlayerSprite;
import bombercraft.game.entity.Entity;
import bombercraft.game.level.Block;
import utils.GVector2f;
import utils.Utils;
import utils.json.JSONObject;

public class Player extends Entity{
	private int		speed;
	private int		healt;
	private int		range;
	private int		direction		= 2;
	private int		demage;
	private int		cadenceBonus	= 0;
	private String	name;
	private String	image;
	private boolean	moving;

	
	public Player(GameAble parent, JSONObject object){
		this(parent,
			 new GVector2f(object.getString("position")),
			 object.getString("name"),
			 object.getInt("speed"),
			 object.getInt("healt"),
			 object.getString("image"),
			 object.getInt("range"));
	}
	
	public Player(GameAble parent, GVector2f position, String name, int speed, int healt, String image, int range) {
		super(position, parent);
		this.speed = speed;
		this.healt = healt;
		this.range = range;
		this.name = name;
		this.demage = 1;
		setImage(image);
	}
	
	public void move(GVector2f move){
		position = position.add(move.mul(speed));
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(getParent() == null || position == null)
			return;
		
		GVector2f pos = position.mul(getParent().getZoom()).sub(getParent().getOffset());

		GVector2f size = Block.SIZE.mul(getParent().getZoom());
		
		PlayerSprite.drawPlayer(pos, size, g2, getDirection(), getImage(), isMoving());
		
		
	}
	
	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		result.put("position", position);
		result.put("name", name);
		result.put("speed", speed);
		result.put("healt", healt);
		result.put("image", image);
		result.put("range", range);
		return result.toString();
	}

	public void respawn() {
		position = getParent().getLevel().getPlayerRespawnZone();
	}
	
	public GVector2f getSelectorPos(){
		GVector2f pos = getPosition().add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		pos = pos.add(Utils.getNormalMoveFromDir(getDirection())).mul(Block.SIZE);
		return pos;
	}
	
	@Override
	public GVector2f getSur() {return position.div(Block.SIZE).toInt();}
	
	@Override
	public GVector2f	getSize() {return Block.SIZE;}
	public boolean 		isMoving() {return moving;}
	public String 		getImage() {return image;}
	public String 		getName() {return name;}
	public int 			getSpeed() {return speed;}
	public int 			getDemage() {return demage;}
	public int 			getDirection() {return direction;}
	public int 			getCadenceBonus() {return cadenceBonus;}

	public void setDirection(int direction) {this.direction = direction;}
	public void setMoving(boolean moving) {this.moving = moving;}
	public void setImage(String image) {
		this.image = image;
		PlayerSprite.setSprite(image, 5, 5, 4, 6);
	}

	

}
