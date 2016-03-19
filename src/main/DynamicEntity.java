package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DynamicEntity extends Entity {
	Point targetPosition;
	Point previousPosition;
	FloatPoint actualPosition;
	private int keyframeCountdown = 0;
	protected int inverseSpeed = 6;
	private Point trailingPoint;

	Point getTrailingPoint() { return trailingPoint; }
	void setTrailingPoint(Point trailingPoint) { this.trailingPoint = trailingPoint; }
	
	@Override
	protected Point getMapLocation(){
		return this.targetPosition == null ? 
				this.position.scale(1f/TopDownGraphics.tileWidthHeight_Pixels) :
				this.targetPosition.scale(1f/TopDownGraphics.tileWidthHeight_Pixels);
	}
	
	@Override
	protected Dimension getSize(){
		//TODO calculate 2x1 / 1x2, 1x1 based on moving direction
		return new Dimension(1, 1);
	}
	
	protected void moveTo(Point p){
		if(p != null){
			this.getChunk().modifyChunkCollider(this, -1);
			this.targetPosition = p.scale(TopDownGraphics.tileWidthHeight_Pixels);
			if(p.x < this.getChunk().getPosition().x ||
					p.y < this.getChunk().getPosition().y ||
					p.x >= this.getChunk().getPosition().x + this.getChunk().getSize().width ||
					p.y >= this.getChunk().getPosition().y + this.getChunk().getSize().height
					){
				Chunk targetChunk = Main.getLevel().getChunk(p);
				if(targetChunk != null) {
					this.moveChunk(targetChunk);
				}
			}
			this.getChunk().modifyChunkCollider(this, 1);
		}
	}
	
	//read user input or do AI logic
	protected void doLogic(){
		if(!previousPosition.equals(targetPosition) || trailingPoint == null){
			this.trailingPoint = new Point(previousPosition);
			this.previousPosition = new Point(this.targetPosition);
			this.actualPosition = new FloatPoint(this.targetPosition);
		}
	}
	
	//move position toward target
	protected void simulate(){
		if(keyframeCountdown == 0){
			keyframeCountdown = inverseSpeed - 1;
			doLogic();
		}else{
			keyframeCountdown--;
		}
		final float dx = (this.targetPosition.x - this.previousPosition.x)/inverseSpeed;
		final float dy = (this.targetPosition.y - this.previousPosition.y)/inverseSpeed;
		
		this.actualPosition.x += dx;
		this.actualPosition.y += dy;

		this.position.x = Math.round(this.actualPosition.x);
		this.position.y = Math.round(this.actualPosition.y);
		
		if(this.position.equals(this.targetPosition)) {
			this.keyframeCountdown = 0;
		}
	}
	
	protected DynamicEntity(Level level, Point mapPosition){
		super(level, mapPosition, new Dimension(1,1));
		this.targetPosition = mapPosition.scale(TopDownGraphics.tileWidthHeight_Pixels);
		this.previousPosition = new Point(targetPosition);
	}
	
	protected class AnimationSet {
		private ArrayList<BufferedImage> north;
		private ArrayList<BufferedImage> south;
		private ArrayList<BufferedImage> east;
		private ArrayList<BufferedImage> west;
		private ArrayList<BufferedImage> idle;
		
		private ArrayList<BufferedImage> getByName(String s){
			switch(s){
			case "up":
				return this.north;
			case "down":
				return this.south;
			case "left":
				return this.west;
			case "right":
				return this.east;
			default:
				return null;
			}
		}

		void setWest(ArrayList<BufferedImage> img) {
			this.west = img;
		}
		void setEast(ArrayList<BufferedImage> img) {
			this.east = img;
		}
		void setNorth(ArrayList<BufferedImage> img) {
			this.north = img;
		}
		void setSouth(ArrayList<BufferedImage> img) {
			this.south = img;
		}
		void setIdle(ArrayList<BufferedImage> img) {
			this.south = img;
		}
		
		ArrayList<BufferedImage> get(Direction direction) {
			switch(direction){
			case EAST:
				return east;
			case NONE:
				return idle;
			case NORTH:
				return north;
			case SHOUTH:
				return south;
			case WEST:
				return west;
			default:
				return null;
			
			}
		}
		
		AnimationSet() {
			north = new ArrayList<BufferedImage>();
			south = new ArrayList<BufferedImage>();
			east = new ArrayList<BufferedImage>();
			west = new ArrayList<BufferedImage>();
			idle = new ArrayList<BufferedImage>();
		}
		
		AnimationSet(final String name, final int numFrames) {
			this();
			
			final String[] directions = {"up", "down", "left", "right"};
			String file = "character_tiles/"+name+"/";
			for(String s : directions) {
				String dir = file + name + "_" + s + "/" + name + "_" + s + "_";
				ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
				for(int i = 0; i < numFrames; i++) {
					frames.add(Main.loadImage(dir + i));
				}
				this.getByName(s).addAll(frames);
			}
			
		}
	}
	protected static enum Direction{
		NORTH,SHOUTH,EAST,WEST,NONE
	}
}