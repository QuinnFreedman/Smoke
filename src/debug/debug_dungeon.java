package debug;

import main.Point;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import proceduralGeneration.DungeonBuilder;
import proceduralGeneration.Pathing;
import proceduralGeneration.Room;

public abstract class debug_dungeon {
	
	public static void buildDungeon() {
		long seed = (long) (Math.random() * Long.MAX_VALUE);
		Random rng = new Random(seed);
		
		final int MAP_WIDTH = 80;
		final int MAP_HEIGHT = 60;
		final int NUM_ROOMS = 20;
		
		int[][] dungeon = new int[MAP_HEIGHT][MAP_WIDTH];
		ArrayList<Room> rooms = new ArrayList<Room>(NUM_ROOMS);
		for(int i = 0; i < NUM_ROOMS; i++) {
			rooms.add(new Room(MAP_WIDTH, MAP_HEIGHT, rng));
		}
		
		DungeonBuilder.collideRooms(rooms, MAP_WIDTH, MAP_HEIGHT);
		
		for(int r = 0; r < rooms.size(); r++){
			for(int i = 0; i < rooms.get(r).roomWalls.size(); i++){
				if(rooms.get(r).roomWalls.get(i).x >= 0){
					dungeon[rooms.get(r).roomWalls.get(i).y][rooms.get(r).roomWalls.get(i).x] = 1;
				}
			}
			for(int y1 = rooms.get(r).ypos; y1 < rooms.get(r).ypos + rooms.get(r).h; y1++){	
				for(int x1 = rooms.get(r).xpos; x1 < rooms.get(r).xpos + rooms.get(r).w; x1++){
					if(dungeon[y1][x1] == 0){
						dungeon[y1][x1] = 2;
					}
				}
				
			}
		}
		
		ArrayList<Point> doors = new ArrayList<Point>();
		for(int i = 0; i < rooms.size(); i++){
			for(int e = 0; e < rooms.get(i).roomDoors.size(); e++){
				doors.add(rooms.get(i).roomDoors.get(e));
			}
		}
		
		Pathing.setPaths(dungeon, doors, rooms, false);
		
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
			
		}
		
	}
}