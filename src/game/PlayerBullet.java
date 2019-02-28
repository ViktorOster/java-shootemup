package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PlayerBullet extends GameObject{
	private float destroyCounter;
	private boolean hitEnemy = false;
	private Handler handler;
	private int sizeX;
	private int sizeY;

	private BufferedImage twinBulletImage;

	private BufferedImage playerBulletImage;
	private BufferedImage cyanBullet;
	private BufferedImage playerBulletLDiagonalImage;
	private BufferedImage playerBulletRDiagonalImage;
	private BufferedImage cyanBulletLUp;
	private BufferedImage cyanBulletRUp;
	private int direction;
	public PlayerBullet(float x, float y, int dir, ID id, Handler handler) { // dir, 1 = left, 2 = up, 3 = right, 4 = down, 6 = diagonal left, 7 = diagonal right, 8 = L/DOWN, 9 = R/DOWN
	
		super(x, y, id);
		this.handler = handler;
		this.direction = dir;
		BufferedImageLoader loader = new BufferedImageLoader();
		playerBulletImage = loader.loadImage("/playerBulletSingle.png");
		playerBulletLDiagonalImage = loader.loadImage("/playerBulletLdiagonal.png");
		playerBulletRDiagonalImage = loader.loadImage("/playerBulletRdiagonal.png");
		//cyanBullet = loader.loadImage("/cyanBullet.png");
		//cyanBulletLUp = loader.loadImage("/cyanBulletLUP.png");
		//cyanBulletRUp = loader.loadImage("/cyanBulletRUP.png");
		sizeX = 8;
		sizeY = 16;

		if(dir == 2){
			velY = -18; // 2 =shoot up

		}
		if(dir == 1) velX = -9; // 1 = left
		if(dir == 3) velX = 9; // 3 =right
		if(dir == 4) velY = 9; // 4 = down
		if(dir == 6) {
			velY = -18;
			velX = -3;
		}
		if(dir == 7) {
			velY = -18;
			velX = 3;
		}
		if(dir == 8) {
			velY = 9;
			velX = -2;
		}
		if(dir == 9) {
			velY = 9;
			velX = 2;
		}
	}

	public void tick() {
		if(hitEnemy){
			destroyCounter++;
			if(destroyCounter > 0){ //destroy after one tick to allow collision code with enemy
				handler.addObject(new Splash(x, y, ID.Splash, "bullet", handler));
				handler.removeObject(this);
			}
		}

		if(y < 0 || y > Game.HEIGHT || x < 0 || x > Game.WIDTH) handler.removeObject(this);

		x += velX;
		y += velY;
		
		collision();		
		
	}
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.ShipEnemy || tempObject.getId() == ID.ShootingEnemy || tempObject.getId() == ID.SideEnemy || tempObject.getId() == ID.Boss1Enemy 
					|| tempObject.getId() == ID.SwervingEnemy || tempObject.getId() == ID.EyeEnemy || tempObject.getId() == ID.CirclingEnemy || tempObject.getId() == ID.DiskEnemy){
				if(getBounds().intersects(tempObject.getBounds())){					
					tempObject.trueHit();
					//handler.addObject(new Splash(x, y, ID.Splash, "bullet", handler));
					hitEnemy = true;
				}
				
			}	
		}
	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, sizeX, sizeY);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);		
		if(direction == 2) g.drawImage(playerBulletImage, (int)x, (int)y, null);
		if(direction == 6) g.drawImage(playerBulletLDiagonalImage, (int)x, (int)y, null);
		if(direction == 7) g.drawImage(playerBulletRDiagonalImage, (int)x, (int)y, null);
		if(direction == 4) g.drawImage(playerBulletImage, (int)x, (int)y, null);
		if(direction == 8) g.drawImage(playerBulletLDiagonalImage, (int)x, (int)y, null);
		if(direction == 9) g.drawImage(playerBulletRDiagonalImage, (int)x, (int)y, null);
		//g2d.draw(getBounds());
		
	}
}
