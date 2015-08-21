package bombercraft.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import bombercraft.game.GameAble;
import bombercraft.gui.component.Bar;
import utils.GVector2f;

public class NavBar extends Bar{
	private int num = 15;
	private int bottomOffset = 20;
	
	
	public NavBar(GameAble parent) {
		super(parent, new GVector2f(50, 50));
		calcPosition();
	}
	
	public void calcPosition(){
		totalSize = size.mul(new GVector2f(num, 1));
		totalPos = new GVector2f((getParent().getCanvas().getWidth() - totalSize.getX()) / 2, 
								  getParent().getCanvas().getHeight() - bottomOffset - totalSize.getY());
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(getBackgroundColor());
		g2.fillRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi());
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		
		for(int i=0 ; i<num ; i++){
			g2.drawRect(totalPos.getXi() + size.getXi() * i,  totalPos.getYi(), size.getXi(), size.getYi());
		}
	}
}
