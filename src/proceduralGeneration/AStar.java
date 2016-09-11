package proceduralGeneration;

import debug.out;
import engine.Point;

import java.util.*;

class AStar {
    static List<Point> path(Point startPoint, Point endPoint, MapHolder map, Config config) {
        out.pln("path from "+startPoint+" to "+endPoint);
        final PriorityQueue<Nodef> openList = new PriorityQueue<>(new Comparator<Nodef>() {
            @Override
            public int compare(Nodef o1, Nodef o2) {
                double difference = o1.f() - o2.f();
                return difference > 0 ? 1 :
                        difference < 0 ? -1 : 0;
            }
        });
        final HashSet<Nodef> closedList = new HashSet<>();
        final List<Point> path = new ArrayList<>();

        openList.add(map.getNode(startPoint.x, startPoint.y));

        boolean done = false;
        do {
            if(openList.isEmpty()) {
                return null;
            }
            Nodef current = openList.poll();
            closedList.add(current);

            if (current.x == endPoint.x && current.y == endPoint.y) {
                done = true;
            }

            for (Nodef neighbor : config.getNeighbors(map, current)) {
                if (!config.isPassable(map, current, neighbor) || closedList.contains(neighbor)) {
                    continue;
                }
                boolean neighborInOpen = openList.contains(neighbor);
                if(!neighborInOpen || current.g + config.moveCost(map, current, neighbor) < neighbor.g) {
                    neighbor.g = current.g + config.moveCost(map, current, neighbor);
                    neighbor.parent = current;
                    if(!neighborInOpen) {
                        neighbor.h = config.distanceBetween(map, neighbor.x, neighbor.y, endPoint.x, endPoint.y);
                        openList.add(neighbor);
                    }
                }
            }

        } while (!done);

        Nodef current = map.getNode(endPoint.x, endPoint.y);
        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        return path;
    }

    interface MapHolder {
        int getWidth();
        int getHeight();
        Nodef getNode(int x, int y);
        double getValue(int x, int y);

        class LazyMapHolder implements MapHolder {
            private int height;
            private int width;
            private double[][] map;
            private Nodef[][] nodes;

            LazyMapHolder(double[][] map) {
                this.map = map;

                this.height = map.length;
                this.width = map[0].length;
                this.nodes = new Nodef[height][width];
            }
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public Nodef getNode(int x, int y) {
                if(x >= width || y >= height || x < 0 || y < 0) {
                    throw new IndexOutOfBoundsException("("+x+","+y+") does not fit in ("+width+","+height+")");
                }
                if(nodes[y][x] == null) {
                    nodes[y][x] = new Nodef(x, y);
                }
                return nodes[y][x];
            }

            @Override
            public double getValue(int x, int y) {
                if(x >= width || y >= height || x < 0 || y < 0) {
                    throw new IndexOutOfBoundsException("("+x+","+y+") does not fit in ("+width+","+height+")");
                }

                return map[y][x];
            }
        }

        class FineMapHolder implements MapHolder {
            private int height;
            private int width;
            private double[][] map;
            private Point upperLeftBound;
            private Point lowerRightBound;
            private Nodef[][] nodes;


            FineMapHolder(double[][] map, Rectangle bounds) {
                this.map = map;
                this.upperLeftBound = new Point(bounds.x, bounds.y);
                this.lowerRightBound = upperLeftBound.translate(bounds.width - 1, bounds.height - 1);

                if(upperLeftBound.x < 0 || upperLeftBound.x > lowerRightBound.x
                        || upperLeftBound.y < 0 || upperLeftBound.y > lowerRightBound.y
                        || lowerRightBound.x < upperLeftBound.x || lowerRightBound.x >= map[0].length
                        || lowerRightBound.y < upperLeftBound.y || lowerRightBound.y >= map.length) {
                    throw new IllegalArgumentException("Point "+upperLeftBound+" or "+lowerRightBound+" out of bounds");
                }
                this.height = lowerRightBound.y - upperLeftBound.y;
                this.width = lowerRightBound.x - upperLeftBound.x;
                this.nodes = new Nodef[height][width];
            }
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public Nodef getNode(int x, int y) {
                if(x >= width || y >= height || x < 0 || y < 0) {
                    throw new IndexOutOfBoundsException("("+x+","+y+") does not fit in ("+width+","+height+")");
                }
                if(nodes[y][x] == null) {
                    nodes[y][x] = new Nodef(x, y);
                }
                return nodes[y][x];
            }

