package engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Renderer{
	static float scale = 5f;
	private static RenderMode renderMode = RenderMode.NONE;
	private static final Dimension size = TopDownGraphics.getViewportSize();
	private static BufferedImage scene;
	private static Graphics2D graphics;
	
	//transitions
	private static int fadeTime = -1;
	private static int fadeDuration = 40;
	private static int fadeDirection = -1;
	private static float step = 1f/ fadeDuration;
	private static RenderMode target;
	
	static void setRenderMode(RenderMode mode) {
		renderMode = mode;
	}
	static RenderMode getRenderMode() {
		return renderMode;
	}
	static int getFadeTime() {
		return fadeTime;
	}
	
	static void initGraphics(){
		scene = new BufferedImage(
				size.width * TopDownGraphics.tileWidthHeight_Pixels,
				size.height * TopDownGraphics.tileWidthHeight_Pixels,
				BufferedImage.TYPE_INT_RGB);
		graphics = scene.createGraphics();
		graphics.setFont(Main.getFont());
		//graphics.scale(scale, scale);
	}
	
	public static void fadeTo(RenderMode mode, int time) {
		fadeTime = 0;
		fadeDuration = time;
		fadeDirection = 1;
		target = mode;
		step = 1f/ fadeDuration;
	}
	
	static void fadeFromBlack(int time) {
		fadeTime = time;
		fadeDuration = -1;
		fadeDirection = -1;
		step = 1f/fadeTime;
	}
	
	static void render(){
		graphics.clearRect(0, 0, size.width * TopDownGraphics.tileWidthHeight_Pixels, 
				size.height * TopDownGraphics.tileWidthHeight_Pixels);
		switch (renderMode) {
		case NONE:
			break;
		case WORLD:
			TopDownGraphics.renderWorld(graphics, Main.getTime());
			break;
		case CUTSCENE:
			cutscene.CutScenes.render(graphics);
			break;
		case MAIN_MENU: 
			menu.StartMenu.render(graphics);
			break;
		default:
			break;
		}

		if(fadeTime != -1) {
			graphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, fadeTime * step));
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, size.width * TopDownGraphics.tileWidthHeight_Pixels, 
					size.height * TopDownGraphics.tileWidthHeight_Pixels);
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			fadeTime += fadeDirection;
			if(fadeTime == fadeDuration) {
				fadeDirection = -1;
				if(target != null) {
					setRenderMode(target);
				}
				target = null;
			}
		}
		
	}
	
	@SuppressWarnings("serial")
	static class RenderPanel extends JPanel {
		RenderPanel() {
			super();
			this.setBackground(Color.BLACK);
		}
		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			if(scene != null) {
				g.drawImage(scene, 0, 0, (int) (scene.getWidth() * scale), 
						(int) (scene.getHeight() * scale), this);
			}
		}
	}
	
	public enum RenderMode{
		MAIN_MENU, PAUSE_MENU, WORLD, INVENTORY, COMBAT, CUTSCENE, NONE
	}
}