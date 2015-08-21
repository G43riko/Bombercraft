package bombercraft.game.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GLog;
import utils.GVector2f;

public class Level implements Interactable {
	private Map map;
	private GameAble parent;
	private List<GVector2f> respawnZones = new ArrayList<GVector2f>();
	
	//CONSTRUCTORS
	
	public Level(){
		respawnZones.add(new GVector2f(40, 40));
		GLog.write(GLog.CREATE, "Level vytvorený");
	}

	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		map.render(g2);
	}
	
	//GETTERS
	
	public boolean isReady(){
		return parent != null && map != null;
	}
	
	public GameAble getParent() {
		return parent;
	}

	public Map getMap(){
		return map;
	}
	
	public GVector2f getPlayerRespawnZone(){
		return respawnZones.get((int)(Math.random() * respawnZones.size()));
	}
	
	//SETTERS
	
	public void setGame(GameAble game){
		parent = game;
		map = new Map(game);
	}

	public List<GVector2f> getRespawnZones() {
		return new ArrayList<GVector2f>(respawnZones);
	}
}
