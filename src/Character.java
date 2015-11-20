public class Character extends Entity {
	Point targetPosition;
	Point previousPosition;
	FloatPoint actualPosition;
	int speed = 5; //pixels/keyframe
	private Race race;
	private String cclass;

	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	
	//read user input or do AI logic
	void doLogic(){
		this.previousPosition = new Point(this.targetPosition);
		this.actualPosition = new FloatPoint(this.targetPosition);
	}
	
	//move position toward target
	void simulate(){
		final float dx = (this.targetPosition.x - this.previousPosition.x)/Main.KEYFRAME_INTERVAL;
		final float dy = (this.targetPosition.y - this.previousPosition.y)/Main.KEYFRAME_INTERVAL;
		
		this.actualPosition.x += dx;
		this.actualPosition.y += dy;

		this.position.x = Math.round(this.actualPosition.x);
		this.position.y = Math.round(this.actualPosition.y);
	}
	
	Character(Point position, Race race, String cclass){
		this.position = new Point(position);
		this.targetPosition = new Point(position);
		this.race = race;
		this.cclass = cclass;
	}
	
	private static enum Direction{
		NORTH,SOUTH,EAST,WEST
	}
	static enum Race{
		HUMAN,ELF,ORC
	}
}