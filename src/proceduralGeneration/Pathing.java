package proceduralGeneration;

import java.util.ArrayList;
import java.util.List;

import main.Point;

public class Pathing{
	private static List<Node> openList = new ArrayList<Node>();
	private static List<Node> closedList = new ArrayList<Node>();
	public static List<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
	
	public static void setPaths(int[][] walls, ArrayList<Point> doors, ArrayList<Room> rooms){
		paths.clear();
		
		Node[][] nodes = new Node[walls.length][walls[0].length];
		for(int y = 0; y < walls.length; y++){
			for(int x = 0; x < walls[0].length; x++){
				if(walls[y][x] == 0){
					nodes[y][x] = new Node(x,y,true);
				}else{
					nodes[y][x] = new Node(x,y,false);
				}
			}
		}
		List<Integer> connected = new ArrayList<Integer>();//list of connected rooms
		connected.add(0);
		List<Point> connectedDoors = new ArrayList<Point>();
		
		//for each door, find closest partner
		for(int i = 1; i < rooms.size(); i++){
			for(int j = 0; j < rooms.get(i).roomDoors.size(); j++){
				if(!contains(connectedDoors,rooms.get(i).roomDoors.get(j))){
					connectedDoors.add(rooms.get(i).roomDoors.get(j));
					Point startPoint = new Point(rooms.get(i).roomDoors.get(j).x,rooms.get(i).roomDoors.get(j).y);
					Point endpoint = new Point();
					int distanceToDoor = -1;

					int saveE = 0;
					for(int e = 0; e < rooms.size(); e++){
						if(e != i){//check if not in same room, if is connected, if is not self
							if(connected.contains(e) || connected.contains(i)){//if room is not connected, must connect to a connected room. else, does not matter
								if(connected.contains(i)){
								}
								
								for(int f = 0; f < rooms.get(e).roomDoors.size(); f++){
									if(distanceBetween(rooms.get(i).roomDoors.get(j),rooms.get(e).roomDoors.get(f)) < distanceToDoor
										|| distanceToDoor == -1)
									{
										endpoint = rooms.get(e).roomDoors.get(f);
										saveE = e;
										distanceToDoor = distanceBetween(startPoint, endpoint);
									}
								}
							}
						}
					}
					if(!connected.contains(i)){
						connected.add(i);
					}else if(!connected.contains(saveE)){
						connected.add(saveE);
					}
					connectedDoors.add(endpoint);
					
					// A* door to closest door
					
					openList.clear();
					closedList.clear();
					nodes[startPoint.y][startPoint.x].h = distanceBetween(nodes[startPoint.y][startPoint.x],nodes[endpoint.y][endpoint.x]);
					closedList.add(nodes[startPoint.y][startPoint.x]);
					nodes[startPoint.y][startPoint.x].g = 0;
				
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
								//Console.log(nodes[localY][localX]);
								//if h is not on open or closed list or is impassable
								if(!openList.contains(nodes[localY][localX]) && !closedList.contains(nodes[localY][localX]) && nodes[localY][localX].isPassable == true){
									
									nodes[localY][localX].parent = closedList.get(g); // parent current node to new node
									nodes[localY][localX].g = closedList.get(g).g+1; // set move cost
									nodes[localY][localX].h = distanceBetween(nodes[localY][localX],nodes[endpoint.y][endpoint.x]);
									nodes[localY][localX].f = nodes[localY][localX].g + nodes[localY][localX].h;
									//level.debugBoard[localY][localX] = (nodes[localY][localX].h+"").charAt((nodes[localY][localX].h+"").length()-1;
									//(nodes[localY][localX].h+"").charAt((nodes[localY][localX].h+"").length()-1
									
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
								if(lowestF == null || test.f < lowestF.f || (test.f == lowestF.f && walls[test.y][test.x] == 3)){
									//prefer nodes that have already been used, if two are equal, to reduce parallel paths
									//TODO /don't/ change to random chance with preference toward used nodes, to allow some wide pathing
									// to eliminate all unnecessary pathing, you could loop through all paths, check if borders 3 empty squares, or if borders > 2 other path tiles, soem of which each border > 2 other tiles
									// check diagonally 
									lowestF = test;
								}
							}
						}else{
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
					//TODO save paths to arraylist, and use last pos in list instead of currentTile
					if(done == true){
						paths.add(new ArrayList<Node>());
						Node currentTile = nodes[endpoint.y][endpoint.x].parent;
						if(currentTile != null){
							while(currentTile.parent != null){
								//Console.log(currentTile.parent.toString()+": "+currentTile.parent.stringify());
								paths.get(paths.size()-1).add(currentTile);
								walls[currentTile.y][currentTile.x] = 3;
								currentTile = currentTile.parent;
							}
						}else{
						}
					}
				}
			}
		}
		//fill in edges of painted area with walls
		for(ArrayList<Node> path : paths){
			for(Node node : path){
				for(int h = 0; h < 8; h++){
					int localX = node.x;
					int localY = node.y;
					if(h == 0){
						localY++;
					}else if(h == 1){
						localX++;
					}else if(h == 2){
						localY--;
					}else if(h == 3){
						localX--;
					}else if(h == 4){
						localY++;
						localX--;
					}else if(h == 5){
						localY++;
						localX++;
					}else if(h == 6){
						localY--;
						localX--;
					}else if(h == 7){
						localY--;
						localX++;
					}
					if(localX >= 0 && localY >= 0 && localY < nodes.length && localX < nodes[0].length){
						if(walls[localY][localX] == 0){
							walls[localY][localX] = 1;
						}
					}
				}
			}
		}
		for(Point door : rooms.get(0).roomDoors){
			if(walls[door.y][door.x+1] == 0){
				//TODO remove door
				walls[door.y][door.x] = 1;
				continue;
			}else if(walls[door.y][door.x-1] == 0){
				//TODO remove door
				walls[door.y][door.x] = 1;
				continue;
			}else if(walls[door.y+1][door.x] == 0){
				//TODO remove door
				walls[door.y][door.x] = 1;
				continue;
			}else if(walls[door.y-1][door.x] == 0){
				//TODO remove door
				walls[door.y][door.x] = 1;
				continue;
			}
		}
	}
	
	private static boolean contains(List<Point> roomWalls, Point p2){
		for (Point p1 : roomWalls) {
			if(p1.x == p2.x && p1.y == p2.y){
				return true;
			}
			
		}
		//Console.log("false");
		return false;
	}

	private static int distanceBetween(Point a, Point b){
		int d = Math.abs(a.x-b.x)+Math.abs(a.y-b.y);
		//Console.log(" > ("+a.x+","+a.y+")("+b.x+","+b.y+") = "+d);
		return d;
	}
	
}

class Node extends Point{
	float gf; //float move cost
	float ff; //float f
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