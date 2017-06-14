package bombercraft.game.entity.helper.weapon;

import java.util.HashMap;

import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.Shootable;
import utils.math.GVector2f;

public abstract class Weapon implements Shootable{
	private static HashMap<String, Weapon> weapons = new HashMap<String, Weapon>(); 
	private int 	bulletSpeed;
	private int 	demage;
	private int 	cadence;
	private int 	bulletHealt;
	private String 	bulletType;
	private long 	lastShot;
	static{
		weapons.put(Helper.WEAPON_LASER, new WeaponLaser());
		weapons.put(Helper.WEAPON_BOW, new WeaponBow());
		weapons.put(Helper.WEAPON_STICK, new WeaponStick());
		weapons.put(Helper.WEAPON_BOOMERANG, new WeaponBoomerang());
	}
	
	//CONSTRUCTORS
	
	public Weapon(int bulletSpeed, int demage, int cadence, String bulletType, int bulletHealt) {
		this.bulletSpeed = bulletSpeed;
		this.bulletHealt = bulletHealt;
		this.bulletType = bulletType;
		this.cadence = cadence;
		this.demage = demage;
		lastShot = System.currentTimeMillis() - cadence;
	}

	//OTHERS
	
	public boolean canShot() {
		return canShot(0);
		}
	
	public boolean canShot(int bonus) {
		return System.currentTimeMillis() - lastShot > cadence - bonus;
	}

	//GETTERS

	public static Weapon 	getWeapon(String type){return weapons.get(type);}
	public int 				getDemage() {return demage;}
	public int 				getBulletSpeed() {return bulletSpeed;}
	public int 				getBulletHealt() {return bulletHealt;}
	public GVector2f 		getPosition() {return new GVector2f();}
	public GVector2f 		getDirection() {return new GVector2f();}
	public String 			getBulletType() {return bulletType;}
	
	//SETTERS
	
	
	public void setLastShot() {lastShot = System.currentTimeMillis();}
}
