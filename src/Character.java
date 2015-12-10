import java.awt.Dimension;

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
	
	@Override
	Point getMapLocation(){
		return this.targetPosition == null ? 
				this.position.scale(1f/TopDownGraphics.tileWidthHeight_Pixels) :
				this.targetPosition.scale(1f/TopDownGraphics.tileWidthHeight_Pixels);
	}
	
	@Override
	public Dimension getSize(){
		//TODO calculate 2x1 / 1x2, 1x1 based on moving direction
		return new Dimension(1, 1);
	}
	
	void moveTo(Point p){
		if(p != null){
			this.getChunk().modifyChunkCollider(this, -1);
			this.targetPosition = p.scale(TopDownGraphics.tileWidthHeight_Pixels);
			if(p.x < this.getChunk().getPosition().x ||
					p.y < this.getChunk().getPosition().y ||
					p.x >= this.getChunk().getPosition().x + this.getChunk().getSize().width ||
					p.y >= this.getChunk().getPosition().y + this.getChunk().getSize().height
					){
				//TODO fix indexOutOfBoundsException
				this.moveChunk(Main.getLevel().getChunk(p));
			}
			this.getChunk().modifyChunkCollider(this, 1);
		}
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
	
	Character(Level level, Point mapPosition, Race race, String cclass){
		super(level, mapPosition, new Dimension(1,1));
		this.targetPosition = mapPosition.scale(TopDownGraphics.tileWidthHeight_Pixels);
		this.previousPosition = new Point(targetPosition);
		this.race = race;
		this.cclass = cclass;
	}

	
	static enum Race{
		HUMAN,ELF,ORC
	}
}