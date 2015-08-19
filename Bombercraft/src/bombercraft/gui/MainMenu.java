package bombercraft.gui;

import glib.util.vector.GVector2f;
import bombercraft.game.CoreGame;
import bombercraft.game.MenuAble;

public class MainMenu extends Menu{
	public final static int MAIN_MENU = 0;
	public final static int OPTIONS = 1;
	public final static int OPTIONS_NEW_GAME = 2;
	public final static int OPTIONS_JOIN_GAME = 3;
	
	private MenuAble parent;
	
	private int actMenu;
	private Menu OptionsMenu;
	
	public MainMenu(MenuAble parent) {
		super(parent.getCanvas());
		this.parent = parent;
		
		addButton("newGame", "Nov· hra");
		addButton("continue", "PokraËovaù");
		addButton("stopGame", "Stopn˙ù hru");
		addButton("connectToGame", "Pripojiù sa k hre");
		addButton("options", "Nastavenia");
		addButton("exit", "Koniec");
		setGameLaunched(false);
		
		
//		OptionsMenu = new OptionsMenu(parent.getCanvas(), this); 
		
		actMenu = MAIN_MENU;
	}
	
	public void setGameLaunched(boolean value){
		components.get("continue").setActive(value);
		components.get("stopGame").setActive(value);
	}
	
	@Override
	public void doAct(GVector2f click) {
		if(parent.gameIs() != CoreGame.MAIN_MENU)
			return;
		if(actMenu == MAIN_MENU){
			if(components.get("newGame").isClickIn(click))
				parent.startNewGame();
			else if(components.get("continue").isClickIn(click))
				parent.continueGame();
			else if(components.get("stopGame").isClickIn(click))
				parent.stopGame();
			else if(components.get("connectToGame").isClickIn(click))
				parent.joinGame();
			else if(components.get("options").isClickIn(click))
				actMenu = OPTIONS;
			else if(components.get("exit").isClickIn(click))
				parent.exitGame();
		}
		else if(actMenu == OPTIONS)
			OptionsMenu.doAct(click);
	}

}
