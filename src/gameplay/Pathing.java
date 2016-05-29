package gameplay;

import java.util.ArrayList;
import java.util.List;

import engine.Point;

abstract class Pathing{
	private static List<Node> openList = new ArrayList<Node>();
	private static List<Node> closedList = new ArrayList<Node>();
	
	static ArrayList<Point> getPath(Point startpoint, Point endpoint, boolean[][] collision){
		ArrayList<Node> path = new ArrayList<Node>();
		Node[][] nodes = new Node[collision.length][collision[0].length];
		for(int y = 0; y < collision.length; y++){
			for(int x = 0; x < collision[0].length; x++){
				nodes[y][x] = new Node(x,y,!collision[y][x]);
			}
		}
		
		openList.clear();
		closedList.clear();
		nodes[startpoint.y][startpoint.x].h = distanceBetween(nodes[startpoint.y][startpoint.x],nodes[endpoint.y][endpoint.x]);
		closedList.add(nodes[startpoint.y][startpoint.x]);
		nodes[startpoint.y][startpoint.x].g = 0;
	
		int g = 0;
		boolean done = false;
		int itt = 0;
		do{
			for(int h = 0; h < 4; h++){
				int localX = closedList.get(g).x;
				int localY = closedList.get(g).y;
			
				if(h == 0){
					localY++;
				}else if(h == 1){
					localX++;
				}else if(h == 2){
					localY--;
				}else if(h == 3){
					localX--;
				}
				//if h exists
				if(localX >= 0 && localY >= 0 && localY < nodes.length && localX < nodes[0].length){
					//if h is not on open or closed list or is impassable
					if(!openList.contains(nodes[localY][localX]) && !closedList.contains(nodes[localY][localX]) && nodes[localY][localX].isPassable == true){
						
						nodes[localY][localX].parent = closedList.get(g); // parent current node to new node
						nodes[localY][localX].g = closedList.get(g).g+1; // set move cost
						nodes[localY][localX].h = distanceBetween(nodes[localY][localX],nodes[endpoint.y][endpoint.x]);
						nodes[localY][localX].f = nodes[localY][localX].g + nodes[localY][localX].h;
						
						openList.add(nodes[localY][localX]); // add new node to open list
						
						
						
					}else if(closedList.contains(nodes[localY][localX])){
						if(closedList.get(g).g + 1 < nodes[localY][localX].g){
							nodes[localY][localX].parent = closedList.get(g);
							nodes[localY][localX].g = closedList.get(g).g + 1;
							nodes[localY][localX].f = nodes[localY][localX].g + nodes[localY][localX].h;
						}
					}else if(localY == endpoint.y && localX == endpoint.x){
						nodes[endpoint.y][endpoint.x].parent = closedList.get(g);
						
						done = true;
					}
				}
			}
			
			//loop through openList - find lowest f
			Node lowestF = null;
			
			if(openList.size() > 0){
				for(Node test : openList){
					if(lowestF == null || test.f < lowestF.f){
						lowestF = test;
					}
				}
			}else{
				//System.out.println("ERROR: PATH NOT FOUND");
				done = true;
			}
			
			
			if(!closedList.contains(lowestF)){
				closedList.add(lowestF); //move next node to be checked to closed list
			}
			if(openList.contains(lowestF)){
				openList.remove(lowestF); //and remove from open list
			}

			//loop do again
			g++;
			itt++;
			
		}while(done == false && itt < 400);
		
		//Trace back
		if(done == true){
			Node currentTile = nodes[endpoint.y][endpoint.x].parent;
			if(currentTile != null){
				while(currentTile.parent != null){
					//Console.log(currentTile.parent.toString()+": "+currentTile.parent.stringify());
					path.add(currentTile);
					currentTile = currentTile.parent;
				}
			}else{
				System.out.println("pathing error");
			}
		}else{
			System.out.println("pathing error");
		}
		ArrayList<Point> pointPath = new ArrayList<Point>(path.size());
		for(int i = path.size() - 1; i >= 0; i--){
			pointPath.add((Point) path.get(i));
		}
		return pointPath;
	}

	private static int distanceBetween(Point a, Point b){
		int d = Math.abs(a.x-b.x)+Math.abs(a.y-b.y);
		return d;
	}
	
	private static class Node extends Point{
		//float gf; //float move cost
		//float ff; //float f
		int h;//heuristic
		int g;//movement cost
		int f;//g+h
		boolean isPassable;
		Node parent;
		
		Node(int x, int y, boolean passable){
			this(x,y);
			isPassable = passable;
		};
		
		Node(int x, int y){
			super(x,y);
		};
	}
}