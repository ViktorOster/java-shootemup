package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import game.Game.STATE;

public class Menu extends MouseAdapter implements MouseListener {
	private Game game;
	private Handler handler;
	private int mx;
	private int my;
	
	private int velY;
	private int yPosition = 0; //for moving stars
	private int yPosition2 = -480;
	
	private BufferedImage xelor_logo_image;
	private BufferedImage play_deselect_image;
	private BufferedImage play_select_image;
	private BufferedImage help_select_image;
	private BufferedImage help_deselect_image;
	private BufferedImage quit_select_image;
	private BufferedImage quit_deselect_image;
	private BufferedImage to_menu_image;
	private BufferedImage to_menu_selected_image;
	private BufferedImage game_over_image;
	private BufferedImage help_screen_image;
	private BufferedImage stars;
	private boolean playSelected = false;
	private boolean helpSelected = false;
	private boolean quitSelected = false;
	private boolean toMenuSelected = false;
	private Rectangle playRect;
	
	public Menu(Game game, Handler handler){
		velY = 1;
		this.game = game;
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		xelor_logo_image = loader.loadImage("/xelor_logo.png");
		play_select_image = loader.loadImage("/play_selected.png");
		play_deselect_image = loader.loadImage("/play_deselected.png");
		help_select_image = loader.loadImage("/help_selected.png");
		help_deselect_image = loader.loadImage("/help_deselected.png");
		quit_select_image = loader.loadImage("/quit_selected.png");
		quit_deselect_image = loader.loadImage("/quit_deselected.png");
		help_screen_image = loader.loadImage("/help_screen.png");
		to_menu_image = loader.loadImage("/toMenu.png");
		to_menu_selected_image = loader.loadImage("/toMenu_selected.png");
		game_over_image = loader.loadImage("/game_over.png");
		stars = loader.loadImage("/space_seamless2.jpg");

		
		AudioPlayer.getMusic("menu_music").loop(); //when initliazed (once)
	}
	
	public void mousePressed(MouseEvent e){
		mx = e.getX();
		my = e.getY();
		if(game.gameState == STATE.GameOver){
			if(mouseOver(mx, my, 20, 400, 100, 32)){ //back to menu
				game.gameState = STATE.Menu;
				AudioPlayer.getMusic("game_over").stop();
				AudioPlayer.getMusic("menu_music").loop(); //start menu music again
				return;
			}
		}
		
		if(game.gameState == STATE.Menu){
			if(mouseOver(mx, my, 220, 150, 200, 64)){ //play
				game.gameState = STATE.Game;
				HUD.clearScore();
				AudioPlayer.getMusic("menu_music").stop();
				//AudioPlayer.getMusic("music").loop();
				handler.addObject(new Player((Game.WIDTH / 2) -32, (Game.HEIGHT / 2) +150, ID.Player, handler));
				//handler.addObject(new BasicEnemy((Game.WIDTH / 2) +50, (Game.HEIGHT / 2) -280, ID.BasicEnemy, handler));
				//handler.addObject(new CirclingEnemy((Game.WIDTH / 2) +50, (Game.HEIGHT / 2) -230, ID.CirclingEnemy, handler));
				
				//handler.addObject(new ShootingEnemy((Game.WIDTH / 2) +50, (Game.HEIGHT / 2) -230, ID.ShootingEnemy, handler));
			    //handler.addObject(new PickupLaserCannon((Game.WIDTH / 2) +50, (Game.HEIGHT / 2) -230, ID.PickupLaserCannon, handler));
			    //handler.addObject(new PickupDiagonalFire((Game.WIDTH / 2) +100, (Game.HEIGHT / 2) -230, ID.PickupDiagonalFire, handler));
			    //handler.addObject(new Boss1Enemy((Game.WIDTH / 2), (Game.HEIGHT / 2) -290, ID.Boss1Enemy, handler));
				
			}
			if(mouseOver(mx, my, 220, 250, 200, 64)){ //help 
				game.gameState = STATE.Help;
			}
			if(mouseOver(mx, my, 220, 350, 200, 64)){ //quit
				System.exit(1);
			}
		}		
		if(game.gameState == STATE.Help){
			if(mouseOver(mx, my, 10, 385, 120, 55)){ //back 
				game.gameState = STATE.Menu;
				return;
			}
		}	
	}
	@Override
	public void mouseMoved(MouseEvent e){
		mx = e.getX();
		my = e.getY();
		
		if(mouseOver(mx, my, 220, 150, 200, 64) == true){
			playSelected =true;
		} else playSelected = false;
		
		if(mouseOver(mx, my, 220, 250, 200, 64)){ //help 
			helpSelected =true;
		} else helpSelected = false;
		
		if(mouseOver(mx, my, 220, 350, 200, 64)){ //quit
			quitSelected = true;
		} else quitSelected = false;
		if(mouseOver(mx, my, 10, 385, 120, 55)){ //to menu
			toMenuSelected = true;
		} else toMenuSelected = false;
	}
	
