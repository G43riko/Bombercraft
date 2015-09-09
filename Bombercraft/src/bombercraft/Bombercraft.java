package bombercraft;

import java.util.HashMap;

import bombercraft.game.CoreGame;
import utils.Data;
import utils.GVector2f;
import utils.OptionsParser;
import utils.XMLParser;

public class Bombercraft extends CoreGame {
	private static XMLParser				parser			= new XMLParser("data.xml");
	private static HashMap<String, Boolean>	viewOptions		= OptionsParser.loadOnlyBooleanFile("viewOptions.txt");
	private static Data						viewData		= new Data("viewData.txt");
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

	public static int getInt(String value) {
		return viewData.getInt(value);
	}

	public static boolean getBoolean(String value) {
		return viewData.getBoolean(value);
	}

	public static float getFloat(String value) {
		return viewData.getFloat(value);
	}

	public static GVector2f getVector2f(String value) {
		return viewData.getVector2f(value);
	}

	public static String getString(String value) {
		return viewData.getString(value);
	}
}
