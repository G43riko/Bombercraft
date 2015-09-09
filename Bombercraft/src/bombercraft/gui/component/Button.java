package bombercraft.gui.component;

import java.awt.Color;

import bombercraft.Config;
import bombercraft.game.entity.Visible;
import utils.GVector2f;

public class Button extends GuiComponent{
	public Button(Visible parent, String text) {
		super(parent);
		this.text = text;
		init();
		
		if(!buttons.containsKey(parent))
			buttons.put(parent, 0);

		topCousePrevButtons = buttons.get(parent);
		buttons.put(parent, topCousePrevButtons + size.getYi() + offset.getYi());
		
		calcPosition();
		textOffset = new GVector2f(CENTER_ALIGN, 0);
	}
	protected void init(){
		diableColor = Color.LIGHT_GRAY;
		backgroundColor = Color.GREEN;
		hoverColor = Color.DARK_GRAY;
		borderColor = Color.black;
		borderWidth = 5;
		textColor = Color.BLACK;
		textOffset = new GVector2f();
		offset = new GVector2f(40, 5);
		textSize = 36;
		round = Config.DEFAULT_ROUND;
		font = "Monospaced";
		size = new GVector2f(600, 50);
	}

	

}
