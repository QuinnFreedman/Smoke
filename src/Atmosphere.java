import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class Atmosphere {
	private static BufferedImage smoke = Main.loadImage("atmospheres/smoke");
	private static Dimension textureSize = new Dimension(
			smoke.getWidth(), smoke.getHeight());
	private static Point position = new Point(
			3 * TopDownGraphics.tileWidthHeight_Pixels, 
			3 * TopDownGraphics.tileWidthHeight_Pixels);
	private static FloatPoint actualPositon = new FloatPoint(position);
	
	static void move(float x, float y) {
		actualPositon.x += x;
		actualPositon.y += y;
		
		position.x = Math.round(actualPositon.x) % textureSize.width;
		position.y = Math.round(actualPositon.y) % textureSize.height;
	}
	
	static void draw(Graphics2D g) {
		final int width = Math.round(textureSize.width * 
				(TopDownGraphics.tileWidthHeight_Pixels / 32));
		final int height = Math.round(textureSize.height * 
				(TopDownGraphics.tileWidthHeight_Pixels / 32));
		final Dimension viewport = new Dimension(
				TopDownGraphics.getViewportSize().width * TopDownGraphics.tileWidthHeight_Pixels, 
				TopDownGraphics.getViewportSize().height * TopDownGraphics.tileWidthHeight_Pixels);
		
		/*RadialGradientPaint grad =
		         new RadialGradientPaint(
		        		 new Point2D.Float(
		        				 Main.getPlayer().getPosition().x,
		        				 Main.getPlayer().getPosition().y),
		        		 10, new float[]{0.0f, 1f},
		        		 new Color[]{g.getBackground(), Color.white});*/
		
		for (int y = -height; y < viewport.height + height; y += height) {
			for (int x = -width; x < viewport.width + width; x += width) {
				g.drawImage(smoke, x + position.x, y + position.y, null);
			}
		}
	}
}