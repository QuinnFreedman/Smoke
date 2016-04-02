package proceduralGeneration;

import java.util.ArrayList;

public abstract class DungeonBuilder{
	
	public static void collideRooms(ArrayList<Room> rooms, int width, int height){
		collideRooms(rooms, width, height, 1);
	}
	
	public static void collideRooms(ArrayList<Room> rooms, int width, int height, int padding){
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
				float halfWidth = rooms.get(r).w >> 1;
				float halfHeight = rooms.get(r).h >> 1;
				for(int x = 0; x < rooms.get(r).w; x++){
					for(int y = 0; y < rooms.get(r).h; y++){
						if((float)x != halfWidth && (float)y != halfHeight
								&& y+rooms.get(r).ypos > 0 && x+rooms.get(r).xpos > 0
								&& y+rooms.get(r).ypos < height && x+rooms.get(r).xpos < width
								)
							overlap[((y < halfHeight) ? 0 : 1)][((x < halfWidth) ? 0 : 1)] += overlapWeights[y+rooms.get(r).ypos][x+rooms.get(r).xpos] - 1;
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
				rooms.get(r).xpos += vectors.get(r)[0];
				if(rooms.get(r).xpos < 0)
					rooms.get(r).xpos = 0;
				else if(rooms.get(r).xpos + rooms.get(r).w > width)
					rooms.get(r).xpos = width - rooms.get(r).w;
				
				rooms.get(r).ypos += vectors.get(r)[1];
				if(rooms.get(r).ypos < 0)
					rooms.get(r).ypos = 0;
				else if(rooms.get(r).ypos + rooms.get(r).h > height)
					rooms.get(r).ypos = height - rooms.get(r).h;
			}
			itt++;
		}while(itt < 10);
		
		for (int r = 0; r < rooms.size(); r++) {
			rooms:{
			for(int x = 0; x < rooms.get(r).w; x++){
				for(int y = 0; y < rooms.get(r).h; y++){
					if(overlapWeights[y+rooms.get(r).ypos][x+rooms.get(r).xpos] > 1){
						rooms.remove(r);
						r--;
						setWeights(overlapWeights, rooms, padding);
						break rooms;
					}
				}
			}
			rooms.get(r).construct();
			}
		}
	}
	
	private static void setWeights(int[][] overlapWeight, ArrayList<Room> rooms, int padding){
		for(int y = 0; y < overlapWeight.length; y++){
			for(int x = 0; x < overlapWeight[0].length; x++){
				overlapWeight[y][x] = 0;				
			}
		}
		for(int r = 0; r < rooms.size(); r++){
			for(int x = 0-padding; x < rooms.get(r).w + padding; x++){
				if(x+rooms.get(r).xpos >= 0 && x+rooms.get(r).xpos < overlapWeight[0].length){
					for(int y = 0-padding; y < rooms.get(r).h + padding; y++){
						if(y+rooms.get(r).ypos >= 0 && y+rooms.get(r).ypos < overlapWeight.length){
							overlapWeight[y+rooms.get(r).ypos][x+rooms.get(r).xpos] += 1;
						}
					}
				}
			}
		}
	}
}