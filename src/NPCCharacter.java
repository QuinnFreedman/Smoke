import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPCCharacter extends Character{
	private Object target = null;
	private int disposition;
	private int alertness;
	private boolean isPathing = false;
	private BufferedImage sprite;
	
	private ArrayList<Point> path;
	private Point pathingTarget;
	private int pathIndex;
	private AIMode followMode;

	public void setFollowMode(AIMode followMode) {
		this.followMode = followMode;
	}

	NPCCharacter(Point position, Race race, String cclass) {
		super(position, race, cclass);
		this.inverseSpeed = 8;
		this.sprite = Main.loadImage("character_tiles/"+this.getRace()+"_"+this.getCclass());
	}
	
	public BufferedImage getSprite(int t) {
		return this.sprite;
	};
	
	@Override
	void doLogic() {
		super.doLogic();
		if(target != null && target instanceof Entity){
			Point targetLocation;
			int followDistance = 0;
			if(target instanceof Character){
				if(this.followMode == AIMode.TRAIL){
					if(
							Math.abs(((Character) target).targetPosition.x - this.targetPosition.x) <= 
							Math.abs(((Character) target).getTrailingPoint().x - this.targetPosition.x) &&
							Math.abs(((Character) target).targetPosition.y - this.targetPosition.y) <= 
							Math.abs(((Character) target).getTrailingPoint().y - this.targetPosition.y)
						){
						targetLocation = ((Character) target).targetPosition;
						followDistance = 1;
					}else{
						targetLocation = ((Character) target).getTrailingPoint();
						followDistance = 0;
					}
				}else if(this.followMode == AIMode.HUNT){
					targetLocation = ((Character) target).previousPosition;
					followDistance = 1;
				}else{
					//TODO
					return;
				}
			}else{
				targetLocation = ((Entity) target).position;
			}
			
			if(!isPathing){
				final int dx = (targetLocation.x - this.position.x)/TopDownGraphics.tileWidthHeight_Pixels;
				final int dy = (targetLocation.y - this.position.y)/TopDownGraphics.tileWidthHeight_Pixels;
				
				Point currentLocation = this.getMapLocation();
				
				Point adjacentTarget = null;
				if(Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > followDistance){
					adjacentTarget = new Point(currentLocation.x, 
							(int) (currentLocation.y + Math.signum(dy)));
				}
				
				if((Math.abs(dy) <= Math.abs(dx) && Math.abs(dx) > followDistance) ||
						(adjacentTarget != null 
							&& Main.getLevel().getCollsionMap()[adjacentTarget.y][adjacentTarget.x]
							&& dx != 0
						)){
					adjacentTarget = new Point(
							(int) (currentLocation.x + Math.signum(dx)), 
							currentLocation.y);
				}
				
				if(adjacentTarget != null 
							&& Main.getLevel().getCollsionMap()[adjacentTarget.y][adjacentTarget.x]
							&& dy != 0
						){
					adjacentTarget = new Point(currentLocation.x, 
							(int) (currentLocation.y + Math.signum(dy)));
				}
				
				if(adjacentTarget != null &&
						!Main.getLevel().getCollsionMap()[adjacentTarget.y][adjacentTarget.x]){
					this.targetPosition = new Point(
							adjacentTarget.x * TopDownGraphics.tileWidthHeight_Pixels,
							adjacentTarget.y * TopDownGraphics.tileWidthHeight_Pixels);
				}else if(adjacentTarget != null){
					this.isPathing = true;
				}
			}

			if(isPathing){
				targetLocation = new Point(
						targetLocation.x / TopDownGraphics.tileWidthHeight_Pixels,
						targetLocation.y / TopDownGraphics.tileWidthHeight_Pixels);
				if(pathingTarget == null || !pathingTarget.equals(targetLocation)){
					pathingTarget = targetLocation;
					path = Pathing.getPath(this.getMapLocation(), pathingTarget, 
							Main.getLevel().getCollsionMap());
					pathIndex = 0;
				}
				if(pathIndex < path.size() - Math.max(0, followDistance - 1)){
					this.targetPosition = new Point(
							path.get(pathIndex).x * TopDownGraphics.tileWidthHeight_Pixels,
							path.get(pathIndex).y * TopDownGraphics.tileWidthHeight_Pixels);
					pathIndex++;
				}else{
					this.isPathing = false;
				}
			}
			
		}
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	static enum AIMode{
		HUNT,TRAIL,FLEE,IDLE,NONE
	}
}