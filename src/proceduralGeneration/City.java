package proceduralGeneration;
import debug.out;
import engine.Direction;
import engine.Point;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class City extends Rectangle {
	static final Dimension citySize = new Dimension(80,80);
	private static final int NUM_ROOMS = 20;
	int[][] walls;
	private List<Room> rooms;
	
	City(int x, int y, int width, int hieght) {
		super(x, y, width, hieght);
	}
	
	void buildCity(Random rng){
		rooms = new ArrayList<>(NUM_ROOMS);
		walls = DungeonBuilder.buildDungeon(citySize.width, citySize.height, NUM_ROOMS, rng, rooms);
		
		/*for(int r = 0; r < 2; r++){
			ArrayList<Integer> road = roadsNorthSouth.get(r);
			//make north-south roads
			road.add((int) (Math.random()*5));
			while(road.get(road.size()-1) < width){
				road.add((int) (Math.random()*5 + 10));
				crossroads.get(r).add(new ArrayList<Integer>());
			}
			
			//make horizontal roads
			for(int i = 0; i < road.size(); i++){
				ArrayList<Integer> localCrossroads = new ArrayList<Integer>();
				while(localCrossroads.isEmpty() || 
						localCrossroads.get(localCrossroads.size()-1) < height/2){
					localCrossroads.add((int) (Math.random()*10 + 10));
				}
				crossroads.get(r).set(i, localCrossroads);
			}
		}*/
		
	}

	Point getAttachmentPoint(Direction direction) {
		if (rooms == null) {
			throw new IllegalStateException("City must be built before attachment point can be retrieved");
		}
		Point p = null;
		for (Room room : rooms) {
			for(Point door : room.roomDoors) {
				if (p == null) {
					p = door;
					continue;
				}
				switch (direction) {
					case NORTH:
						if(door.y < p.y) {
							p = door;
						}
						break;
					case SOUTH:
						if(door.y > p.y) {
							p = door;
						}
						break;
					case EAST:
						if(door.x > p.x) {
							p = door;
						}
						break;
					case WEST:
						if(door.x < p.x) {
							p = door;
						}
						break;
					default:
						throw new IllegalArgumentException("direction must me a valid direction");
				}
			}
		}
		return p.translate(this.x, this.y);
	}

	public List<Room> getRooms() {
		return rooms;
	}
	

	/*int majorRoads = 1;
	ArrayList<ArrayList<Integer>> roadsNorthSouth = new ArrayList<ArrayList<Integer>>(
			Arrays.asList(
					new ArrayList<Integer>(),
					new ArrayList<Integer>()));
	ArrayList<ArrayList<ArrayList<Integer>>> crossroads = new ArrayList<ArrayList<ArrayList<Integer>>>(
			Arrays.asList(
					new ArrayList<ArrayList<Integer>>(),
					new ArrayList<ArrayList<Integer>>()));*/
	
}