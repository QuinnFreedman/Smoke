import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class Renderer{
	static final float scale = 2f;
	static RenderMode renderMode = RenderMode.WORLD;
	
	@SuppressWarnings("serial")
	public static class RederPanel extends JPanel {
		public RederPanel() {
			super();
			this.setBackground(Color.BLACK);
		}
		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(scale, scale);
			switch (renderMode) {
			case WORLD:
				TopDownGraphics.renderWorld(g2, Main.getTime());
				break;
			default:
				break;
			}
		}
	}
	public static enum RenderMode{
		MENU, WORLD, INVENTORY, COMBAT, CUTSCENE
	}
}