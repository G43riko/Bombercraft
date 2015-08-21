package bombercraft.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashMap;

import core.Input;
import utils.GVector2f;
import bombercraft.game.GameAble;
import bombercraft.game.entity.Visible;
import bombercraft.gui.component.Bar;
import bombercraft.gui.component.MiniButton;

public class SideBar extends Bar implements Visible, Clicable{
	private int offset = 10;
	private HashMap<String, MiniButton> buttons = new HashMap<String, MiniButton>(); 
	
	public SideBar(GameAble parent) {
		super(parent, new GVector2f(200,0));
		calcPosition();
		buttons.put("newGame" ,new MiniButton(this, "New game", 30));
		buttons.put("clearGame", new MiniButton(this, "Clear game", 30));
		
		Input.addMenu(this);
	}

	@Override
	public void calcPosition() {
		totalPos = new GVector2f(getParent().getCanvas().getWidth() - size.getX() + offset, offset);
		totalSize = new GVector2f(size.getX(), getParent().getCanvas().getHeight()).sub(offset * 2);
		buttons.entrySet().stream().forEach(a -> a.getValue().calcPosition());
	}
	
	@Override
	public void update(float delta) {
		buttons.entrySet().stream().forEach(a -> a.getValue().update(delta));
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(getBackgroundColor());
		g2.fillRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi());
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		
		buttons.entrySet().stream().forEach(a -> a.getValue().render(g2));
	}

	@Override
	public GVector2f getPosition() {
		return totalPos;
	}

	@Override
	public GVector2f getSize() {
		return totalSize;
	}

	@Override
	public void doAct(GVector2f click) {
		if(buttons.get("newGame").isClickIn(click)){
			getParent().newGame();
		}
		else if(buttons.get("clearGame").isClickIn(click)){
			getParent().clearGame();
		}
		
	}
	
	
	
}
