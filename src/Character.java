public class Character extends Entity {
	Point targetPosition;
	Point previousPosition;
	FloatPoint actualPosition;
	private Race race;
	private String cclass;
	private int keyframeCountdown = 0;
	protected int inverseSpeed = 6;
	private Point trailingPoint;

	public Point getTrailingPoint() { return trailingPoint; }
	public void setTrailingPoint(Point trailingPoint) { this.trailingPoint = trailingPoint; }
	
	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	Point getMapLocation(){
		return new Point(this.targetPosition.x/TopDownGraphics.tileWidthHeight_Pixels,
						 this.targetPosition.y/TopDownGraphics.tileWidthHeight_Pixels);
	}
	
	//read user input or do AI logic
	void doLogic(){
		if(!previousPosition.equals(targetPosition) || trailingPoint == null){
			this.trailingPoint = new Point(previousPosition);
			this.previousPosition = new Point(this.targetPosition);
		}
		this.actualPosition = new FloatPoint(this.targetPosition);
	}
	
	//move position toward target
	void simulate(){
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
	}
	
	Character(Point position, Race race, String cclass){
		this.position = new Point(position);
		this.targetPosition = new Point(position);
		this.previousPosition = new Point(position);
		this.race = race;
		this.cclass = cclass;
	}

	
	static enum Race{
		HUMAN,ELF,ORC
	}
}