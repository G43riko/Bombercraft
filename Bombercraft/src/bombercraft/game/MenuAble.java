package bombercraft.game;

import java.awt.Canvas;

import bombercraft.game.level.Level;

public interface MenuAble {
	public void startNewGame();
	public void joinGame();
	public void continueGame();
	public void pausedGame();
	public void stopGame();
	public void exitGame();
	public Canvas getCanvas();
	public void createGame(Level level);
	public int gameIs();
}
