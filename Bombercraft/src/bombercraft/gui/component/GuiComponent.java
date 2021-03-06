package bombercraft.gui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.game.entity.Visible;
import core.Input;
import core.Interactable;
import utils.math.GColision;
import utils.math.GVector2f;

public abstract class GuiComponent implements Interactable, Visible{
	protected final static int 		SIDEBAR_DEFAULT_BUTTON_HEIGHT = 30;
	protected final static float 	CENTER_ALIGN = -1.111111f;
	
	public static HashMap<Visible, Integer> buttons = new HashMap<Visible, Integer>();
	private Visible 						parent;
	protected GVector2f 					offset = new GVector2f();
	protected GVector2f 					textOffset = new GVector2f();
	protected GVector2f 					position;
	protected GVector2f 					size;
	protected int 							textSize = 20;
	protected int 							round = 0;
	protected int 							borderWidth = 0;
	protected int 							topCousePrevButtons = 0;
	protected boolean 						hover;
	protected boolean 						disable;
	protected boolean 						value = true;
	protected String 						text = "";
	protected Color 						backgroundColor = Color.WHITE;
	protected Color 						borderColor = Color.black;
	protected Color 						textColor = Color.black;
	protected Color 						hoverColor = Color.lightGray;
	protected Color 						diableColor = Color.DARK_GRAY;
	protected String 						font = "Garamond";
	
	public GuiComponent(Visible parent) {
		this.parent = parent;
		position = parent.getPosition();
		size = parent.getSize();
	}
	
	protected abstract void init();
	
	protected void clickIn(){
		value = !value;
	};
	
	public boolean isClickIn(GVector2f click){
		if(disable)
			return false;
			
		boolean result = GColision.pointRectCollision(position, size, click);
		
		if(result)
			clickIn();
		
		return result; 
	}
	
	@Override
	public void update(float delta) {
		if(disable)
			return;
		
		hover = GColision.pointRectCollision(position, size, Input.getMousePosition()); 
	}
	
	public void calcPosition() {
		position = getParent().getPosition().add(offset.add(new GVector2f(0, topCousePrevButtons)));
		size = new GVector2f(getParent().getSize().getXi() - 2 * offset.getX(), size.getY() - offset.getY() );
	}

	@Override
	public void render(Graphics2D g2) {
		if(disable)
			g2.setColor(diableColor);
		else if(hover)
			g2.setColor(hoverColor);
		else
			g2.setColor(backgroundColor);
		
		g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		
		if(borderWidth > 0){
			g2.setColor(borderColor);
			g2.setStroke(new BasicStroke(borderWidth));
			g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		}
		
		g2.setColor(textColor);
		g2.setFont(new Font(font, Font.BOLD | Font.ITALIC , textSize));
		if(text != null && text != "" && textSize > 0){
			if(textOffset.getX() == CENTER_ALIGN)
				g2.drawString(text, 
							  position.getX() + (size.getX() - g2.getFontMetrics().stringWidth(text)) / 2, 
							  position.getY() + textSize + textOffset.getY());
			else
				g2.drawString(text, 
							  position.getX() + textOffset.getX(), 
							  position.getY() + textSize + textOffset.getY());
		}
	}

	
	public void setDisable(boolean disable) {this.disable = disable;}

	public Visible 		getParent() {return parent;}
	public GVector2f 	getPosition() {return position;}
	public GVector2f 	getSize() {return size;}
}
