package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ShootingEnemy extends GameObject{
	private Handler handler;
	private float counter;
	public float health = 100;
	private float hitCount;
	private BufferedImage shootingEnemy_image;
	private BufferedImage shootingEnemy_hit;
	
	public ShootingEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		shootingEnemy_image = loader.loadImage("/shootingEnemy_spr.png");
		shootingEnemy_hit = loader.loadImage("/shootingEnemy_hit.png");
		velX = 0;
		velY = 1;
		
	}

	public void tick() {
		counter++;
		
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		if(counter >= 50){
			counter = 0;
			handler.addObject(new SmartEnemy(x-8, y-4, ID.SmartEnemy, handler, this));
		}
		
		handler.addObject(new Trail(x +37, y-3, ID.Trail, Color.orange, 25, 4, 0.07f, handler, 3));
		handler.addObject(new Trail(x+7, y-3, ID.Trail, Color.orange, 25, 4, 0.07f, handler, 3));
		
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
		
		return new Rectangle((int)x, (int)y, 62, 30);
		
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
					AudioPlayer.getSound("enemy_hit").play();
				}
			}

		}
	}

	public void render(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		//g2d.draw(getBounds());
		if(hit) g.drawImage(shootingEnemy_hit, (int)x-7, (int)y-4, null);
		else g.drawImage(shootingEnemy_image, (int)x-7, (int)y-4, null);
		
		

	}

}
