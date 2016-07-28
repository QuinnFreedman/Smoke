package proceduralGeneration;

import java.util.*;
import java.util.List;

import debug.debug_dungeon;
import debug.out;
import engine.Point;

public abstract class WorldBuilder {
	static final int[][] levelTextures = new int[][]{
		{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4,  4,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  4,  4, 11,  4,  4,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  1,  6,  6,  6,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  1,  6,  6,  6,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  6,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
		{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1}
	};
	
	static final int[][] staticSprites = new int[][]{
		{ 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,-51, -9, -9, -9, -9, -9,-15,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,-10,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,-52,-15,  0,  0,  0,-51,-25,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,  0, -8,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,  0, -8,  0,  0,  0, -8,  0,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,  0,-52, -9,  0, -9,-25,  0,  0,  0,  0,  0,  0,  0,  0, 0},
		{ 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0}
	};
	
	
	public static WorldData buildWorld() {
		assert levelTextures.length > 10 && levelTextures[0].length > 10;
		return new WorldData(levelTextures, staticSprites);
	}
		
	public static class WorldData {
		private int[][] levelTextures;
		private int[][] staticSprites;
		public int[][] getLevelTextures() { return levelTextures; }
		public int[][] getStaticSprites() { return staticSprites; }
		WorldData(int[][] levelTextures, int[][] staticSprites) {
			this.levelTextures = levelTextures;
			this.staticSprites = staticSprites;
		}
	}
	
	private static final int WORLD_WIDTH = 1000;
	private static final int WORLD_HEIGHT = 1000;
	
	private static int[][] world = new int[WORLD_HEIGHT][WORLD_WIDTH];
	static int[][] dynamicTiles = new int[WORLD_HEIGHT][WORLD_WIDTH];
	
	private static final float[] levels = {.1f, .2f, .33f, .45f};
	public static final float[] biomeThresholds = {.45f, .55f};

	private static double limit(double x){
		return -1/(x + 1) + 1;
	}
	private static ArrayList<Point> makeRiver(Point origin, double[][] elevation){
		Point active = origin;
		ArrayList<Point> river = new ArrayList<>();
		boolean[][] isRiver = new boolean[elevation.length][elevation[0].length];
		for (int y = 0; y < isRiver.length; y++) {
			for (int x = 0; x < isRiver[0].length; x++) {
				isRiver[y][x] = false;
			}
		}
		
		int itt = 0;
		while(itt < 500){
			itt++;
			river.add(active);
			int x = active.x;
			int y = active.y;
			Point[] neighbors = {new Point(x-1, y),
								 new Point(x, y+1),
								 new Point(x+1, y),
								 new Point(x, y-1)};
			
			double min = 2;
			active = null;
			for(Point p : neighbors){
				double pVal = elevation[p.y][p.x];
				if(pVal < getSeaLevel())
					return river;
				if(isRiver[p.y][p.x])
					continue;
				else if(pVal < min){
					min = pVal;
					active = p;
				}
			}
			world[y][x] = -1;
			
			if(active == null)
				return river;
		}
		return null;
	}
	
	private static void fillRivers(double[][] elevation){
		for(int y = 1; y < WORLD_HEIGHT - 1; y++){
			for(int x = 1; x < WORLD_WIDTH - 1; x++){
				if(world[y-1][x] == -1 ||
						world[y][x+1] == -1 || 
						world[y+1][x] == -1 ||
						world[y][x-1] == -1
					) {
					world[y][x] = 0;
				}
			}
		}
	}

	private enum Direction {
		NORTH, SOUTH, EAST, WEST;

		private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		static Direction getRandom(Random rng) {
			return VALUES.get(rng.nextInt(SIZE));
		}
	}

	public static void buildWorld2(Random rng) {
		SimplexNoise simplexNoise = new SimplexNoise(200, 0.25, (int) (rng.nextDouble() * 5000));
		SimplexNoise percipNoise = new SimplexNoise(500, 0.4, (int) (rng.nextDouble() * 5000));

		final Point center = new Point(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

		final int maxDistance = Math.max(WORLD_WIDTH, WORLD_HEIGHT) / 2;

		//CALCULATE ELEVATION
		double[][] elevation = new double[WORLD_HEIGHT][WORLD_WIDTH];
		double[][] precipitation = new double[WORLD_HEIGHT][WORLD_WIDTH];

		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				double r = Math.pow(Math.pow(x - center.x, 4) + Math.pow(y - center.y, 4), .25);
				double normalizer = r < maxDistance ? 1 - r / maxDistance : 0;
				elevation[y][x] = 0.5 * (1 + simplexNoise.getNoise(x, y)) * normalizer;
				assert (elevation[y][x] >= 0 && elevation[y][x] < 1);
				precipitation[y][x] = 0.5 * (1 + percipNoise.getNoise(x, y));
			}
		}

		//SET BOUNDS
//
//	    boolean moveNorth = true;
//	    boolean moveSouth = true;
//	    boolean moveEast = true;
//	    boolean moveWest = true;
//	    Rectangle bounds = new Rectangle((int) (WORLD_WIDTH/2f) - 1,
//	    		(int) (WORLD_HEIGHT/2f) - 1, 2, 2);
//	    while(moveNorth || moveSouth || moveEast || moveWest){
//
//	    	boolean water;
//	    	//NORTH
//	    	if(moveNorth){
//	    		water = false;
//		    	for(int i = bounds.x; i < bounds.x + bounds.width; i++){
//		    		if(i < 0 || i == WORLD_WIDTH || elevation[bounds.y][i] < getSeaLevel()){
//		    			water = true;
//		    			bounds.y += 10;
//		    			bounds.height -= 10;
//		    			break;
//		    		}
//		    	}
//		    	if(water){
//		    		moveNorth = false;
//		    	}else{
//	    			bounds.y -= 10;
//	    			bounds.height += 10;
//	    		}
//	    	}
//
//	    	//WEST
//	    	if(moveWest){
//		    	water = false;
//		    	for(int i = bounds.y; i < bounds.y + bounds.height; i++){
//		    		if(i < 0 || i == WORLD_HEIGHT || elevation[i][bounds.x] < getSeaLevel()){
//		    			water = true;
//		    			bounds.x += 10;
//		    			bounds.width -= 10;
//		    			break;
//		    		}
//		    	}
//		    	if(water){
//		    		moveWest = false;
//		    	}else{
//	    			bounds.x -= 10;
//	    			bounds.width += 10;
//	    		}
//	    	}
//
//	    	//SOUTH
//	    	if(moveSouth){
//	    		water = false;
//		    	for(int i = bounds.x; i < bounds.x + bounds.width; i++){
//		    		if(i < 0 || i == WORLD_WIDTH ||
//		    				elevation[bounds.y+bounds.height][i] < getSeaLevel()){
//		    			water = true;
//		    			bounds.height -= 10;
//		    			break;
//		    		}
//		    	}
//		    	if(water){
//		    		moveSouth = false;
//		    	}else{
//	    			bounds.height += 10;
//	    		}
//	    	}
//
//	    	//EAST
//	    	if(moveEast){
//		    	water = false;
//		    	for(int i = bounds.y; i < bounds.y + bounds.height; i++){
//		    		if(i < 0 || i == WORLD_HEIGHT ||
//		    				elevation[i][bounds.x+bounds.width] < getSeaLevel()){
//		    			water = true;
//		    			bounds.width -= 10;
//		    			break;
//		    		}
//		    	}
//		    	if(water){
//		    		moveEast = false;
//		    	}else{
//	    			bounds.width += 10;
//	    		}
//	    	}
//
//	    }
//
//	    //CITIES
//
//	    ArrayList<City> cities = new ArrayList<>(3);
//		while(cities.size() < 3){
//			cities = new ArrayList<>(3);
//
//			while(cities.size() < 3){
//				int x = (int) (rng.nextDouble()*(bounds.width - City.citySize.width));
//				int y = (int) (rng.nextDouble()*(bounds.height - City.citySize.height));
//				cities.add(new City(x, y, City.citySize.width, City.citySize.height));
//			}
//
//			DungeonBuilder.collideRooms(cities, bounds.width, bounds.height, 300);
//
//			for(City city : cities){
//				city.x += bounds.x;
//				city.y += bounds.y;
//			}
//		}
//
//		for(City c : cities) {
//			c.buildCity(rng);
//			for(int y = 0; y < c.height; y++) {
//				for(int x = 0; x < c.width; x++) {
//					if( c.walls[y][x] != 0) {
//						elevation[c.y + y][c.x + x] = .9;
//					}
//				}
//			}
//		}

		Direction lastSide = null;
		ArrayList<City> cities = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			out.pln("City " + i + "...");
			Direction side;
			do {
				side = Direction.getRandom(rng);
			} while (side == lastSide);
			lastSide = side;

			int x = -1;
			int y = -1;
			switch (side) {
				case NORTH:
					x = (int) (Math.random() * (WORLD_WIDTH - City.citySize.width));
					y = 0;
					break;
				case EAST:
					x = WORLD_WIDTH - City.citySize.width - 1;
					y = (int) (Math.random() * (WORLD_HEIGHT - City.citySize.height));
					break;
				case SOUTH:
					x = (int) (Math.random() * (WORLD_WIDTH - City.citySize.width));
					y = WORLD_HEIGHT - City.citySize.height - 1;
					break;
				case WEST:
					x = 0;
					y = (int) (Math.random() * (WORLD_HEIGHT - City.citySize.height));
					break;
			}
			double ratioX = (center.x - x) / (float) WORLD_WIDTH * 10;
			double ratioY = (center.y - y) / (float) WORLD_HEIGHT * 10;
			out.pln("    side == " + side);
			while (!(
					(elevation[y][x] > levels[i] &&
							elevation[y + City.citySize.height][x] > levels[i] &&
							elevation[y][x + City.citySize.width] > levels[i] &&
							elevation[y + City.citySize.height][x + City.citySize.width] > levels[i]) ||
							(elevation[y][x] > levels[i + 1] &&
									elevation[y + City.citySize.height][x] > levels[i + 1] &&
									elevation[y][x + City.citySize.width] > levels[i + 1] &&
									elevation[y + City.citySize.height][x + City.citySize.width] > levels[i + 1]))) {
				x = (int) (x + ratioX);
				y = (int) (y + ratioY);
			}
			out.pln("    x == " + x + " y == " + y);

			switch (side) {
				case NORTH:
					for (int _x = x; _x < x + City.citySize.width; _x++) {
						int _y = y + City.citySize.height;
						while (elevation[_y][_x] > levels[i] || _y > y) {
							//if(elevation[_y][_x] > levels[i + 1]) {
							elevation[_y][_x] = levels[i];
							//}
							_y--;
						}
					}
					break;
				case SOUTH:
					for (int _x = x; _x < x + City.citySize.width; _x++) {
						int _y = y;
						while (elevation[_y][_x] > levels[i] || _y < y + City.citySize.height) {
							//if(elevation[_y][_x] > levels[i + 1]) {
							elevation[_y][_x] = levels[i];
							//}
							_y++;
						}
					}
					break;
				case EAST:
					for (int _y = y; _y < y + City.citySize.height; _y++) {
						int _x = x;
						while (elevation[_y][_x] > levels[i] || _x < x + City.citySize.width) {
							//if(elevation[_y][_x] > levels[i + 1]) {
							elevation[_y][_x] = levels[i];
							//}
							_x++;
						}
					}
					break;
				case WEST:
					for (int _y = y; _y < y + City.citySize.height; _y++) {
						int _x = x + City.citySize.width;
						while (elevation[_y][_x] > levels[i] || _x > x) {
							elevation[_y][_x] = levels[i];
							_x--;
						}
					}
					break;
			}

			cities.add(new City(x, y, City.citySize.width, City.citySize.height));
		}

