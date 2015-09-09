package bombercraft.game;

import java.awt.Canvas;

import bombercraft.game.entity.Visible;
import bombercraft.game.level.Level;

public interface MenuAble extends Visible{
	public void 	startNewGame();
	public void 	joinGame();
	public void 	continueGame();
	public void 	pausedGame();
	public void 	stopGame();
	public void 	exitGame();
	public void 	createGame(Level level, String string);
	
	public boolean 	isGameLauched();
	public Canvas 	getCanvas();
	public int 		getGameIs();
	public void 	setProfile(String profilName);
}
