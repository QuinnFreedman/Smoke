package proceduralGeneration;

import debug.out;
import engine.Point;

import java.util.ArrayList;
import java.util.List;

class FloatPathing {

    static ArrayList<Point> path(Point startPoint, Point endPoint, double[][] elevation) {
        final List<Nodef> openList = new ArrayList<>();
        final List<Nodef> closedList = new ArrayList<>();

        Nodef[][] nodes = new Nodef[elevation.length][elevation[0].length];
        for(int y = 0; y < elevation.length; y++) {
            for(int x = 0; x < elevation[0].length; x++) {
                nodes[y][x] = new Nodef(x, y);
            }
        }

        nodes[startPoint.y][startPoint.x].h = distanceBetween(nodes[startPoint.y][startPoint.x],
                nodes[endPoint.y][endPoint.x]);
        closedList.add(nodes[startPoint.y][startPoint.x]);
        nodes[startPoint.y][startPoint.x].g = 0;

        int g = 0;
        boolean done = false;
        int itt = 0;
        do {
            for(int h = 0; h < 4; h++) {
                int localX = closedList.get(g).x;
                int localY = closedList.get(g).y;

                out.pln("Probing from point ("+localX+","+localY+")");
                out.pln("openList == ("+openList.size()+", closedList == "+closedList.size()+")");

                if (h == 0) {
                    localY++;
                } else if(h == 1) {
                    localX++;
                } else if(h == 2) {
                    localY--;
                } else if(h == 3) {
                    localX--;
                }
                //if h exists
                if(localX >= 0 && localY >= 0 && localY < nodes.length && localX < nodes[0].length) {
                    //if h is not on open or closed list or is impassable
                    Nodef testNode = nodes[localY][localX];
                    if(!openList.contains(testNode) && !closedList.contains(testNode)
                            && testNode.isPassable) {

                        testNode.parent = closedList.get(g); // parent current node to new node
                        testNode.g = closedList.get(g).g + moveCost(elevation, closedList.get(g), testNode); // set move cost
                        testNode.h = distanceBetween(testNode, nodes[endPoint.y][endPoint.x]);
                        testNode.f = testNode.g + testNode.h;

                        openList.add(testNode); // add new node to open list

                    } else if(closedList.contains(testNode)) {
                        if(closedList.get(g).g + moveCost(elevation, testNode, closedList.get(g)) < testNode.g) {
                            testNode.parent = closedList.get(g);
                            testNode.g = closedList.get(g).g + moveCost(elevation, closedList.get(g), testNode);
                            testNode.f = testNode.g + testNode.h;
                        }
                    } else if(localY == endPoint.y && localX == endPoint.x) {
                        nodes[endPoint.y][endPoint.x].parent = closedList.get(g);

                        done = true;
                    }
                }
            }

            //loop through openList - find lowest f
            Nodef lowestF = null;

            if(openList.size() > 0){
                for(Nodef test : openList){
                    if(lowestF == null || test.f < lowestF.f){
                        lowestF = test;
                    }
                }
            } else {
                done = true;
            }


            if(!closedList.contains(lowestF)) {
                closedList.add(lowestF); //move next node to be checked to closed list
            }
            if(openList.contains(lowestF)) {
                out.pln(openList.remove(lowestF)); //and remove from open list
            }

            //loop do again
            g++;
            itt++;

        } while(!done && itt < 700);

        //Trace back
        ArrayList<Point> path = new ArrayList<>();
        Nodef currentTile = nodes[endPoint.y][endPoint.x].parent;
        if(currentTile != null) {
            while(currentTile.parent != null) {
                path.add(currentTile);
                currentTile = currentTile.parent;
            }
        }
        return path;
    }

    private static double moveCost(double[][] elevation, Nodef from, Nodef to) {
        return 1;//+ elevation[to.y][to.x] - elevation[from.y][from.x];
    }

    private static int distanceBetween(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}

class Nodef extends Point{
    double h; //heuristic
    double g; //movement cost
    double f; //g+h
    boolean isPassable;
    Nodef parent;

    Nodef (int x, int y, boolean passable) {
        super(x,y);
        isPassable = passable;
    }

    Nodef (int x, int y) {
        this(x, y, true);
    }
}
