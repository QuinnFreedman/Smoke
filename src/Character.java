public class Character extends Entity {
	Point targetPosition;
	Point previousPosition;
	FloatPoint actualPosition;
	int speed = 5; //pixels/keyframe
	private Race race;
	private String cclass;
	private int keyframeCountdown = 0;
	private static final int KEYFRAME_INTERVAL = 6;

	Race getRace(){ return this.race; }
	String getCclass(){ return this.cclass; }
	
	//read user input or do AI logic
	void doLogic(){
		this.previousPosition = new Point(this.targetPosition);
		this.actualPosition = new FloatPoint(this.targetPosition);
	}
	
	//move position toward target
	void simulate(){
		if(keyframeCountdown == 0){
			keyframeCountdown = KEYFRAME_INTERVAL - 1;
			doLogic();
		}else{
			keyframeCountdown--;
		}
		
		final float dx = (this.targetPosition.x - this.previousPosition.x)/KEYFRAME_INTERVAL;
		final float dy = (this.targetPosition.y - this.previousPosition.y)/KEYFRAME_INTERVAL;
		
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