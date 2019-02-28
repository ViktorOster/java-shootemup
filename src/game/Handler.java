package game;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import game.Game.STATE;

public class Handler {
	private boolean playerDead = false;
	private int deadTimer = 0;
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick(){
		
		if(playerDead){
			deadTimer++;
			if(deadTimer > 150){
				clearEverything();
				playerDead = false;
			} 
			
		} 
		
		for(int i = 0; i < object.size(); i++){
			
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}			
	}
	
	public void render(Graphics g){

		for(int i = 0; i < object.size(); i++){
			
			
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}	
		
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	public void playerDied(){
		playerDead = true;
	}
	public void clearEnemies(){
		object.clear();
	}
	
	public void clearEverything(){
		object.clear();
		//Menu menu = new Menu(game, handler);
		Game.gameState = STATE.GameOver;
		HUD.HEALTH = 100;
		Spawn.clearSpawn();
		AudioPlayer.getMusic("music").stop();
		AudioPlayer.getMusic("game_over").loop();
	}
	
}
