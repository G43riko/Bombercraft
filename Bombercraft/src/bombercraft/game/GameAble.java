package bombercraft.game;
import java.awt.Canvas;
import java.util.ArrayList;

import bombercraft.game.entity.Enemy;
import bombercraft.game.entity.Item;
import bombercraft.game.entity.Visible;
import bombercraft.game.level.Level;
import bombercraft.multiplayer.Communicable;
import core.Interactable;
import utils.GVector2f;

public interface GameAble extends Interactable {
	public void changeZoom(float f);
	
	public Communicable getConnection();
	public Item getItem(String string);
	public Level getLevel();
	public float getZoom();
	public GVector2f getOffset();
	public Canvas getCanvas();
	public boolean isVisible(Visible b);
	
	public boolean hasBomb(String string);
	public boolean hasItem(String string);
	public boolean hasHelper(String string);
	
	public void addHelper(GVector2f position, int type);

	public void addBullet(GVector2f position, int direction, int bulletSpeed, int attack, int bulletDefaultHealt);
	
	public ArrayList<Enemy> getEnemiesAround(GVector2f position, int range);
}
