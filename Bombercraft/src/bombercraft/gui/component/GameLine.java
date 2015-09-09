package bombercraft.gui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import bombercraft.game.entity.Visible;
import utils.GVector2f;

public class GameLine extends GuiComponent{
	private String game = "Gabova Hra";
	private String players = "1";
	private String maxPlayer = "4";
	private String ip = "127.0.0.1";
	private String time = "3:49";
	private String map = "Killer zone";
	

	public GameLine(Visible parent, String game,String players, String maxPlayer, String ip, String time, String map) {
		super(parent);
		this.maxPlayer = maxPlayer;
		this.players = players;
		this.game = game;
		this.time = time;
		this.map = map;
		this.ip = ip;
		init();
		calcPosition();
	}
	public GameLine(Visible parent) {
		this(parent, "Názov hry", "Poèet hráèov", "Maximum hráèov", "IP adresa", "Dåžka hry", "Názov mapy");
	}
	
	protected void init() {
		round = 5;
		borderWidth = 3;
		borderColor = Color.black;
		backgroundColor = Color.GREEN;
		offset = new GVector2f(20, 0);
		size = new GVector2f(200,50);
		text = "test";
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(backgroundColor);
		g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		if(borderWidth > 0){
			g2.setColor(borderColor);
			g2.setStroke(new BasicStroke(borderWidth));
			g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
		}
		g2.setFont(new Font(font,Font.CENTER_BASELINE, 15));
		g2.drawString(game, 	 position.getX() + size.getX() / 6 * 0, position.add(size.div(2)).getY());
		g2.drawString(players,   position.getX() + size.getX() / 6 * 1, position.add(size.div(2)).getY());
		g2.drawString(maxPlayer, position.getX() + size.getX() / 6 * 2, position.add(size.div(2)).getY());
		g2.drawString(ip, 		 position.getX() + size.getX() / 6 * 3, position.add(size.div(2)).getY());
		g2.drawString(time, 	 position.getX() + size.getX() / 6 * 4, position.add(size.div(2)).getY());
		g2.drawString(map, 		 position.getX() + size.getX() / 6 * 5, position.add(size.div(2)).getY());

		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke(borderWidth));
		for(int i=1 ; i<6 ; i++){
			g2.drawLine(position.getXi() + size.getXi() / 6 * i - 10, position.getYi(),
						position.getXi() + size.getXi() / 6 * i - 10, position.add(size).getYi());
		}
	}

}
