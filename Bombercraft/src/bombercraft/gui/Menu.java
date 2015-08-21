package bombercraft.gui;

import utils.GVector2f;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Config;
import bombercraft.gui.component.Button;
import bombercraft.gui.component.GuiComponent;
import bombercraft.gui.component.Switch;
import core.Input;
import core.Interactable;

public abstract class Menu implements Interactable, Clicable{
	protected HashMap<String,GuiComponent> components = new HashMap<String,GuiComponent>();
	private Canvas canvas;
	
	public Menu(Canvas canvas){
		this.canvas = canvas;
		Input.addMenu(this);
	}
	
	protected void addButton(String val, String txt){
		GVector2f size = new GVector2f(Config.MENU_ITEM_WIDTH, Config.MENU_ITEM_HEIGHT);
		components.put(val, new Button(txt, size, Config.MENU_TEXT_SIZE, components.size() + 1, canvas));
	}
	
	protected void addSwitch(String val, String txt, boolean options){
		GVector2f size = new GVector2f(Config.MENU_ITEM_WIDTH, Config.MENU_ITEM_HEIGHT);
		components.put(val, new Switch(txt, size, Config.MENU_TEXT_SIZE, components.size() + 1, canvas, options));
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
	
	public void updateSize(){
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().updateSize());
	}
}
