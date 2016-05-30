package gameplay;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import cutscene.CutScenes;
import debug.out;
import engine.AnimationSet;
import engine.Atmosphere;
import engine.Level;
import engine.Main;
import engine.Point;
import engine.TopDownGraphics;

public class PlayerCharacter extends Character{

	private AnimationSet walkingAnimation;
	private AnimationSet swordAnimation;
	
	private static class KeyPress {
		//keys held down
		static boolean left  = false;
		static boolean right = false;
		static boolean up 	 = false;
		static boolean down  = false;
		
		//key pressed since last frame
		static boolean leftPressed  = false;
		static boolean rightPressed = false;
		static boolean upPressed    = false;
		static boolean downPressed  = false;
		
		static void reset() {
			 leftPressed  = false;
			 rightPressed = false;
			 upPressed    = false;
			 downPressed  = false;
			
		}
	}
	
	public PlayerCharacter(Level level, Point mapPosition, boolean male, Race race, String cclass) {
		super(level, mapPosition, male, race, cclass);
		this.animFrames = 7;
		
		if(male) {
			this.walkingAnimation = this.sprite;
			this.swordAnimation = new AnimationSet("character_tiles/male_sword_spritesheet");
		} else {
			this.walkingAnimation = this.sprite;
			this.swordAnimation = new AnimationSet("character_tiles/female_sword_spritesheet");
		}
		//this.sprite = new AnimationSet("character_tiles/Harper_spritesheet", this.animFrames);
		
	}
	
	@Override
	protected void doLogic() {
		super.doLogic();
		int dx = 0;
		int dy = 0;
		if(KeyPress.up || KeyPress.upPressed) {
			dy = -1;
		} else if(KeyPress.down || KeyPress.downPressed) {
			dy = 1;
		} else if(KeyPress.left || KeyPress.leftPressed) {
			dx = -1;
		} else if(KeyPress.right || KeyPress.rightPressed) {
			dx = 1;
		}

		KeyPress.reset();
		
		Point tilePoint = new Point(
				this.previousPosition.x/TopDownGraphics.tileWidthHeight_Pixels + dx,
				this.previousPosition.y/TopDownGraphics.tileWidthHeight_Pixels + dy);
		
		if(!Main.getLevel().collides(tilePoint)){
			this.moveTo(tilePoint);
		}
		
	}
	
	@Override
	protected void simulate() {
		super.simulate();
		final float dx = (this.targetPosition.x - this.previousPosition.x)/inverseSpeed;
		final float dy = (this.targetPosition.y - this.previousPosition.y)/inverseSpeed;
		Atmosphere.move(dx * -1.5f, dy * -1.5f);
	}
	
	public static void handleKeyboardInput(KeyEvent e, boolean keyPressed){
		int c = e.getKeyCode();
		//TODO DEBUG
		if(keyPressed){
			if(c == KeyEvent.VK_SPACE) {
				Main.getPlayer().sprite = (Main.getPlayer().sprite == Main.getPlayer().walkingAnimation) ?
						Main.getPlayer().swordAnimation : Main.getPlayer().walkingAnimation;
			}
			/*if(c == KeyEvent.VK_COMMA) {
				Atmosphere.light = !Atmosphere.light;
			} else if(c == KeyEvent.VK_PERIOD) {
				Atmosphere.shadow = !Atmosphere.shadow;
			} else */if(c == KeyEvent.VK_1) {
				CutScenes.setScene(CutScenes.SCRUPLES);
			}
		}
		
		if(c == KeyEvent.VK_LEFT){
			if(!KeyPress.left && keyPressed) {
				KeyPress.leftPressed = true;
			}
			KeyPress.left = keyPressed;
			if(keyPressed){
				KeyPress.right = false;
				KeyPress.down = false;
				KeyPress.up = false;
			}
		}else if(c == KeyEvent.VK_RIGHT){
			if(!KeyPress.right && keyPressed) {
				KeyPress.rightPressed = true;
			}
			KeyPress.right = keyPressed;
			if(keyPressed){
				KeyPress.left = false;
				KeyPress.down = false;
				KeyPress.up = false;
			}
		}else if(c == KeyEvent.VK_UP){
			if(!KeyPress.up && keyPressed) {
				KeyPress.upPressed = true;
			}
			KeyPress.up = keyPressed;
			if(keyPressed){
				KeyPress.left = false;
				KeyPress.right = false;
				KeyPress.down = false;
			}
		}else if(c == KeyEvent.VK_DOWN){
			if(!KeyPress.down && keyPressed) {
				KeyPress.downPressed = true;
			}
			KeyPress.down = keyPressed;
			if(keyPressed){
				KeyPress.left = false;
				KeyPress.right = false;
				KeyPress.up = false;
			}
		}
	}
}