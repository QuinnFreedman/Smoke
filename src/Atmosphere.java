import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
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
	
	static void draw(Graphics2D g, int t) {
		final int width = Math.round(textureSize.width * 
				(TopDownGraphics.tileWidthHeight_Pixels / 32));
		final int height = Math.round(textureSize.height * 
				(TopDownGraphics.tileWidthHeight_Pixels / 32));
		final Dimension viewport = new Dimension(
				TopDownGraphics.getViewportSize().width * TopDownGraphics.tileWidthHeight_Pixels, 
				TopDownGraphics.getViewportSize().height * TopDownGraphics.tileWidthHeight_Pixels);
		
		for (int y = -height; y < viewport.height + height; y += height) {
			for (int x = -width; x < viewport.width + width; x += width) {
				g.drawImage(smoke, x + position.x, y + position.y, width, height, null);
			}
		}
		
		float viewRadius = .5f;
		RadialGradientPaint grad = new RadialGradientPaint(
		        		 new Point2D.Float(viewport.width/2f, viewport.height/2f),
		        		 viewport.height/2f,
		        		 new float[]{0.0f, 1f},
		        		 new Color[]{new Color(0f,0f,0f,0f), new Color(0f,0f,0f,.9f)});
		
		RadialGradientPaint torchlight = new RadialGradientPaint(
		        		 new Point2D.Float(viewport.width/2f, viewport.height/2f),
		        		 viewport.height*viewRadius,
		        		 new float[]{
		        			 0.0f, 
		        			 stackedNoise(t)+ 0.25f,
		        			 0.8f},
		        		 new Color[]{
		        			 	new Color(0f,0f,0f,0f), 
		        			 	new Color(.5f,.4f,0f,.3f * .5f / viewRadius),
		        			 	new Color(0f,0f,0f,0f)});
		
		g.setPaint(grad);
		g.fillRect(0, 0, viewport.width, viewport.height);
		g.setPaint(torchlight);
		g.fillRect(0, 0, viewport.width, viewport.height);
	}
	
	private static float stackedNoise(int t) {
		return (float) (Math.sin(2 * t)) * 0.003f
			 + (float) (Math.sin(.7f * t)) * 0.003f
		 	 + (float) (Math.sin(0.25f * t)) * 0.01f;
	}
}