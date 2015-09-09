package bombercraft.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import bombercraft.Bombercraft;
import utils.GLog;
import utils.json.JSONObject;

public class ClientPlayer {
	private Socket socket;
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;
	private String name;
	private int id;
	
	public String getName() {
		return name;
	}

	public ClientPlayer(Socket socket, int id) {
		this.id = id;
		this.socket = socket;
		try {
			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
			GLog.write(GLog.SITE, "S: Profil hr��a " + name + " bol vytvoren�");
		} catch (IOException e) {
			GLog.write(GLog.SITE, "S: Nepodarilo sa vytvori� profil hr��ovy " + name); 
		}
	}
	
	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object read(){
		try {
			Object o = objectReader.readObject();
			return o;
		} catch (ClassNotFoundException | IOException e) {
//			GLog.write(GLog.SITE, "S: Nepodarila sa pre��ta� spr�va pri hr��ovy menom: " + name);
		}
		return null;
	}
	
	public void cleanUp() {
		try {
			socket.close();

			objectReader.close();
			objectWritter.close();
			GLog.write(GLog.SITE, "S: �spe�ne sa zmazal hr��: " + name);
		} catch (IOException e) {
			GLog.write(GLog.SITE, "S: Nepodarilo sa zmaza� hr��a menom: " + name);
		}
	}

	public void write(Serializable o, String type){
		Bombercraft.sendMessages++;
		JSONObject object = new JSONObject();
		object.put("type", type);
		object.put("msg", o.toString());
		try {
			objectWritter.writeObject(object.toString());
			objectWritter.flush();
		} catch (IOException e) {
			GLog.write(GLog.SITE_MESSAGES, "S: Nepodailo sa odosla� spr�vu " + o + " hr��ovy: " + name);
		}
	}

}
