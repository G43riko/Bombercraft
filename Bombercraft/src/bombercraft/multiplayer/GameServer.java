package bombercraft.multiplayer;

import core.Input;
import bombercraft.Config;
import bombercraft.game.CoreGame;
import bombercraft.game.Player;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.entity.helper.Shootable;
import bombercraft.game.level.Block;
import bombercraft.game.level.Level;
import bombercraft.multiplayer.core.ClientPlayer;
import bombercraft.multiplayer.core.Server;
import utils.GLog;
import utils.GVector2f;
import utils.json.JSONObject;

public class GameServer extends Server implements Communicable{
	private Level actLevel;
	
	//CONTRUCTORS
	
	public GameServer(CoreGame coreGame){
		actLevel = new Level();
		coreGame.createGame(actLevel, null);
		GLog.write(GLog.CREATE, "GameServer vytvorený");
	}
	
	//PUTTERS
	
	@Override
	public void putBomb(Player player) {
		if(isOnline()){
			JSONObject object = new JSONObject();
			object.put("position", player.getPosition());
			object.put("range", Config.BOMB_DEFAULT_RANGE);
			object.put("time", Config.BOMB_DEFAULT_TIME);
			object.put("demage", Config.BOMB_DEFAULT_DEMAGE);
			
			write(object.toString(), Server.PUT_BOMB);
		}
		
		actLevel.getParent().addBomb(player.getPosition(), 
									 Config.BOMB_DEFAULT_RANGE, 
									 Config.BOMB_DEFAULT_TIME, 
									 Config.BOMB_DEFAULT_DEMAGE);
	}

	@Override
	public void putHelper(Player player) {
		GVector2f position = player.getPosition().add(Block.SIZE/*.mul(actLevel.getParent().getZoom())*/.div(2));
		GVector2f selectorSur = player.getSelectorPos().add(Block.SIZE.div(2)).div(Block.SIZE).toInt();
		GVector2f target = Input.getMousePosition();
//		System.out.println("position: " + position);
//		System.out.println("selectorSur: " + selectorSur);
//		System.out.println("target: " + target);
		if(isOnline()){
			JSONObject object = new JSONObject();
			object.put("selectorSur", selectorSur);
			object.put("cadenceBonus", player.getCadenceBonus());
			object.put("position", position);
			object.put("demage", player.getDemage());
			object.put("type", player.getParent().getNavBar().getSelecedItem().getId());
			object.put("target", target.add(actLevel.getParent().getOffset()).div(actLevel.getParent().getZoom()));
			write(object.toString(), Server.PUT_HELPER);
		}
		
		actLevel.getParent().addHelper(selectorSur,
									   player.getCadenceBonus(),
									   position,
									   player.getDemage(),
									   player.getParent().getNavBar().getSelecedItem().getId(),
									   target);
	}

	@Override
	public void putBullet(Shootable tower) {
		actLevel.getParent().addBullet(tower.getPosition(), 
									   tower.getDirection(), 
									   tower.getBulletSpeed(), 
									   tower.getDemage(),
									   tower.getBulletType(),
									   1);
	}

	@Override
	public void putEmmiter(String type, GVector2f position) {
		actLevel.getParent().addEmmiter(position, type);
	}

	//GETTERS

	@Override
	protected String getLevelInfo() {
		JSONObject o = new JSONObject();
		o.put("level", actLevel.toJSON());
		o.put("game", actLevel.getParent().toJSON());
		return o.toString();
	}

	@Override
	public boolean isOnline() {
		return getNumberOfClients() > 0;
	}

	@Override
	public Level getLevel() {
		return actLevel;
	}

	@Override
	public int getNumberPlayersInGame() {
		return 0;
	}

	@Override
	public GVector2f getMyPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	//OTHERS
	
	@Override
	protected void processMessage(String data, ClientPlayer client) {
		JSONObject txt = new JSONObject(data);
		JSONObject o = new JSONObject(txt.getString("msg"));
		switch(txt.getString("type")){
			case PLAYER_NAME :
				renameClient(client.getId() + "", o.getString("name"));
				actLevel.getParent().addPlayer(o.getString("name"), o.getString("avatar"));
				break;
			case PLAYER_MOVE :
				writeExcept(txt.getString("msg"), PLAYER_MOVE, client);
				Player p = actLevel.getParent().getPlayers().get(o.getString("player"));
				p.setDirection(o.getInt("direction"));
				p.setPosition(new GVector2f(o.getString("position")));
				break;
			case PUT_HELPER :
//				actLevel.getParent().addHelper(player, type);;
				break;
			case HIT_BLOCK :
				break;
			case PUT_BOMB :
				writeExcept(txt.getString("msg"), PUT_BOMB, client);
				actLevel.getParent().addBomb(new GVector2f(o.getString("position")), 
											 o.getInt("range"),
											 o.getInt("time"), 
											 o.getInt("demage"));
				break;
		}
	}

	@Override
	public void eatItem(GVector2f sur, int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bombExplode(Bomb bomb) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playerNewPos(Player player) {
		if(!isOnline())
			return;
		
		JSONObject object = new JSONObject();
		object.put("position", player.getPosition());
		object.put("direction", player.getDirection());
		object.put("player", player.getName());
		write(object.toString(), Server.PLAYER_MOVE);
	}

	@Override
	public void hitBlock(GVector2f position, int demage) {
		JSONObject object = new JSONObject();
		object.put("demage", demage);
		object.put("position", position);
		
		write(object.toString(), Server.HIT_BLOCK);
	}

}
