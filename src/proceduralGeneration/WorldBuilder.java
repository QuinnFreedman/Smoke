package proceduralGeneration;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import debug.debug_dungeon;
import debug.out;
import main.Point;

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
	
	static int[][] world = new int[WORLD_HEIGHT][WORLD_WIDTH];
	static int[][] dynamicTiles = new int[WORLD_HEIGHT][WORLD_WIDTH];
	
	static float shape = 1.2f;
	
	static float[] levels = {.22f, .32f, .5f, .8f};
	
	private static double limit(double x){
		return -1/(x + 1) + 1;
	}
	private static ArrayList<Point> makeRiver(Point origin, double[][] elevation){
		Point active = origin;
		ArrayList<Point> river = new ArrayList<Point>();
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
			Point[] neighbors = {new Point(y-1, x),
								 new Point(y, x+1),
								 new Point(y+1, x),
								 new Point(y, x-1)};
			
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
					){
					world[y][x] = 0;
				}
			}
		}
	}
	
	public static void buildWorld2(Random rng){
		SimplexNoise simplexNoise = new SimplexNoise(200,0.3,(int) (rng.nextDouble()*5000));

	    double[][] normalizer = new double[WORLD_HEIGHT][WORLD_WIDTH];
	    for(int y = 0; y < WORLD_HEIGHT; y++){
			for(int x = 0; x < WORLD_WIDTH; x++){
				normalizer[y][x] = limit(shape*Math.min(
						(1 - Math.abs(WORLD_WIDTH/2.0 - x)/(WORLD_WIDTH/2.0)),
		 				(1 - Math.abs(WORLD_HEIGHT/2.0 - y)/(WORLD_HEIGHT/2.0))
	 				));
			}
		}
	    
	    //CALCULATE ELEVATION
	    double[][] elevation = new double[WORLD_HEIGHT][WORLD_WIDTH];
	    double[][] percipitation = new double[WORLD_HEIGHT][WORLD_WIDTH];
	    
	    for(int y = 0; y < WORLD_HEIGHT; y++){
			for(int x = 0; x < WORLD_WIDTH; x++){
				elevation[y][x] = 0.5*(1+simplexNoise.getNoise(x,y)) * normalizer[y][x];
				percipitation[y][x] = 0.5*(1+simplexNoise.getNoise(x,y));
	        }
	    }
	    
	    //SET BOUNDS
	    
	    boolean moveNorth = true;
	    boolean moveSouth = true;
	    boolean moveEast = true;
	    boolean moveWest = true;
	    Rectangle bounds = new Rectangle((int) (WORLD_WIDTH/2f) - 1, 
	    		(int) (WORLD_HEIGHT/2f) - 1, 2, 2);
	    while(moveNorth || moveSouth || moveEast || moveWest){

	    	boolean water;
	    	//NORTH
	    	if(moveNorth){
	    		water = false;
		    	for(int i = bounds.x; i < bounds.x + bounds.width; i++){
		    		if(i < 0 || i == WORLD_WIDTH || elevation[bounds.y][i] < getSeaLevel()){
		    			water = true;
		    			bounds.y += 10;
		    			bounds.height -= 10;
		    			break;
		    		}
		    	}
		    	if(water){
		    		moveNorth = false;
		    	}else{
	    			bounds.y -= 10;
	    			bounds.height += 10;
	    		}
	    	}
	    	
	    	//WEST
	    	if(moveWest){
		    	water = false;
		    	for(int i = bounds.y; i < bounds.y + bounds.height; i++){
		    		if(i < 0 || i == WORLD_HEIGHT || elevation[i][bounds.x] < getSeaLevel()){
		    			water = true;
		    			bounds.x += 10;
		    			bounds.width -= 10;
		    			break;
		    		}
		    	}
		    	if(water){
		    		moveWest = false;
		    	}else{
	    			bounds.x -= 10;
	    			bounds.width += 10;
	    		}
	    	}

	    	//SOUTH
	    	if(moveSouth){
	    		water = false;
		    	for(int i = bounds.x; i < bounds.x + bounds.width; i++){
		    		if(i < 0 || i == WORLD_WIDTH || 
		    				elevation[bounds.y+bounds.height][i] < getSeaLevel()){
		    			water = true;
		    			bounds.height -= 10;
		    			break;
		    		}
		    	}
		    	if(water){
		    		moveSouth = false;
		    	}else{
	    			bounds.height += 10;
	    		}
	    	}
	    	
	    	//EAST
	    	if(moveEast){
		    	water = false;
		    	for(int i = bounds.y; i < bounds.y + bounds.height; i++){
		    		if(i < 0 || i == WORLD_HEIGHT || 
		    				elevation[i][bounds.x+bounds.width] < getSeaLevel()){
		    			water = true;
		    			bounds.width -= 10;
		    			break;
		    		}
		    	}
		    	if(water){
		    		moveEast = false;
		    	}else{
	    			bounds.width += 10;
	    		}
	    	}

	    }
	    
	    //CITIES
	    
	    ArrayList<City> cities = new ArrayList<City>(3);
		while(cities.size() < 3){
			cities = new ArrayList<City>(3);
			
			while(cities.size() < 3){
				int x = (int) (rng.nextDouble()*(bounds.width - City.citySize.width));
				int y = (int) (rng.nextDouble()*(bounds.height - City.citySize.height));
				cities.add(new City(x, y, City.citySize.width, City.citySize.height));
			}
			
			DungeonBuilder.collideRooms(cities, bounds.width, bounds.height, 300);
			
			for(City city : cities){
				city.x += bounds.x;
				city.y += bounds.y;
			}
		}
	    
		for(City c : cities) {
			c.buildCity(rng);
			for(int y = 0; y < c.height; y++) {
				for(int x = 0; x < c.width; x++) {
					if( c.walls[y][x] != 0) {
						elevation[c.y + y][c.x + x] = .9;
					}
				}
			}
		}
		debug_dungeon.drawHeightmap(elevation, getSeaLevel());
		/*
	    //FILL MAP
	    for(int y = 0; y < WORLD_HEIGHT; y++){
			for(int x = 0; x < WORLD_WIDTH; x++){
	            double result = elevation[y][x];
	            double ratio = limit(shape*1d);
	            if(result > levels[3]*ratio)
	            	world[y][x] = 4;
	            else if(result > levels[2]*ratio)
	            	world[y][x] = 3;
	            else if(result > levels[1]*ratio)
	            	world[y][x] = 2;
	            else if(result > levels[0]*ratio)
					world[y][x] = 1;
				else
					world[y][x] = 0;
	        }
	    }*/
	}
	static float getSeaLevel() {
		return (float) (levels[0]*limit(shape*1d));
	}
}