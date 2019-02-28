package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HUD {
	private BufferedImage health_overlay;
	private BufferedImage health_screen;
	private BufferedImage score_bar;
	private BufferedImage powerupBar;
	private BufferedImage powerupOverlay;
	
	public void loadHudImages(){
		BufferedImageLoader loader = new BufferedImageLoader();
		health_overlay = loader.loadImage("/health_HUD.png");
		health_screen = loader.loadImage("/health_HUD_overlay.png");
		score_bar = loader.loadImage("/scoreBar.png");
		powerupBar = loader.loadImage("/powerup_hud.png");
		powerupOverlay = loader.loadImage("/powerup_overlay.png");
	}
	
	public static float HEALTH = 100;
	
	private float greenValue = 255;
	private float greenValueTwin = 255;
	private float greenValueLaser = 255;
	
	static float score = 0;
	static float points = 0;
	private float level = 1;
	
	public void tick(){

		HEALTH = Game.clamp(HEALTH, 0, 100);
		HEALTH = 100;
		greenValue = Game.clamp(greenValue,  0 , 255);		
		greenValue = HEALTH*2;
		
		score ++;
	}
	public static void clearScore(){
		score = 0;
	}
	
	
	public void render(Graphics g){
		//g.setColor(Color.gray);
		//g.fillRect(2, 415, 200, 32);
		Font fnt = new Font("monotype", 1 , 20);
		
		g.setFont(fnt);
		g.setColor(Color.yellow);
		g.drawString("Score: " + (int)points, 25, 25);
		//g.drawRect(2, 415, 200, 32);
		g.setColor(new Color(75, (int)greenValue, 0));
		g.fillRect(8, (int)Game.HEIGHT-60, (int)HEALTH, 22);
		
		g.drawString("counterGlobal: " + score, 15, 100);
		//g.drawString("Level: " + level, 15, 80);
		//g.drawString("HP: " + HEALTH, 70, 435);
		
		g.drawImage(health_overlay, 5, (int)Game.HEIGHT-60, null);
		//g.drawImage(health_screen, 5, (int)Game.HEIGHT-60, null);
		
		//g.drawImage(score_bar, 5, 5, null);
		
	}
	
	public void score(float score){
		this.score = score;
	}
	public float getScore(){
		return score;
	}
	public float getLevel(){
		return level;
	}
	public void setLevel(float level){
		this.level = level;
	}

}
