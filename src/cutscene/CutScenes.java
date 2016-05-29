package cutscene;

import java.awt.Dimension;
import java.awt.Graphics2D;

import debug.out;
import engine.Point;
import engine.Renderer;
import engine.TopDownGraphics;

public abstract class CutScenes {
	private static boolean scenesBuilt = false;
	private static Scene activeScene = null;
	private static int animTime = 0;
	static Scene getActiveScene() {
		return activeScene;
	}
	private static int fadeTime;
	
	public static void setScene(Scene scene, int fadeTime) {
		if(!scenesBuilt) {
			buildScenes();
		}
		animTime = 0;
		activeScene = scene;
		CutScenes.fadeTime = fadeTime;
		if(scene != null) {
			scene.load();
			Renderer.fadeTo(Renderer.RenderMode.CUTSCENE, fadeTime);
		}
	}
	
	public static void setScene(Scene scene) {
		setScene(scene, 30);
	}
	
	public static void render(Graphics2D g) {
		if(activeScene == null) {
			return;
		}
		animTime++;
		//int t = Math.round(animTime/ANIMATION_SPEED);
		activeScene.draw(g, animTime);
		
		if(animTime == activeScene.getDuration() - fadeTime) {
			Renderer.fadeTo(Renderer.RenderMode.WORLD, fadeTime);
			if(animTime >= activeScene.getDuration()) {
				setScene(null);
			}
		}
	}
	
	
	
	/* scenes */
	public static final Scene SCRUPLES = new Scene(100);
	public static final Scene CANDLE = new Scene(200);
	
	static void buildScenes() {
		if(scenesBuilt) {
			return;
		}
		scenesBuilt = true;
		
		//scruples
		SCRUPLES.addAnimation(new Animation("animation_frames/Scruples", 
				Animation.mode.LOOP_DELAY, 
				new Point(TopDownGraphics.getViewportSize_pixels().width / 2 
							- TopDownGraphics.tileWidthHeight_Pixels * 1.5f,
						TopDownGraphics.getViewportSize_pixels().height / 2 
						- TopDownGraphics.tileWidthHeight_Pixels * 1.5f),
				new Dimension(TopDownGraphics.tileWidthHeight_Pixels * 3, 
						TopDownGraphics.tileWidthHeight_Pixels * 3)).setDelayTime(20));
		
		//candle
		CANDLE.addAnimation(new Animation("animation_frames/Candle", 
				Animation.mode.LOOP, 
				new Point(0, 0),
				new Dimension((int) (222 * 2.3), (int) (130 * 2.3))));
		//CANDLE.addPostProcess(PostProcess.CANDLE_POST_PROCESS);
	};
}