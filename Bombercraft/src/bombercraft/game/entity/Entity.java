package bombercraft.game.entity;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GVector2f;
import utils.IDGenerator;

public abstract class Entity implements Visible, Interactable{
	private int id;
	private GameAble parent;
	protected GVector2f position;
	protected boolean alive;
	
	
//	public Entity(JSONObject json, GameAble parent){
//		this.parent = parent;
//		id = json.getInt("id");
//		alive = json.getBoolean("alive");
//		position = new GVector2f(json.getString("position"));
//		
//	}
	
	public Entity(GVector2f position, GameAble parent){
		this.position = position;
		this.parent = parent;
		alive = true;
		id = IDGenerator.getId();
	}
	
	public abstract String toJSON();

	public abstract GVector2f getSur();
	
	public int getID(){
		return id;
	}
	
	@Override
	public GVector2f getPosition() {
		return position;
	}

	@Override
	public abstract GVector2f getSize();

	public boolean isAlive() {
		return alive;
	}

	public GameAble getParent() {
		return parent;
	}
}
