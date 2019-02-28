package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyBullet extends GameObject{
	private float destroyCounter;
	private boolean hitPlayer = false;
	private Handler handler;
	private Color color;
	private int sizeX;
	private int sizeY;
	private int dir;
	private String parent;
	private BufferedImage laserCircle;
	private BufferedImage boss1Bullet;
	private BufferedImage boss1Blast;
	private BufferedImage boss1BlastRight;
	private BufferedImage basicEnemyBullet;
	private BufferedImage swerveBullet;
	public EnemyBullet(float x, float y, int xSize, int ySize, int direction, ID id, String par, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		this.sizeX = xSize;
		this.sizeY = ySize;
		this.dir = direction;
		this.parent = par;
		BufferedImageLoader loader = new BufferedImageLoader();
		laserCircle = loader.loadImage("/laserCircle.png");
		boss1Bullet = loader.loadImage("/boss1Bullet.png");
		boss1Blast = loader.loadImage("/Boss1Blast.png");
		boss1BlastRight = loader.loadImage("/Boss1BlastRight.png");
		basicEnemyBullet = loader.loadImage("/basicBullet.png");
		swerveBullet = loader.loadImage("/swerveBullet.png");
		//boss1BulletRight = loader.loadImage("/boss1Bullet.png");
		if(direction == 1) { //bullet goes left
			velX = -4;
			velY = 0;
		}
		if(direction == 3){ // right
			velX = 4;
			velY = 0;
		}
		if (direction == 2) {  //bullet goes down
			velY = 6;
			velX = 0;
		}
		if(parent.equals("Boss1Enemy, bullet")){
			velY += 1;
		}
		if(direction == 4){ // downL
			velX = -4;
			velY = 6;
		}
		if(direction == 5){ // downR
			velX = 4;
			velY = 6;
		}

		//AudioPlayer.getSound("enemy_shoot").play();
	}


	public void tick() {
		while(hitPlayer){
			destroyCounter++;
			if(destroyCounter >= 20){
				handler.removeObject(this);
				hitPlayer = false;
				destroyCounter = 0;
			}		
		}		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		x += velX;
		y += velY;
		
		collision();
		
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){					
					hitPlayer = true;
					if(parent.equals("Boss1Enemy, laser")) HUD.HEALTH -= 7;
					if(parent.equals("Boss1Enemy, bullet")) HUD.HEALTH -= 5;
					else HUD.HEALTH -= 10;
				}
			}		
		}
	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, sizeX, sizeY);
	}
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setColor(Color.blue);
		//g2d.fillRect((int)x, (int)y, 16, 16);
		
		//g.setColor(Color.green);
		//g.fillOval((int)x, (int)y, sizeX, sizeY);

		if(parent.equals("BasicEnemy")){
			g.drawImage(basicEnemyBullet, (int)x, (int)y, null);
		}
		if(parent.equals("Boss1Enemy, laser")){
			g.drawImage(laserCircle, (int)x, (int)y, null);
		}
		if(parent.equals("Boss1Enemy, bullet")){
			g.drawImage(boss1Bullet, (int)x, (int)y, null);
		} 
		if(parent.equals("Boss1Enemy, blast")){
			if(dir == 1){
				g.drawImage(boss1Blast, (int)x, (int)y, null);
			}
			if(dir == 3){
				g.drawImage(boss1BlastRight, (int)x, (int)y, null);
			}
		} 
		if((parent.equals("Boss1Enemy, bullet") || (parent.equals("Boss1Enemy, laser") || (parent.equals("BasicEnemy")))) == false) {
			g.drawImage(swerveBullet, (int)x, (int)y, null);	
		}
	}
}
	
