package game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
	
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load(){
		try {
			soundMap.put("player_shoot", new Sound("res/laser.ogg"));
			soundMap.put("player_twinTurret", new Sound("res/player_shoot2.wav"));
			soundMap.put("player_laserCannon", new Sound("res/player_shoot2.wav"));
			soundMap.put("player_hit", new Sound("res/player_hit.ogg"));
			soundMap.put("enemy_exp", new Sound("res/expHard.ogg"));
			soundMap.put("enemy_hit", new Sound("res/enemy_hit_short.ogg"));
			soundMap.put("enemy_shoot", new Sound("res/enemy_shoot.ogg"));
			soundMap.put("star_shoot", new Sound("res/star_shoot.ogg"));
			soundMap.put("player_exp", new Sound("res/player_exp.wav"));
			soundMap.put("pickup_health", new Sound("res/pickup.ogg"));
			soundMap.put("enemyHitShort", new Sound("res/enemy_hit_short.ogg"));
			soundMap.put("diagonalVoice", new Sound("res/diagonalVoice.ogg"));
			musicMap.put("music", new Music("res/space_theme_fix.ogg"));
			musicMap.put("game_over", new Music("res/game_over.ogg"));
			musicMap.put("menu_music", new Music("res/menu_music.ogg"));
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Music getMusic(String key){
		return musicMap.get(key);
	}
	public static Sound getSound(String key){
		return soundMap.get(key);
	}
	
}
