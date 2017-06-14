package bombercraft.gui.menu;

import bombercraft.gui.component.Button;
import utils.math.GVector2f;

public class OptionsMenu extends Menu{
	private MainMenu parent;
	
	public OptionsMenu(MainMenu parent) {
		super(parent.getCoreGame());
		this.parent = parent;
		
		addComponent("back", new Button(this, "Naspa"));
	}

	@Override
	public void doAct(GVector2f click) {
		if(parent.getActMenu() != MainMenu.OPTIONS)
			return;
		
		if(components.get("back").isClickIn(click)){
			parent.setMainMenu();
		}
	}

	@Override
	public void calcPosition() {
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
