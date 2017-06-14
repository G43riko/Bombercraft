package bombercraft.gui;

import java.awt.Color;

import bombercraft.game.GameAble;
import bombercraft.game.entity.Visible;
import bombercraft.gui.component.Clicable;
import core.Input;
import core.Interactable;
import utils.math.GVector2f;

public abstract class Bar implements Interactable, Clicable, Visible{
	private Color backgroundColor 	= Color.WHITE;
	private Color borderColor 		= Color.DARK_GRAY;
	
	protected GVector2f size;
	protected GVector2f totalSize;
	protected GVector2f totalPos;
	
	private boolean visible = true;
	private GameAble parent;
	private int borderWidth = 3;
	
	//CONTRUCTORS
	
	public Bar(GameAble parent, GVector2f size) {
		this.parent = parent;
		this.size = size;
		Input.addMenu(this);
	}

	//ABSTRACT
	
	public abstract void calcPosition();
	
	//SETTERS

	public void setVisible(boolean visible) {this.visible = visible;}
	public void setBorderWidth(int borderWidth) {this.borderWidth = borderWidth;}
	public void setBorderColor(Color borderColor) {this.borderColor = borderColor;}
	public void setBackgroundColor(Color backgroundColor) {this.backgroundColor = backgroundColor;}
	
	//GETTERS
	
	public boolean isVisible() {return visible;}
	public GameAble getParent() {return parent;}
	public int getBorderWidth() {return borderWidth;}
	public Color getBorderColor() {return borderColor;}
	public Color getBackgroundColor() {return backgroundColor;}

}
