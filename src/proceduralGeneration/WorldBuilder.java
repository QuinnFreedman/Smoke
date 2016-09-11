package proceduralGeneration;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import debug.debug_dungeon;
import debug.out;
import engine.Direction;
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

	public static void buildWorld2(Random rng) {
		SimplexNoise simplexNoise = new SimplexNoise(200, 0.25, (int) (rng.nextDouble() * 5000));
		SimplexNoise precipNoise = new SimplexNoise(500, 0.4, (int) (rng.nextDouble() * 5000));

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
				precipitation[y][x] = 0.5 * (1 + precipNoise.getNoise(x, y));
			}
		}

		Direction lastSide = null;
		ArrayList<City> cities = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			out.pln("City " + i + "...");
			Direction side;
			do {
				side = Direction.getRandom(rng);
			} while (side == lastSide);
			lastSide = side;

			Point startingPoint = getRandomPointOnSide(side, rng);
			int x = startingPoint.x;
			int y = startingPoint.y;

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

		for (City c : cities) {
			c.buildCity(rng);
			for(Room room : c.getRooms()) {
				for (int y = 0; y < room.height; y++) {
					for (int x = 0; x < room.width; x++) {
						precipitation[c.y + room.y + y][c.x + room.x + x] = -1;
					}
				}
			}
		}

		List<List<Point>> roads = new ArrayList<>();
		long startTime = System.currentTimeMillis();

		for (int _city = 0; _city < 2; _city++) {
			int grain = 50;
			final int city = _city;
			City city1 = cities.get(city);
			City city2 = cities.get(city + 1);

			//approx start/end points
			Point start = new Point(city1.x + City.citySize.width / 2, city1.y + City.citySize.height / 2);
			Point end = new Point(city2.x + City.citySize.width / 2, city2.y + City.citySize.height / 2);


			city1.buildCity(rng);
			for(Rectangle r : city1.getRooms())
			for (int y = 0; y < r.height; y++) {
				for (int x = 0; x < r.width; x++) {
					elevation[city1.y + r.y + y][city1.x + r.x + x] = 0;
				}
			}

			List<Point> road = AStar.path(start.scale(1f/grain), end.scale(1f/grain),
					new AStar.MapHolder.CoarseMapHolder(elevation, grain),
					new AStar.Config.CoarseRoadConfig() {
						@Override
						public boolean isPassable(AStar.MapHolder map, AStar.Nodef from, AStar.Nodef to) {
							double value = elevation[to.y * grain][to.x * grain];
							return value <= levels[city + 2] && value > levels[0];
						}
					});

			for (int i = 0; i < road.size(); i++) {
				road.set(i, road.get(i).scale(grain));
			}

			//Set exact start/end points
			int differenceX = city1.x - city2.x;
			int differenceY = city1.y - city2.y;
			if (Math.abs(differenceX) > Math.abs(differenceY)) {
				if(differenceX > 0) {
					start = city1.getAttachmentPoint(Direction.WEST);
					end = city2.getAttachmentPoint(Direction.EAST);
				} else {
					start = city1.getAttachmentPoint(Direction.EAST);
					end = city2.getAttachmentPoint(Direction.WEST);
				}
			} else {
				if(differenceY > 0) {
					start = city1.getAttachmentPoint(Direction.NORTH);
					end = city2.getAttachmentPoint(Direction.SOUTH);
				} else {
					start = city1.getAttachmentPoint(Direction.SOUTH);
					end = city2.getAttachmentPoint(Direction.NORTH);
				}
			}

			for(Direction d : Direction.getDirections()) {
				Point p = start.translate(d, 1);
				if(elevation[p.y][p.x] != 0) {
					out.pln("start: "+p+" -> "+start);
					start = p;
					break;
				}
			}
			for(Direction d : Direction.getDirections()) {
				Point p = end.translate(d, 1);
				if(elevation[p.y][p.x] != 0) {
					out.pln("end:   "+p+" -> "+end);
					end = p;
					break;
				}
			}

			out.pln("end:   "+road.get(0)+" -> "+end);
			road.set(0, end);
			out.pln("start: "+road.get(road.size() - 1)+" -> "+start);
			road.set(road.size() - 1, start);

			AStar.Config config = new AStar.Config.RoadConfig() {
				@Override
				public boolean isPassable(AStar.MapHolder map, AStar.Nodef from, AStar.Nodef to) {
					double value = map.getValue(to.x, to.y);
					return value <= levels[city + 2] && value >= levels[0];
				}

				@Override
				public double moveCost(AStar.MapHolder map, AStar.Nodef from, AStar.Nodef to) {
					return precipitation[to.y][to.x] == -1 ? super.moveCost(map, from, to)/2d :
							super.moveCost(map, from, to);
				}
			};
			List<Point> fullRoad = /*AStar.path(start, end,
						new AStar.MapHolder.FineMapHolder(elevation, new Rectangle(0,0,WORLD_WIDTH,WORLD_HEIGHT)), config);//=*/ new ArrayList<>(road.size());
			for (int i = 1; i < road.size(); i++) {
				Point from = road.get(i - 1);
				Point to = road.get(i);
//				Rectangle bounds = new Rectangle(0,0,WORLD_WIDTH,WORLD_HEIGHT);//new Rectangle(from, to);
//				List<Point> segment = AStar.path(from.translate(-bounds.x, -bounds.y), to.translate(-bounds.x, -bounds.y),
//						new AStar.MapHolder.FineMapHolder(elevation, bounds), config);
				List<Point> segment = AStar.path(from, to, new AStar.MapHolder.LazyMapHolder(elevation), config);
				if(segment != null) {
//					fullRoad.addAll(segment.stream().map(p -> p.translate(bounds.x, bounds.y)).collect(Collectors.toList()));
					fullRoad.addAll(segment);
				} else {
					System.err.println("null path!");
				}
			}
			roads.add(fullRoad);
		}

		out.pln("done in "+(System.currentTimeMillis() - startTime)+"ms");

		for(List<Point> road : roads) {
			for(Point p : road) {
				precipitation[p.y][p.x] = -1;
			}
		}

		for (City c : cities) {
			for (int y = 0; y < c.height; y++) {
				for (int x = 0; x < c.width; x++) {
					if (c.walls[y][x] != 0) {
						precipitation[c.y + y][c.x + x] = -1;
					}
				}
			}
		}

		//make rivers
		AStar.Config riverConfig = new AStar.Config.RoadConfig() {
			//TODO
		};
		for (int i = 0; i < 1; i++) {
			int grain = 20;
			Point end = getRandomPointOnSide(Direction.getRandom(rng), rng);
			AStar.MapHolder.CoarseMapHolder coarseMapHolder = new AStar.MapHolder.CoarseMapHolder(elevation, grain);
			List<Point> river = AStar.path(center.scale(1f/grain), end.scale(1f/grain), coarseMapHolder, coarseRiverConfig);
			if (river != null) {
				for (int j = 0; j < river.size(); j++) {
					river.set(j, river.get(j).scale(grain));
				}
				for (int j = 0; j < river.size() - 1; j++) {
					Point from = river.get(j);
					Point to = river.get(j + 1);
					Point coarseFrom = from.scale(1f/grain);
					Point coarseTo = to.scale(1f/grain);
					if (coarseMapHolder.getValue(coarseFrom.x, coarseFrom.y) > levels[0] ||
							coarseMapHolder.getValue(coarseTo.x, coarseTo.y) > levels[0]) {
						List<Point> fill = AStar.path(from, to, new AStar.MapHolder.LazyMapHolder(elevation),
								new AStar.Config.RoadConfig());
						if (fill != null) {
							for (Point p : fill) {
								elevation[p.y][p.x] = 0;
							}
						}
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

	private static Point getRandomPointOnSide(Direction side, Random rng) {
		int x = -1;
		int y = -1;
		switch (side) {
			case NORTH:
				x = rng.nextInt(WORLD_WIDTH - City.citySize.width);
				y = 0;
				break;
			case EAST:
				x = WORLD_WIDTH - City.citySize.width - 1;
				y = rng.nextInt(WORLD_HEIGHT - City.citySize.height);
				break;
			case SOUTH:
				x = rng.nextInt(WORLD_WIDTH - City.citySize.width);
				y = WORLD_HEIGHT - City.citySize.height - 1;
				break;
			case WEST:
				x = 0;
				y = rng.nextInt(WORLD_HEIGHT - City.citySize.height);
				break;
		}
		return new Point(x, y);
	}
}