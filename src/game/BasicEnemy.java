package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BasicEnemy extends GameObject{
	
	private Handler handler;
	private float counter;
	private float health = 100;
	private float hitCount;
	private BufferedImage basicEnemy_image;
	private BufferedImage basicEnemy_hit;
	
	
	private BufferedImage heli1;
	private BufferedImage heli2;
	private BufferedImage heli3;
	private BufferedImage heli4;
	private int animCount;
	
	private int destroyCounter = 0;
	
	public BasicEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		
		heli1 = loader.loadImage("/heli1.png");
		heli2 = loader.loadImage("/heli2.png");
		heli3 = loader.loadImage("/heli3.png");
		heli4 = loader.loadImage("/heli4.png");
		
		basicEnemy_hit = loader.loadImage("/basicEnemy_hit.png");
		
		
		
		hit = false;
		velX = 0;
		velY = 2.5f;

	}

	public void tick() {
		animCount++;
		if(animCount >=11) animCount = 0;
		
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		
		counter++;
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		if(counter >= 40){
			counter = 0;
			handler.addObject(new SemiGuideBullet(x+19, y+22, ID.SemiGuideBullet, "BasicEnemy", handler));
			//handler.addObject(new EnemyBullet(x+19, y+22, 9, 12, 2, ID.EnemyBullet, Color.green, "BasicEnemy", handler));
		}
		
		if(health <= 0){
			destroyCounter++;
			if(destroyCounter == 1){
				AudioPlayer.getSound("enemy_exp").play();
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
			}
			if(destroyCounter > 10){
				HUD.points = HUD.points + 25;
				handler.removeObject(this);
			}
			
		}
		
		//handler.addObject(new Trail(x-8, y-4, ID.Trail, Color.orange, 32, 4, 0.07f, handler, false));
		//handler.addObject(new Trail(x+2, y-5, ID.Trail, Color.orange, 17, 4, 0.07f, handler, 3));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 44, 32);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					
					if(Player.hasLaserCannon){
						health -= 100;
					}else {
						health -= 100;
					}
					if(health > 1) AudioPlayer.getSound("enemy_hit").play();
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
		
		
		if(destroyCounter <= 0){
			
			//if(hit) g.drawImage(basicEnemy_hit, (int)x, (int)y, null);
			//else {
				if(animCount >=0 && animCount <2) g.drawImage(heli1, (int)x, (int)y, null);
				if(animCount >=2 && animCount <5) g.drawImage(heli2, (int)x, (int)y, null);
				if(animCount >=5 && animCount <8) g.drawImage(heli3, (int)x, (int)y, null);
				if(animCount >=8 && animCount <11) g.drawImage(heli4, (int)x, (int)y, null);
	
				
			//}
		}
	

	}

}
