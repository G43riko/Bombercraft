package bombercraft;

import utils.Data;
import utils.GLog;

/*
 * kolko bolo minut hrania kolko bolo nov�ch hier kolko kr�t bol profil na��tan�
 * kedy bol naposledy ulo�en�
 */
public class Profil {
	private String	name			= "playerName";
	private String	avatar			= "player1.png";
	private float	msOfPlaying		= 0;
	private int		newGames		= 0;
	private int		profilLoaded	= 0;
	private float	lastLogin		= System.currentTimeMillis();

	// CONTRUCTORS

	public Profil(String profilName) {
		Data d = new Data("profiles/" + profilName + ".txt");

		name = d.getString("name");
		avatar = d.getString("avatar");
		msOfPlaying = d.getFloat("msOfPlaying");
		newGames = d.getInt("newGames");
		profilLoaded = d.getInt("profilLoaded");

		lastLogin = System.currentTimeMillis();
		profilLoaded++;

		GLog.write(GLog.PROFILE, "na��tal sa profil: " + this);
	}

	// OTHERS

	public static void saveProfil(Profil profil) {
		profil.msOfPlaying += (System.currentTimeMillis() - profil.lastLogin);

		StringBuilder profile = new StringBuilder();
		profile.append("name = " + profil.name + System.getProperty("line.separator"));
		profile.append("avatar = " + profil.avatar + System.getProperty("line.separator"));
		profile.append("msOfPlaying = " + profil.msOfPlaying + System.getProperty("line.separator"));
		profile.append("lastLogin = " + profil.lastLogin + System.getProperty("line.separator"));
		profile.append("newGames = " + profil.newGames + System.getProperty("line.separator"));
		profile.append("profilLoaded = " + profil.profilLoaded + System.getProperty("line.separator"));

		GLog.write(GLog.PROFILE, "ulo�il sa profil: " + profile);
	}

	// OVERRIDES

	@Override
	public String toString() {
		return "name: " + name + ", avatar: " + avatar;
	}

	// GETTERS

	public int getNewGames() {return newGames;}
	public int getProfilLoaded() {return profilLoaded;}
	public float getLastLogin() {return lastLogin;}
	public float getMsOfPlaying() {return msOfPlaying;}
	public String getName() {return name;}
	public String getAvatar() {return avatar;}
}
