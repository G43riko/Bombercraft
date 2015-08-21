package utils;

public class Utils {
	public static  GVector2f getMoveFromDir(int dir){
		switch(dir){
			case 0:
				return new GVector2f(00, -1);
			case 1:
				return new GVector2f(01, 00);
			case 2:
				return new GVector2f(00, 01);
			case 3:
				return new GVector2f(-1, 00);
			default:
				return new GVector2f();
		}
	}

	public static GVector2f getNormalMoveFromDir(int dir) {
		switch(dir){
			case 0:
				return new GVector2f(-1, 0);
			case 1:
				return new GVector2f(01, 00);
			case 2:
				return new GVector2f(00, -1);
			case 3:
				return new GVector2f(00, 01);
			default:
				return new GVector2f();
		}
	}
}