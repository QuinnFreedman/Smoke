package cutscene;

import java.awt.Dimension;
import java.awt.Graphics2D;

import main.Point;
import main.Renderer;
import main.TopDownGraphics;

public abstract class CutScenes {
	private static final float ANIMATION_SPEED = 2f;
	private static boolean scenesBuilt = false;
	private static Scene activeScene = null;
	private static int animTime = 0;
	static Scene getActiveScene() {
		return activeScene;
	}
	
	public static void setScene(Scene scene) {
		if(!scenesBuilt) {
			buildScenes();
		}
		animTime = 0;
		activeScene = scene;
		if(scene != null) {
			scene.load();
			Renderer.setRenderMode(Renderer.RenderMode.CUTSCENE);
		}
	}
	
	public static void render(Graphics2D g) {
		if(activeScene == null) {
			return;
		}
		animTime++;
		if(!activeScene.draw(g, Math.round(animTime/ANIMATION_SPEED))) {
			setScene(null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* scenes */
	public static final Scene SCRUPLES = new Scene(100);
	
	static void buildScenes() {
		if(scenesBuilt) {
			return;
		}
		scenesBuilt = true;
		SCRUPLES.addAnimation(new Animation("animation_frames/Scruples", 
				Animation.mode.LOOP_DELAY, 
				new Point(
						TopDownGraphics.getViewportSize().width / 2 - 1, 
						TopDownGraphics.getViewportSize().height / 2 - 1)
								.scale(TopDownGraphics.tileWidthHeight_Pixels),
				new Dimension(TopDownGraphics.tileWidthHeight_Pixels * 3, 
						TopDownGraphics.tileWidthHeight_Pixels * 3)).setDelayTime(20));
	};
}