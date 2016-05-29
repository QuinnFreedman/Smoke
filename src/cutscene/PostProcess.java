package cutscene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import engine.TopDownGraphics;

interface PostProcess {
	public boolean render(Graphics2D g, int t);

	
	
	static final PostProcess CANDLE_POST_PROCESS = new PostProcess() {
		int width = 200, 
			height = 130,
			center_x = 40,
			center_y = 50;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		
		static final float radius = 60;
		@Override
		public boolean render(Graphics2D g, int t) {

			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					float xx = center_x - x,
						  yy = center_y - y;
					
					float distance = (float) Math.sqrt(xx * xx - yy * yy);
					
					//int color = new Color(.5f,.4f,0f, distance/radius).getARGB();
					
					
				}
			}
			
			Dimension size = TopDownGraphics.getViewportSize_pixels();
			g.drawImage(image, 0, 0, size.width, size.height, null);
			
			
			return false;
		}
		
		private float stackedNoise(int t) {
			return (float) (Math.sin(2 * t)) * 0.003f
				 + (float) (Math.sin(.7f * t)) * 0.003f
			 	 + (float) (Math.sin(0.25f * t)) * 0.01f;
		}
	};
}