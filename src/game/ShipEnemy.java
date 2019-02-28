package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShipEnemy extends GameObject{
	
	private Handler handler;
	private float counter;
	private float health = 100;
	private float hitCount;
	private BufferedImage shipR1;
	private BufferedImage shipR2;
	private BufferedImage basicEnemy_hit;
	

	private int animCount;
	
	private int destroyCounter = 0;
	
	public ShipEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		
		shipR1 = loader.loadImage("/shipR1.png");
		shipR2 = loader.loadImage("/shipR2.png");
	
		hit = false;
		velX = 1;
		velY = 0.5f;

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
		if(counter >= 60){
			counter = 0;
			handler.addObject(new SemiGuideBullet(x+85, y+22, ID.SemiGuideBullet, "BasicEnemy", handler));
			//handler.addObject(new EnemyBullet(x+75, y, 4, 4, 4, ID.EnemyBullet, "ship", handler));
			//handler.addObject(new EnemyBullet(x+85, y, 4, 4, 2, ID.EnemyBullet, "ship", handler));
			//handler.addObject(new EnemyBullet(x+95, y, 4, 4, 5, ID.EnemyBullet, "ship", handler));
			
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
		
		return new Rectangle((int)x+35, (int)y, 111, 34);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					
					if(Player.hasLaserCannon){
						health -= 100;
					}else {
						health -= 33;
					}
					if(health > 1) AudioPlayer.getSound("enemy_hit").play();
					//collision code
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
				if(animCount >=0 && animCount <2) g.drawImage(shipR1, (int)x, (int)y, null);
				if(animCount >=2 && animCount <5) g.drawImage(shipR1, (int)x, (int)y, null);
				if(animCount >=5 && animCount <8) g.drawImage(shipR2, (int)x, (int)y, null);
				if(animCount >=8 && animCount <11) g.drawImage(shipR2, (int)x, (int)y, null);
	
				
			//}
		}
	

	}

}
