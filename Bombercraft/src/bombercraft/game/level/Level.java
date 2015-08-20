package bombercraft.game.level;

import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GLog;

public class Level implements Interactable {
	private Map map;
	private GameAble parent;
	
	//CONSTRUCTORS
	
	public Level(){
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
	
	//SETTERS
	
	public void setGame(GameAble game){
		parent = game;
		map = new Map(game);
	}
}
