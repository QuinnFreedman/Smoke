import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Renderer{
	static final float scale = 2f;
	static RenderMode renderMode = RenderMode.WORLD;
	private static final Dimension size = TopDownGraphics.getViewportSize();
	private static BufferedImage scene;
	private static Graphics2D graphics;
	
	public static void initGraphics(){
		scene = new BufferedImage(
				(int) (size.width * TopDownGraphics.tileWidthHeight_Pixels * scale),
				(int) (size.height * TopDownGraphics.tileWidthHeight_Pixels  * scale), 
				BufferedImage.TYPE_INT_RGB);
		graphics = scene.createGraphics();
		graphics.scale(scale, scale);
	}
	
	public static void render(){
		switch (renderMode) {
		case WORLD:
			graphics.clearRect(0, 0, size.width * TopDownGraphics.tileWidthHeight_Pixels, 
					size.height * TopDownGraphics.tileWidthHeight_Pixels);
			TopDownGraphics.renderWorld(graphics, Main.getTime());
			break;
		default:
			break;
		}
		//graphics.dispose();
	}
	
	@SuppressWarnings("serial")
	public static class RederPanel extends JPanel {
		public RederPanel() {
			super();
			this.setBackground(Color.BLACK);
		}
		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			g.drawImage(scene, 0, 0, this);
		}
	}
	
	public static enum RenderMode{
		MENU, WORLD, INVENTORY, COMBAT, CUTSCENE
	}
}