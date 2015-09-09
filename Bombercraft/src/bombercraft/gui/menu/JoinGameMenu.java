package bombercraft.gui.menu;

import bombercraft.game.MenuAble;
import bombercraft.gui.component.GameLine;
import utils.GVector2f;

public class JoinGameMenu extends Menu {

	public JoinGameMenu(MenuAble parent) {
		super(parent);
		addComponent("newGame", new GameLine(this));
	}

	@Override
	public void doAct(GVector2f click) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcPosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	

}
