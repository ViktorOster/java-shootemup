package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss1Enemy extends GameObject{
	private Handler handler;
	private float counter;
	public float health = 4000;
	private float hitCount;
	private BufferedImage boss1_image;
	private BufferedImage boss1Hit;
	private BufferedImage boss1Blast;
	private int moveCounter;
	private boolean movingLeft = true;
	private int moveDownCounter;
	private int blastCounter = 0;
	
	public Boss1Enemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		boss1_image = loader.loadImage("/boss1_spr.png");
		boss1Hit = loader.loadImage("/boss1Hit.png");
		velX = 0;
		velY = 1;		
	}

	public void tick() {
		blastCounter++;
		if(blastCounter > 200) blastCounter = 0;
		moveDownCounter++;
		counter++; //bullet timer
		
		if(moveDownCounter < 200){ //move down first
			velX = 0;
			velY = 1;
		}
		
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		moveCounter++;   //movement
		if(moveCounter >220){
			moveCounter = 0;
		}
		if(moveDownCounter > 200){ //dont swerve until has moved down screen
			
			if(movingLeft){
				if(moveCounter <220 && moveCounter >170){
					velX = 3;
					velY = 1;
				}
				if(moveCounter >= 0 && moveCounter <= 170){
					velX = -2;
					velY = 0;
				}
			}
			if(movingLeft == false){
				if(moveCounter <220 && moveCounter >170){
					velX = -3;
					velY = -1;
				}
				if(moveCounter >= 0 && moveCounter <= 170){
					velX = 2;
					velY = 1;
				}
			}
		}
		if(counter == 1){
			handler.addObject(new EnemyBullet(x+15, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
			handler.addObject(new EnemyBullet(x+180, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
		}	
		if(counter == 25){
			handler.addObject(new EnemyBullet(x+45, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
			handler.addObject(new EnemyBullet(x+150, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
		}
		if(counter == 50){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
			
			handler.addObject(new EnemyBullet(x+15, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
			handler.addObject(new EnemyBullet(x+180, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
		}
		if(counter == 53){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
		}
		if(counter == 56){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
		}
		if(counter == 59 ){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
		}
		if(counter == 62 ){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
			
			handler.addObject(new EnemyBullet(x+45, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
			handler.addObject(new EnemyBullet(x+150, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
		}
		if(counter == 65 ){
			handler.addObject(new EnemyBullet(x+86, y+165, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, laser", handler));
		}
		if(counter == 75){
			handler.addObject(new EnemyBullet(x+15, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
			handler.addObject(new EnemyBullet(x+180, y+160, 8, 8, 2, ID.EnemyBullet, "Boss1Enemy, bullet", handler));
		}
		if(blastCounter == 199){
			handler.addObject(new EnemyBullet(x+15, y+20, 8, 8, 1, ID.EnemyBullet, "Boss1Enemy, blast", handler));
			handler.addObject(new EnemyBullet(x+180, y+20, 8, 8, 3, ID.EnemyBullet, "Boss1Enemy, blast", handler));
		}

		if(y > 31){
			velY = 0;
		}
		
		y += velY;
		
		if(x <= 15 || x >= Game.WIDTH - 210) {
			velX *= -1; 
			if(x <= 15){
				movingLeft = false;
			}
			if(x >= Game.WIDTH - 210){
				movingLeft = true;
			}
		}		
		x += velX;
		
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		if(counter >= 100){
			counter = 0;
			//handler.addObject(new SmartEnemy(x-8, y-4, ID.SmartEnemy, handler, this));
		}
		
		handler.addObject(new Trail(x +52, y+1, ID.Trail, Color.blue, 40, 4, 0.07f, handler, 2));
		handler.addObject(new Trail(x+127, y+1, ID.Trail, Color.blue, 40, 4, 0.07f, handler, 2));
		
		collision();
		if(health <= 0){
			for(int i = 0; i < 5; i++){
				handler.addObject(new Particle(x+5, y+5, ID.Particle, Color.red, 0.05f, handler));
				
			}
			AudioPlayer.getSound("enemy_exp").play();
			HUD.points = HUD.points + 50;
			handler.removeObject(this);
			
		}
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 205, 200);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					if(Player.hasLaserCannon){
						health -= 50;
					}else {
						health -= 20;
					}
					//hit = true;
					AudioPlayer.getSound("enemy_hit").play();
				}
			}
		}
	}

	public void render(Graphics g) {
				
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		//g2d.draw(getBounds());
		if(hit) g.drawImage(boss1Hit, (int)x, (int)y, null);
		else g.drawImage(boss1_image, (int)x, (int)y, null);
	}

}
