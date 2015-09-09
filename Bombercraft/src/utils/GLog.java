package utils;

public class GLog {
	public final static boolean CREATE = false;
	public final static boolean SITE = false;
	public final static boolean SITE_MESSAGES = false;
	public final static boolean PROFILE = true;
	
	public static void write(boolean can, Object o){
		if(can)
			System.out.println(o);
	}
}
