package proceduralGeneration;

import java.util.ArrayList;
import main.Level;
import main.Point;

public class Room
{
	public int h;
	public int w;
	public int xpos;
	public int ypos;
	public int doors;
	ArrayList<Point> roomWalls;
	ArrayList<Point> roomDoors;
	private Level level;
	
	private static int randomBtwnBiased(int min, int max)
	{
		return (int) (/*Main.generator.nextDouble()*/ Math.random() * (max - min + 1)) + min;
	}
	public static int randomBtwn(int min, int max)
	{
		//inclusive
		return (int) Math.floor((/*Main.generator.nextDouble()*/ Math.random() * (max - min + 1)) + min);
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
		for(int e=1; e<=doors; e++){
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
				}else if(roomWalls.get(k).y == level.getSize().height - 1 ||
						roomWalls.get(k).x == level.getSize().width - 1 ||
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
	Room(Level level){
		h = randomBtwn(4, 7);
		w = randomBtwn(4, 7);
		xpos = randomBtwn(0, level.getSize().width-this.w);
		ypos = randomBtwn(0, level.getSize().height-this.h);
		this.level = level;
		//construct();
	}
	Room(int h, int w, int x, int y){
		this.h = h;
		this.w = w;
		this.xpos = x;
		this.ypos = y;
		//construct();
	}
	public void construct(){
		if(this.level == null)
			return;
		doors = randomBtwnBiased(1,3);
		setWalls();
		setDoors();
	}
	
	@Override
	public String toString() {
		return super.toString()+"[x = "+xpos+" y = "+ypos+" w = "+w+" h = "+h+"]";
	}
}