package bombercraft.multiplayer;

import glib.util.vector.GVector2f;
import bombercraft.game.Bomb;
import bombercraft.game.level.Level;

public class GameClient implements Communicable{

	@Override
	public void playerMove(GVector2f move, int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putBomb(GVector2f position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
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

}
