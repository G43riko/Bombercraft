package bombercraft.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import bombercraft.Config;
import bombercraft.game.GameAble;
import bombercraft.game.NavBarItem;
import bombercraft.game.entity.Helper;
import bombercraft.game.level.Block;
import core.Input;
import utils.math.GColision;
import utils.math.GVector2f;

public class NavBar extends Bar{
	private static HashMap<String, NavBarItem> allItems = new HashMap<String, NavBarItem>();
	static{
		allItems.put(Helper.TOWER_LASER, 	   	new NavBarItem("icon_laser_tower",  		Helper.TOWER_LASER));
		allItems.put(Helper.TOWER_MACHINE_GUN,	new NavBarItem("icon_machine_gun_tower", 	Helper.TOWER_MACHINE_GUN));
		allItems.put(Helper.SHOVEL, 		    new NavBarItem("icon_shovel",  				Helper.SHOVEL));
		allItems.put(Helper.WEAPON_LASER, 	   	new NavBarItem("icon_weapon_laser", 		Helper.WEAPON_LASER));
		allItems.put(Helper.WEAPON_BOW, 	   	new NavBarItem("icon_weapon_bow", 			Helper.WEAPON_BOW));
		allItems.put(Helper.BOMB_NORMAL, 	    new NavBarItem("icon_bomb", 				Helper.BOMB_NORMAL));
		allItems.put(Helper.WEAPON_LIGHTNING,  	new NavBarItem("icon_light", 				Helper.WEAPON_LIGHTNING));
		allItems.put(Helper.WEAPON_STICK, 	   	new NavBarItem("icon_stick", 				Helper.WEAPON_STICK));
		allItems.put(Helper.WEAPON_BOOMERANG,  	new NavBarItem("icon_bumerang", 			Helper.WEAPON_BOOMERANG));
		allItems.put(Helper.OTHER_RESPAWNER,   	new NavBarItem("respawner", 				Helper.OTHER_RESPAWNER));
		allItems.put(Helper.OTHER_ADDUCTOR,		new NavBarItem("adductor", 					Helper.OTHER_ADDUCTOR));
		
		allItems.put(Block.IRON, new NavBarItem("block_iron", Block.IRON));
		allItems.put(Block.WOOD, new NavBarItem("block_wood", Block.WOOD));
	}
	
	private HashMap<Integer, NavBarItem> items = new HashMap<Integer, NavBarItem>(); 
	private int selectedItem = 0;
	
	public NavBar(GameAble parent) {
		super(parent, Config.NAVBAR_DEFAULT_SIZE);
		
		addItem(0, Helper.TOWER_LASER);
		addItem(1, Helper.TOWER_MACHINE_GUN);
		addItem(2, Helper.SHOVEL);
		addItem(3, Helper.WEAPON_LASER);
		addItem(4, Helper.WEAPON_BOW);
		addItem(5, Helper.BOMB_NORMAL);
		addItem(6, Helper.WEAPON_LIGHTNING);
		addItem(7, Helper.WEAPON_STICK);
		addItem(8, Helper.WEAPON_BOOMERANG);
		
		addItem(9, Block.IRON);
		addItem(10, Block.WOOD);
		
		addItem(11, Helper.OTHER_RESPAWNER);
		addItem(12, Helper.OTHER_ADDUCTOR);
		
		calcPosition();
	}
	
	public void removeItem(int index){
		items.remove(index);
	}
	
	public void addItem(int index, String itemId){
		items.put(index, allItems.get(itemId));
	}
	
	public void moveItem(int from, int to){
		items.put(to, items.remove(from));
	}
	
	public NavBarItem getSelecedItem(){
		return items.get(selectedItem);
	}
	
	public int getSelecedIndex(){
		return selectedItem;
	}
	
	public void calcPosition(){
		totalSize = size.mul(new GVector2f(Config.NAVBAR_DEFAULT_NUMBER_OF_BLOCKS, 1));
		totalPos = new GVector2f((getParent().getCanvas().getWidth() - totalSize.getX()) / 2, 
								  getParent().getCanvas().getHeight() - Config.NAVBAR_DEFAULT_BOTTOM_OFFSET - totalSize.getY());
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(getBackgroundColor());
		g2.fillRect(totalPos.getXi(), totalPos.getYi(), totalSize.getXi(), totalSize.getYi());
		
		g2.setStroke(new BasicStroke(getBorderWidth()));
		g2.setColor(getBorderColor());
		
		for(int i=0 ; i<Config.NAVBAR_DEFAULT_NUMBER_OF_BLOCKS ; i++){
			if(items.containsKey(i))
				g2.drawImage(items.get(i).getImage(), totalPos.getXi() + size.getXi() * i,  totalPos.getYi(), size.getXi(), size.getYi(), null);
			g2.drawRect(totalPos.getXi() + size.getXi() * i,  totalPos.getYi(), size.getXi(), size.getYi());
		}
		
		g2.setColor(new Color(255, 0, 0, 200));
		g2.drawRect(totalPos.getXi() + size.getXi() * selectedItem,  totalPos.getYi(), size.getXi(), size.getYi());
	}

	@Override
	public void doAct(GVector2f click) {
		if(GColision.pointRectCollision(totalPos, totalSize, Input.getMousePosition())){
			selectedItem = Input.getMousePosition().sub(totalPos).div(size).getXi();
		}
	}

	@Override
	public GVector2f getPosition() {
		return totalPos;
	}

	@Override
	public GVector2f getSize() {
		return size;
	}
}