		List<List<Point>> roads = new ArrayList<>();

		for (int _city = 0; _city < 2; _city++) {
			int grain = 50;
			final int city = _city;
			List<Point> road = AStar.path(new Point((cities.get(city).x + City.citySize.width) / grain,
							(cities.get(city).y + City.citySize.width) / grain),
					new Point((cities.get(city + 1).x + City.citySize.width) / grain,
							(cities.get(city + 1).y + City.citySize.width) / grain),
					new AStar.MapHolder.CoarseMapHolder(elevation, grain),
					new AStar.Config.CorseRoadConfig() {
						@Override
						public boolean isPassable(AStar.MapHolder map, AStar.Nodef from, AStar.Nodef to) {
							double value = elevation[to.y * grain][to.x * grain];
							return value <= levels[city + 2] && value > levels[0];
						}
					});
			for (int i = 0; i < road.size(); i++) {
				road.set(i, road.get(i).scale(grain));
			}

			AStar.Config config = new AStar.Config.RoadConfig() {
				@Override
				public boolean isPassable(AStar.MapHolder map, AStar.Nodef from, AStar.Nodef to) {
					double value = map.getValue(to.x, to.y);
					out.pln("getValue("+to.x+", "+to.y+") == "+value);
					return value <= levels[city + 2] && value >= levels[0];
				}
			};
			List<Point> fullRoad = new ArrayList<>(road.size());
			for (int i = 1; i < road.size(); i++) {
				Point from = road.get(i - 1);
				Point to = road.get(i);
				out.pln("from " + from + " to " + to);
				Rectangle bounds = new Rectangle(from, to);
				List<Point> segment = AStar.path(from.translate(-bounds.x, -bounds.y), to.translate(-bounds.x, -bounds.y),
						new AStar.MapHolder.FineMapHolder(elevation, bounds), config);
				for (Point p : segment) {
					fullRoad.add(p.translate(bounds.x, bounds.y));
				}
			}
			roads.add(fullRoad);
		}

		for(List<Point> road : roads) {
			for(Point p : road) {
				elevation[p.y][p.x] = 0;
			}
		}

		for (City c : cities) {
			c.buildCity(rng);
			for (int y = 0; y < c.height; y++) {
				for (int x = 0; x < c.width; x++) {
					if (c.walls[y][x] != 0) {
						elevation[c.y + y][c.x + x] = 0;
					}
				}
			}
		}
		debug_dungeon.drawHeightmap(elevation, precipitation);
	}

	static float getSeaLevel() {
		return levels[0];
	}

	public static float[] getLevels() {
		return levels;
	}
}