import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract class TopDownGraphics {
	private static final int viewportWidth = 6;
	private static final int viewportHeight = 4;
	static final int tileWidthHeight_Pixels = 32;
	static Map<Integer, BufferedImage> cachedTextures = Collections.synchronizedMap(new HashMap<Integer, BufferedImage>());
	static Map<Integer, String> mapTextures = Collections.synchronizedMap(new HashMap<Integer, String>());
	private static Point viewportUpperLeft;
	
	public static void loadTextures(){
		mapTextures.put(0, "textures/Default");
		mapTextures.put(1, "textures/wooden_floor");
		
	}
	
	public static Dimension getViewportSize() {
		return new Dimension(viewportWidth * 2 + 1, viewportHeight * 2 + 1);
	}
	
	public static void renderWorld(Graphics2D g, int t){
		if(Main.getPlayer() == null){
			return;
		}

		//draw map
		viewportUpperLeft = new Point(
				Main.getPlayer().position.x - (viewportWidth * tileWidthHeight_Pixels),
				Main.getPlayer().position.y - (viewportHeight * tileWidthHeight_Pixels));
		int max_y = viewportUpperLeft.y/tileWidthHeight_Pixels + getViewportSize().height;
		int max_x = viewportUpperLeft.x/tileWidthHeight_Pixels + getViewportSize().width;
		for(int y = viewportUpperLeft.y/tileWidthHeight_Pixels; y <= max_y; y++){
			for(int x = viewportUpperLeft.x/tileWidthHeight_Pixels; x <= max_x; x++){
				if(y >= 0 && y < Main.getLevel().getSize().getHeight() &&
						x >= 0 && x < Main.getLevel().getSize().getWidth()){
					int i = Main.getLevel().getTextureMap()[y][x];
					BufferedImage image = cachedTextures.get(i);
					if(image == null){
						String s = mapTextures.get(i);
						if(s == null){s = "textures/Default";}
						image = Main.loadImage(s);
						cachedTextures.put(i, image);
					}
					g.drawImage(image,
							x*tileWidthHeight_Pixels - viewportUpperLeft.x, 
							y*tileWidthHeight_Pixels - viewportUpperLeft.y, 
							Main.canvas);
				}
			}
		}
		//DEBUG only
		//draw npcs
		for(NPCCharacter testNPC : Main.testNPCs){
		g.drawImage(testNPC.getSprite(t),
				testNPC.position.x - viewportUpperLeft.x, 
				testNPC.position.y - viewportUpperLeft.y, 
				Main.canvas);
		}
		
		//draw player character
		g.drawImage(Main.getPlayer().getSprite(t),
				viewportWidth*tileWidthHeight_Pixels, 
				viewportHeight*tileWidthHeight_Pixels, 
				Main.canvas);
				
	}
}