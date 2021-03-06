package core;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import bombercraft.Bombercraft;
import bombercraft.Config;
import utils.Utils;
import utils.math.GVector2f;


public abstract class CoreEngine {
	private static boolean RENDER_TIME;
	private static int FPS = 60;
	private static int UPS = 60;
	private boolean running;
	private float actFPS = FPS;
	private float actUPS = UPS;
	private float actLoops = 0;
	private Window window;
	private Input input = new Input();
	private Canvas canvas = new Canvas();
	private Graphics2D g2;
	
	public CoreEngine(){
		this(60, 60, true);
	}
	
	public CoreEngine(int fps, int ups, boolean renderTime){
		defaultInit();
		
		
		RENDER_TIME = renderTime;
		FPS = fps;
		UPS = ups;
	}
	
	public void run(){
		running = true;
		
		mainLoop();
	}
	
	protected void cleanUp() {
		window.dispose();
		window.removeAll();
		Input.cleanUp();
		
	}

	public void stop(){
		running = false;
	}
	
	private void mainLoop(){
		long initialTime = System.nanoTime();
		final double timeU = 1000000000 / UPS;
		final double timeF = 1000000000 / FPS;
		double deltaU = 0, deltaF = 0;
		int frames = 0, ticks = 0, loops = 0;
		long timer = System.currentTimeMillis();

		    while (running) {
		        long currentTime = System.nanoTime();
		        deltaU += (currentTime - initialTime) / timeU;
		        deltaF += (currentTime - initialTime) / timeF;
		        initialTime = currentTime;
		        
		        loops++;
		        
		        if (deltaU >= 1) {
		            defaultInput();
		            defaultUpdate((float)Math.min(deltaU, 2));
		            ticks++;
		            deltaU--;
		            Utils.sleep(1);
		        }

		        if (deltaF >= 1) {
		            defaultRender();
		            frames++;
		            deltaF--;
		            Utils.sleep(1);
		        }

		        if (System.currentTimeMillis() - timer > 1000) {
		            if (RENDER_TIME)
		                System.out.println(String.format("UPS: %s, FPS: %s, LOOPS: %s", ticks, frames, loops));
		            
		            Bombercraft.totalMessages = new GVector2f(Bombercraft.sendMessages, Bombercraft.recieveMessages);
		            Bombercraft.sendMessages = 0;
		            Bombercraft.recieveMessages = 0;
		            actFPS = frames;
		            actUPS = ticks;
		            actLoops = loops;
		            frames = 0;
		            ticks = 0;
		            loops = 0;
		            timer += 1000;
		        }
		        
		    }
	}
	
	//DEFAULT MAIN METHODS
	
	private void defaultInit(){
		window = new Window(this, Config.WINDOW_DEFAULT_TITLE, Config.WINDOW_DEFAULT_SIZE.getXi(), Config.WINDOW_DEFAULT_SIZE.getYi());
		window.add(canvas);
		
		canvas.addMouseListener(input);
		canvas.addKeyListener(input);
		canvas.addMouseMotionListener(input);
		init();
	};
	
	private void defaultInput(){
		input();
	}
	
	private void defaultUpdate(float delta){
		update(delta);
	}
	
	private void defaultRender(){
		BufferStrategy buffer = canvas.getBufferStrategy();
		if(buffer==null){
			canvas.createBufferStrategy(3);
			return;
		}
		g2 = (Graphics2D)buffer.getDrawGraphics();
		render(g2);
		
//		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
//		render((Graphics2D)image.getGraphics());
//		g2.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

		g2.dispose();
		buffer.show();
	}
	
	//MAIN METHODS
	
	protected void init(){
		
	};
	
	protected void input(){
		
	}
	
	protected void update(float delta){
		
	}
	
	protected void render(Graphics2D g2){
		
	}

	//GETTERS
	
	public Window getWindow() {
		return window;
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	public Input getInput() {
		return input;
	}

	public float getLoops() {
		return actLoops;
	}
	
	public float getFPS() {
		return actFPS;
	}
	
	public float getUPS() {
		return actUPS;
	}
	
	
	public abstract void onResize();
	public abstract void onExit();
}
