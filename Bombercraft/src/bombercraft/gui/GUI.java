package bombercraft.gui;

import java.awt.Graphics2D;

import core.Input;
import core.Interactable;
import bombercraft.Bombercraft;
import bombercraft.game.Game;
import bombercraft.game.level.Minimap;

public class GUI implements Interactable{

	private Minimap minimap;
	private SideBar sideBar;
	private NavBar navBar;
	private GameLogs logs;
	
	public GUI(Game game){
		navBar = new NavBar(game);
		sideBar = new SideBar(game);
		logs = new GameLogs(game);
		minimap = new Minimap(game.getLevel().getMap());
	}
	
	public void calcPosition(){
		sideBar.calcPosition();
		navBar.calcPosition();
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(navBar.isVisible())
			navBar.render(g2);

		if(sideBar.isVisible())
			sideBar.render(g2);
		
		if(Bombercraft.getViewOption("renderMiniMap"))
			minimap.render(g2);
		
		if(Bombercraft.getViewOption("renderLogs"))
			logs.render(g2);
	}
	
	@Override
	public void input() {
		if(Input.isKeyDown(Input.KEY_Q))
			sideBar.setVisible(sideBar.isVisible() == false);
		
		if(navBar.isVisible())
			navBar.input();
	}
	
	@Override
	public void update(float delta) {
		if(sideBar.isVisible())
			sideBar.update(delta);
	}

	public Minimap getMinimap() {
		return minimap;
	}

	public SideBar getSideBar() {
		return sideBar;
	}

	public NavBar getNavBar() {
		return navBar;
	}

	public GameLogs getLogs() {
		return logs;
	}
}
