package core;

import utils.GVector2f;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import bombercraft.gui.Clicable;
import bombercraft.gui.Menu;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	public final static int KEY_W = 87;
	public final static int KEY_A = 65;
	public final static int KEY_S = 83;
	public final static int KEY_D = 68;
	public final static int KEY_Q = 81;
	public final static int KEY_E = 69;
	public final static int KEY_F = 70;
	public final static int KEY_UP = 38;
	public final static int KEY_LEFT = 37;
	public final static int KEY_DOWN = 40;
	public final static int KEY_RIGHT = 39;
	public final static int KEY_LCONTROL = 17;
	public final static int KEY_ENTER = 10;
	public final static int KEY_ESCAPE = 27;
	public final static int KEY_LALT = 18;
	public final static int KEY_LSHIFT = 16;
	public final static int KEY_SPACE = 32;
	
	public final static int BUTTON_LEFT = 1;
	public final static int BUTTON_RIGHT = 3;
	private HashMap<Integer,Boolean> mouses = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> keys = new HashMap<Integer,Boolean>();
	private static Input input;
	
	public Input() {
		input = this;
	}
	
	private static ArrayList<Clicable> menus = new ArrayList<Clicable>();
	
	private static GVector2f mousePosition = new GVector2f();
	
	public static GVector2f getMousePosition(){
		return mousePosition;
	}
	
	public static boolean isKeyDown(int key){
		if(input.keys.containsKey(key)){
			return input.keys.get(key);
		}
		return false;
	}
	
	public static void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePosition = new GVector2f(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition = new GVector2f(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		new ArrayList<Clicable>(menus).stream().forEach(a -> a.doAct(new GVector2f(e.getX(), e.getY())));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouses.put(e.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouses.put(e.getButton(), false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void addMenu(Clicable menu){
		menus.add(menu);
	}

}
