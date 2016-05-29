package proceduralGeneration;


import java.util.ArrayList;
import java.util.Random;

import engine.Point;

public class Room extends Rectange{
	public int numDoors;
	public ArrayList<Point> roomWalls;
	public ArrayList<Point> roomDoors;
	private int levelWidth = -1;
	private int levelHeight = -1;
	
	private static int randomBtwnBiased(int min, int max) {
		return (int) (/*Main.generator.nextDouble()*/ Math.random() * (max - min + 1)) + min;
	}
	private static int randomBtwn(int min, int max) {
		//inclusive
		return (int) Math.floor((/*Main.generator.nextDouble()*/ Math.random() * (max - min + 1)) + min);
	}

	private static int normalInRange(int min, int max, Random rng) {
		int range = max - min;
		double normal;
		do {
			normal = rng.nextGaussian();
			normal = normal/6d * range + min + range/2;
		} while(normal > max && normal < min);
		
		return (int) Math.round(normal);
	}
	
	
	private void setWalls(){
		roomWalls = new ArrayList<Point>();
		roomDoors = new ArrayList<Point>();
		
		for(int i = 0; i < this.width; i++) {
			roomWalls.add(new Point(this.x+i, this.y));
			roomWalls.add(new Point(this.x+i, this.y+this.height-1));
		}
		for(int i = 2; i < this.height; i++) {
			roomWalls.add(new Point(this.x, this.y+i-1));
			roomWalls.add(new Point(this.x+this.width - 1, this.y+i-1));
		}
	}
	private void setDoors(){
		boolean corner;
		for(int e = 1; e <= numDoors; e++){
			do{	
				corner = false;			
				int k = randomBtwn(0, roomWalls.size()-1);
				
				//check for corners
				if((roomWalls.get(k).y == this.height + this.y - 1 && roomWalls.get(k).x == this.width + this.x - 1) || 
					(roomWalls.get(k).y == this.y && roomWalls.get(k).x == this.width + this.x - 1) ||
					(roomWalls.get(k).y == this.y && roomWalls.get(k).x == this.x) ||
					(roomWalls.get(k).y == this.height + this.y - 1 && roomWalls.get(k).x == this.x)
				){
					corner = true;
				}else if(roomWalls.get(k).y == levelHeight - 1 ||
						roomWalls.get(k).x == levelWidth - 1 ||
						roomWalls.get(k).y == 0 ||
						roomWalls.get(k).x == 0
				){
					corner = true;
				}else{
					roomDoors.add(roomWalls.get(k));
					roomWalls.remove(k);
				}
				
			}while(corner == true);
			
		}
		
	}
	public Room(int maxWidth, int maxHeight, Random rng){
		levelHeight = maxHeight;
		levelWidth = maxWidth;
		//h = randomBtwn(4, 7);
		//w = randomBtwn(4, 7);
		this.height = normalInRange(4, 7, rng);
		this.width = normalInRange(4, 7, rng);
		//x = randomBtwn(1, maxWidth-this.w-1);
		//y = randomBtwn(1, maxHeight-this.h-1);
		this.x = normalInRange(1, maxWidth - this.width - 1, rng);
		this.y = normalInRange(1, maxHeight - this.height - 1, rng);
	}
	
	public Room(int x, int y, int width, int height, int levelWidth, int levelHeight) {
		super(x, y, width, height);
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
	}
	public void construct(){
		assert(levelWidth != -1 && levelHeight != -1) : "cannot construct room without level";
		numDoors = randomBtwnBiased(1,3);
		setWalls();
		setDoors();
	}
	
	@Override
	public String toString() {
		return super.toString()+"[x = "+x+" y = "+y+" w = "+width+" h = "+height+"]";
	}
}