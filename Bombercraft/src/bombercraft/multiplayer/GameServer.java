package bombercraft.multiplayer;

import utils.GLog;
import utils.GVector2f;
import bombercraft.game.CoreGame;
import bombercraft.game.Player;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.level.Level;

public class GameServer implements Communicable{
	Level actLevel;
	
	public GameServer(CoreGame coreGame){
		actLevel = new Level();
		coreGame.createGame(actLevel);
		GLog.write(GLog.CREATE, "GameServer vytvorený");
	}
	
	
	@Override
	public void playerMove(GVector2f move, int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putBomb(Player player) {
		// TODO Auto-generated method stub
		
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
	public void putHelper(Player player) {
		actLevel.getParent().addHelper(player.getPosition(), (int)(1000 + Math.random() * 2));
	}

}
