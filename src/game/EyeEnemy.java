package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EyeEnemy extends GameObject{
	
	private Handler handler;
	private float counter;
	private int shootCounter = 0;
	private float health = 100;
	private float hitCount;
	private BufferedImage eyeAnim1;
	private BufferedImage eyeAnim2;
	private BufferedImage eyeAnim3;
	private BufferedImage eyeAnim4;
	private BufferedImage eyeEnemyHitImage;
	private int animCount = 0;
	private int startMoving = 0;
	private Random r = new Random();
	private boolean canShoot;
	
	private int destroyCounter = 0;
	
	//private BufferedImage basicEnemy_hit;
	public EyeEnemy(float x, float y, ID id, Handler handler) {
		
		super(x, y, id);
		this.handler = handler;
		if(r.nextInt(2) == 1) canShoot = true; //every ~3rd enemy spawned can shoot
		
		BufferedImageLoader loader = new BufferedImageLoader();
		eyeAnim1 = loader.loadImage("/heliB1.png");
		eyeAnim2 = loader.loadImage("/heliB2.png");
		eyeAnim3 = loader.loadImage("/heliB3.png");
		eyeAnim4 = loader.loadImage("/heliB4.png");
		eyeEnemyHitImage = loader.loadImage("/eyeHit.png");
		
		
	
		hit = false;
		velX = -3;
		//velY = 2;
	}

	public void tick() {
		startMoving++;
		animCount++;
		shootCounter++;
		if(animCount > 15) animCount = 0;
			
		if(hit){
			hitCount++;
			if(hitCount > 5){
				hitCount = 0;
				hit = false;
			}
		}	
		if(startMoving >= 140){
			System.out.println(velY + " " + velX);
			counter++;
			
			//code for moving in circle
			if(counter > 0 && counter <= 10){  // move 100% L/UP
				velY = -3;
				velX = -3;
			}
			
			if(counter > 10 && counter <= 20){  // smoothen
				velY = -3;
				velX = -2;
			}
			
			
			if(counter > 20 && counter <= 40){  
				velY = -3;
				velX = -1;
			}									
			
			if(counter > 40 && counter <= 60){ // move 100% UP
				velY = -3 ;
				velX = 0;
			}
			if(counter > 60 && counter <= 70){ 
				velY = -3 ;
				velX = 1;
			}
			if(counter > 70 && counter <= 80){ //smoothen
				velY = -3 ;
				velX = 2;
			}
			
			
			if(counter > 80 && counter <= 90){ // move 100% R/UP
				velY = -3 ;
				velX = 3;	
			}
			if(counter > 90 && counter <= 100){ // smoothen
				velY = -2 ;
				velX = 3;
	
			}
			if(counter > 100 && counter <= 120){ 
				velY = -1 ;
				velX = 3;
			}
			
			
			if(counter > 120 && counter <= 140){ // move 100% R
				velY = 0 ;
				velX = 3;
			}

			if(counter > 140 && counter <= 150){ 
				velY = 1 ;
				velX = 3;
			}
			if(counter > 150 && counter <= 160){ //smoothen
				velY = 2 ;
				velX = 3;
			}
			
			
			if(counter > 160 && counter <= 170){ // move 100% R/DOWN
				velY = 3 ;
				velX = 3;
			}
			if(counter > 170 && counter <= 180){ //  smoothen
				velY = 3 ;
				velX = 2;
			}

			if(counter > 180 && counter <= 200){ // move 100% R/DOWN
				velY = 3 ;
				velX = 1;
			}
			
			
			if(counter > 200 && counter <= 220){ // move 100% DOWN
				velY = 3 ;
				velX = 0;
			}
			if(counter > 220 && counter <= 230){ 
				velY = 3 ;
				velX = -1;
			}
			if(counter > 230 && counter <= 240){ 
				velY = 3 ;
				velX = -2;
			}
			
			
			if(counter > 240 && counter <= 250){ // move 100% L/DOWN
				velY = 3 ;
				velX = -3;
			}
			if(counter > 250 && counter <= 260){ // move 100% L/DOWN
				velY = 2 ;
				velX = -3;
			}

			if(counter > 260 && counter <= 280){ 
				velY = 1 ;
				velX = -3;
			}
			
			if(counter > 280 && counter <= 300){ // move 100% L
				velY = 0;
				velX = -3;
				
			}
			if(counter > 300 && counter <= 310){ 
				velY = -1;
				velX = -3;				
			}
			if(counter > 310 && counter <= 320){ //smoothen
				velY = -2;
				velX = -3;				
			}
			if(counter > 320){ // reset move timer
				counter = 0;
			}
		}
		
		if(canShoot){

			if(shootCounter > 110){
				handler.addObject(new SemiGuideBullet(x, y, ID.SemiGuideBullet, "EyeEnemy", handler));
				shootCounter = 0;
			}
		}

		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		//if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		if(health <= 0){
			
			destroyCounter++;
			if(destroyCounter == 1){
				AudioPlayer.getSound("enemy_exp").play();
				handler.addObject(new Splash(x-15, y-15, ID.Splash, "explosion", handler));
			}
			if(destroyCounter > 10){
				HUD.points = HUD.points + 25;
				handler.removeObject(this);
			}
		}
		
		//handler.addObject(new Trail(x-8, y-4, ID.Trail, Color.orange, 32, 4, 0.07f, handler, false));
		//handler.addObject(new Trail(x+2, y-5, ID.Trail, Color.orange, 17, 4, 0.07f, handler, 3));
		collision();
		
	} 
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 44, 44);
		
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.PlayerBullet || tempObject.getId() == ID.SideBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					
					if(Player.hasLaserCannon){
						health -= 100;
					}else {
						health -= 25;
					}
					AudioPlayer.getSound("enemyHitShort").play();
					//collision code
				}				
			}
			if(tempObject.getId() == ID.ShootingEnemy){
				if(getBounds().intersects(tempObject.getBounds())){
					
					handler.removeObject(this);
				}				
			}			
		}
	}

	public void render(Graphics g) {
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.green);
		//if(hit) g.drawImage(eyeEnemyHitImage, (int)x, (int)y, null);
		//else{
			if(destroyCounter == 0){
				if(animCount >= 0 && animCount <= 3) g.drawImage(eyeAnim1, (int)x, (int)y, null);
				if(animCount >= 4 && animCount <= 7) g.drawImage(eyeAnim2, (int)x, (int)y, null);
				if(animCount >= 8 && animCount <= 11) g.drawImage(eyeAnim3, (int)x, (int)y, null);
				if(animCount >= 12 && animCount <= 15) g.drawImage(eyeAnim4, (int)x, (int)y, null);
			}
		//}
	
		
		//g2d.draw(getBounds());


	}

}
