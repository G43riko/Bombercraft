package bombercraft.gui.menu;

import java.awt.Graphics2D;

import bombercraft.game.CoreGame;
import bombercraft.game.MenuAble;
import bombercraft.gui.component.Button;
import core.Input;
import utils.math.GVector2f;

public class MainMenu extends Menu{
	enum ActualMenu{
		MAIN_MENU,OPTIONS, OPTIONS_NEW_GAME, OPTIONS_JOIN_GAME, OPTIONS_PROFILE;
	}
	public final static int MAIN_MENU 			= 0;
	public final static int OPTIONS 			= 1;
	public final static int OPTIONS_NEW_GAME 	= 2;
	public final static int OPTIONS_JOIN_GAME 	= 3;
	public final static int OPTIONS_PROFILE 	= 4;
	private MenuAble coreGame; 
	private int actMenu  = OPTIONS_PROFILE;
	private JoinGameMenu joinMenu;
	private ProfileMenu profileMenu;
	private OptionsMenu optionsMenu;
	
	public MainMenu(MenuAble parent) {
		super(parent);
		coreGame = parent;
		Input.addMenu(this);
		init();
		initMenus();
		changeGameLaunched();
	}

	@Override
	public void doAct(GVector2f click) {
		if(coreGame.getGameIs() != CoreGame.MAIN_MENU)
			return;
		if(actMenu == MAIN_MENU){
			if(components.get("newGame").isClickIn(click))
				coreGame.startNewGame();
			else if(components.get("continue").isClickIn(click))
				coreGame.continueGame();
			else if(components.get("stopGame").isClickIn(click))
				coreGame.stopGame();
			else if(components.get("joinGame").isClickIn(click)){
				actMenu = OPTIONS_JOIN_GAME;
				coreGame.joinGame();
			}
			else if(components.get("options").isClickIn(click))
				actMenu = OPTIONS;
			else if(components.get("exit").isClickIn(click))
				coreGame.exitGame();
			else if(components.get("changeProfil").isClickIn(click))
				actMenu = OPTIONS_PROFILE;
		}
		else if(actMenu == OPTIONS)
			optionsMenu.doAct(click);
//		else if(actMenu == OPTIONS_NEW_GAME)
		else if(actMenu == OPTIONS_JOIN_GAME)
			joinMenu.doAct(click);
		else if(actMenu == OPTIONS_PROFILE)
			profileMenu.doAct(click);
	}
	
	private void initMenus(){
		joinMenu = new JoinGameMenu(coreGame);
		optionsMenu = new OptionsMenu(this);
		profileMenu = new ProfileMenu(this);
	}
	
	@Override
	public void update(float delta) {
		if(actMenu == MAIN_MENU)
			super.update(delta);
		else if(actMenu == OPTIONS_JOIN_GAME)
			joinMenu.update(delta);
		else if(actMenu == OPTIONS_PROFILE)
			profileMenu.update(delta);
		else if(actMenu == OPTIONS)
			optionsMenu.update(delta);
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(actMenu == MAIN_MENU)
			super.render(g2);
		else if(actMenu == OPTIONS_JOIN_GAME)
			joinMenu.render(g2);
		else if(actMenu == OPTIONS_PROFILE)
			profileMenu.render(g2);
		else if(actMenu == OPTIONS)
			optionsMenu.render(g2);
	}

	@Override
	public void calcPosition() {
		if(joinMenu != null)
			joinMenu.calcPosition();
		if(optionsMenu != null)
			optionsMenu.calcPosition();
		if(profileMenu != null)
			profileMenu.calcPosition();
		
		components.entrySet().stream().forEach(a -> a.getValue().calcPosition());
	}

	@Override
	protected void init() {
		addComponent("newGame", new Button(this, "Nova hra"));
		addComponent("continue", new Button(this, "Pokracovat"));
		addComponent("stopGame", new Button(this, "Stopnut hru"));
		addComponent("joinGame", new Button(this, "Pripojit sa k hre"));
		addComponent("changeProfil", new Button(this, "Zmenit profil"));
		addComponent("options", new Button(this, "Nastavenia"));
		addComponent("exit", new Button(this, "Koniec"));
		
	}

//	public static int getMainMenu() {
//		return MAIN_MENU;
//	}

	public int getActMenu() {
		return actMenu;
	}

	public MenuAble getCoreGame() {
		return coreGame;
	}

	public void setMainMenu() {
		actMenu = MAIN_MENU;
	}

	public void changeGameLaunched(){
		components.get("continue").setDisable(!coreGame.isGameLauched());
		components.get("stopGame").setDisable(!coreGame.isGameLauched());
	}
}
