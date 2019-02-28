package game;

public class Camera {

	private static float x;
	private static float y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject player){
		
		x = -player.getX() + Game.WIDTH/2;
		y = -player.getY() + Game.HEIGHT/2;
		x = Game.clamp(x,  -(Game.WIDTH+64),  Game.WIDTH+64);
		y = Game.clamp(y,  -0,  0);
	}
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public static float getX(){
		return x;
		
	}
	public static float getY(){
		return y;
		
	}
	
}

