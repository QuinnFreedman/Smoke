package main;

import java.awt.Dimension;

public class DynamicEntity extends Entity {
	protected Point targetPosition;
	protected Point previousPosition;
	protected Point.Float actualPosition;
	private int keyframeCountdown = 0;
	protected int inverseSpeed = 6;
	private Point trailingPoint;
	private Direction moveDirection = Direction.NONE;
	private Direction facingDirection = Direction.SHOUTH;

	Point getTrailingPoint() { return trailingPoint; }
	void setTrailingPoint(Point trailingPoint) { this.trailingPoint = trailingPoint; }
	
	protected Direction getMoveDirection() { return moveDirection; }
	protected Direction getFacingDirection() { return facingDirection; }
	
	@Override
	public Point getMapLocation(){
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
			if(p.x < this.getMapLocation().x) {
				this.moveDirection = Direction.WEST;
			} else if(p.x > this.getMapLocation().x) {
				this.moveDirection = Direction.EAST;
			} else if(p.y < this.getMapLocation().y) {
				this.moveDirection = Direction.NORTH;
			} else if(p.y > this.getMapLocation().y) {
				this.moveDirection = Direction.SHOUTH;
			} else {
				this.moveDirection = Direction.NONE;
			}
			
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
		if(moveDirection != Direction.NONE) {
			this.facingDirection = this.moveDirection;
		}
		this.moveDirection = Direction.NONE;
		if(!previousPosition.equals(targetPosition) || trailingPoint == null){
			this.trailingPoint = new Point(previousPosition);
			this.previousPosition = new Point(this.targetPosition);
			this.actualPosition = new Point.Float(this.targetPosition);
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
	
	
	protected static enum Direction{
		NORTH,SHOUTH,EAST,WEST,NONE
	}
}