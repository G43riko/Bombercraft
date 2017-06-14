package bombercraft;

import utils.GLog;
import utils.resources.Data;

/*
 * kolko bolo minut hrania kolko bolo novych hier kolko krat bol profil nacitany
 * kedy bol naposledy ulozeny
 */
public class Profil {
	private final static String NAME 			= "name";
	private final static String AVATAR 			= "avatar";
	private final static String NEW_GAMES 		= "newGames";
	private final static String LAST_LOGIN 		= "lastLogin";
	private final static String PLAYING_TIME 	= "msOfPlaying";
	private final static String PROFIL_LOADED	= "profilLoaded";
	
	private String	name			= "playerName";
	private String	avatar			= "player1.png";
	private float	msOfPlaying		= 0;
	private int		newGames		= 0;
	private int		profilLoaded	= 0;
	private float	lastLogin		= System.currentTimeMillis();

	// CONTRUCTORS

	public Profil(String profilName) {
		Data d = new Data("profiles/" + profilName + ".txt");

		name 			= d.getString(NAME);
		avatar 			= d.getString(AVATAR);
		newGames 		= d.getInt(NEW_GAMES);
		profilLoaded 	= d.getInt(PROFIL_LOADED);
		msOfPlaying 	= d.getFloat(PLAYING_TIME);

		lastLogin = System.currentTimeMillis();
		profilLoaded++;

		GLog.write(GLog.PROFILE, "nacital sa profil: " + this);
	}

	// OTHERS

	public static void saveProfil(Profil profil) {
		profil.msOfPlaying += (System.currentTimeMillis() - profil.lastLogin);

		StringBuilder profile = new StringBuilder();
		profile.append("name = " 			+ profil.name 			+ System.lineSeparator());
		profile.append("avatar = " 			+ profil.avatar 		+ System.lineSeparator());
		profile.append("msOfPlaying = " 	+ profil.msOfPlaying 	+ System.lineSeparator());
		profile.append("lastLogin = " 		+ profil.lastLogin 		+ System.lineSeparator());
		profile.append("newGames = " 		+ profil.newGames 		+ System.lineSeparator());
		profile.append("profilLoaded = " 	+ profil.profilLoaded 	+ System.lineSeparator());

		GLog.write(GLog.PROFILE, "ulozil sa profil: " + profile);
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
