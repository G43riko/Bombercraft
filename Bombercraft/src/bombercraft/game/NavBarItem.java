package bombercraft.game;

import java.awt.Image;

import utils.resources.ResourceLoader;

public class NavBarItem {
	private String	id;
	private Image	image;

	public NavBarItem(String name, String id) {
		this.id = id;
		image = ResourceLoader.loadTexture(name + ".png");
	}

	public String getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}
}