            @Override
            public double getValue(int x, int y) {
                if(x >= width || y >= height || x < 0 || y < 0) {
                    throw new IndexOutOfBoundsException("("+x+","+y+") does not fit in ("+width+","+height+")");
                }
                x += upperLeftBound.x;
                y += upperLeftBound.y;

                return map[y][x];
            }
        }

        class CoarseMapHolder implements MapHolder {
            private int height;
            private int width;
            private double[][] map;
            private double[][] reducedMap;
            private Nodef[][] nodes;
            private int grain;
            CoarseMapHolder(double[][] map, int grain) {
                this.grain = grain;
                this.map = map;
                int height = map.length;
                int width = map[0].length;
                out.pln("width == "+width, "grain == "+grain, "width % grain == "+(width % grain));
                if(!(height % grain == 0 && width % grain == 0)) {
                    throw new IllegalArgumentException("height % grain and width % grain must be 0");
                }
                this.width = width/grain;
                this.height = height/grain;
                this.nodes = new Nodef[this.height][this.width];
                this.reducedMap = new double[this.height][this.width];
                for (int y = 0; y < height; y += grain) {
                    for (int x = 0; x < width; x += grain) {
                        reducedMap[y/grain][x/grain] = -1;
                    }
                }
            }
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public Nodef getNode(int x, int y) {
                if(nodes[y][x] == null) {
                    nodes[y][x] = new Nodef(x, y);
                }
                return nodes[y][x];
            }

            @Override
            public double getValue(int x, int y) {
                if(x >= width || y >= height || x < 0 || y < 0) {
                    throw new IndexOutOfBoundsException("("+x+","+y+") does not fit in ("+width+","+height+")");
                }
                if(reducedMap[y][x] == -1) {
                    double total = 0;
                    int yStart = y * grain;
                    int xStart = x * grain;
                    for (int yy = yStart; yy < yStart + grain; yy++) {
                        for (int xx = xStart; xx < xStart + grain; xx++) {
                            double value = map[yy][xx];
                            total += value;
                        }
                    }
                    reducedMap[y][x] = total / (grain * grain);
                }
                return reducedMap[y][x];
            }
        }
    }

    interface Config {
        double distanceBetween(MapHolder map, int x1, int y1, int x2, int y2);
        List<Nodef> getNeighbors(MapHolder map, Nodef node);
        double moveCost(MapHolder map, Nodef from, Nodef to);
        boolean isPassable(MapHolder map, Nodef from, Nodef to);

        class GradientDescent implements Config {
            double seaLevel;
            GradientDescent(double seaLevel) {
                this.seaLevel = seaLevel;
            }

            @Override
            public double distanceBetween(MapHolder map, int x1, int y1, int x2, int y2) {
                if (map.getValue(x2, y2) <= seaLevel) return 0;

                double z1 = map.getValue(x1, y1) * 500;
                double z2 = map.getValue(x2, y2) * 500;
                return Math.cbrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
            }

            @Override
            public List<Nodef> getNeighbors(MapHolder map, Nodef node) {
                int height = map.getHeight();
                int width = map.getWidth();
                ArrayList<Nodef> neighbors = new ArrayList<>(4);
                if (node.x > 0)
                    neighbors.add(map.getNode(node.x - 1, node.y));
                if (node.x + 1 < width)
                    neighbors.add(map.getNode(node.x + 1, node.y));
                if (node.y > 0)
                    neighbors.add(map.getNode(node.x, node.y - 1));
                if (node.y + 1 < height)
                    neighbors.add(map.getNode(node.x, node.y + 1));

                return neighbors;
            }

