package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PickupHealth extends GameObject {

	Handler handler;
	private BufferedImage health_image;

	public PickupHealth(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;		
		BufferedImageLoader loader = new BufferedImageLoader();
		health_image = loader.loadImage("/health_spr.png");
		
	}

	public void tick() {
		y += 1;
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail(x, y-4, ID.Trail, Color.lightGray, 16, 4, 0.07f, handler, 3));
		
		collision();
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH += 20;
					AudioPlayer.getSound("pickup_health").play();
					handler.removeObject(this);
				}
			}	
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		//g2d.draw(getBounds());
		g.drawImage(health_image, (int)x, (int)y, null);
		
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
