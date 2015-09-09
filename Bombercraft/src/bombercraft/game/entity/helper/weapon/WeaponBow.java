package bombercraft.game.entity.helper.weapon;

import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.bullet.Bullet;

public class WeaponBow extends Weapon{
	private static HashMap<String, String> data = Bombercraft.getData(Helper.WEAPON_BOW);
	
	public WeaponBow() {
		super(Integer.valueOf(data.get("bulletSpeed")), 
			  Integer.valueOf(data.get("demage")), 
			  Integer.valueOf(data.get("cadence")), 
			  Bullet.BULLET_ARROW, 
			  Integer.valueOf(data.get("healt")));
	}
	
}
