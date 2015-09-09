package bombercraft.game.level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GLog;
import utils.GVector2f;
import utils.json.JSONObject;

public class Level implements Interactable {
	private Map map;
	private GameAble parent;
	private List<GVector2f> respawnZones = new ArrayList<GVector2f>();
	private String mapData;
	private JSONObject playerInfo;
	//CONSTRUCTORS
	
	public Level(JSONObject object){
		mapData = object.getString("map");
//		map = new Map(new JSONObject(), parent);
		playerInfo = object.getJSONObject("playerInfo");
		int i = 0;
		while(object.has("respawnZone" + i)){
			respawnZones.add(new GVector2f(object.getString(("respawnZone" + i))));
			i++;
		}
	}
	
	public Level(){
		respawnZones.add(new GVector2f(40, 40));
		GLog.write(GLog.CREATE, "Level vytvorený");
		setDefaultPlayerInfo();
	}
	
	//OTHERS
	
	public void changeBlock(GVector2f position, int healt, int type){
		
	}

	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		map.render(g2);
	}

	public String toJSON(){
		JSONObject result = new JSONObject();
		result.put("map", map.toJSON());
		
		for(int i=0 ; i<respawnZones.size() ; i++)
			result.put("respawnZone" + i, respawnZones.get(i));
			
		result.put("playerInfo", playerInfo);
		return result.toString();
	}
	
	//GETTERS
	
	public Map getMap(){return map;}
	public GameAble getParent() {return parent;}
	public boolean isReady(){return parent != null && map != null;}
	public List<GVector2f> getRespawnZones() {return new ArrayList<GVector2f>(respawnZones);}
	public GVector2f getPlayerRespawnZone(){return new GVector2f(respawnZones.get((int)(Math.random() * respawnZones.size())));}
	
	//SETTERS
	
	public void setDefaultPlayerInfo(){
		playerInfo = new JSONObject();
		playerInfo.put("speed", 4);
		playerInfo.put("range", 2);
		playerInfo.put("healt", 10);
	}
	
	public void setGame(GameAble game){
		parent = game;
		
		if(mapData != null)
			map = new Map(new JSONObject(mapData), parent);
		else
			map = new Map(game);
	}

	public JSONObject getDefaultPlayerInfo() {
		return playerInfo;
	}


	
}
