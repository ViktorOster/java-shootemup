package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PickupDiagonalFire extends GameObject {

	Handler handler;
	private BufferedImage twin_pickup_image;

	public PickupDiagonalFire(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;	
		BufferedImageLoader loader = new BufferedImageLoader();
		twin_pickup_image = loader.loadImage("/twinShootPwrup_spr.png");
	}

	public void tick() {
		y += 1;
		if(y >= Game.HEIGHT) handler.removeObject(this);

		collision();
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())) {
					Player.hasDiagonalFire = true;
					AudioPlayer.getSound("pickup_health").play();
					AudioPlayer.getSound("diagonalVoice").play();
					handler.removeObject(this);
				}
			}	
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		
		g.drawImage(twin_pickup_image, (int)x, (int)y, null);
		//g2d.draw(getBounds());
		
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 21, 21);
	}

}
