package proceduralGeneration;

import java.util.ArrayList;
import java.util.Random;

import engine.Point;

abstract class DungeonBuilder{
	
	static int[][] buildDungeon(int dungeonWidth, int dungeonHeight,
								int numRooms, Random rng) {
		
		int[][] dungeon = new int[dungeonHeight][dungeonWidth];
		ArrayList<Room> rooms = new ArrayList<>(numRooms);
		for(int i = 0; i < numRooms; i++) {
			rooms.add(new Room(dungeonWidth, dungeonHeight, rng));
		}
		
		DungeonBuilder.collideRooms(rooms, dungeonWidth, dungeonHeight);
		
		for(int r = 0; r < rooms.size(); r++){
			for(int i = 0; i < rooms.get(r).roomWalls.size(); i++){
				if(rooms.get(r).roomWalls.get(i).x >= 0){
					dungeon[rooms.get(r).roomWalls.get(i).y][rooms.get(r).roomWalls.get(i).x] = 1;
				}
			}
			for(int y1 = rooms.get(r).y; y1 < rooms.get(r).y + rooms.get(r).height; y1++){	
				for(int x1 = rooms.get(r).x; x1 < rooms.get(r).x + rooms.get(r).width; x1++){
					if(dungeon[y1][x1] == 0){
						dungeon[y1][x1] = 2;
					}
				}
				
			}
		}
		
		ArrayList<Point> doors = new ArrayList<>();
		for(int i = 0; i < rooms.size(); i++){
			for(int e = 0; e < rooms.get(i).roomDoors.size(); e++){
				doors.add(rooms.get(i).roomDoors.get(e));
			}
		}
		
		Pathing.setPaths(dungeon, doors, rooms, false);
		return dungeon;
	}
	
	private static void collideRooms(ArrayList<? extends Rectangle> rooms, int width, int height){
		collideRooms(rooms, width, height, 1);
	}
	
	static void collideRooms(ArrayList<? extends Rectangle> rooms, int width, int height, int padding){
		int[][] overlapWeights = new int[height][width];
		ArrayList<int[]> vectors = new ArrayList<int[]>();
		
		int itt = 0;
		do{
			setWeights(overlapWeights, rooms, padding);
			for(int r = 0; r < rooms.size(); r++) {
				int[][] overlap = new int[2][2];
					//[[NW,NE],
					// [SW,SE]]
				int vectorNE;
				int vectorSE;
				int vectorE;
				int vectorS;
				float halfWidth = rooms.get(r).width >> 1;
				float halfHeight = rooms.get(r).height >> 1;
				for(int x = 0; x < rooms.get(r).width; x++){
					for(int y = 0; y < rooms.get(r).height; y++){
						if((float)x != halfWidth && (float)y != halfHeight
								&& y+rooms.get(r).y > 0 && x+rooms.get(r).x > 0
								&& y+rooms.get(r).y < height && x+rooms.get(r).x < width
								)
							overlap[((y < halfHeight) ? 0 : 1)][((x < halfWidth) ? 0 : 1)] += overlapWeights[y+rooms.get(r).y][x+rooms.get(r).x] - 1;
					}
				}
				vectorNE = (overlap[1][0] == overlap[0][1]) ? 0 : (int) (Math.max(Math.round(Math.sqrt((float) overlap[1][0]) - Math.sqrt((float) overlap[0][1])), ((overlap[1][0] - overlap[0][1] > 0) ? 1 : -1)));
				vectorSE = (overlap[0][0] == overlap[1][1]) ? 0 : (int) (Math.max(Math.round(Math.sqrt((float) overlap[0][0]) - Math.sqrt((float) overlap[1][1])), ((overlap[0][0] - overlap[1][1] > 0) ? 1 : -1)));
				vectorE = (int) Math.round(0.7f*(vectorSE + vectorNE));
				vectorS = (int) Math.round(0.7f*(vectorSE - vectorNE));
				if(r >= vectors.size()){
					vectors.add(new int[]{vectorE,vectorS});
				}else{
					vectors.set(r, new int[]{vectorE,vectorS});
				}

			}

			for(int r = 0; r < rooms.size(); r++){
				rooms.get(r).x += vectors.get(r)[0];
				if(rooms.get(r).x < 0)
					rooms.get(r).x = 0;
				else if(rooms.get(r).x + rooms.get(r).width > width)
					rooms.get(r).x = width - rooms.get(r).width;
				
				rooms.get(r).y += vectors.get(r)[1];
				if(rooms.get(r).y < 0)
					rooms.get(r).y = 0;
				else if(rooms.get(r).y + rooms.get(r).height > height)
					rooms.get(r).y = height - rooms.get(r).height;
			}
			itt++;
		}while(itt < 10);
		
		for (int r = 0; r < rooms.size(); r++) {
			rooms:{
			for(int x = 0; x < rooms.get(r).width; x++){
				for(int y = 0; y < rooms.get(r).height; y++){
					if(overlapWeights[y+rooms.get(r).y][x+rooms.get(r).x] > 1){
						rooms.remove(r);
						r--;
						setWeights(overlapWeights, rooms, padding);
						break rooms;
					}
				}
			}
			if(rooms.get(r) instanceof Room) {
				((Room) rooms.get(r)).construct();
			}
			}
		}
	}
	
	private static void setWeights(int[][] overlapWeight, ArrayList<? extends Rectangle> rooms, int padding){
		for(int y = 0; y < overlapWeight.length; y++){
			for(int x = 0; x < overlapWeight[0].length; x++){
				overlapWeight[y][x] = 0;				
			}
		}
		for(int r = 0; r < rooms.size(); r++){
			for(int x = 0-padding; x < rooms.get(r).width + padding; x++){
				if(x+rooms.get(r).x >= 0 && x+rooms.get(r).x < overlapWeight[0].length){
					for(int y = 0-padding; y < rooms.get(r).height + padding; y++){
						if(y+rooms.get(r).y >= 0 && y+rooms.get(r).y < overlapWeight.length){
							overlapWeight[y+rooms.get(r).y][x+rooms.get(r).x] += 1;
						}
					}
				}
			}
		}
	}
}