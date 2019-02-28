package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SwervingEnemy extends GameObject{
	private Handler handler;
	private float counter;
	private float health = 100;
	private float moveCounter;
	private BufferedImage swervingEnemy_image;
	private BufferedImage swervingEnemy_imageL;
	private BufferedImage swervingEnemy_imageR;
	
	private BufferedImage swervingEnemy_hit;
	
	private int hitCounter = 0;
	private int destroyCounter = 0;

	public SwervingEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 2;

		BufferedImageLoader loader = new BufferedImageLoader();
		swervingEnemy_image = loader.loadImage("/stealth.png");
		swervingEnemy_imageL = loader.loadImage("/stealthL.png");
		swervingEnemy_imageR = loader.loadImage("/stealthR.png");
		
		swervingEnemy_hit = loader.loadImage("/swervingEnemy_hit.png");
		
	}

	public void tick() {
		
		if(hit){
			hitCounter++;
			if(hitCounter > 5){
				hit = false;
			}
		}
		
		moveCounter++;
		if(moveCounter >180){
			moveCounter = 0;
		}
		if(moveCounter <180 && moveCounter >60){
			velX = -2;
		}
		if(moveCounter >= 0 && moveCounter <= 60){
			velX = 4;
		}
		
		counter++;
		
		if(y >= Game.HEIGHT) handler.removeObject(this); //destroy if outside
		
		y += velY;
		
		if(x <= 0 || x >= Game.WIDTH - 16) {
			velX *= -1; 
		}
		x += velX;

		
		//if(counter >= 80){
		//	counter = 0;
		//	handler.addObject(new EnemyBullet(x, y+12, 11, 11, 2, ID.EnemyBullet, Color.cyan, "SwervingEnemy", handler));
		//}
		
		if(health <= 0){
			destroyCounter++;
			if(destroyCounter == 1){
				AudioPlayer.getSound("enemy_exp").play();
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
			}
			if(destroyCounter > 18){
				HUD.points = HUD.points + 25;
				handler.removeObject(this);
			}
			
		}
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 43, 36);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					
					health -= 56;						
					if(health > 1) AudioPlayer.getSound("enemy_hit").play();
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
			//if(hit) g.drawImage(swervingEnemy_hit, (int)x, (int)y, null);
			//else 
			//if(velX <3) g.drawImage(swervingEnemy_imageL, (int)x, (int)y, null);
			//if(velX >3) g.drawImage(swervingEnemy_imageR, (int)x, (int)y, null);
			g.drawImage(swervingEnemy_image, (int)x, (int)y, null);
		}
		
	
	}

}
