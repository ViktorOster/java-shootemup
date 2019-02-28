package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.Game.STATE;

public class Player extends GameObject {
	static float shootCounter;
	static boolean canShoot = true;
	Random r = new Random();
	Handler handler;
	private float hitCount;
	static boolean hasDiagonalFire = false;
	static boolean hasLaserCannon = false;
	static int twinTurretTimer = 0;
	static int laserCannonTimer = 0;
	static boolean isDead = false;
	private BufferedImage player_image;
	private BufferedImage player_hit;
	private BufferedImage playerTurnR;
	private BufferedImage playerTurnL;
	private int turnTimer = 0;
	static boolean shootL = true;
		
	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		hit = false;
		isDead = false;
		BufferedImageLoader loader = new BufferedImageLoader();
		player_image = loader.loadImage("/plane.png");
		player_hit = loader.loadImage("/player_hit.png");
		playerTurnR = loader.loadImage("/planeR.png");
		playerTurnL = loader.loadImage("/planeL.png");
	}

	public void tick() {
		
		if(velX >0 || velX <0){
			turnTimer++;
		} else turnTimer = 0;
		
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		
		shootCounter++;		

		if(shootCounter <= 5){ //cannot shoot until wait x ticks
			canShoot = false;
		} 
		if(shootCounter >5){
			canShoot = true;
			if(KeyInput.spacePressed) {
				shootBullet();
			}				
		}
		
		if (hasLaserCannon){ //if player has laser cannon, recharge longer
		
			if(shootCounter <= 35){ //resets when player shoots, waits 5 ticks until can shoot again
				canShoot = false;
			} 
			if(shootCounter >35){
				canShoot = true;
				if(KeyInput.spacePressed) shootBullet();
			}			
		}
		
		x += velX;
		y += velY;
		
		x = Game.clamp(x,  -130,  Game.WIDTH+130);
		y = Game.clamp(y,  4,  Game.HEIGHT -84);

		if(HUD.HEALTH <= 0){
			
			for(int i = 0; i < 12; i++){
				handler.addObject(new Particle(x+5, y+5, ID.Particle, Color.white, 0.05f, handler));
				
			}
			AudioPlayer.getSound("player_exp").play();
			handler.playerDied();
			isDead = true;
			handler.object.remove(this);
		}
		
		collision();
		//System.out.println(x + " " + y);
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SwervingEnemy|| tempObject.getId() == ID.SmartEnemy 
					|| tempObject.getId() == ID.EnemyBullet || tempObject.getId() == ID.Boss1Enemy || tempObject.getId() == ID.SemiGuideBullet){
				if(getBounds().intersects(tempObject.getBounds()))
				{
					if(tempObject.getId() == ID.EnemyBullet || tempObject.getId() == ID.SemiGuideBullet || tempObject.getId() == ID.SideEnemy){
						//HUD.HEALTH -= 20;
						AudioPlayer.getSound("player_hit").play();
						hit = true;
					} else {
						HUD.HEALTH -= 2; 
						hit= true; 
						AudioPlayer.getSound("player_hit").play();
					}
				}
			}	
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		//g2d.draw(getBounds());

		if(velX >0){
			if(turnTimer >4) g.drawImage(playerTurnR, (int)x, (int)y, null);
			else g.drawImage(player_image, (int)x, (int)y, null);
		}
		if(velX <0){
			if(turnTimer >4) g.drawImage(playerTurnL, (int)x, (int)y, null);
			else g.drawImage(player_image, (int)x, (int)y, null);
		}

		if(hit) g.drawImage(player_hit, (int)x, (int)y, null);
		else {
			if(velX == 0) g.drawImage(player_image, (int)x, (int)y, null);
		}
	}

	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 47, 49);
	}
	@Override
	public void shootBullet(){
		if(canShoot){
		
			shootCounter = 0;
			AudioPlayer.getSound("player_shoot").play();
			
			if(KeyInput.shootDown == false){
				
					if(shootL) handler.addObject(new PlayerBullet(x+8, y-4, 2, ID.PlayerBullet, handler));
					else handler.addObject(new PlayerBullet(x+27, y-4, 2, ID.PlayerBullet, handler));
				
				if(hasDiagonalFire) {
					if(shootL) handler.addObject(new PlayerBullet(x+31, y-4, 7, ID.PlayerBullet, handler));
					else handler.addObject(new PlayerBullet(x+6, y-4, 6, ID.PlayerBullet, handler));

				}
			}
			if(KeyInput.shootDown){
				if(!hasDiagonalFire) handler.addObject(new PlayerBullet(x+11, y+33, 4, ID.PlayerBullet, handler));
				if(hasDiagonalFire) {
					handler.addObject(new PlayerBullet(x+11, y+33, 4, ID.PlayerBullet, handler));
					handler.addObject(new PlayerBullet(x+6, y+33, 8, ID.PlayerBullet, handler));
					handler.addObject(new PlayerBullet(x+31, y+33, 9, ID.PlayerBullet, handler));
				}
			}
			if(shootL) shootL = false;
			else shootL = true;

		}
	}

}
