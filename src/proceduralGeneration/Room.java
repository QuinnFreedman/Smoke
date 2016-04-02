package proceduralGeneration;


import java.util.ArrayList;
import java.util.Random;

import debug.out;
import main.Point;

public class Room
{
	public int h;
	public int w;
	public int xpos;
	public int ypos;
	public int numDoors;
	public ArrayList<Point> roomWalls;
	public ArrayList<Point> roomDoors;
	private int levelWidth;
	private int levelHeight;
	
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
		
		for(int i = 0; i<this.w; i++){
			roomWalls.add(new Point(this.xpos+i, this.ypos));
			roomWalls.add(new Point(this.xpos+i, this.ypos+this.h-1));
		}
		for(int i = 2; i<this.h; i++){
			roomWalls.add(new Point(this.xpos, this.ypos+i-1));
			roomWalls.add(new Point(this.xpos+this.w - 1, this.ypos+i-1));
		}
	}
	private void setDoors(){
		boolean corner;
		for(int e = 1; e <= numDoors; e++){
			do{	
				corner = false;			
				int k = randomBtwn(0, roomWalls.size()-1);
				
				//check for corners
				if((roomWalls.get(k).y == this.h + this.ypos - 1 && roomWalls.get(k).x == this.w + this.xpos - 1) || 
					(roomWalls.get(k).y == this.ypos && roomWalls.get(k).x == this.w + this.xpos - 1) ||
					(roomWalls.get(k).y == this.ypos && roomWalls.get(k).x == this.xpos) ||
					(roomWalls.get(k).y == this.h + this.ypos - 1 && roomWalls.get(k).x == this.xpos)
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
		h = normalInRange(4, 7, rng);
		w = normalInRange(4, 7, rng);
		//xpos = randomBtwn(1, maxWidth-this.w-1);
		//ypos = randomBtwn(1, maxHeight-this.h-1);
		xpos = normalInRange(1, maxWidth-this.w-1, rng);
		ypos = normalInRange(1, maxHeight-this.h-1, rng);
	}
	
	public void construct(){
		numDoors = randomBtwnBiased(1,3);
		setWalls();
		setDoors();
	}
	
	@Override
	public String toString() {
		return super.toString()+"[x = "+xpos+" y = "+ypos+" w = "+w+" h = "+h+"]";
	}
}