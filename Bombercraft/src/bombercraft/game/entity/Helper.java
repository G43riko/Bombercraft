package bombercraft.game.entity;

import bombercraft.game.GameAble;
import utils.math.GVector2f;

public abstract class Helper extends Entity {
	public final static String TOWER_MACHINE_GUN	= "towerMachineGun";
	public final static String TOWER_LASER 			= "towerLaser";
	public final static String TOWER_ROCKET 		= "towerRocket";
	public final static String TOWER_FLAME_THROWER 	= "towerFlameThrower";
	public final static String TOWER_SNIPER 		= "towerSniper";
	
	public final static String BOMB_NORMAL 			= "bombBasic";
	public final static String BOMB_ATOM 			= "bombAtom";
	public final static String BOMB_NANO 			= "bombNano";
	public final static String BOMB_FIRE			= "bombFire";
	public final static String BOMB_FREEZE 			= "bombFreeze";
	public final static String BOMB_SLIME	 		= "bombSlime";
	public final static String BOMB_MINE	 		= "bombMine";
	
	public final static String WEAPON_BASIC			= "weaponBasic";
	public final static String WEAPON_LASER			= "weaponLaser";
	public final static String WEAPON_GUN			= "weaponGun";
	public final static String WEAPON_ROCKET		= "weaponRocket";
	public final static String WEAPON_GRANADE		= "weaponGranade";
	public final static String WEAPON_BOW			= "weaponBow";
	public final static String WEAPON_SHOTGUN		= "weaponShotgun";
	public final static String WEAPON_LIGHTNING		= "weaponLight";
	public final static String WEAPON_STICK			= "weaponStick";
	public final static String WEAPON_BOOMERANG		= "weaponBoomerang";
	
	public final static String OTHER_ADDUCTOR		= "adductor";
	public final static String OTHER_RESPAWNER		= "respawner";
	
	public final static String SHOVEL  	 			= "showel";
	
	public Helper(GVector2f position, GameAble parent) {
		super(position, parent);
	}

}
