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

public class PickupShip extends GameObject{
	private Handler handler;
	private float health = 100;
	private BufferedImage pickupShipImage;
	private BufferedImage pickupShipImage2;

	private int hitCounter = 0;
	private int destroyCounter = 0;
	private String pickup;
	
	private int animCount;

	public PickupShip(float x, float y, ID id, String pu, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		this.pickup = pu;
		velX = 0;
		velY = 2;

		BufferedImageLoader loader = new BufferedImageLoader();
		pickupShipImage = loader.loadImage("/transportplane.png");
		pickupShipImage2 = loader.loadImage("/transportplane2.png");
	
	}

	public void tick() {
		animCount++;
		if(animCount > 3) animCount = 0;
		
		if(y >= Game.HEIGHT) handler.removeObject(this); //destroy if outside
		
		y += velY;
		
		if(x <= 0 || x >= Game.WIDTH - 16) {
			velX *= -1; 
		}
		x += velX;

		if(health <= 0){
			destroyCounter++;
			if(destroyCounter == 1){
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
				AudioPlayer.getSound("enemy_exp").play();
			}
			if(destroyCounter > 10){
				HUD.points = HUD.points + 10;
				if(pickup.equals("diagonalFire")){
					handler.addObject(new PickupDiagonalFire(x, y, ID.PickupDiagonalFire, handler));
				}
				handler.removeObject(this);
			}
			
		}
		collision();
		
	} 
	public Rectangle getBounds() {		
		return new Rectangle((int)x, (int)y, 62, 46);		
	}	
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					
					health -= 100;						
					//AudioPlayer.getSound("enemy_hit").play();
					//collision code
				}
			}	
		}
	}

	public void render(Graphics g) {
			
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		//g2d.draw(getBounds());

		if(destroyCounter <= 0) {
			if(animCount >=0 && animCount <3) g.drawImage(pickupShipImage, (int)x, (int)y, null);
			if(animCount >=2 && animCount <4) g.drawImage(pickupShipImage2, (int)x, (int)y, null);
		}
		
	}

}