	public void mouseReleased(MouseEvent e){
		System.out.println("released");
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			} else return false;
		} else return false;
	}
	
	public void tick(){
		yPosition += velY;
		if(yPosition > 480){
			yPosition = -480;
		}
		
		yPosition2 += velY;
		
		if(yPosition2 > 480){
			yPosition2 = -480;
		}

		/*
		if(mouseOver(mx, my, 220, 150, 200, 64) == true){
			playSelected = true;	
		} else playSelected = false;
		
		if(mouseOver(mx, my, 220, 250, 200, 64)){ //help 
			helpSelected =true;
		} else helpSelected = false;
		
		if(mouseOver(mx, my, 220, 350, 200, 64)){ //quit
			quitSelected = true;
		} else quitSelected = false;
		*/
		//System.out.print(playSelected);
	}
	
	public void render(Graphics g){
		g.drawImage(stars, 0, yPosition, null);
		g.drawImage(stars, 0, yPosition2, null);
		
		if(game.gameState == STATE.Menu){
			/*
			g.setColor(Color.white);
			
			
			Font fnt = new Font("arial", 1 , 50);
			Font fnt2 = new Font("arial", 1 , 30);
			g.setFont(fnt);
			g.drawString("Menu", 254, 70);
			
			g.setFont(fnt2);
			g.drawString("Play", 286, 190);
			g.drawString("Help", 286, 290);
			g.drawString("Quit", 286, 390);
			
			g.drawRect(220, 150, 200, 64);
			g.drawRect(220, 250, 200, 64);
			g.drawRect(220, 350, 200, 64);
			
			*/
			
			
			g.drawImage(xelor_logo_image, 169, 45, null);
			if(playSelected) g.drawImage(play_select_image, 220, 150, null);
			else if(!playSelected) g.drawImage(play_deselect_image, 220, 150, null);
			
			if(helpSelected) g.drawImage(help_select_image, 220, 250, null);
			else if(!helpSelected) g.drawImage(help_deselect_image, 220, 250, null);
			
			if(quitSelected) g.drawImage(quit_select_image, 220, 350, null);
			else if(!quitSelected) g.drawImage(quit_deselect_image, 220, 350, null);
			
		} else if (game.gameState == STATE.Help) {
			
			/*
			g.setColor(Color.white);
			Font fnt = new Font("arial", 1 , 50);
			Font fnt2 = new Font("arial", 1 , 25);
			Font fnt3 = new Font("arial", 1 , 20);
			g.setFont(fnt);
			g.drawString("Help", 254, 70);
			g.drawRect(20, 400, 100, 32);
			g.setFont(fnt2);
			g.drawString("Back", 38, 425);
			g.setFont(fnt3);
			g.drawString("Use WASD keys to control the ship, and space to shoot.", 50, 130);
			g.drawString("Avoid enemies, and enemy bullets and see how far you can get.", 18, 160);
			*/
			g.setColor(Color.white);
			
			g.drawImage(help_screen_image, 0, 0, null);
			if (toMenuSelected) g.drawImage(to_menu_selected_image, 10, 385, null);
			else g.drawImage(to_menu_image, 10, 385, null);
			//g.drawRect(10, 385, 120, 55);
			
		} else if (game.gameState == STATE.GameOver){
			/*
			g.setColor(Color.white);
			Font fnt = new Font("arial", 1 , 50);
			Font fnt2 = new Font("arial", 1 , 20);
			Font fnt3 = new Font("arial", 1 , 15);
			g.setFont(fnt);
			g.drawString("GAME OVER", 190, 70);
			g.setFont(fnt3);
			g.drawString(HUD.points, 220, 180);
			g.drawRect(20, 400, 100, 32);
			g.setFont(fnt2);
			g.drawString("To Menu", 30, 425);
			*/
			Font fnt = new Font("arial", 1 , 30);
			g.setColor(Color.orange);
			g.drawImage(game_over_image, 0, 0, null);
			g.setFont(fnt);
			g.drawString("" + HUD.points, 300, 195);
			
			if (toMenuSelected) g.drawImage(to_menu_selected_image, 10, 385, null);
			else g.drawImage(to_menu_image, 10, 385, null);
		}
	}

	
}
