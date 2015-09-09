package bombercraft.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import bombercraft.Config;
import bombercraft.game.GameAble;
import core.Interactable;

public class GameLogs implements Interactable{
	private GameAble parent;
	
	public GameLogs(GameAble parent){
		this.parent = parent;
	}
	
	@Override
	public void render(Graphics2D g2) {
		ArrayList<String> data = parent.getLogInfos();
		
		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(0, 0,  
						 200 + 5, Config.LOG_DEFAULT_TEXT_SIZE * data.size() + 5, 
						 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);
		
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.white);
		g2.drawRoundRect(0, 0,  
						 200 + 5, Config.LOG_DEFAULT_TEXT_SIZE * data.size() + 5, 
						 Config.DEFAULT_ROUND, Config.DEFAULT_ROUND);

		g2.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC , Config.LOG_DEFAULT_TEXT_SIZE));
		g2.setColor(Color.white);
		
		
		
		for(int i=0 ; i< data.size() ; i++)
			g2.drawString(data.get(i), 6, Config.LOG_DEFAULT_TEXT_SIZE * i + Config.LOG_DEFAULT_TEXT_SIZE);
		
	}
}
