package bombercraft.multiplayer;

import java.util.HashMap;

import bombercraft.game.Player;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.level.Level;
import utils.GVector2f;

public interface Communicable {
	public void playerMove(GVector2f move, int direction);
//	public void sendImage();
	public void putBomb(Player player);
//	public boolean isReady();
	public void eatItem(GVector2f sur, int type);
	public void bombExplode(Bomb bomb);

	public Level getLevel();
	public int getNumberPlayersInGame();
	public GVector2f getMyPosition();
	
	public default HashMap<String, Player> getPlayers(){
		return new HashMap<String, Player>();
	};
	
	public void putHelper(Player player);
}
