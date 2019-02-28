package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import game.Game.STATE;


public class Game extends Canvas implements Runnable{
	

	private static final long serialVersionUID = -4584388369897487885L;
	
	public static final float WIDTH = 540, HEIGHT = 640;
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	private HUD hud;
	private Spawn spawner;
	private int velY;
	private int velY2;
	
	private int yPosition = 0;
	private int yPosition2 = -640;
	private int yPositionOverlay = 0;
	private int yPositionOverlay2 = -640;
	private int yPositionDrips = 0;
	private int yPositionDrips2 = -640;
	
	public static BufferedImage stars;
	public static BufferedImage planets;
	public static BufferedImage spaceOverlay;
	public static BufferedImage spaceBorder;
	public static BufferedImage starsMiddle;
	public static BufferedImage starsOverlay;
	public static BufferedImage sea;
	public static BufferedImage seaB;
	
	private int animCount;
	
	private Random r;
	private Menu menu;
	private Camera cam;
	
	public enum STATE {
		Menu,
		Help,
		GameOver,
		Game
	};
	
	static STATE gameState = STATE.Menu;
	
	public Game(){
		AudioPlayer.load();
		velY = 3;
		velY2 = 3;
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));	
		
		menu = new Menu(this, handler);
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		
		hud = new HUD();
		hud.loadHudImages();
		cam = new Camera(0, 0);
		spawner = new Spawn(handler, hud);
		
		new Window((int)WIDTH, (int)HEIGHT, "This is a Game", this);
		BufferedImageLoader loader = new BufferedImageLoader();
		planets= loader.loadImage("/space_planets.jpg");
		spaceBorder = loader.loadImage("/spaceBorder.png");
		spaceOverlay = loader.loadImage("/spaceOverlay.png");
		starsMiddle = loader.loadImage("/starsMiddle.png");
		starsOverlay = loader.loadImage("/stars_overlay.png");
		sea = loader.loadImage("/oceanC64.png");
		seaB = loader.loadImage("/oceanC64B.png");
		if(gameState == STATE.Game){

			
			/*
			handler.addObject(new Player((WIDTH / 2) -32, (HEIGHT / 2) +170, ID.Player, handler));
			handler.addObject(new BasicEnemy((WIDTH / 2) +50, (HEIGHT / 2) -280, ID.BasicEnemy, handler));
			handler.addObject(new CirclingEnemy((WIDTH / 2) +50, (HEIGHT / 2) -230, ID.CirclingEnemy, handler));
			*/
			
			//handler.addObject(new CirclingEnemy(((r.nextInt() % 2 == 0) ? 10 : Game.WIDTH - 20), (Game.HEIGHT / 2) - r.nextInt(((280 - 150) + 1) + 150), ID.CirclingEnemy, handler));
			//handler.addObject(new PickupHealth((WIDTH / 2) +100, (HEIGHT / 2) -280, ID.PickupHealth, handler));
			//handler.addObject(new PickupTwinTurret((WIDTH / 2) +160, (HEIGHT / 2) -280, ID.PickupTwinTurret, handler));
			//handler.addObject(new PickupLaserCannon((WIDTH / 2) +200, (HEIGHT / 2) -280, ID.PickupLaserCannon, handler));
		}
		

	}

	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
			
		}
		stop();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);

		if(gameState == STATE.GameOver){
			g.drawImage(stars, 0, yPosition, this);
			g.drawImage(stars, 0, yPosition2, this);
		}
		
		if(gameState == STATE.Game){
			//g2d.translate(cam.getX(), cam.getY()/2);	
			if(animCount >=0 && animCount <14){
				g.drawImage(sea, 0, yPosition+250, this);
				g.drawImage(sea, 0, yPosition, this);
				g.drawImage(sea, 0, yPosition2, this);
			}
			if(animCount >=14 && animCount <30){
				g.drawImage(seaB, 0, yPosition+250, this);
				g.drawImage(seaB, 0, yPosition, this);
				g.drawImage(seaB, 0, yPosition2, this);
			}
			//g.drawImage(spaceOverlay, 0, yPositionOverlayStars, this);
			//g.drawImage(spaceOverlay, 0, yPositionOverlayStars2, this);
			//g2d.translate(-cam.getX(), -cam.getY()/2);
			
			hud.render(g);
			
			
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.GameOver){
			menu.render(g);		
		}
		
		//g2d.translate(cam.getX(), cam.getY()/2);			
		handler.render(g);
		//g2d.translate(-cam.getX(), -cam.getY()/2);
	
		
		//g.drawImage(spaceBorder, 0, 0, null);
		g.dispose();
		bs.show();
	}

	private void tick() {
		animCount++;
		if(animCount >=30) animCount = 0;
		
		handler.tick();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				cam.tick(handler.object.get(i));
			}
		}
		
		if(gameState == STATE.Game){
			
			yPosition += velY;
			if(yPosition > 640){ 
				yPosition = -637;
			}
			
			yPosition2 += velY;
			
			if(yPosition2 > 640){
				yPosition2 = -637;
			}
			
			yPositionOverlay += (velY+1); 
			if(yPositionOverlay > 640){
				yPositionOverlay = -637;
			}
			
			yPositionOverlay2 += (velY+1);
			
			if(yPositionOverlay2 > 640){
				yPositionOverlay2 = -637;
			}
			
			yPositionDrips += (velY+3); 
			if(yPositionDrips > 640){
				yPositionDrips = -646;
			}
			
			yPositionDrips2 += (velY+3);
			
			if(yPositionDrips2 > 640){
				yPositionDrips2 = -646;
			}
			
			
			
		
			hud.tick();
			spawner.tick();
		} else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.GameOver){
			menu.tick();
		}
		if(gameState == STATE.GameOver){
			yPosition += velY;
			if(yPosition > 480){
				yPosition = -480;
			}
			
			yPosition2 += velY;
			
			if(yPosition2 > 480){
				yPosition2 = -480;
			}
		}	
			
	}
	
	public static float clamp(float var, float min, float max){
		if(var >= max){
			return var = max;
		}				
		else if(var <= min) {
			return var = min;	
		}
		else 
			return var;
	}
	
	
	public static void main(String[] args){
		new Game();

	}
	

}
