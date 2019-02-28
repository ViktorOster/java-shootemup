package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Splash extends GameObject {

	private Handler handler;
	
	private BufferedImage splash1;
	private BufferedImage splash2;
	private BufferedImage splash3;
	private BufferedImage splash4;
	
	private BufferedImage expl1;
	private BufferedImage expl2;
	private BufferedImage expl3;
	private BufferedImage expl4;
	private BufferedImage expl5;
	
	private BufferedImage bulletSplash1;
	private BufferedImage bulletSplash2;
	private BufferedImage bulletSplash3;
	private BufferedImage alienTrail;
	
	private int animCount = 0;
	private String type;

	public Splash(float x, float y, ID id, String typ, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.type = typ;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		splash1 = loader.loadImage("/splash1.png");
		splash2 = loader.loadImage("/splash2.png");
		splash3 = loader.loadImage("/splash3.png");
		splash4 = loader.loadImage("/splash4.png");
		
		expl1 = loader.loadImage("/expl1.png");
		expl2 = loader.loadImage("/expl2.png");
		expl3 = loader.loadImage("/expl3.png");
		expl4 = loader.loadImage("/expl4.png");
		expl5 = loader.loadImage("/expl5.png");
		
		bulletSplash1 = loader.loadImage("/bulletHit1.png");
		bulletSplash2 = loader.loadImage("/bulletHit2.png");
		bulletSplash3 = loader.loadImage("/bulletHit3.png");
		
		
		
		//alienTrail = loader.loadImage("/alienTrail.png");
	}

	@Override
	public void tick() {
		animCount++;
		
		if(!type.equals("trail")){
			if(animCount > 50) handler.removeObject(this);
		} else if(animCount >50) handler.removeObject(this);
		
		
	}

	@Override
	public void render(Graphics g) {
		if(type.equals("slime")){
			if(animCount >= 0 && animCount <= 2) g.drawImage(splash2, (int)x, (int)y, null);
			if(animCount >= 3 && animCount <= 5) g.drawImage(splash3, (int)x, (int)y, null);
			if(animCount >= 6 && animCount <= 7) g.drawImage(splash4, (int)x, (int)y, null);
		}
		if(type.equals("trail")){
			g.drawImage(alienTrail, (int)x, (int)y, null);
			
		}
		if(type.equals("bullet")){
			if(animCount >= 0 && animCount <= 2) g.drawImage(bulletSplash1, (int)x, (int)y, null);
			if(animCount >= 3 && animCount <= 5) g.drawImage(bulletSplash2, (int)x, (int)y, null);
			if(animCount >= 6 && animCount <= 8) g.drawImage(bulletSplash3, (int)x, (int)y, null);
		}
		if(type.equals("explosion")){
			if(animCount > 0 && animCount <= 3) g.drawImage(expl1, (int)x, (int)y, null);
			if(animCount >= 4 && animCount <= 7) g.drawImage(expl2, (int)x, (int)y, null);
			if(animCount >= 8 && animCount <= 11) g.drawImage(expl3, (int)x, (int)y, null);
			if(animCount >= 12 && animCount <= 15) g.drawImage(expl4, (int)x, (int)y, null);
			if(animCount >= 16 && animCount <= 19) g.drawImage(expl5, (int)x, (int)y, null);
		}
		
		
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
