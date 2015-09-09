package bombercraft.game;
import java.awt.Canvas;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import bombercraft.Profil;
import bombercraft.game.entity.Item;
import bombercraft.game.entity.Visible;
import bombercraft.game.entity.enemy.Enemy;
import bombercraft.game.entity.helper.bullet.Bullet;
import bombercraft.game.level.Level;
import bombercraft.gui.NavBar;
import bombercraft.multiplayer.Communicable;
import core.Interactable;
import utils.GVector2f;

public interface GameAble extends Interactable, Visible{
	public Communicable 			getConnection();
	public Item 					getItem(String string);
	public Level 					getLevel();
	public float 					getZoom();
	public GVector2f 				getOffset();
	public Canvas 					getCanvas();
	public NavBar 					getNavBar();
	public MyPlayer 				getMyPlayer();
	public HashMap<String, Player> 	getPlayers();
	public ArrayList<Enemy> 		getEnemiesAround(GVector2f position, int range);
	public Profil 					getProfil();
	public ArrayList<String> 		getLogInfos();
	public boolean 					isVisible(Visible b);
	
	public boolean hasWall(float i, float j);
	
	public void addHelper(GVector2f selectorSur, int cadenceBonus,GVector2f pos, int demage,  String type, GVector2f target);
	public void addBomb(GVector2f position, int range, int time, int demage);
	public void addBullet(GVector2f position, GVector2f direction, int bulletSpeed, int attack, String bulletType, int bulletDefaultHealt);
	public void addPlayer(String name, String image);
	public void addExplosion(GVector2f position, GVector2f size, Color color, int number);
	public void addEmmiter(GVector2f position, String type);
	public void addEnemy(GVector2f position, String type);
	
	public boolean 	bulletHitEnemy(Bullet bullet);
	public void 	calcPosition();
	public void 	newGame();
	public void 	resetGame();
	public String 	toJSON();
	public void 	changeZoom(float f);
	
}
