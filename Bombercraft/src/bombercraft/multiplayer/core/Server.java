package bombercraft.multiplayer.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import utils.GLog;
import utils.IDGenerator;
import utils.Utils;
import bombercraft.Bombercraft;
import bombercraft.Config;

public abstract class Server {
	public static final String LEVEL_INFO      = "LEVEL INFO";
	public static final String PLAYER_MOVE     = "PLAYER MOVE";
	public static final String PLAYER_NAME     = "PLAYER NAME";
	public static final String PLAYER_IS_READY = "PLAYER IS READY";
	public static final String PUT_HELPER      = "PUT HELPER";
	public static final String HIT_BLOCK       = "BLOCK CHANGE";
	public static final String PUT_BOMB 	   = "PUT BOMB";
	
	private ServerSocket serverSocket;
	private boolean readerIsRunning = true;
	private boolean accepterIsRunning = true;
	private Thread acceptThread;
	private Thread listenThread;
	private HashMap<String, ClientPlayer> clients = new HashMap<String, ClientPlayer>();
	
	public Server(){
		try {
			serverSocket = new ServerSocket(Config.PORT);
			GLog.write(GLog.SITE, "S: Server sa vytvoril");
		} catch (IOException e) {
			GLog.write(GLog.SITE, "S: Server sa nepodarilo vytvoriť");
		}
		
		listen();
		accept();
	}
	
	protected abstract void processMessage(String txt, ClientPlayer client);
	
	private void listen() {
		listenThread = new Thread(new Runnable(){
			public void run(){
				while(readerIsRunning){
					new HashMap<String, ClientPlayer>(clients).entrySet()
															  .stream()
															  .map(a -> a.getValue())
															  .forEach(a -> read(a));

					Utils.sleep(1);						 
				}
			}
		});
		listenThread.start();
	}
	
	private void read(ClientPlayer a){
		Object o = a.read();
		if(o == null)
			return;
		Bombercraft.recieveMessages++;
		if(o instanceof String)
			processMessage(String.valueOf(o), a);
		GLog.write(GLog.SITE_MESSAGES, "S: server prijal správu " + o);
	}
	
	protected abstract String getLevelInfo();
	
	private void accept(){
		acceptThread = new Thread(new Runnable(){
			public void run(){
				while(accepterIsRunning){
					try {
						Socket client = serverSocket.accept();
						ClientPlayer c = new ClientPlayer(client, IDGenerator.getId());
						clients.put(c.getId() + "", c);
						c.write(getLevelInfo(), LEVEL_INFO);
						
						GLog.write(GLog.SITE, "S: Client sa pripojil");
						
					} catch (IOException e) {
						GLog.write(GLog.SITE, "S: Server bol ukončený");
					}
				}
			}
		});
		acceptThread.start();
	}
	
	protected void renameClient(String oldName, String newName){
		ClientPlayer temp = clients.get(oldName); 
		temp.setName(newName);
		clients.remove(oldName);
		clients.put(newName, temp);
	}
	
	public void write(String o, String type){
		new HashMap<String, ClientPlayer>(clients).entrySet()
												  .stream()
												  .forEach(a -> a.getValue().write(o, type));
	}
	

	protected void writeExcept(String o, String type, ClientPlayer client) {
		new HashMap<String, ClientPlayer>(clients).entrySet()
		  										  .stream()
		  										  .filter(a -> a.getValue().getId() != client.getId())
		  										  .forEach(a -> a.getValue().write(o, type));
	}
	
	public void cleanUp() {
		readerIsRunning = false;
		accepterIsRunning = false;
		
		clients.entrySet()
		       .stream()
			   .forEach(a -> a.getValue().cleanUp());
		
		clients.clear();
		
		try {
			if(serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			System.out.println("nepodarilo sa zavrie server");
		}
		serverSocket = null;
	}
	
	public int getNumberOfClients(){
		return clients.size();
	}
}
