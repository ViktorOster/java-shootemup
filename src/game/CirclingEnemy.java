package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class CirclingEnemy extends GameObject{
	private Handler handler;
	private float counter;
	private float health = 100;
	private boolean movingLeft;
	private BufferedImage circlingL;
	private BufferedImage circlingUp;
	private BufferedImage circlingR;
	private BufferedImage circlingDown;
	
	private BufferedImage circlingEnemy_hit;
	private BufferedImage expl1;
	private BufferedImage expl2;
	private BufferedImage expl3;
	private BufferedImage expl4;
	private BufferedImage expl5;
	private int destroyCounter = 0;
	private float hitCount;
	
	public CirclingEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		
		
		if(x < (Game.WIDTH / 2)){  //if enemy is spawned at left corner, fly right
			velX = 5;
			velY = 0;
			movingLeft = false;
		}
		if(x > (Game.WIDTH / 2)){  //if enemy is spawned at right corner, fly left
			velX = -5;
			velY = 0;
			movingLeft= true;
		}
		
		BufferedImageLoader loader = new BufferedImageLoader();
		circlingL = loader.loadImage("/circlingL.png");
		circlingUp = loader.loadImage("/circlingUp.png");
		circlingR = loader.loadImage("/circlingR.png");
		circlingDown = loader.loadImage("/circlingDown.png");
		
		circlingEnemy_hit = loader.loadImage("/circlingEnemy_hit.png");
		expl1 = loader.loadImage("/expl1.png");
		expl2 = loader.loadImage("/expl2.png");
		expl3 = loader.loadImage("/expl3.png");
		expl4 = loader.loadImage("/expl4.png");
		expl5 = loader.loadImage("/expl5.png");
		
	
	}

	public void tick() {
		
		if(x <= 5){
			velX = 0;
			velY = 5;
			if(y >= (Game.HEIGHT -100)){
				velX = 5;
				velY = 0;
			}
		}
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}
		counter++;
				
		y += velY;
		x += velX;
		
		if(x >= Game.WIDTH+300) {
			handler.removeObject(this);
		}
				
		if(counter >= 90){
			counter = 0;
			
			handler.addObject(new SemiGuideBullet(x-6, y+9, ID.SemiGuideBullet, "CirclingEnemy", handler));
			
		}
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		
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
		
		//handler.addObject(new Trail(x+8, y+27, ID.Trail, Color.orange, 8, 13, 0.2f, handler, true));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 49, 53);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					if(Player.hasLaserCannon){
						health -= 100;
					} else health -= 56;		
					if(health >1) AudioPlayer.getSound("enemyHitShort").play();

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
			//if(hit) g.drawImage(circlingEnemy_hit, (int)x, (int)y, null);
			//else {
			
			if(velX < 0) g.drawImage(circlingL, (int)x, (int)y, null);
			else if(velX > 0) g.drawImage(circlingR, (int)x, (int)y, null);
			else if(velY > 0) g.drawImage(circlingDown, (int)x, (int)y, null);
			else if(velY < 0) g.drawImage(circlingUp, (int)x, (int)y, null);
			//}
		}

		
	}

}