            @Override
            public double moveCost(MapHolder map, Nodef from, Nodef to) {
//                double z1 = map.getValue(from.x, from.y) * 10000;
//                double z2 = map.getValue(to.x, to.y) * 10000;
//                return Math.cbrt((from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y) + (z1 - z2) * (z1 - z2));
                return distanceBetween(map, from.x, from.y, to.x, to.y);//1 + map[to.y][to.x] - map[from.y][from.x];
            }

            @Override
            public boolean isPassable(MapHolder map, Nodef from, Nodef to) {
                return true;
            }
        }

        class CoarseGradientDescent extends GradientDescent {
            CoarseGradientDescent(double seaLevel) {
                super(seaLevel);
            }

            @Override
            public List<Nodef> getNeighbors(MapHolder map, Nodef node) {
                int height = map.getHeight();
                int width = map.getWidth();
                List<Nodef> neighbors = super.getNeighbors(map, node);
                if (node.x > 0 && node.y > 0)
                    neighbors.add(map.getNode(node.x - 1, node.y - 1));
                if (node.x > 0 && node.y + 1 < height)
                    neighbors.add(map.getNode(node.x - 1, node.y + 1));
                if (node.x + 1 < width && node.y > 0)
                    neighbors.add(map.getNode(node.x + 1, node.y - 1));
                if (node.x + 1 < width && node.y + 1 < height)
                    neighbors.add(map.getNode(node.x + 1, node.y + 1));
                return neighbors;
            }
        }

        class RoadConfig implements Config {
            @Override
            public double distanceBetween(MapHolder map, int x1, int y1, int x2, int y2) {
                double z1 = map.getValue(x1, y1) * 500;
                double z2 = map.getValue(x2, y2) * 500;
                return Math.cbrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
            }

            @Override
            public List<Nodef> getNeighbors(MapHolder map, Nodef node) {
                int height = map.getHeight();
                int width = map.getWidth();
                ArrayList<Nodef> neighbors = new ArrayList<>(4);
                if (node.x > 0)
                    neighbors.add(map.getNode(node.x - 1, node.y));
                if (node.x + 1 < width)
                    neighbors.add(map.getNode(node.x + 1, node.y));
                if (node.y > 0)
                    neighbors.add(map.getNode(node.x, node.y - 1));
                if (node.y + 1 < height)
                    neighbors.add(map.getNode(node.x, node.y + 1));

                return neighbors;
            }

            @Override
            public double moveCost(MapHolder map, Nodef from, Nodef to) {

                double z1 = map.getValue(from.x, from.y) * 500;
                double z2 = map.getValue(to.x, to.y) * 500;
                return Math.sqrt((from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y) + (z1 - z2) * (z1 - z2));
                //return distanceBetween(map, from.x, from.y, to.x, to.y);//1 + map[to.y][to.x] - map[from.y][from.x];
            }

            @Override
            public boolean isPassable(MapHolder map, Nodef from, Nodef to) {
                return true;
            }
        }

        class CoarseRoadConfig extends RoadConfig {
            @Override
            public List<Nodef> getNeighbors(MapHolder map, Nodef node) {
                List<Nodef> neighbors = super.getNeighbors(map, node);
                int height = map.getHeight();
                int width = map.getWidth();

                if (node.x > 0 && node.y > 0)
                    neighbors.add(map.getNode(node.x - 1, node.y - 1));
                if (node.x > 0 && node.y + 1 < height)
                    neighbors.add(map.getNode(node.x - 1, node.y + 1));
                if (node.x + 1 < width && node.y > 0)
                    neighbors.add(map.getNode(node.x + 1, node.y - 1));
                if (node.x + 1 < width && node.y + 1 < height)
                    neighbors.add(map.getNode(node.x + 1, node.y + 1));

                return neighbors;
            }
        }
    }

    static class Nodef extends Point {
        double h; //heuristic
        double g; //movement cost
        double f() { return g + h; } //g+h
        Nodef parent;

        Nodef (int x, int y) {
            super(x, y);
        }
    }
}

