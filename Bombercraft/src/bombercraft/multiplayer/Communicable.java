package bombercraft.multiplayer;

import java.util.HashMap;

import bombercraft.game.Player;
import bombercraft.game.entity.helper.Bomb;
import bombercraft.game.entity.helper.Shootable;
import bombercraft.game.level.Level;
import utils.math.GVector2f;

public interface Communicable {
	
	public void playerNewPos(Player player);
	public void eatItem(GVector2f sur, int type);
	public void bombExplode(Bomb bomb);
	public void cleanUp();
	public void hitBlock(GVector2f position, int demage);
	
	public Level 		getLevel();
	public int 			getNumberPlayersInGame();
	public GVector2f 	getMyPosition();
	public boolean 		isOnline();
	
	public default HashMap<String, Player> getPlayers(){
		return new HashMap<String, Player>();
	};

	public void putHelper(Player player);
	public void putBullet(Shootable tower);
	public void putEmmiter(String type, GVector2f position);
	public void putBomb(Player player);
	

}
