package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SideBullet extends GameObject{
	private float destroyCounter;
	private boolean hitEnemy = false;
	private Handler handler;
	private int size;
	int direction;
	public SideBullet(float x, float y, ID id, Handler handler, int direction) { //1=left, 2=right
		
		super(x, y, id);
		size = 6;
		this.direction = direction;
		this.handler = handler;
		if(direction == 1){
			velX = -4;	
		} else velX = 4;	

	}


	public void tick() {
		if(x <= 0 || x >= Game.WIDTH) handler.removeObject(this);
		
		while(hitEnemy){
			destroyCounter++;
			if(destroyCounter >= 20){
				handler.removeObject(this);
				hitEnemy = false;
				destroyCounter = 0;
				
			}
			
		}

		x += velX;

		handler.addObject(new Trail(x+1, y, ID.Trail, Color.cyan, size-2, size-2, 0.2f, handler, 3));
		
		collision();
		
		
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.ShootingEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SideEnemy || tempObject.getId() == ID.Boss1Enemy){
				if(getBounds().intersects(tempObject.getBounds())){					
					hitEnemy = true;
					tempObject.trueHit();
				}
			}
		
			
		}
	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, size, size);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.blue);
		//g2d.fillRect((int)x, (int)y, 16, 16);
		
		g.setColor(Color.blue);
		g.fillOval((int)x, (int)y, size, size);
		
		
		
		
	}


}
