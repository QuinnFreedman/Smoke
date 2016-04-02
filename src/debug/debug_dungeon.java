package debug;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import proceduralGeneration.WorldBuilder;

public abstract class debug_dungeon {
	
	public static void buildDungeon() {
		long seed = (long) (Math.random() * Long.MAX_VALUE);
		out.pln("Seed = "+seed);
		out.pln("building world...");
		Random rng = new Random(seed);
		
		/*final int MAP_WIDTH = 80;
		final int MAP_HEIGHT = 60;
		final int NUM_ROOMS = 20;
		
		int[][] dungeon = DungeonBuilder.buildDungeon(MAP_WIDTH, MAP_HEIGHT, NUM_ROOMS, rng);
		
		BufferedImage img = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		for(int y = 0; y < MAP_HEIGHT; y++) {
			for(int x = 0; x < MAP_WIDTH; x++) {
				Color color = dungeon[y][x] == 0 ? Color.WHITE : 
							  dungeon[y][x] == 1 ? Color.GRAY :
							  dungeon[y][x] == 2 ? Color.BLACK :
							  					   new Color(.8f,.8f,.8f);
				img.setRGB(x, y, color.getRGB());
			}
		}
		try {
			File outputfile = new File("test.png");
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			
		}*/
		
		WorldBuilder.buildWorld2(rng);
		
	}
	
	public static void drawHeightmap(double[][] elevation, double seaLevel) {
		BufferedImage img = new BufferedImage(elevation[0].length, elevation.length, BufferedImage.TYPE_3BYTE_BGR);
		for(int y = 0; y < elevation.length; y++) {
			for(int x = 0; x < elevation[0].length; x++) {
				Color color = elevation[y][x] < seaLevel ? Color.BLACK : 
						new Color((float) elevation[y][x],(float) elevation[y][x],(float) elevation[y][x]);
				img.setRGB(x, y, color.getRGB());
			}
		}
		try {
			File outputfile = new File("heightmap.png");
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			
		}
	}
}