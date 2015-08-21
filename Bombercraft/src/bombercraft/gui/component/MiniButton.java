package bombercraft.gui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;

import utils.GColision;
import utils.GVector2f;
import bombercraft.game.entity.Visible;
import core.Input;
import core.Interactable;

public class MiniButton implements Interactable{
	private static HashMap<Visible, Integer> buttons = new HashMap<Visible, Integer>(); 
	private String text;
	private Color textColor = Color.WHITE;
	private int textSize = 20;
	private int textOffset = 4;
	private Color backgroundColor = Color.DARK_GRAY;
	private Color borderColor = Color.LIGHT_GRAY;
	private int borderWidth = 1;
	private Visible parentBox;
	private int top;
	private int offset = 2;
	private int height = 20;
	private GVector2f position;
	private GVector2f size;
	private int round = 10;
	
	private boolean hover;
	private Color hoverColor = Color.GRAY;
	
	public MiniButton(Visible parentBox, String text, int height){
		this.parentBox = parentBox;
		this.height = height;
		this.text = text;
		
		
		if(!buttons.containsKey(parentBox))
			buttons.put(parentBox, 0);
		
		top = buttons.get(parentBox);
		buttons.put(parentBox, top + height);
	
		calcPosition();
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(hover)
			g2.setColor(hoverColor);
		else
			g2.setColor(backgroundColor);
		
		g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke(borderWidth));
		g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		
		g2.setColor(textColor);
		g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , textSize));
		g2.drawString(text, position.getX() + textOffset, position.getY() + textSize );
	}
	
	protected void clickIn(){
		
	};
	
	public boolean isClickIn(GVector2f click){
		boolean result = GColision.pointRectCollision(position, size, click);
		
		if(result)
			clickIn();
		
		return result; 
	}
	
	@Override
	public void update(float delta) {
		hover = GColision.pointRectCollision(position, size, Input.getMousePosition()); 
	}
	
	public void calcPosition(){
		position = parentBox.getPosition().add(new GVector2f(offset, offset + top));
		size = new GVector2f(parentBox.getSize().getXi() - 2 * offset, height - offset);
	}
}
