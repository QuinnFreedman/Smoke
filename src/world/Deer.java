package world;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.DynamicEntity;
import main.Entity;
import main.Level;
import main.Main;
import main.Point;
import main.TopDownGraphics;

public class Deer extends DynamicEntity {

	private int waiting = 0;
	//private DynamicEntity.AnimationSet sprite;
	private BufferedImage sprite;
	
	public Deer(Level level, Point mapPosition) {
		super(level, mapPosition);
		this.animFrames = 8;
		//this.sprite = new DynamicEntity.AnimationSet("animals", "deer", this.animFrames);
		this.sprite = Main.loadImage("dynamic_entities/animals/wolf/Wolf");
		this.inverseSpeed = 10;
	}
	
	
	
	@Override
	protected void doLogic() {
		super.doLogic();
		if(waiting == 0) {
			waiting = 10;
			flee(Main.getPlayer(), 5);
		} else {
			waiting--;
		}
		
	}
	
	@Override
	protected BufferedImage getSprite(int t) {
		return this.sprite;
		//return sprite.get(DynamicEntity.Direction.NORTH).get(0);
	}
	
	private static int manhatten(Point a, Point b){
		return Math.abs((a.x - b.x)) + Math.abs((a.y - b.y));
	}
	private void flee(Entity target, int distance) {
		Point targetLocation = target.getMapLocation();
		Point currentLocation = this.getMapLocation();
		
		if(manhatten(targetLocation, currentLocation) > distance){
			return;
		}
		
		ArrayList<Point> openList = new ArrayList<Point>();
			openList.add(new Point(currentLocation.x, currentLocation.y + 2));
			openList.add(new Point(currentLocation.x, currentLocation.y - 2));
			openList.add(new Point(currentLocation.x + 2, currentLocation.y));
			openList.add(new Point(currentLocation.x - 2, currentLocation.y));

		Point adjacentTarget = null;			
		int bestDist = 0;
		int currentDist = manhatten(targetLocation, currentLocation);
		for(Point p : openList){
			if(!Main.getLevel().collides(p)){
				int h = manhatten(p, targetLocation);
				if((adjacentTarget == null && h > currentDist) ||
						(adjacentTarget != null && 
						(h > bestDist || h >= bestDist && Math.random() < .5f) && h > currentDist)
				){
					adjacentTarget = p;
					bestDist = h;
				}
			}
		}
		
		this.moveTo(adjacentTarget);
	
	}
	
	@Override
	protected void simulate(){
		super.simulate();
		this.position.y = Math.round(this.actualPosition.y - quadInterpolate(Math.max(
					Math.abs(this.previousPosition.x - this.actualPosition.x), 
					Math.abs(this.previousPosition.y - this.actualPosition.y)), 
				15, 2 * TopDownGraphics.tileWidthHeight_Pixels));
	}
	
	private static float quadInterpolate(float x, float maxHeight, float distance) {
		assert(distance != 0);
		float a = -(4 * maxHeight)/(distance*distance);
		float b = (4 * maxHeight)/distance;
		return a*x*x + b*x;
	}
	
}