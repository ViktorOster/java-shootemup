package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

public class FastEnemy extends GameObject{
	private Handler handler;
	public FastEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 5;
		p = new Polygon();
		for (int i = 0; i < 3; i++){
			p.addPoint((int) 
		    ((x-24) + (32 + 16 * Math.cos(i * 2.5 * Math.PI / 5))),
		    (int)(y-32) + (int) (32 + 20 * Math.sin(i * 2.5 * Math.PI / 5)));
	    }
	
	}

	public void tick() {
		
		x += velX;
		y += velY;
		for(int i=0;i<p.xpoints.length;i++)
		    p.xpoints[i]+=velX;
		for(int i=0;i<p.ypoints.length;i++)
		    p.ypoints[i]+=velY;
		
		//if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		handler.addObject(new Trail(x-8, y-4, ID.Trail, Color.orange, 32, 4, 0.07f, handler, 3));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x-7, (int)y, 30, 16);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					System.out.println("hit enemy");
					handler.removeObject(this);
					//collision code
				}
			}
		
			
		}
	}

	public void render(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.green);
		//g2d.draw(getBounds());
		g.setColor(Color.orange);

		g.fillPolygon(p);

		//g.fillPolygon(p);
	}

}
