package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SemiGuideBullet extends GameObject{
	private Handler handler;
	private GameObject player;
	private float counter;
	private String parent;
	private BufferedImage semiGuideBulletImage;
	private boolean hitPlayer = false;
	private int destroyCounter = 0;
	
	public SemiGuideBullet(float x, float y, ID id, String par, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.parent = par;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		semiGuideBulletImage = loader.loadImage("/smartBullet.png");
		
		if(Player.isDead == false){ //check to see if player died to avoid nullpointer e
			for(int i = 0; i < handler.object.size(); i++){ //code to set movement location to player
				if(handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
			}
			
			float diffX = x - player.getX() - 8;
			float diffY = y - player.getY() - 8;
			float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()) );
			
			if(parent.equals("EyeEnemy")){
				velX = ((-4 / distance) * diffX);
				velY = ((-4 / distance) * diffY);
			} else {
				velX = ((-6 / distance) * diffX);
				velY = ((-6 / distance) * diffY);
			}
			
		}	
	}
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 8, 8);
	}

	public void tick() {
		x += velX;
		y += velY;
		while(hitPlayer){
			destroyCounter++;
			if(destroyCounter >= 20){
				handler.removeObject(this);
				hitPlayer = false;
				destroyCounter = 0;
			}		
		}	

		if(y <= 0 || y >= Game.HEIGHT) handler.removeObject(this);
		if(x <= 0 || x >= Game.WIDTH) handler.removeObject(this);
		collision();
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){					
					hitPlayer = true;
					HUD.HEALTH -= 10;
				}
			}		
		}
	}


	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		//g.fillOval((int)x, (int)y, 8, 8);
		g.drawImage(semiGuideBulletImage, (int)x, (int)y, null);
	}

}
