package bombercraft.game.entity.helper.weapon;

import java.util.HashMap;

import bombercraft.Bombercraft;
import bombercraft.game.entity.Helper;
import bombercraft.game.entity.helper.bullet.Bullet;
import utils.GVector2f;

public class WeaponBoomerang extends Weapon{
	private static HashMap<String, String> data = Bombercraft.getData(Helper.WEAPON_BOOMERANG);
	
	public WeaponBoomerang() {
		super(Integer.valueOf(data.get("bulletSpeed")), 
				  Integer.valueOf(data.get("demage")), 
				  Integer.valueOf(data.get("cadence")), 
				  Bullet.BULLET_BOOMERANG, 
				  Integer.valueOf(data.get("healt")));
	}
}
