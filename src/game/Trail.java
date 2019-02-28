package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Trail extends GameObject {
	private float alpha = 1;
	private Handler handler;
	private Color color;
	private float width, height;
	private float life;
	//life = 0.001 - 0.1
	private int direction;
	
	public Trail(float x, float y, ID id, Color color, float width, float height, float life,  Handler handler, int dir) { //1 = down, 2 = up, 3 = null, 4 = left, 5 = right
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
		this.direction = dir;
	}

	@Override
	public void tick() {
		
		if(alpha > life){
			alpha -= (life - 0.001f);
		} else handler.removeObject(this);
		
		if(direction == 1){ //down
			y += 4;
		} 
		if(direction == 2){ //up
			y -= 2;
		} 
		if(direction == 4){ //left
			x -= 3;
		} 
		if(direction == 5){ //right
			x += 3;
		} 
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillOval((int)x, (int)y, (int)width, (int)height);
		
		g2d.setComposite(makeTransparent(1));
		
	}
	private AlphaComposite makeTransparent(float alpha){
		float type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance((int)type, alpha));
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
