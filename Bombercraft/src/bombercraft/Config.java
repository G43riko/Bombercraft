package bombercraft;

import java.awt.Color;

import utils.math.GVector2f;

public class Config {
	public final static Color	BACKGROUND_COLOR		= Color.WHITE;
	public final static boolean	ALLOW_ZOOMING			= true;
	public final static int		PORT					= 4444;
	public final static int		DEFAULT_ROUND			= 20;
	public final static int		DEFAULT_ZOOM			= 1;

	public static final Color		MINIMAP_DEFAULT_BACKGROUND_COLOR	= Color.WHITE;
	public static final GVector2f	MINIMAP_DEFAULT_SIZE				= new GVector2f(200, 200);
	public static final GVector2f	MINIMAP_DEFAULT_POSITION			= new GVector2f(10, 10);
	public static final int			MINIMAP_DEFAULT_BORDER_WIDTH		= 1;
	

	public final static int		LOG_DEFAULT_TEXT_SIZE	= 20;

	public final static int			BLOCK_DEFAULT_BORDER	= 3;
	public final static GVector2f	BLOCK_DEFAULT_SIZE		= new GVector2f(40, 40);

	public final static GVector2f	WINDOW_DEFAULT_SIZE			= new GVector2f(800, 600);
	public final static int			WINDOW_DEFAULT_FPS			= 60;
	public final static int			WINDOW_DEFAULT_UPS			= 60;
	public final static String		WINDOW_DEFAULT_TITLE		= "Bombercraft";
	public final static boolean		WINDOW_DEFAULT_RENDER_TEXT	= false;

	public static final int	ENEMY_DEFAULT_OFFSET		= 5;
	public static final int	ENEMY_DEFAULT_ROUND			= 10;
	public final static int	ENEMY_DEFAULT_SPEED			= 1;
	public final static int	ENEMY_DEFAULT_BULLET_SPEED	= 4;
	public final static int	ENEMY_DEFAULT_CADENCE		= 200;
	public final static int	ENEMY_DEFAULT_ATTACK		= 1;
	public final static int	ENEMY_DEFAULT_HEALT			= 1;

	public static final int	BULLET_DEFAULT_HEALT	= 1;
	public static final int	BULLET_DEFAULT_OFFSET	= 1;
	public static final int	BULLET_DEFAULT_ROUND	= 1;

	public static final GVector2f	NAVBAR_DEFAULT_SIZE				= new GVector2f(40, 40);
	public static final int			NAVBAR_DEFAULT_BOTTOM_OFFSET	= 20;
	public static final int			NAVBAR_DEFAULT_NUMBER_OF_BLOCKS	= 19;

	public static final Color	SIDEBAR_DEFAULT_BACKGROUND_COLOR	= new Color(155, 155, 155, 100);
	public static final Color	SIDEBAR_DEFAULT_BORDER_COLOR		= new Color(105, 105, 105, 100);
	public static final int		SIDEBAR_DEFAULT_OFFSET				= 10;
	public static final int		SIDEBAR_DEFAULT_BORDER_WIDTH		= 3;

	public static final int		BOMB_DEFAULT_RANGE			= 2;
	public static final int		BOMB_DEFAULT_TIME			= 2000;
	public static final int		BOMB_DEFAULT_DEMAGE			= 1;
	public static final Color	BOMB_DEFAULT_RANGE_COLOR	= new Color(255, 255, 0, 100);

	public static final Color	LOADING_DEFAULT_BACKGROUND_COLOR	= Color.red;
	public static final int		LOADING_DEFAULT_FONT				= 40;
	public static final Color	LOADING_DEFAULT_TEXT_COLOR			= Color.blue;

	public static final Color	PLAYER_DEFAULT_SELECTOR_COLOR	= Color.MAGENTA;
	public static final int		PLAYER_DEFAULT_SELECTOR_WIDTH	= 2;
}
