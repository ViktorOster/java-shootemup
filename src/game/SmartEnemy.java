package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SmartEnemy extends GameObject{
	private Handler handler;
	private GameObject player;
	private float counter;
	private ShootingEnemy parent;
	private BufferedImage smartBulletImage;
	
	public SmartEnemy(float x, float y, ID id, Handler handler, ShootingEnemy parent) {
		super(x, y, id);
		this.handler = handler;
		this.parent = parent;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		smartBulletImage = loader.loadImage("/smartBullet.png");
		
		if(Player.isDead == false){ //check to see if player died to avoid nullpointer e
			for(int i = 0; i < handler.object.size(); i++){
				if(handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
			}
			
			float diffX = x - player.getX() - 8;
			float diffY = y - player.getY() - 8;
			float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()) );
			
			velX = ((-3 / distance) * diffX);
			velY = ((-3 / distance) * diffY);
		}
		
		
	}
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 8, 8);
	}

	public void tick() {
		counter++;
		x += velX;
		y += velY;
		
		
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		//handler.addObject(new Trail(x, y, ID.Trail, Color.red, 8, 8, 0.07f, handler, false));
		
		//moves towards player every tick
		if(Player.isDead == false){ //check if player died to avoid nullpointer
			float diffX = x - player.getX() - 8;
			float diffY = y - player.getY() - 8;
			float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()) );
			
			if(parent.health <= 0){
				handler.removeObject(this);
			}
			
			velX = ((-2 / distance) * diffX);
			velY = ((-2 / distance) * diffY);
			if(counter >= 200){
				handler.removeObject(this);
			}
		}
		
		
		
	} 


	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		//g.fillOval((int)x, (int)y, 8, 8);
		g.drawImage(smartBulletImage, (int)x-2, (int)y-2, null);
	}

}
