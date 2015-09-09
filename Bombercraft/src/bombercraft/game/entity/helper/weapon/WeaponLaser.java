package bombercraft.game.entity.helper.weapon;

import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.bullet.Bullet;

public class WeaponLaser extends Weapon{
	private static HashMap<String, String> data = Bombercraft.getData(Helper.WEAPON_LASER);
	
	public WeaponLaser() {
		super(Integer.valueOf(data.get("bulletSpeed")), 
			  Integer.valueOf(data.get("demage")), 
			  Integer.valueOf(data.get("cadence")), 
			  Bullet.BULLET_LASER, 
			  Integer.valueOf(data.get("healt")));
	}

}
