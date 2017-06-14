package bombercraft.game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import bombercraft.Config;
import bombercraft.Profil;
import bombercraft.game.level.Level;
import bombercraft.gui.menu.MainMenu;
import bombercraft.multiplayer.Communicable;
import bombercraft.multiplayer.GameClient;
import bombercraft.multiplayer.GameServer;
import core.CoreEngine;
import core.Input;
import utils.Utils;
import utils.math.GVector2f;

public abstract class CoreGame extends CoreEngine implements MenuAble{
	public static final int	MAIN_MENU	= 0;
	public static final int	LOADING		= 1;
	public static final int	RUNNING		= 2;
	
	protected int			gameIs		= MAIN_MENU;
	private Profil			profil;
	private MainMenu		mainMenu;
	private GameAble		game;
	private Communicable	comunication;
	private boolean			gameLauched	= false;

	//CONTRUCTORS
	
	public CoreGame(){
		super(Config.WINDOW_DEFAULT_FPS, Config.WINDOW_DEFAULT_UPS, Config.WINDOW_DEFAULT_RENDER_TEXT);

		Utils.sleep(100);
		mainMenu = new MainMenu(this);
	}
	
	//OVERRIDES
	
	@Override
	protected void render(Graphics2D g2) {
		clearScreen(g2);
		initHints(g2);
		
		switch(gameIs){
			case MAIN_MENU:
				mainMenu.render(g2);
				break;
			case LOADING :
				g2.setColor(Config.LOADING_DEFAULT_BACKGROUND_COLOR);
				g2.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
				g2.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC , Config.LOADING_DEFAULT_FONT));
				g2.setColor(Config.LOADING_DEFAULT_TEXT_COLOR);
				g2.drawString("Loading",
							  (getCanvas().getWidth()  - g2.getFontMetrics().stringWidth("Loading")) / 2, 
							  (getCanvas().getHeight() - Config.LOADING_DEFAULT_FONT) / 2);
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
		if(Input.isKeyDown(Input.KEY_ESCAPE)){
			if(gameIs == RUNNING)
				gameIs = MAIN_MENU;
			mainMenu.changeGameLaunched();
		}
		
		if(game != null)
			game.input();
	}
	
	//MENUABLE
	
	@Override
	public void startNewGame() {
		if(gameLauched)
			stopGame();
		
		gameIs = LOADING;
		comunication = new GameServer(this);
		mainMenu.changeGameLaunched();
	}
	@Override
	public void joinGame() {
		gameIs = LOADING;
		comunication = new GameClient(this);
		
	}
	@Override
	public void continueGame() {
		if(game == null || gameIs == RUNNING)
			return;
		
		gameIs = RUNNING;
		
	}
	@Override
	public void pausedGame() {
		gameIs = MAIN_MENU;
		gameLauched = true;
		
	}
	@Override
	public void stopGame() {
		if(game != null)
			game.cleanUp();
		
		if(comunication != null)
			comunication.cleanUp();
		gameLauched = false;
		mainMenu.changeGameLaunched();
		game = null;
	}
	@Override
	public void exitGame() {
		stopGame();
		mainMenu.cleanUp();
		stop();
		cleanUp();
		System.exit(1);
	}

	//OTHERS
	
	@Override
	public void createGame(Level level, String gameData) {
		gameIs = LOADING;
		if(gameLauched)
			stopGame();
		
		game = new Game(level, this, gameData);
		gameLauched = true;
		gameIs = RUNNING;
	}
	
	private void clearScreen(Graphics2D g2){
		g2.setColor(Config.BACKGROUND_COLOR);
		g2.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
	}
	
	public void initHints(Graphics2D g2){
		boolean val = true;
		
		if(val){
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 		RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 	RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, 			RenderingHints.VALUE_RENDER_QUALITY);
	
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 		RenderingHints.VALUE_STROKE_PURE);
			
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 	RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_DITHERING, 			RenderingHints.VALUE_DITHER_ENABLE);
			
			g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 	250);
		}
		else{

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 		RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 	RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, 			RenderingHints.VALUE_RENDER_SPEED);
			
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 		RenderingHints.VALUE_STROKE_NORMALIZE);
			
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 	RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_DITHERING, 			RenderingHints.VALUE_DITHER_DISABLE);
			
			g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 	100);
		}
	}
	
	//EVENTS
	
	@Override
	public void onExit() {
		Profil.saveProfil(profil);
	}
	
	@Override
	public void onResize() {
		if(game != null)
			game.calcPosition();
		if(mainMenu != null)
			mainMenu.calcPosition();
	}
	
	//SETTERS

	public void setProfile(String profilName){
		if(profil != null)
			Profil.saveProfil(profil);
		
		profil = new Profil(profilName);
	}
	
	//GETTERS

	public GVector2f 	getSize() {return new GVector2f(getCanvas().getWidth(), getCanvas().getHeight());}
	public Communicable getComunication() {return comunication;}
	public GVector2f 	getPosition() {return new GVector2f();}
	public Profil 		getProfil() {return profil;}
	public int 			getGameIs() {return gameIs;}
	public boolean 		isGameLauched() {return gameLauched;}
}
