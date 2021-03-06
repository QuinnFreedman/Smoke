package engine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class TopDownGraphics {
	private static final int viewportWidth = 6;
	private static final int viewportHeight = 4;
	public static final int tileWidthHeight_Pixels = 32;
	static Map<Integer, BufferedImage> cachedTextures = Collections.synchronizedMap(
			new HashMap<Integer, BufferedImage>()
		);
	static Map<Integer, String> mapTextures = Collections.synchronizedMap(
			new HashMap<Integer, String>()
		);
	private static Point viewportUpperLeft;
	
	static void loadTextures(){
		// <0 = wall
		// >0 = floor
		mapTextures.put(0, "textures/Default");
		mapTextures.put(1, "textures/Doyt");
		mapTextures.put(2, "textures/grass/grass_1");
		mapTextures.put(3, "textures/grass/grass_0");
		mapTextures.put(4, "textures/Unused/Chapel Floor");
		mapTextures.put(5, "textures/flagstone");
		mapTextures.put(6, "textures/wooden_floor");
		mapTextures.put(7, "textures/Gwassy Doyt");
		mapTextures.put(-51, "textures/Unused/Chapel Corner 4");
		mapTextures.put(-15, "textures/Unused/Chapel Corner 3");
		mapTextures.put(-52, "textures/Unused/Chapel Corner 1");
		mapTextures.put(-25, "textures/Unused/Chapel Corner 2");
		mapTextures.put(-8, "textures/Unused/Chapel Wall Vertical");
		mapTextures.put(-9, "textures/Unused/Chapel Wall Horizontal");
		mapTextures.put(-10, "textures/Unused/Chapel Window");
		mapTextures.put(11, "textures/Unused/image (9)");
		
	}
	
	static Dimension getViewportSize() {
		return new Dimension(viewportWidth * 2 + 1, viewportHeight * 2 + 1);
	}
	
	public static Dimension getViewportSize_pixels() {
		return new Dimension((viewportWidth * 2 + 1) * tileWidthHeight_Pixels,
							(viewportHeight * 2 + 1) * tileWidthHeight_Pixels);
	}
	
	static void renderWorld(Graphics2D g, int t){
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
					
					int j = Main.getLevel().getStaticSprites()[y][x];
					if(j == 0){
						continue;
					}
					BufferedImage image2 = cachedTextures.get(j);
					if(image2 == null){
						String s = mapTextures.get(j);
						if(s == null){ continue; }
						image2 = Main.loadImage(s);
						cachedTextures.put(j, image2);
					}
					
					g.drawImage(image2,
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
		
		Point[] testPoints = new Point[] {
				viewportUpperLeftOnMap.copy(),
				viewportUpperLeftOnMap.translate(getViewportSize().width, 0),
				viewportUpperLeftOnMap.translate(getViewportSize().width, getViewportSize().height),
				viewportUpperLeftOnMap.translate(0, getViewportSize().height)
		};

		int width = Main.getLevel().getSize().width;
		int height = Main.getLevel().getSize().height;
		for(Point p : testPoints) {
			if(p.x >= width) {
				p.x = width - 1;
			} else if(p.x < 0) {
				p.x = 0;
			}
			if(p.y >= height) {
				p.y = height - 1;
			} else if(p.y < 0) {
				p.y = 0;
			}

			Chunk a = Main.getLevel().getChunk(p);
			if(a != null && !visibleChunks.contains(a)) { visibleChunks.add(a); }
		}
		
		//draw all entities in visible chunks
		for(Chunk chunk : visibleChunks) {
			//DEBUG: visualize rendered chunks
			/*g.setColor(Color.RED);
			g.fillRect(chunk.getPosition().x * tileWidthHeight_Pixels - viewportUpperLeft.x,
					chunk.getPosition().y * tileWidthHeight_Pixels - viewportUpperLeft.y,
					chunk.getSize().width  * tileWidthHeight_Pixels,
					chunk.getSize().height * tileWidthHeight_Pixels);
			*/
			chunk.sortEntites();
			for(Entity e : chunk.getEntities()) {
				//TODO DEBUG:
				assert(e.position != null);
				assert(e.getSize() != null);
				assert(e.getSprite(t) != null);

				if(e == Main.getPlayer()) {
					Atmosphere.drawTorchlight(g, t);
				}

				g.drawImage(e.getSprite(t),
						e.position.x - viewportUpperLeft.x, 
						e.position.y - viewportUpperLeft.y,
						e.getSize().width * tileWidthHeight_Pixels,
						e.getSize().height * tileWidthHeight_Pixels,
						Main.canvas);
			}
		}
		
		Atmosphere.draw(g, t);
	}
}