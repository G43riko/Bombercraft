package bombercraft;

import java.util.HashMap;

import bombercraft.game.CoreGame;
import utils.math.GVector2f;
import utils.resources.Data;
import utils.resources.OptionsParser;
import utils.resources.ResourceLoader;
import utils.resources.XMLParser;

public class Bombercraft extends CoreGame {
	private static XMLParser				parser			= ResourceLoader.getXMLParser("data.xml");
	private static HashMap<String, Boolean>	viewOptions		= ResourceLoader.getBooleanOptions("viewOptions.txt");
	
	public static  int						sendMessages	= 0;
	public static  int						recieveMessages	= 0;
	public static  GVector2f				totalMessages	= null;

	public static void switchViewOption(String option) {
		viewOptions.put(option, !viewOptions.get(option));
	}

	public static boolean getViewOption(String option) {
		return viewOptions.get(option);
	}
	public static HashMap<String, String> getData(String type) {
		return parser.getData(type);
	}
}
