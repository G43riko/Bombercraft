package bombercraft.gui.menu;

import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.game.MenuAble;
import bombercraft.gui.component.Clicable;
import bombercraft.gui.component.GuiComponent;
import utils.math.GVector2f;
public abstract class Menu extends GuiComponent implements Clicable{
	protected HashMap<String, GuiComponent> components = new HashMap<String, GuiComponent>();
	

	public Menu(MenuAble parent){
		super(parent);
		position = new GVector2f(0, 100);
		calcPosition();
	}
	
	@Override
	public void render(Graphics2D g2) {
		components.entrySet()
			      .stream()
			      .forEach(a -> a.getValue().render(g2));
	}
	
	@Override
	public void update(float delta) {
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().update(delta));
	}
	
	public abstract void calcPosition();

	protected void addComponent(String name, GuiComponent component){
		components.put(name, component);
	}
}
