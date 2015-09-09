package bombercraft.game.entity.helper.weapon;

import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.bullet.Bullet;

public class WeaponStick extends Weapon{
	private static HashMap<String, String> data = Bombercraft.getData(Helper.WEAPON_STICK);
	
	public WeaponStick() {
		super(Integer.valueOf(data.get("bulletSpeed")), 
				  Integer.valueOf(data.get("demage")), 
				  Integer.valueOf(data.get("cadence")), 
				  Bullet.BULLET_MAGIC, 
				  Integer.valueOf(data.get("healt")));
	}

}
