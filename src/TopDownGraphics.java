import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract class TopDownGraphics {
	private static final int viewportWidth = 6;
	private static final int viewportHeight = 4;
	static final int tileWidthHeight_Pixels = 32;
	static Map<Integer, BufferedImage> cachedTextures = Collections.synchronizedMap(
			new HashMap<Integer, BufferedImage>()
		);
	static Map<Integer, String> mapTextures = Collections.synchronizedMap(
			new HashMap<Integer, String>()
		);
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
							tileWidthHeight_Pixels, tileWidthHeight_Pixels,
							Main.canvas);
				}
			}
		}
		//calculate visible chunks
		Point viewportUpperLeftOnMap = Main.getPlayer().getMapLocation().translate(
				-viewportWidth, -viewportHeight);
		ArrayList<Chunk> visibleChunks = new ArrayList<Chunk>();
		
		Chunk[] testChunks = new Chunk[4];
		testChunks[0] = Main.getLevel().getChunk(viewportUpperLeftOnMap);
		testChunks[1] = Main.getLevel().getChunk(viewportUpperLeftOnMap.translate(
				viewportWidth + 1, 0));
		testChunks[2] = Main.getLevel().getChunk(viewportUpperLeftOnMap.translate(
				viewportWidth + 1, viewportHeight + 1));
		testChunks[3] = Main.getLevel().getChunk(viewportUpperLeftOnMap.translate(
				0, viewportHeight + 1));
		
		for(Chunk a : testChunks) {
			if(a != null && !visibleChunks.contains(a)) { visibleChunks.add(a); }
		}
		
		//draw all entities in visible chunks
		for(Chunk chunk : visibleChunks) {
			chunk.sortEntites();
			for(Entity e : chunk.getEntities()) {
				g.drawImage(e.getSprite(t),
						e.position.x - viewportUpperLeft.x, 
						e.position.y - viewportUpperLeft.y,
						e.getSize().width * tileWidthHeight_Pixels,
						e.getSize().height * tileWidthHeight_Pixels,
						Main.canvas);
			}
		}
		
		Atmosphere.draw(g);
	}
}