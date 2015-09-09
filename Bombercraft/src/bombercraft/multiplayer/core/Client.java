package bombercraft.multiplayer.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utils.GLog;
import utils.json.JSONObject;
import bombercraft.Bombercraft;
import bombercraft.Config;

public abstract class Client {
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;

	private Socket socket;

	private boolean readerIsRunning = true;
	
	public Client() {
		try {
			socket = new Socket("localhost", Config.PORT);
			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
			GLog.write(GLog.SITE, "C: Client sa pripojil");
		} catch (IOException e) {
			GLog.write(GLog.SITE, "C: Clientovy sa nepodarilo pripojiù");
		}
		
		listen();
	}
	
	public abstract void sendPlayerInfo();

	protected abstract void processMessage(String txt);
	
	private void listen(){
		Thread listenThread = new Thread(new Runnable(){
			public void run() {
				while(readerIsRunning){
					Object o = read();
					if(o != null){
						Bombercraft.recieveMessages++;
						GLog.write(GLog.SITE_MESSAGES, "C: Client prijal spr·vu: " + o);
						processMessage(String.valueOf(o));
					}
						
				}
			}
		});
		listenThread.start();
	}
	
	private Object read(){
		try {
			return objectReader.readObject();	
		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	
	
	public void write(String o, String type){
		JSONObject object = new JSONObject();
		object.put("type", type);
		object.put("msg", o.toString());
		try {
			objectWritter.writeObject(object.toString());
			Bombercraft.sendMessages++;
		} catch (IOException e) {
			GLog.write(GLog.SITE, "C: Nepopdarilo sa odoslaù spr·vu: " + o);
		}
	}
	

	public void cleanUp() {
readerIsRunning = false;
		
		try {
			if(socket != null)
				socket.close();
			
			objectReader.close();
			objectWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
