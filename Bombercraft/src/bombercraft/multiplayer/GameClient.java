package bombercraft.multiplayer;

import bombercraft.Config;
import bombercraft.game.CoreGame;
import bombercraft.game.Player;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.entity.helper.Shootable;
import bombercraft.game.level.Level;
import bombercraft.multiplayer.core.Client;
import bombercraft.multiplayer.core.Server;
import utils.GLog;
import utils.Utils;
import utils.json.JSONObject;
import utils.math.GVector2f;

public class GameClient extends Client implements Communicable{
	private Level actLevel;
	private CoreGame coreGame;
	
	//CONTRUCTORS
	
	public GameClient(CoreGame coreGame){
		this.coreGame = coreGame;
		GLog.write(GLog.CREATE, "GameClient vytvoren�");
	}
	
	//PUTTERS

	@Override
	public void putHelper(Player player) {
		switch(player.getParent().getNavBar().getSelecedItem().getId()){
			case Helper.BOMB_NORMAL :
				putBomb(player);
				break;
		}
	}

	@Override
	public void putBullet(Shootable tower) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putEmmiter(String type, GVector2f position) {
		actLevel.getParent().addEmmiter(position, type);
	}

	@Override
	public void putBomb(Player player) {
		JSONObject object = new JSONObject();
		object.put("position", player.getSelectorPos());
		object.put("range", Config.BOMB_DEFAULT_RANGE);
		object.put("time", Config.BOMB_DEFAULT_TIME);
		object.put("demage", Config.BOMB_DEFAULT_DEMAGE);
		
		write(object.toString(), Server.PUT_BOMB);
		
		actLevel.getParent().addBomb(player.getSelectorPos(), 
				 Config.BOMB_DEFAULT_RANGE, 
				 Config.BOMB_DEFAULT_TIME, 
				 Config.BOMB_DEFAULT_DEMAGE);
	}
	
	//GETTERS

	@Override
	public Level getLevel() {
		return actLevel;
	}

	@Override
	public int getNumberPlayersInGame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GVector2f getMyPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	//OTHERS
	
	@Override
	public void playerNewPos(Player player) {
		JSONObject object = new JSONObject();
		object.put("position", player.getPosition());
		object.put("direction", player.getDirection());
		object.put("player", player.getName());
		write(object.toString(), Server.PLAYER_MOVE);
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
	protected void processMessage(String data) {
		JSONObject txt = new JSONObject(data);
		JSONObject msg = new JSONObject(txt.getString("msg"));
		
		switch(txt.getString("type")){
			case Server.LEVEL_INFO :
				actLevel = new Level(new JSONObject(msg.getString("level")));
				coreGame.createGame(actLevel, msg.getString("game"));
				Utils.sleep(100);
				sendPlayerInfo();
				break;
			case Server.PLAYER_MOVE :
				Player p = actLevel.getParent().getPlayers().get(msg.getString("player"));
				p.setDirection(msg.getInt("direction"));
				p.setPosition(new GVector2f(msg.getString("position")));
				break;
			case Server.PUT_HELPER :
				actLevel.getParent().addHelper(new GVector2f(msg.getString("selectorSur")),
											   msg.getInt("cadenceBonus"),
											   new GVector2f(msg.getString("position")),
											   msg.getInt("demage"),
											   msg.getString("type"),
											   new GVector2f(msg.getString("target")));
				break;
			case Server.PUT_BOMB :
				actLevel.getParent().addBomb(new GVector2f(msg.getString("position")), 
											 msg.getInt("range"),
											 msg.getInt("time"), 
											 msg.getInt("demage"));
			case Server.HIT_BLOCK :
//				GVector2f pos = new GVector2f(msg.getString("position"));
//				actLevel.getParent().getLevel().getMap().getBlockOnPosition(pos).hit(msg.getInt("demage"));
				break;
		}
		
	}

	@Override
	public void hitBlock(GVector2f position, int demage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPlayerInfo() {
		JSONObject result = new JSONObject();
//		result.put("name", actLevel.getParent().getProfil().getName());
		result.put("name", actLevel.getParent().getMyPlayer().getName());
		result.put("avatar", actLevel.getParent().getProfil().getAvatar());
		write(result.toString() , Server.PLAYER_NAME);
	}
}
