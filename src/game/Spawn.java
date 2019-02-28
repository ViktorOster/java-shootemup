package game;

import java.util.Random;

public class Spawn {
	private Handler handler;
	private HUD hud;
	private Random r = new Random();

	
	static int counterGlobal = 0;
	static int swerveSpawn = 0;
	static int shootingSpawn = 0;
	static int eyeSpawn = 0;
	static int diskSpawn = 0;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
	}
	public static void clearSpawn(){
		counterGlobal = 0;

		swerveSpawn = 0;

		shootingSpawn = 0;
		eyeSpawn = 0;
		diskSpawn = 0;
	}
	
	public void tick(){
		counterGlobal++;
		swerveSpawn++;
		eyeSpawn++;
		diskSpawn++;
		//swerving wave L screen
		if(counterGlobal == 1600) handler.addObject(new ShipEnemy(-50, 100, ID.ShipEnemy, handler));
		if(counterGlobal == 1000) handler.addObject(new ShipEnemy(-50, 100, ID.ShipEnemy, handler));
		
		if(counterGlobal >= 0 && counterGlobal <= 0 + (30* 4)){ // 30 =  time between spawns
			if(counterGlobal == 1) swerveSpawn = 0; //resets spawn timer at start
			
			if(swerveSpawn == 1) {            
				handler.addObject(new SwervingEnemy(200, -50, ID.SwervingEnemy, handler));
			}
			if(swerveSpawn == 30) {
				handler.addObject(new SwervingEnemy(200, -50, ID.SwervingEnemy, handler));			
			}
			if(swerveSpawn == 60) {
				handler.addObject(new SwervingEnemy(200, -50, ID.SwervingEnemy, handler));
			}
			if(swerveSpawn == 90) {
				handler.addObject(new SwervingEnemy(200, -50, ID.SwervingEnemy, handler));	
			}
			if(swerveSpawn == 130) {
				handler.addObject(new SwervingEnemy(200, -50, ID.SwervingEnemy, handler));	
			}
		}
		if(counterGlobal >= 240 && counterGlobal <= 240 + (30* 4)){ // 30 =  time between spawns
			if(counterGlobal == 240) swerveSpawn = 0; //resets spawn timer at start
			
			if(swerveSpawn == 0) {            
				handler.addObject(new SwervingEnemy(100, -50, ID.SwervingEnemy, handler));
			}
			if(swerveSpawn == 30) {
				handler.addObject(new SwervingEnemy(100, -50, ID.SwervingEnemy, handler));			
			}
			if(swerveSpawn == 60) {
				handler.addObject(new SwervingEnemy(100, -50, ID.SwervingEnemy, handler));
			}
			if(swerveSpawn == 90) {
				handler.addObject(new SwervingEnemy(100, -50, ID.SwervingEnemy, handler));	
			}
			if(swerveSpawn == 130) {
				handler.addObject(new SwervingEnemy(100, -50, ID.SwervingEnemy, handler));	
			}
		}
		
		
		
		if(counterGlobal == 620) handler.addObject(new PickupShip((Game.WIDTH / 2), -60, ID.PickupShip, "diagonalFire", handler));
		
		
		//spawns diskEnemies at L screen
		
		if(counterGlobal >= 500 && counterGlobal <= 500 + (13* 5)){ // 30 =  time between spawns, 9 = amount of enemies
			if(counterGlobal == 500) diskSpawn = 0; //resets spawn timer at start
			
			if(diskSpawn == 0) {            
				handler.addObject(new DiskEnemy(-5, 40, ID.DiskEnemy, handler));
			}
			if(diskSpawn == 13) {
				handler.addObject(new DiskEnemy(-5, 40, ID.DiskEnemy, handler));			
			}
			if(diskSpawn == 26) {
				handler.addObject(new DiskEnemy(-5, 40, ID.DiskEnemy, handler));
			}
			if(diskSpawn == 39) {
				handler.addObject(new DiskEnemy(-5, 40, ID.DiskEnemy, handler));		
			}
			if(diskSpawn == 52) {
				handler.addObject(new DiskEnemy(-5, 40, ID.DiskEnemy, handler));		
			}		
		} 	
		
		if(counterGlobal ==  600) handler.addObject(new CirclingEnemy(10, 50, ID.CirclingEnemy, handler));

		//spawns diskEnemies at R screen
		
		if(counterGlobal >= 610 && counterGlobal <= 610 + (13* 6)){ // 30 =  time between spawns, 6 = amount of enemies -1
			if(counterGlobal == 610) diskSpawn = 0; //resets spawn timer at start
			
			if(diskSpawn == 0) {            
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));
			}
			if(diskSpawn == 13) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));			
			}
			if(diskSpawn == 26) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));
			}
			if(diskSpawn == 39) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));		
			}
			if(diskSpawn == 52) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));		
			}
			if(diskSpawn == 65) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));		
			}
			if(diskSpawn == 78) {
				handler.addObject(new DiskEnemy(630, 40, ID.DiskEnemy, handler));		
			}
		} 
		if(counterGlobal ==  850) handler.addObject(new BasicEnemy(Game.WIDTH/2, -20, ID.BasicEnemy, handler));
		if(counterGlobal ==  900) handler.addObject(new BasicEnemy(Game.WIDTH/2, -20, ID.BasicEnemy, handler));
		if(counterGlobal ==  950) handler.addObject(new BasicEnemy(Game.WIDTH/2, -20, ID.BasicEnemy, handler));
		if(counterGlobal ==  1000) handler.addObject(new BasicEnemy(Game.WIDTH/2, -20, ID.BasicEnemy, handler));
		
		if(counterGlobal ==  1100+50) handler.addObject(new SideEnemy(635, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1130+50) handler.addObject(new SideEnemy(-50, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1160+50) handler.addObject(new SideEnemy(635, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1190+50) handler.addObject(new SideEnemy(-50, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1220+50) handler.addObject(new SideEnemy(635, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1250+50) handler.addObject(new SideEnemy(-50, 30, ID.SideEnemy, handler));
		
		if(counterGlobal ==  1100+200) handler.addObject(new CirclingEnemy(635, 30, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1130+200) handler.addObject(new CirclingEnemy(635, 40, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1160+200) handler.addObject(new CirclingEnemy(635, 30, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1190+200) handler.addObject(new CirclingEnemy(635, 40, ID.CirclingEnemy, handler));
		
		if(counterGlobal ==  1300+200) handler.addObject(new CirclingEnemy(635, 300, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1330+200) handler.addObject(new CirclingEnemy(635, 300, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1360+200) handler.addObject(new CirclingEnemy(635, 300, ID.CirclingEnemy, handler));
		if(counterGlobal ==  1390+200) handler.addObject(new CirclingEnemy(635, 300, ID.CirclingEnemy, handler));
		
		if(counterGlobal ==  1800) handler.addObject(new SideEnemy(635, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1830) handler.addObject(new SideEnemy(-50, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1860) handler.addObject(new SideEnemy(635, 30, ID.SideEnemy, handler));
		if(counterGlobal ==  1890) handler.addObject(new SideEnemy(-50, 30, ID.SideEnemy, handler));
		
		//spawns full circle of EyeEnemy
		if(counterGlobal >= 2000 && counterGlobal <= 2000 + (30* 9)){ // 30 =  time between spawns, 9 = amount of enemies
			if(counterGlobal == 2000) eyeSpawn = 0; //resets spawn timer at start
			
			if(eyeSpawn == 0) {            
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));
			}
			if(eyeSpawn == 30) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));			
			}
			if(eyeSpawn == 60) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));
			}
			if(eyeSpawn == 90) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 120) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 150) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 180) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 210) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 240) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
			if(eyeSpawn == 270) {
				handler.addObject(new EyeEnemy(580, 450, ID.EyeEnemy, handler));		
			}
		} 
		
		
		
									
		if(counterGlobal >= 3500){ // time for boss battle!!!
			if (counterGlobal == 3500) handler.addObject(new Boss1Enemy((Game.WIDTH / 2), (Game.HEIGHT / 2) -600, ID.Boss1Enemy, handler));
		
		}	
			
	}

	
}
