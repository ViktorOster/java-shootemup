package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Particle extends GameObject {
	private float alpha = 1;
	private Handler handler;
	private Color color;
	private float width, height;
	private float life;
	private Random r = new Random();
	//life = 0.001 - 0.1
	private float velX;
	private float velY;
	public Particle(float x, float y, ID id, Color color, float life,  Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = r.nextInt((6 - 2) + 1) + 2;
		this.height = r.nextInt((6 - 2) + 1) + 2;
		this.life = life;
		velX = (r.nextInt(10)-5);
		velY = (r.nextInt(10)-5);
	}

	@Override
	public void tick() {
		
		
		if(alpha > life){
			alpha -= (life - 0.001f);
		} else handler.removeObject(this);
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		
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
