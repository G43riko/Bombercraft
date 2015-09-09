package bombercraft.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.Config;
import bombercraft.game.GameAble;
import bombercraft.gui.component.GuiComponent;
import bombercraft.gui.component.MiniButton;
import bombercraft.gui.component.MiniSwitch;
import utils.GVector2f;

public class SideBar extends Bar{
	private int offset = Config.SIDEBAR_DEFAULT_OFFSET;
	private HashMap<String, GuiComponent> buttons = new HashMap<String, GuiComponent>();
	
	public SideBar(GameAble parent) {
		super(parent, new GVector2f(200,0));
		calcPosition();
		buttons.put("newGame" ,		new MiniButton(this, "New game"));
		buttons.put("resetGame", 	new MiniButton(this, "Reset game"));
		buttons.put("showLogs", 	new MiniSwitch(this, "Show logs", Bombercraft.getViewOption("renderLogs")));
		buttons.put("showMinimap", 	new MiniSwitch(this, "Show minimap", Bombercraft.getViewOption("renderLogs")));
		buttons.put("showLights", 	new MiniSwitch(this, "Show shadows", Bombercraft.getViewOption("renderLights")));
		buttons.put("showLightMap", new MiniSwitch(this, "Show light map", Bombercraft.getViewOption("renderOnlyLightMap")));
		
		init();
	}
	
	private void init(){
		setBackgroundColor(Config.SIDEBAR_DEFAULT_BACKGROUND_COLOR);
		setBorderColor(Config.SIDEBAR_DEFAULT_BORDER_COLOR);
		setVisible(false);
		setBorderWidth(Config.SIDEBAR_DEFAULT_BORDER_WIDTH);
	}
	
	@Override
	public void calcPosition() {
		totalPos = new GVector2f(getParent().getCanvas().getWidth() - size.getX() + offset, offset);
		
		totalSize = new GVector2f(size).sub(offset * 2);
		
		if(size.getY() == 0)
			totalSize.setY(getParent().getCanvas().getHeight() - offset * 2);
		
		if(size.getX() == 0)
			totalSize.setX(getParent().getCanvas().getWidth() - offset * 2);
		
		buttons.entrySet().stream().forEach(a -> a.getValue().calcPosition());
	}
	
	@Override
	public void update(float delta) {
		buttons.entrySet().stream().forEach(a -> a.getValue().update(delta));
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(getBackgroundColor());
		g2.fillRoundRect(totalPos.getXi(), totalPos.getYi(), 
						 totalSize.getXi(), totalSize.getYi(), 
						 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		g2.drawRoundRect(totalPos.getXi(), totalPos.getYi(), 
				 		 totalSize.getXi(), totalSize.getYi(), 
				 		 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);
		
		
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
		if(!isVisible())
			return;
		if(buttons.get("newGame").isClickIn(click)){
			getParent().newGame();
		}
		else if(buttons.get("resetGame").isClickIn(click)){
			getParent().resetGame();
		}
		else if(buttons.get("showLogs").isClickIn(click)){
			Bombercraft.switchViewOption("renderLogs");
		}
		else if(buttons.get("showMinimap").isClickIn(click)){
			Bombercraft.switchViewOption("renderMiniMap");
		}
		else if(buttons.get("showLights").isClickIn(click)){
			Bombercraft.switchViewOption("renderLights");
		}
		else if(buttons.get("showLightMap").isClickIn(click)){
			Bombercraft.switchViewOption("renderOnlyLightMap");
		}
	}
	
	
	
}
