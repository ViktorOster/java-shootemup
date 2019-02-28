package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DiskEnemy extends GameObject{
	
	private Handler handler;
	private float counter;
	private float health = 100;
	private float hitCount;
	private BufferedImage diskAnim1;
	private BufferedImage diskAnim2;

	private BufferedImage diskHitImage;
	private int animCount = 0;
	private int startMoving = 0;

	private int destroyCounter = 0;
	private boolean leftSpawn;
	
	//private BufferedImage basicEnemy_hit;
	public DiskEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		
		if(x < (Game.WIDTH / 2)){  //enemy spawned at left
			leftSpawn = true;
			
		}
		if(x > (Game.WIDTH / 2)){ // spawned at right
			leftSpawn = false;
		}
		
		BufferedImageLoader loader = new BufferedImageLoader();
		diskAnim1 = loader.loadImage("/mine1.png");
		diskAnim2 = loader.loadImage("/mine2.png");

		//diskHitImage = loader.loadImage("/eyeHit.png");
	
	
		hit = false;
		if(leftSpawn) velX = 3;
		else velX = -3;
		//velY = 2;
	}

	public void tick() {
		startMoving++;
		animCount++;
		if(animCount > 35) animCount = 0;
			
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		if(leftSpawn){
			if(startMoving >= 20){				
				counter++;
				if(counter == 1) { //move down
					velY = 4;
					velX = 0;
				}
				if(counter == 42) { //smoothen
					velY = 3;
					velX = 1;
				}
				if(counter == 50) { 
					velY = -1;
					velX = 2;
				}
				if(counter == 55) { 
					velY = -3;
					velX = 3;
				}		
				if(counter == 60) { //move R/UP
					velY = -4;	
					velX = 4;
				}
				if(counter == 78) { 
					velY = -2;	
					velX = 2;
				}
				if(counter == 82) {
					velY = -1;	
					velX = 1;
				}
				if(counter == 83) {
					velY = 1;	
					velX = 0;
				}			
				if(counter >85) counter = 0;
			}
		}
		if(leftSpawn == false){
			if(startMoving >= 20){				
				counter++;
				if(counter == 1) { //move down
					velY = 4;
					velX = 0;
				}
				if(counter == 42) { //smoothen
					velY = 3;
					velX = -1;
				}
				if(counter == 50) { 
					velY = -1;
					velX = -2;
				}
				if(counter == 55) { 
					velY = -3;
					velX = -3;
				}		
				if(counter == 60) { //move L/UP
					velY = -4;	
					velX = -4;
				}
				if(counter == 78) { 
					velY = -2;	
					velX = -2;
				}
				if(counter == 82) {
					velY = -1;	
					velX = -1;
				}
				if(counter == 83) {
					velY = 1;	
					velX = 0;
				}			
				if(counter >85) counter = 0;
			}
		}
		
		
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT || y < 0) handler.removeObject(this);
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		if(health <= 0){
			
			destroyCounter++;
			if(destroyCounter == 1) {
				AudioPlayer.getSound("enemy_hit").play();
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
			}
			if(destroyCounter > 10){
				HUD.points = HUD.points + 25;
				AudioPlayer.getSound("enemy_exp").play();
				handler.removeObject(this);
			}
		}
		
		//handler.addObject(new Trail(x-8, y-4, ID.Trail, Color.orange, 32, 4, 0.07f, handler, false));
		//handler.addObject(new Trail(x+2, y-5, ID.Trail, Color.orange, 17, 4, 0.07f, handler, 3));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 31, 30);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
				
					health = 0;
					//handler.removeObject(tempObject);
					
					//collision code
				}				
			}
			if(tempObject.getId() == ID.ShootingEnemy){
				if(getBounds().intersects(tempObject.getBounds())){
					
					handler.removeObject(this);
				}				
			}			
		}
	}

	public void render(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);

		if(destroyCounter == 0){
			if(animCount >= 0 && animCount <= 8) g.drawImage(diskAnim1, (int)x, (int)y, null);
			if(animCount >= 9 && animCount <= 17) g.drawImage(diskAnim2, (int)x, (int)y, null);
			if(animCount >= 18 && animCount <= 26) g.drawImage(diskAnim1, (int)x, (int)y, null);
			if(animCount >= 27 && animCount <= 35) g.drawImage(diskAnim2, (int)x, (int)y, null);
		}
		
		
		
		//g2d.draw(getBounds());


	}

}
