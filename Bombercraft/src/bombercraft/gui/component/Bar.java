package bombercraft.gui.component;

import java.awt.Color;

import utils.GVector2f;
import bombercraft.game.GameAble;
import core.Interactable;

public abstract class Bar implements Interactable{
	private Color backgroundColor = Color.WHITE;
	private boolean visible = true;
	private GameAble parent;
	private int borderWidth = 3;
	private Color borderColor = Color.DARK_GRAY;
	protected GVector2f size;
	
	protected GVector2f totalSize;
	protected GVector2f totalPos;
	
	public abstract void calcPosition();
	
	public Bar(GameAble parent, GVector2f size) {
		this.parent = parent;
		this.size = size;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public GameAble getParent() {
		return parent;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public Color getBorderColor() {
		return borderColor;
	}
	
}
