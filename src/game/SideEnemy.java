package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SideEnemy extends GameObject{
	Random r = new Random();
	private Handler handler;
	int randomX;
	int randomY;
	private BufferedImage m1L;
	private BufferedImage m2L;
	private BufferedImage m1R;
	private BufferedImage m2R;
	
	private int destroyCounter = 0;
	private int health = 100;
	
	private int animCount;
	
	public SideEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		m1L = loader.loadImage("/sideL1.png");
		m2L = loader.loadImage("/sideL2.png");
		m1R = loader.loadImage("/sideR1.png");
		m2R = loader.loadImage("/sideR2.png");
		
		this.randomX = r.nextInt((16 - 8) + 1) + 8;
		this.randomY = r.nextInt((16 - 8) + 1) + 8;
		this.handler = handler;
		
		if(x < (Game.WIDTH / 2)){  //if enemy is spawned at left corner, fly right
			velX = 5;
			velY = 5;
			
		}
		if(x > (Game.WIDTH / 2)){  //if enemy is spawned at right corner, fly left
			velX = -5;
			velY = 5;
			
		}
		//AudioPlayer.getSound("star_shoot").play();
		

	}

	public void tick() {
		animCount++;
		if(animCount>7) animCount = 0;
		
		x += velX;
		y += velY;
		
		if(health <= 0){
			destroyCounter++;
			if(destroyCounter == 1){
				AudioPlayer.getSound("enemy_exp").play();
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
			}
			if(destroyCounter > 12){
				HUD.points = HUD.points + 25;
				handler.removeObject(this);
			}
			
		}
		
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		
		//handler.addObject(new Trail(x+3, y, ID.Trail, Color.orange, 16, 16, 0.07f, handler, 3));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 52, 56);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet || tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					//System.out.println("hit enemy");
					HUD.points = HUD.points + 10;
					health = 0;						
					//collision code
				}
			}
		
			
		}
	}

	public void render(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		//g2d.draw(getBounds());
		if(destroyCounter <= 0){
			if(velX>0){
				if(animCount >=0 && animCount <4) g.drawImage(m1R, (int)x, (int)y, null);
				if(animCount >=4 && animCount <8) g.drawImage(m2R, (int)x, (int)y, null);
			}
			if(velX<0){
				if(animCount >=0 && animCount <4) g.drawImage(m1L, (int)x, (int)y, null);
				if(animCount >=4 && animCount <8) g.drawImage(m2L, (int)x, (int)y, null);
			}
		}
		
		
	
		
	}

}
