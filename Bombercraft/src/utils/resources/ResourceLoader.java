package utils.resources;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import utils.AudioPlayer;

public class ResourceLoader {
	private static HashMap<String, Image> loadedTextures = new HashMap<String, Image>();
	private static SoundManager soundManager = new SoundManager();
	
	public static InputStream load(String fileName){
		InputStream input = ResourceLoader.class.getResourceAsStream(fileName);
		if(input == null){
			input = ResourceLoader.class.getResourceAsStream("/" + fileName);
		}
		return input;
	}
	
	public static Image loadTexture(String fileName){
		if(loadedTextures.containsKey(fileName))
			return loadedTextures.get(fileName);
		else{
			try {
				loadedTextures.put(fileName, ImageIO.read(load("texture/"+fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loadedTextures.get(fileName);
		}
	}
	
	public static AudioPlayer getSound(String name){
		return soundManager.getAudio(name);
	}
	public static AudioPlayer loadSound(String name){
		return soundManager.loadAudio(name);
	}
	public static AudioPlayer checkAndGetSound(String name){
		return soundManager.checkAndGetAudio(name);
	}

	public static HashMap<String, Boolean> getBooleanOptions(String fileName){
		return OptionsParser.loadOnlyBooleanFile(fileName);
	}
	public static XMLParser getXMLParser(String fileName){
		return new XMLParser("data.xml");
	}
}
