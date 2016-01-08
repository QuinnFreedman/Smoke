import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NPCCharacter extends Character{
	private Entity target = null;
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

	public void setTarget(Entity target) {
		this.target = target;
	}
	
	static enum AIMode{
		HUNT,TRAIL,FLEE,IDLE,NONE
	}
	
	NPCCharacter(Level level, Point position, Race race, String cclass) {
		super(level, position, race, cclass);
		this.inverseSpeed = 8;
		this.sprite = Main.loadImage("character_tiles/"+this.getRace()+"_"+this.getCclass());
	}
	
	public BufferedImage getSprite(int t) {
		return this.sprite;
	}
	
	@Override
	void doLogic() {
		super.doLogic();
		switch (followMode) {
		case TRAIL:
			AI.trail(target, this, 0);
			break;
		case HUNT:
			AI.trail(target, this, 0);
		case FLEE:
			AI.flee(target, this, 4);
		default:
			break;
		}
	}
	
	private static class AI {
		private static int manhatten(Point a, Point b){
			return Math.abs((a.x - b.x)) + Math.abs((a.y - b.y));
		}
		public static void flee(Entity target, NPCCharacter self, int distance) {
			Point targetLocation = target.getMapLocation();
			Point currentLocation = self.getMapLocation();
			
			if(manhatten(targetLocation, currentLocation) > distance){
				return;
			}
			
			ArrayList<Point> openList = new ArrayList<Point>();
				openList.add(new Point(currentLocation.x, currentLocation.y + 1));
				openList.add(new Point(currentLocation.x, currentLocation.y - 1));
				openList.add(new Point(currentLocation.x + 1, currentLocation.y));
				openList.add(new Point(currentLocation.x - 1, currentLocation.y));

			Point adjacentTarget = null;			
			int bestDist = 0;
			int currentDist = manhatten(targetLocation, currentLocation);
			for(Point p : openList){
				if(!Main.getLevel().collides(p)){
					int h = manhatten(p, targetLocation);
					if((adjacentTarget == null && h > currentDist) ||
							(adjacentTarget != null && 
							(h > bestDist || h >= bestDist && Math.random() < .5f) && 
							h > currentDist)
					){
						adjacentTarget = p;
						bestDist = h;
					}
				}
			}
			
			self.moveTo(adjacentTarget);
		
		}
		static void trail(Entity target, NPCCharacter self, int distance){
			if(target != null){
				Point targetLocation;
				int followDistance = distance;
				if(target instanceof Character){
					if(self.followMode == AIMode.TRAIL){
						if(((Character) target).getTrailingPoint() == null || (
								Math.abs(((Character) target).targetPosition.x - self.targetPosition.x) <= 
								Math.abs(((Character) target).getTrailingPoint().x - self.targetPosition.x) &&
								Math.abs(((Character) target).targetPosition.y - self.targetPosition.y) <= 
								Math.abs(((Character) target).getTrailingPoint().y - self.targetPosition.y)
							)){
							targetLocation = ((Character) target).targetPosition;
							followDistance = distance + 1;
						}else{
							targetLocation = ((Character) target).getTrailingPoint();
							followDistance = distance;
						}
					}else if(self.followMode == AIMode.HUNT){ //TODO this is awkward
						targetLocation = ((Character) target).previousPosition;
						followDistance = distance + 1;
					}else{
						return;
					}
				}else{
					targetLocation = ((Entity) target).position;
				}
				
				if(!self.isPathing){
					final int dx = (targetLocation.x - self.position.x)/TopDownGraphics.tileWidthHeight_Pixels;
					final int dy = (targetLocation.y - self.position.y)/TopDownGraphics.tileWidthHeight_Pixels;
					
					Point currentLocation = self.getMapLocation();
					
					Point adjacentTarget = null;
					if(Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > followDistance){
						adjacentTarget = new Point(currentLocation.x, 
								(int) (currentLocation.y + Math.signum(dy)));
					}
					
					if((Math.abs(dy) <= Math.abs(dx) && Math.abs(dx) > followDistance) ||
							(adjacentTarget != null 
								&& Main.getLevel().collides(adjacentTarget)
								&& dx != 0
							)){
						adjacentTarget = new Point(
								(int) (currentLocation.x + Math.signum(dx)), 
								currentLocation.y);
					}
					
					if(adjacentTarget != null 
								&& Main.getLevel().collides(adjacentTarget)
								&& dy != 0
							){
						adjacentTarget = new Point(currentLocation.x, 
								(int) (currentLocation.y + Math.signum(dy)));
					}
					
					if(adjacentTarget != null && !Main.getLevel().collides(adjacentTarget)){
						self.moveTo(adjacentTarget);
						//TODO REMOVE self.targetPosition = adjacentTarget.scale(TopDownGraphics.tileWidthHeight_Pixels);
					}else if(adjacentTarget != null){
						self.isPathing = true;
					}
				}
	
				if(self.isPathing){
					targetLocation = targetLocation.scale(1f/TopDownGraphics.tileWidthHeight_Pixels);
					if(self.pathingTarget == null || !self.pathingTarget.equals(targetLocation)){
						System.out.println("A* pathfinding");
						self.pathingTarget = targetLocation;
						self.path = Pathing.getPath(self.getMapLocation(), self.pathingTarget, 
								Main.getLevel().getCollsionMap());
						self.pathIndex = 0;
						System.out.println(self.path);
					}
					
					if(self.pathIndex < self.path.size() - Math.max(0, followDistance - 1)){
						self.moveTo(self.path.get(self.pathIndex));
						//self.targetPosition = self.path.get(self.pathIndex).scale(TopDownGraphics.tileWidthHeight_Pixels);
						self.pathIndex++;
					}else{
						self.isPathing = false;
					}
				}
				
			}
		}
	}

}