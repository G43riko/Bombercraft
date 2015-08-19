package bombercraft.game;

import java.awt.Graphics2D;

import bombercraft.Config;
import bombercraft.game.level.Level;
import bombercraft.gui.MainMenu;
import bombercraft.multiplayer.Communicable;
import bombercraft.multiplayer.GameServer;
import core.CoreEngine;

public abstract class CoreGame extends CoreEngine implements MenuAble{
	public static final int MAIN_MENU = 0;
	public static final int RUNNING = 1;
	
	protected int gameIs =  MAIN_MENU;
	
	private MainMenu mainMenu = new MainMenu(this);
	private Game game;
	private Communicable comunication;
	
	public CoreGame(){
		super(Config.WINDOW_DEFAULT_FPS, Config.WINDOW_DEFAULT_UPS, Config.WINDOW_DEFAULT_RENDER_TEXT);
	}
	
	@Override
	protected void render(Graphics2D g2) {
		clearScreen(g2);
		
		switch(gameIs){
			case MAIN_MENU:
				mainMenu.render(g2);
				break;
			case RUNNING:
				if(game != null)
					game.render(g2);
				break;
		}
	}
	
	@Override
	protected void update(float delta) {
		if(gameIs == MAIN_MENU)
			mainMenu.update(delta);
		
		if(game != null)
			game.update(delta);
	}
	
	@Override
	protected void input() {
		// TODO Auto-generated method stub
		super.input();
	}
	
	@Override
	protected void cleanUp() {
		// TODO Auto-generated method stub
		super.cleanUp();
	}
	
	@Override
	public void startNewGame() {
		comunication = new GameServer(this);
		
	}
	@Override
	public void joinGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void continueGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pausedGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stopGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createGame(Level level) {
		game = new Game(level);
		gameIs = RUNNING;
	}
	
	private void clearScreen(Graphics2D g2){
		g2.setColor(Config.BACKGROUND_COLOR);
		g2.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
	}
	
	@Override
	public int gameIs() {
		return gameIs;
	}
}
